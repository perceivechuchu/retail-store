package ca.company.store.retail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.company.store.retail.model.Product;

/**   
* @author  Perceive Chuchu
*/

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	public Product findByBarCode(String barCode);
}
