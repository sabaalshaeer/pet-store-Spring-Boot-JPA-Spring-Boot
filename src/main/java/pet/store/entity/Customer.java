package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity //tell Spring this class is going to be a table
@Data  //that is going to create getter and setter
public class Customer {
	
	@Id //going to tell spring that is our primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)// auto-increment
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	@Column(unique = true)
	private String customerEmail;
	
	//many-to-many relationship
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
	private Set<PetStore> petStores = new HashSet<>();
}
