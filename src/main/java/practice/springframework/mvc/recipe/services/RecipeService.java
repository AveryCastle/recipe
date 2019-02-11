package practice.springframework.mvc.recipe.services;

import lombok.extern.slf4j.Slf4j;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<Recipe> findAll() {
        log.debug("I'm the RecipeSerivce.");
        
        Iterable<Recipe> recipes = recipeRepository.findAll();

        Set<Recipe> recipeSet = new HashSet<>();
        recipes.iterator().forEachRemaining(recipeSet::add);

        return recipeSet;
    }
}
