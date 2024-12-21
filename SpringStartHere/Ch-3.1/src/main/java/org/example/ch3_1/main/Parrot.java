package org.example.ch3_1.main;

public class Parrot {
    private String name;

    public Parrot() {
        System.out.println("Parrot created");
    }

    public Parrot(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Parrot: " + name;
    }
}
