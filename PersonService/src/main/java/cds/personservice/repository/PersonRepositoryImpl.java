package cds.personservice.repository;

import cds.personservice.entities.Person;
import cds.personservice.entities.Person_;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
public class PersonRepositoryImpl extends SimpleJpaRepository<Person, Long> implements PersonRepository{

    @Autowired
    private EntityManager entityManager;

    public PersonRepositoryImpl(Class<Person> domainClass, EntityManager em) {
        super(domainClass, em);
    }


    public List<Person> findPerson(Person person) {
        CriteriaQuery<Person> query = buildFindPersonQuery(person);
        return entityManager.createQuery(query)
                .getResultList();
    }

    private CriteriaQuery<Person> buildFindPersonQuery(Person person) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        List<Predicate> conditions = new ArrayList<>();
        if(null != person){
            if(StringUtils.isNotBlank(person.getFirstName())){
                Predicate firstName = builder.like(root.get(Person_.firstName),"%"+person.getFirstName().toUpperCase()+"%");
                conditions.add(firstName);
            }
            if(StringUtils.isNotBlank(person.getName())){
                Predicate lastName = builder.like(root.get(Person_.name),"%"+person.getName().toUpperCase()+"%");
                conditions.add(lastName);
            }
        }
        query.where(conditions.toArray(new Predicate[conditions.size()]));
        return query;
    }

    private CriteriaQuery<Person> buildFindUniquePersonQuery(String name, String firstName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        List<Predicate> conditions = new ArrayList<>();

        if(StringUtils.isNotBlank(firstName)){
            Predicate firstNameCrit = builder.equal(root.get(Person_.firstName),firstName.toUpperCase());
            conditions.add(firstNameCrit);
        }
        if(StringUtils.isNotBlank(name)){
            Predicate lastNameCrit = builder.equal(root.get(Person_.name),name.toUpperCase());
            conditions.add(lastNameCrit);
        }

        query.where(conditions.toArray(new Predicate[conditions.size()]));
        return query;
    }

    public Person findUniquePerson(String name, String firstName) {
        CriteriaQuery<Person> query = buildFindUniquePersonQuery(name, firstName);
        return entityManager.createQuery(query).getSingleResult();
    }
}
