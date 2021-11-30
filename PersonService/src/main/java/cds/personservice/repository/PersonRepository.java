package cds.personservice.repository;

import cds.personservice.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {


}
