package practice.springframework.mvc.recipe.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.exceptions.NotFoundException;
import practice.springframework.mvc.recipe.services.RecipeService;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RecipeControllerTest {

    private static final Long ID = 1L;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private Recipe recipe;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        recipe = Recipe.builder().id(ID).description("Making Chiabata Bread").build();
    }

    @Test
    public void show() throws Exception {
        when(recipeService.findById(ID)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/" + ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attribute("recipe", hasProperty("description", is("Making Chiabata Bread"))));
    }

    @Test
    public void givenNotFound_whenFind_thenThrowException() throws Exception {
        String message = String.format("id(%d) Not Found Entity", ID);
        when(recipeService.findById(anyLong())).thenThrow(new NotFoundException(message));

        mockMvc.perform(get("/recipe/show/" + ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
