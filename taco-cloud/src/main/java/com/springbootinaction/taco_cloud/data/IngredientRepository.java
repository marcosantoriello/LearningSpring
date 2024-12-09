package com.springbootinaction.taco_cloud.data;

import com.springbootinaction.taco_cloud.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    public Iterable<Ingredient> findAll();

    // its the equivalent of findById, but it is convention to call it like that
    Optional<Ingredient> findOne(String id);

    public Ingredient save(Ingredient ingredient);
}
