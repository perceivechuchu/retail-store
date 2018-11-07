package ca.company.store.retail.api.response;

/**   
* @author  Perceive Chuchu
*/ 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
	
	@Getter @Setter
	private T responseBody;
	@Getter @Setter
	private String statusCode;
	@Getter @Setter
	private String message;
	
}
