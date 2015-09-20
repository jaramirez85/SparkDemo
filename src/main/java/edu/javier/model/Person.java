package edu.javier.model;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JavierAlonso on 08/09/2015.
 */
public class Person {

    private UUID id;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
