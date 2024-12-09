package com.springbootinaction.taco_cloud;


/*
    Class that represents the linking between Taco and each Ingredient that makes up the Taco
*/

import lombok.Data;

@Data
public class IngredientRef {
    private final String ingredient;
}
