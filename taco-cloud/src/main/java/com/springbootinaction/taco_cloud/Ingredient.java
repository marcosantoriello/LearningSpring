package com.springbootinaction.taco_cloud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@AllArgsConstructor
//@RequiredArgsConstructor
public class Ingredient {
    @Id
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}
