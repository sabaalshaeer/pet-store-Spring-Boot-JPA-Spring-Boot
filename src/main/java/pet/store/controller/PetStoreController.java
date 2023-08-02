package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController // this annotation tells Spring that this class is a REST controller. This means
				// that the class will handle HTTP requests and return JSON responses. The
				// default
				// response status code is 200 (OK), but we can specify a different status code
				// if needed. The @RestController annotation also tells Spring to take care of
				// mapping the requests to the appropriate methods in this class and returning
				// JSON responses.
@RequestMapping("/pet_store") // this annotation tells Spring that the URI for every HTTP request that is
								// mapped to a method in this controller class must start with "/pet_store".
@Slf4j // This is a Lombok annotation that creates an SLF4J logger. This logger is
		// named "log" and can be used in the class to print log messages easily.
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;

	/*
	 * Create a public method that maps an HTTP POST request to "/pet_store". The
	 * response status should be 201 (Created). Pass the contents of the request
	 * body as a parameter (type PetStoreData) to the method. (Use @RequestBody.)
	 * The method should return a PetStoreData object. Log the request. Call a
	 * method in the service class (savePetStore) that will insert or modify the pet
	 * store data.
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Create Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	//modify one of the pet store objects 
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId,
			@RequestBody PetStoreData petStoreData) {
		//Set the pet store ID in the pet store data from the ID parameter.
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);

		return petStoreService.savePetStore(petStoreData);
	}

}
