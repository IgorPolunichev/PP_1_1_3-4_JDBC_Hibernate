package jm.task.core.jdbc.model;

import javax.persistence.*;
@Entity
@Table (name = "MyUserTable")
public class User {
    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    @Column (name = "UserId")
    private Long id;

    @Column (name = "Name")
    private String name;

    @Column (name = "LastName")
    private String lastName;

    @Column (name = "Age")
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString(){
        String output = String.format("ID: %d | UserName: %s | UserLastname: %s | UserAge: %d",
                this.id,
                this.name,
                this.lastName,
                this.age);
        return output;
    }
}
