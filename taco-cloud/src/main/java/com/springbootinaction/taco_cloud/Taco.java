package com.springbootinaction.taco_cloud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Taco {
    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Name must be at the least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at the least 1 ingredient")
    private List<Ingredient> ingredients;

    public Taco() {
        name = "";
        ingredients = new ArrayList<>();
    }

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Taco taco = (Taco) o;
        return Objects.equals(id, taco.id) && Objects.equals(createdAt, taco.createdAt) && Objects.equals(name, taco.name) && Objects.equals(ingredients, taco.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, name, ingredients);
    }

    @Override
    public String toString() {
        return "Taco{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
