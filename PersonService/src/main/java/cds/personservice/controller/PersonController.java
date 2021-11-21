package cds.personservice.controller;

import cds.personservice.entities.Person;
import cds.personservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping(value = "{id}")
    public Person getById(@PathVariable("id") Long id){
        return this.personService.getById(id);
    }

    @GetMapping(value = "/name/{name}/firstName/{firstName}")
    public List<Person> getPersonsByName(@PathVariable("name") String name,
                                         @PathVariable("firstName") String firstName){
        return this.personService.getListFilteredPerson(name, firstName);
    }

    @PostMapping(value = "/name/{name}/firstName/{firstName}")
    public ResponseEntity savePerson(@PathVariable("name") String name,
                                   @PathVariable("firstName") String firstName) {
        try {
            Person person = this.personService.saveNewPerson(name, firstName);
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity updatePerson(@RequestBody Person input){
        Person person = this.personService.updatePerson(input);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id){
        this.personService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
