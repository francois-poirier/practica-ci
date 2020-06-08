package es.codeurjc.daw.repository;

import org.springframework.data.repository.CrudRepository;

import es.codeurjc.daw.entity.Job;

public interface JobRepository extends CrudRepository<Job,Long>{

}
