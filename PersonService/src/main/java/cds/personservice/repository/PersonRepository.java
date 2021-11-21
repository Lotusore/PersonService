package cds.personservice.repository;

import cds.personservice.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    @Query("select p from Person p where p.name like %:name% and p.firstName like %:firstName%")
    public List<Person> getPerson(@Param("name")String name, @Param("firstName")String firstName);

    @Query("select p from Person p where p.name like %:name% ")
    public List<Person> getByName(@Param("name")String name);

    @Query("select p from Person p where p.firstName like %:firstName%")
    public List<Person> getByFirstName(String firstName);

}
