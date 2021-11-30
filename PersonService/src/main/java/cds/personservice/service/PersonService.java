package cds.personservice.service;

import cds.personservice.entities.Person;
import cds.personservice.repository.PersonRepository;
import cds.personservice.repository.PersonRepositoryImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class PersonService {

    private PersonRepositoryImpl personRepository;

    @Autowired
    public PersonService(PersonRepositoryImpl personRepository){
        this.personRepository = personRepository;
    }

    public Person saveNewPerson(String name, String firstName){
        if(StringUtils.isBlank(name) || StringUtils.isBlank(firstName)){
            throw new PersistenceException("Name and/or FirstName can't be empty");
        }

        if(null != this.personRepository.findUniquePerson(name, firstName)){
            throw new PersistenceException("This person already exist in DataBase");
        }

        Person person = new Person();
        person.setFirstName(firstName);;
        person.setName(name);

        return this.personRepository.save(person);
    }

    public Person getById(Long id){
        return this.personRepository.getById(id);
    }

    public List<Person> getListFilteredPerson(String name, String firstName){
        Person person = new Person();
        person.setFirstName(firstName);;
        person.setName(name);
        return this.personRepository.findPerson(person);
    }

    public void remove(Long id){
        this.personRepository.deleteById(id);
    }

    public Person updatePerson(Person person){
        this.personRepository.save(person);
        return person;
    }

}
