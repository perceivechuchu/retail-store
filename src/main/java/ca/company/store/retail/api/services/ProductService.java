package ca.company.store.retail.api.services;

/**   
* @author  Perceive Chuchu
*/ 

import java.util.List;

import ca.company.store.retail.api.ui.pojos.ProductInfo;
import ca.company.store.retail.model.Product;

public interface ProductService {
	
	public List<Product> listProducts();

	public Product createProduct(Product product);
	
	public Product updateProduct(Long id, ProductInfo productInfo);
	
	public Product findProduct(Long id);
	
	public Product deleteProduct(Long id);
}
