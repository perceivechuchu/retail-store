package ca.company.store.retail.commons.exceptions;

/**   
* @author  Perceive Chuchu
*/

public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -395566558435201809L;
	
	public RecordNotFoundException(String message) {
		super(message);
	}

}
