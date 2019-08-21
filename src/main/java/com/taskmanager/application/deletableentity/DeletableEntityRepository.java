package com.taskmanager.application.deletableentity;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface for enabling soft-delete based database queries. Overrides all CrudRepository methods that deal with selection or deletion.
 */

@NoRepositoryBean
public interface DeletableEntityRepository<T extends DeletableEntity, ID extends Long> extends CrudRepository<T, ID>{
	
	//Note: If using a standard SQL database instead of H2, @Transactional should become @Transactional(readOnly = true) where applicable.
	//		This provides a small performance boost, but H2 does not support the readOnly flag.
	@Override
	@Transactional
	@Query("select e from #{#entityName} e where e.isDeleted = false")
	List<T> findAll();
	
	@Override
	@Transactional
	@Query("select e from #{#entityName} e where e.id = ?1 and e.isDeleted = false")
	Iterable<T> findAllById(Iterable<ID> ids);
	
	@Override
	@Transactional
	@Query("select e from #{#entityName} e where e.id = ?1 and e.isDeleted = false")
	Optional<T> findById(ID id);
	
	@Transactional
	@Query("select e from #{#entityName} e where e.isDeleted = false")
	List<T> findDeleted();
	
	@Override
	@Transactional
	@Query("select count(e) from #{#entityName} e where e.isDeleted = true")
	long count();
	
	@Override
	@Transactional
	default boolean existsById(ID id) {
		return findById(id) != null;
	}
	
	@Override
	@Transactional
	@Query("update #{#entityName} e set e.isDeleted = true where e.id = ?1")
	@Modifying
	void deleteById(Long id);
	
	@Override
	@Transactional
	default void delete(T entity) {
		deleteById(entity.getId());
	}
	
	@Override
	@Transactional
	default void deleteAll(Iterable<? extends T> entities) {
		entities.forEach(entity -> deleteById(entity.getId()));
	}
	
	@Override
	@Transactional
	@Query("update #{#entityName} e set e.isDeleted = true")
	@Modifying
	void deleteAll();
	
}
