package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // This annotation to mark class as a JPA entity, which means it will be mapped
		// to a database table.that means the name of table will be Employee by default
@Data // generates standard boilerplate code for getters, setters, toString(),
		// equals(), and hashCode() methods.
public class Employee {

	@Id // field is the primary key of the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // this line specifies that the employeeId will be generated
														// using an identity column(auto-increment) in the database.
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;

	//FK refers to PetStpre table
	// many-to-one relationShip with PetStore (owned class)
	@EqualsAndHashCode.Exclude // these annotations will exclude these specific fields from generating the
								// equals(), hashCode(), and toString() methods that Lombok automatically
								// generates for a class.
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_store_id", nullable = false)
	private PetStore petStore;

	/*
	 * Many employees can be associated with one pet store.
	 */
}
