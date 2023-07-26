package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class PetStore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;

	// many-to-many relationship with Customer
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST) // Using Persist instead of All because I don't want to delete customers
												// just because they shop at a different pet store
	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();

	// one-to-many relationship with Employee
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true) // Using All with CascadeType
																						// because I want to delete
																						// employees if they left the
																						// pet store and set orphan to
																						// true
	private Set<Employee> employees = new HashSet<>();

	/*
	 * pet store can be associated with many employees. pet store can be associated
	 * with many customers and customer can be associated with many pet store
	 */

	/*
	 * The mappedBy attribute in the @OneToMany annotation specifies that the
	 * employees field in the Employee entity is the owning side of the
	 * relationship. which means that the Employee entity holds the actual foreign
	 * key (pet_store_id) that links each employee to the corresponding petStore.
	 */

	/*
	 * @JoinTable annotation is used to define the association table
	 * "pet_store_customer" for a many-to-many relationship between two
	 * entities(pet_store and customer) in JPA. we can say , that by using this
	 * annotation we are instructing JPA to create the necessary association table
	 * to represent the many-to-many relationship between PetStore and Customer
	 * 
	 * The @JoinColumn annotation specifies the foreign key column as(pet_store_id)
	 * in the Employee table that references the primary key (petStoreId) in the
	 * PetStore table.
	 */

}
