package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/*The DTO(Data Transfer Object is used by Jackson to transfer the objecct from/to JSON. 
In PetStoreData , Jackson will transfer the data between the pet.store.service layer and the client in JSON format
 when the client makes a request to retrive pet store information,the pet.store.service will use the PetStoreData class to represent the data 
This DTO object will then be converted to a JSON response by Jackson, allowing the client to receive the data in a JSON format.
Similarly, when the client sends data to the pet.store.service, such as when creating or updating pet store details, the client will send 
the data in JSON format. Jackson will convert this JSON data into a PetStoreData DTO object, which the pet.store.service can then use to process the incoming data 
*/
@Data
@NoArgsConstructor
public class PetStoreData {
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	private Set<PetStoreCustomer> customers = new HashSet<>();
	private Set<PetStoreEmployee> employees = new HashSet<>();

	// constructor takes a PetStore as a parameter
	public PetStoreData(PetStore petStore) {
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreAddress();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
		for(Customer customer : petStore.getCustomers()) {
			customers.add(new PetStoreCustomer(customer));
		}
		
		for(Employee employee : petStore.getEmployees()) {
			employees.add(new PetStoreEmployee(employee));
		}

	}

	// Inner class , public static so can be used as a separate DTO
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {
		
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		private Set<String> petStores = new HashSet<>();
		
		//constructor that takes a Customer Object
		public PetStoreCustomer(Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerEmail = customer.getCustomerEmail();
			
			for(PetStore petStore : customer.getPetStores()) {
				petStores.add(petStore.getPetStoreName());
			
		}
	}
	}

	// Using inner class it maintains the relationship
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee {
		
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		private PetStoreData petStore;
		
		//Constructor that takes Employee Object
		public PetStoreEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
			
		}
	}

}
