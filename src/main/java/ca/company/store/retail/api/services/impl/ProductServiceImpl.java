package ca.company.store.retail.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.company.store.retail.api.services.ProductService;
import ca.company.store.retail.api.ui.pojos.ProductInfo;
import ca.company.store.retail.commons.exceptions.DuplicateRecordException;
import ca.company.store.retail.commons.exceptions.RecordNotFoundException;
import ca.company.store.retail.model.Product;
import ca.company.store.retail.model.commons.builders.ProductBuilder;
import ca.company.store.retail.model.enums.ProductCategory;
import ca.company.store.retail.repositories.ProductRepository;

/**   
* @author  Perceive Chuchu
*/

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> listProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(Product product) {
		Product existing = productRepository.findByBarCode(product.getBarCode());
		if (existing != null) {
			throw new DuplicateRecordException(
					"Another product with bar code " + product.getBarCode() + " already exists");
		}
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, ProductInfo productInfo) {
		final Product product = productRepository.getOne(id);
		if (product == null) {
			throw new RecordNotFoundException("Product " + id + " could not be found.");
		}
		final Product productToUpdate = ProductBuilder.updateProduct(product).withName(productInfo.getName())
				.withBarCode(productInfo.getBarCode())
				.withCategory(
						ProductCategory.valueOf(productInfo.getCategory()) == null ? null : productInfo.getCategory())
				.withPrice(productInfo.getPrice()).build();
		return productRepository.save(productToUpdate);
	}

	@Override
	public Product findProduct(Long id) {
		final Product product = productRepository.getOne(id);
		if (product == null) {
			throw new RecordNotFoundException("Product " + id + " could not be found.");
		}
		return product;
	}

	@Override
	public Product deleteProduct(Long id) {
		final Product product = productRepository.getOne(id);
		if (product == null) {
			throw new RecordNotFoundException("Product " + id + " could not be found.");
		}
		productRepository.delete(product);
		return product;
	}

}
