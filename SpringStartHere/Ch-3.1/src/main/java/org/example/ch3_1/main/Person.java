package org.example.ch3_1.main;

public class Person {
    private String name;
    private Parrot parrot;

    public Person() {}
    public Person(String name, Parrot parrot) {
        this.name = name;
        this.parrot = parrot;
    }

    public String getName() {
        return this.name;
    }

    public Parrot getParrot() {
        return this.parrot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParrot(Parrot parrot) {
        this.parrot = parrot;
    }
}
