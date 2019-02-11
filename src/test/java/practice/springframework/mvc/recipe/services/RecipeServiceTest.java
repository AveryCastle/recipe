package practice.springframework.mvc.recipe.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

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
}
