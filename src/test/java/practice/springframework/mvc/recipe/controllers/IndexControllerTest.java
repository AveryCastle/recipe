package practice.springframework.mvc.recipe.controllers;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebMvcTest
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Model model;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private IndexController indexController;

    @Test
    public void indexShouldReturnRecipesFromService() throws Exception {
        // Given.
        final Long ID = 1L;
        final Recipe prepared = Recipe.builder().id(ID).description("Delicious").build();
        final Set<Recipe> recipes = new HashSet<>(Arrays.asList(prepared));
        when(recipeService.findAll()).thenReturn(recipes);

        // When & Then.
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Delicious")));

        verify(recipeService, times(1)).findAll();
    }

    @Test
    public void getIndexPage() {
        // Given.
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        recipes.add(recipe);

        when(recipeService.findAll()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.index(model);

        //then
        assertEquals("index", viewName);
        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}
