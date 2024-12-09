package com.springbootinaction.taco_cloud;

import com.springbootinaction.taco_cloud.data.IngredientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.springbootinaction.taco_cloud.Ingredient.Type;

@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType((List<Ingredient>) ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        tacoOrder.addTaco(taco);
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    // Semplice filtro per tipologia di ingrediente. Utilizziamo la Stream API
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        // converto la lista degli ingredients in uno stream. Filtro gli oggetti di tale stream in base ad un predicato.
        // Infine converto lo stream contenente gli oggetti che rispettano il predicato in una lista.
        return ingredients.stream().filter(x->x.getType().equals(type)).collect(Collectors.toList());
    }

}
