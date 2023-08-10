package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	/*
	 * Spring can automatically inject the PetStoreDao object into the petStoreDao variable. The @Autowired annotation tells Spring to do this.
	 *  When the bean is created, Spring will look for an instance of the PetStoreDao interface and inject it into the petStoreDao variable.
	 */
	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		//check if the petStore info which pass in the method exists by checking the Id
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		//call copyPetStoreFields
		copyPetStoreFields(petStore, petStoreData);
		
		//save the updated or new created pet store and return corresponding PetStoreData Object
		return new PetStoreData(petStoreDao.save(petStore));
	}

	// this method will copy the data from the input (petStoreData) to the pet store object (petStore)
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}

	// This method finds an existing pet store in the database based on the provided pet store ID,
	// or it creates a new pet store if the ID is null.
	private PetStore findOrCreatePetStore(Long petStoreId) {
		
		// If the petStoreId is null, create a new PetStore entity object
		if(Objects.isNull(petStoreId)) {
			return new PetStore();
			}else {
			// If the petStoreId is not null, find the PetStore entity object by the ID
			return findPetStoreById(petStoreId);
		}
		
	}

	// This method finds an existing pet store in the database based on the provided pet store ID.
	// If the pet store with the given ID is not found, it throws a NoSuchElementException.
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet Store with Id=" + petStoreId+ " is not exist."));
	}
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
	    PetStore petStore = findPetStoreById(petStoreId); //fetch the pet store object using the passing Id
	    Long employeeId = petStoreEmployee.getEmployeeId(); 
	    Employee employee = findOrCreateEmployee(employeeId, petStoreId); // get employee object associate with that specific employee Id and belong to that pet Store with that pet store Id

	    copyEmployeeFields(employee, petStoreEmployee); //set the fields from petStoreEmployee to the existing employee

	    employee.setPetStore(petStore); //set the pet store object for the employee
	    petStore.getEmployees().add(employee); //add the employee to the list of employees associate with the pet store

	    return new PetStoreEmployee(employeeDao.save(employee)); //save modified or new employee object to the database using employeeDao.save() and return new PetStoreEmployee Object created from the saved employee
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
	    Employee employee = employeeDao.findById(employeeId)
	            .orElseThrow(() -> new NoSuchElementException("Employee with ID " + employeeId + " was not found"));

	    if (employee.getPetStore().getPetStoreId().equals(petStoreId)) {
	        return employee;
	    } else {
	        throw new IllegalArgumentException("Employee with ID " + employeeId + " is not associated with the PetStore with ID " + petStoreId);
	    }
	}
	
	//retrieve an existing employee or to create a new one.
	private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
	    if (employeeId == null) {
	        return new Employee();
	    } else {
	        return findEmployeeById(petStoreId, employeeId);
	    }
	}

	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
	    employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
	    employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
	    employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	    employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}

	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
	    PetStore petStore = findPetStoreById(petStoreId); //fetch the pet store object using the passing Id
	    Long customerId = petStoreCustomer.getCustomerId();//fetch customerId 
	    Customer customer = findOrCreateCustomer(petStoreId, customerId); // get customer object associate with that specific customerId Id and belong to that pet Store with that pet store Id

	    copyCustomerFields(customer, petStoreCustomer); //set the fields from petStoreCustomer to the existing customer

	    // Add the pet store to the customer's associated pet stores
	    customer.getPetStores().add(petStore);
	    petStore.getCustomers().add(customer);

	    return new PetStoreCustomer(customerDao.save(customer)); //save modified or new customer object to the database using customerDao.save and return new PetStoreCustomer Object created from the saved customer
	}
	
	private Customer findCustomerById(Long petStoreId, Long customerId) {
	    Customer customer = customerDao.findById(customerId)
	            .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " was not found"));

	    //to loop through the list of PetStore objects looking for the pet store with the given pet store ID
	    for (PetStore petStore : customer.getPetStores()) {
            if (petStore.getPetStoreId().equals(petStoreId)) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " is already associated with the PetStore with ID " + petStoreId);
            }
        }
	    return customer;
		
	}
	
	//retrieve an existing customer or to create a new one.
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	    if (customerId == null) {
	        return new Customer();
	    } else {
	        return findCustomerById(petStoreId, customerId);
	    }
	}

	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}

	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
	    List<PetStore> petStores = petStoreDao.findAll(); // Fetch all pet stores from the DAO
	    
	    List<PetStoreData> resultList = new LinkedList<>();
	    
	    for (PetStore petStore : petStores) {
	        PetStoreData psd = new PetStoreData(petStore); // Convert PetStore to PetStoreData
	        psd.getCustomers().clear(); // Remove customer data
	        psd.getEmployees().clear(); // Remove employee data
	        
	        resultList.add(psd);
	    }

	    return resultList;
	}

	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreWithID(Long petStoreId) {
		PetStore petStoreWithId = findPetStoreById(petStoreId);//to retrieve the PetStore entity.
		return new PetStoreData(petStoreWithId);
	}


	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStoreWithId = findPetStoreById(petStoreId);//to retrieve the PetStore entity.
		petStoreDao.delete(petStoreWithId);
		
	}

	

	
	

    

}
