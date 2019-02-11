package practice.springframework.mvc.recipe.repositories;

import practice.springframework.mvc.recipe.domain.Recipe;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
