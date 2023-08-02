package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.PetStore;

/*
 *  The PetStoreDao interface inherits a set of methods from the JpaRepository interface. 
 *  These methods are used to perform common database operations, such as saving, deleting, and finding pets. 
 *  Spring Data JPA automatically generates the implementation for these methods, so we don't have to write them ourself.
 */
public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}
