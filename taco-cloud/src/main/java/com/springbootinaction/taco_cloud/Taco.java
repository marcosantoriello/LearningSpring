package com.springbootinaction.taco_cloud;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at the least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at the least 1 ingredient")
    @ManyToMany
    private List<IngredientRef> ingredients;
}
