package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	/*
	 * Spring can automatically inject the PetStoreDao object into the petStoreDao variable. The @Autowired annotation tells Spring to do this.
	 *  When the bean is created, Spring will look for an instance of the PetStoreDao interface and inject it into the petStoreDao variable.
	 */
	@Autowired
	private PetStoreDao petStoreDao;

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		//check if the petStore info which pass in the method exists by checking the Id
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreateContributor(petStoreId);
		
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
	private PetStore findOrCreateContributor(Long petStoreId) {
		
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
	
	

}
