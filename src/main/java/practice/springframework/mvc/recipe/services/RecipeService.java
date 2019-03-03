package practice.springframework.mvc.recipe.services;

import lombok.extern.slf4j.Slf4j;
import practice.springframework.mvc.recipe.commands.RecipeCommand;
import practice.springframework.mvc.recipe.converter.RecipeCommandToRecipe;
import practice.springframework.mvc.recipe.converter.RecipeToRecipeCommand;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.exceptions.NotFoundException;
import practice.springframework.mvc.recipe.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeService(RecipeRepository recipeRepository,
                         RecipeCommandToRecipe recipeCommandToRecipe,
                         RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public Set<Recipe> findAll() {
        log.debug("I'm the RecipeSerivce.");

        Iterable<Recipe> recipes = recipeRepository.findAll();

        Set<Recipe> recipeSet = new HashSet<>();
        recipes.iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }

    public Recipe findById(Long id) {
        String errorMessage = String.format("id(%d) Not Found Entity", id);
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException(errorMessage));
    }

    public RecipeCommand save(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(recipe);

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }
}
