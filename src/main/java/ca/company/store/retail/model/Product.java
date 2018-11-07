package ca.company.store.retail.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**   
* @author  Perceive Chuchu
*/

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
	
	private static final long serialVersionUID = -4263073857174454591L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String barCode;
	
	private String category;
	
	private double price; 
	
	private LocalDateTime dateCreated;
}
