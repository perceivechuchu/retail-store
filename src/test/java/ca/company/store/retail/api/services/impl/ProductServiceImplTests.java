package ca.company.store.retail.api.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ca.company.store.retail.commons.exceptions.DuplicateRecordException;
import ca.company.store.retail.commons.exceptions.RecordNotFoundException;
import ca.company.store.retail.model.Product;
import ca.company.store.retail.repositories.ProductRepository;

/**   
* @author  Perceive Chuchu
*/

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTests {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	private Product product;
	
	@Before
	public void setup() {
		product = new Product();
		product.setId(200L);
		product.setName("Soap");
		product.setCategory("GROCERY");
		product.setBarCode("3473837473");
	}
	
	@Test
	public void findProductShouldReturnProductWhenProductExists() throws Exception {
		given(productRepository.getOne(anyLong())).willReturn(product);
		Product found = productService.findProduct(200L);
		assertThat(found).isNotNull();
		assertThat(found.getBarCode()).isEqualTo("3473837473");
		then(productRepository).should(times(1)).getOne(anyLong());
	}
	
	@Test(expected = DuplicateRecordException.class)
	public void shouldThrowDuplicateResourceWhenAProductExistWithTheSameBarCode() throws Exception {
		given(productRepository.findByBarCode(anyString())).willReturn(product);
		productService.createProduct(product);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void findProductShouldThrowRecordNotFoundWhenProductDoesNotExist() throws Exception {
		given(productRepository.getOne(anyLong())).willReturn(null);
		productService.findProduct(500L);
	}
	
	@Test(expected = RecordNotFoundException.class)
	public void deleteDriverShouldThrowResourceNotFoundWhenDriverDoesNotExist() throws Exception {
		given(productRepository.getOne(anyLong())).willReturn(null);
		productService.deleteProduct(product.getId());
	}
}
