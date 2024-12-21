package org.example.ch3_2.beans;

import org.springframework.stereotype.Component;

@Component
public class Parrot {
    private final String name = "Koko";

    public Parrot() {}

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Parrot name: " + this.name;
    }
}
