package ca.company.store.retail.commons.exceptions;

/**   
* @author  Perceive Chuchu
*/

public class DuplicateRecordException extends RuntimeException {

	private static final long serialVersionUID = 4899295034943393L;

	public DuplicateRecordException(String message) {
		super(message);
	}

}
