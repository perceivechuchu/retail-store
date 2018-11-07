package ca.company.store.retail.api.ui.pojos;

import ca.company.store.retail.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**   
* @author  Perceive Chuchu
*/

@Data
@ToString
@NoArgsConstructor
public class ProductInfo {
	
	private Long id;
	
    private String name;
	
	private String barCode;
	
	private String category;
	
	private double price; 
	
	public ProductInfo(Product product) {
		this.setId(product.getId());
		this.setName(product.getName());
		this.setBarCode((product.getBarCode()));
		this.category = product.getCategory() == null ? null : product.getCategory();
		this.price = product.getPrice();
	}
}
