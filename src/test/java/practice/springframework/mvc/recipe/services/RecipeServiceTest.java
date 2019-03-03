package practice.springframework.mvc.recipe.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import practice.springframework.mvc.recipe.converter.RecipeCommandToRecipe;
import practice.springframework.mvc.recipe.converter.RecipeToRecipeCommand;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.exceptions.NotFoundException;
import practice.springframework.mvc.recipe.repositories.RecipeRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class RecipeServiceTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeService(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void findAll() {
        final Long ID = 1L;
        final Recipe prepared = Recipe.builder().id(ID).description("Delicious").build();
        Set<Recipe> recipes = new HashSet<>(Arrays.asList(prepared));
        given(recipeRepository.findAll()).willReturn(recipes);

        Recipe foundOne = recipeService.findAll().iterator().next();
        assertThat(foundOne.getId()).isEqualTo(ID);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        final Long ID = 1L;
        final Recipe prepared = Recipe.builder().id(ID).description("Delicious").build();
        given(recipeRepository.findById(ID)).willReturn(Optional.of(prepared));

        Recipe foundOne = recipeService.findById(ID);
        assertThat(foundOne.getId()).isEqualTo(ID);
        verify(recipeRepository, times(1)).findById(ID);
    }

    @Test
    public void givenNotFoundEntity_whenFind_thenThrowNotFoundException() {
        final long ID = 1L;
        given(recipeRepository.findById(ID)).willReturn(Optional.empty());

        NotFoundException actual = assertThrows(NotFoundException.class, () -> {
            recipeService.findById(ID);
        });

        assertEquals("id(1) Not Found Entity", actual.getMessage());
    }
}
