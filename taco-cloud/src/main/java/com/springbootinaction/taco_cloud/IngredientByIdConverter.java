package com.springbootinaction.taco_cloud;

import com.springbootinaction.taco_cloud.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /*
        Prende un ID come input. Cerca nella mappa l’oggetto Ingredient corrispondente a quell’ID
        e lo restituisce.
    */
    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findOne(id).orElse(null);
    }
}
