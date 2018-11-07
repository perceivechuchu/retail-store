package ca.company.store.retail.api;

/**   
* @author  Perceive Chuchu
*/ 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.company.store.retail.api.response.ApiResponse;
import ca.company.store.retail.api.services.ProductService;
import ca.company.store.retail.api.ui.pojos.ProductInfo;
import ca.company.store.retail.model.Product;
import ca.company.store.retail.model.commons.builders.ProductBuilder;
import ca.company.store.retail.model.enums.ProductCategory;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/retail/api/v1/products")
@CrossOrigin(origins = { "*" }, allowCredentials = "false")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	public ApiResponse<List<ProductInfo>> listProducts(){
		log.info("listProducts");
		List<Product> products = productService.listProducts();
		if(products.isEmpty()) {
			return new ApiResponse<>(new ArrayList<>(), HttpStatus.NOT_FOUND.toString(), "No products found");
		}
		List<ProductInfo> productsInfos = products.stream()
				.map(ProductInfo::new)
				.collect(Collectors.toList());
		return new ApiResponse<>(productsInfos, HttpStatus.OK.toString(), "Products retrieved successfully");
	}
	
	@PostMapping
	public ApiResponse<ProductInfo> createProduct(@RequestBody ProductInfo productInfo){
		log.info("createProduct request:" + productInfo);
		final Product productToCreate = ProductBuilder.createProduct()
				.withName(productInfo.getName())
				.withBarCode(productInfo.getBarCode())
				.withCategory(ProductCategory.valueOf(productInfo.getCategory()) == null ? null: productInfo.getCategory())
				.withPrice(productInfo.getPrice())
				.build();
		
		Product createdProduct = productService.createProduct(productToCreate);
		
		return new ApiResponse<>(new ProductInfo(createdProduct), HttpStatus.OK.toString(), "Product created successfully");
	}
	
	@PutMapping("/{id}")
	public ApiResponse<ProductInfo> updateProduct(@PathVariable final Long id, @RequestBody ProductInfo productInfo){
		log.info("updateProduct request:" + productInfo);
		Product updatedProduct = productService.updateProduct(id, productInfo);	
		return new ApiResponse<>(new ProductInfo(updatedProduct), HttpStatus.OK.toString(), "Product created successfully");
	}
	
	@GetMapping("/{id}")
	public ApiResponse<ProductInfo> findProduct(@PathVariable final Long id){
		log.info("findProduct id:" + id);
		final Product product = productService.findProduct(id);
		return new ApiResponse<>(new ProductInfo(product), HttpStatus.OK.toString(), "Product retrieved successfully");
	}
		
	@DeleteMapping("/{id}")
	public ApiResponse<ProductInfo> deleteProduct(@PathVariable final Long id){
		log.info("deleteProduct id:" + id);
		final Product product = productService.deleteProduct(id);
		return new ApiResponse<>(new ProductInfo(product), HttpStatus.OK.toString(), "Product has been deleted successfully");
	}
}
