package practice.springframework.mvc.recipe.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import practice.springframework.mvc.recipe.commands.RecipeCommand;
import practice.springframework.mvc.recipe.domain.Recipe;
import practice.springframework.mvc.recipe.exceptions.GlobalExceptionHandler;
import practice.springframework.mvc.recipe.exceptions.NotFoundException;
import practice.springframework.mvc.recipe.services.RecipeService;

import org.springframework.http.MediaType;
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

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

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
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/show/" + ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @Test
    public void givenNonNumberParams_whenFind_thenThrowException() throws Exception {
        mockMvc.perform(get("/recipe/show/no-number"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400"));
    }

    @Test
    public void givenNewForm_whenSave_thenSuccess() throws Exception {
        // Given
        final Long ID = 2L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID);
        when(recipeService.save(any())).thenReturn(recipeCommand);

        // When & Then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
                .param("prepTime", "10")
                .param("cookTime", "10")
                .param("servings", "5")
                .param("url", "http://www.cook.com")
                .param("directions", "some direction"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + ID + "/show"));
    }
}
