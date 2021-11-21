package cds.personservice.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    public Person(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
