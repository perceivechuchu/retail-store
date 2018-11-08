package ca.company.store.retail.api;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import ca.company.store.retail.api.response.ApiResponse;
import ca.company.store.retail.api.ui.pojos.ProductInfo;
import ca.company.store.retail.model.Product;

/**   
* @author  Perceive Chuchu
*/

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTests {
	
	@LocalServerPort
	private int port;
	
    @Autowired
	private TestRestTemplate testRestTemplate;
	
	private SoftAssertions softAssertions;
	
	Product product;
	
	private String urlRoot = "http://localhost:";
	
	private String baseUrlEndpoint = "/store/retail/api/v1/products";
	
	@Before
	public void setup() {
		softAssertions = new SoftAssertions();

		product = new Product();
		product.setId(100L);
		product.setName("Cooking Oil");
		product.setBarCode("263553426");
		product.setCategory("GROCERY");
		product.setPrice(95.34);
		product.setDateCreated(LocalDateTime.now());
	}
	
	@Test
	public void listProductsShouldReturnOkWithListOfProducts() {
		ResponseEntity<ApiResponse<List<ProductInfo>>> response = testRestTemplate.exchange(
				createURLWithPort(baseUrlEndpoint),
				HttpMethod.GET, HttpEntity.EMPTY, 
				new ParameterizedTypeReference<ApiResponse<List<ProductInfo>>>() {
				});
		ApiResponse<List<ProductInfo>> apiResponse = response.getBody();

		softAssertions.assertThat(apiResponse.getStatusCode()).isEqualTo(HttpStatus.OK.toString());
		softAssertions.assertThat(apiResponse.getResponseBody()).isNotNull();
		softAssertions.assertThat(apiResponse.getResponseBody()).isNotEmpty();
		softAssertions.assertAll();
	}
	
	@Test
	public void createProductShouldCreateNewProductAndReturnSuccess() throws Exception {
		ProductInfo toCreate = new ProductInfo(product);
		HttpEntity<ProductInfo> request = new HttpEntity<>(toCreate);

		ResponseEntity<ApiResponse<ProductInfo>> httpResponse = testRestTemplate.exchange(
				createURLWithPort(baseUrlEndpoint), HttpMethod.POST, request,
				new ParameterizedTypeReference<ApiResponse<ProductInfo>>() {
				});
		ApiResponse<ProductInfo> apiResponse = httpResponse.getBody();
		softAssertions.assertThat(apiResponse.getStatusCode()).isEqualTo(HttpStatus.OK.toString());
		softAssertions.assertThat(apiResponse.getMessage()).isEqualTo("Product created successfully");
		softAssertions.assertThat(apiResponse.getResponseBody()).isNotNull();
		softAssertions.assertThat(apiResponse.getResponseBody().getId()).isGreaterThan(0L);
		softAssertions.assertThat(apiResponse.getResponseBody().getName()).isEqualTo(toCreate.getName());
		softAssertions.assertThat(apiResponse.getResponseBody().getBarCode()).isEqualTo(toCreate.getBarCode());
		softAssertions.assertThat(apiResponse.getResponseBody().getCategory()).isEqualTo(toCreate.getCategory());
		softAssertions.assertAll();
	}
	
	@Test
	public void findProductShouldReturnProductWithSuccess() throws Exception {
		ResponseEntity<ApiResponse<ProductInfo>> response = testRestTemplate.exchange(
				createURLWithPort(baseUrlEndpoint + "/1"),
				HttpMethod.GET, HttpEntity.EMPTY, 
				new ParameterizedTypeReference<ApiResponse<ProductInfo>>() {
				});
		ApiResponse<ProductInfo> apiResponse = response.getBody();
		softAssertions.assertThat(apiResponse.getStatusCode()).isEqualTo(HttpStatus.OK.toString());
		softAssertions.assertThat(apiResponse.getMessage()).isEqualTo("Product retrieved successfully");
		softAssertions.assertThat(apiResponse.getResponseBody()).isNotNull();
		softAssertions.assertThat(apiResponse.getResponseBody().getId()).isEqualTo(1L);
		softAssertions.assertAll();
	}
	
	private String createURLWithPort(String uri) {
		return urlRoot + port + uri;
	}
}
