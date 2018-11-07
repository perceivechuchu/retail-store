package ca.company.store.retail.model.commons.builders;

import java.time.LocalDateTime;

import ca.company.store.retail.model.Product;

/**   
* @author  Perceive Chuchu
*/

public class ProductBuilder {
	
	private Product product;
	
	private ProductBuilder() {
		this.product = new Product();
		this.product.setDateCreated(LocalDateTime.now());
	}

	public ProductBuilder(Product product) {
		this.product = product;
	}

	public static ProductBuilder createProduct() {
		return new ProductBuilder();
	}
	
	public static ProductBuilder updateProduct(Product product) {
		return new ProductBuilder(product);
	}
	
	public ProductBuilder withName(final String name) {
		product.setName(name);
		return this;
	}	
	
	public ProductBuilder withBarCode(final String barCode) {
		product.setBarCode(barCode);
		return this;
	}
	
	public ProductBuilder withCategory(final String category) {
		product.setCategory(category);
		return this;
	}

	
	public ProductBuilder withPrice(final double price) {
		product.setPrice(price);
		return this;
	}

	public Product build() {
		return this.product;
	}
	
	
}
