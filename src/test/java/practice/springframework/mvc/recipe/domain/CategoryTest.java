package practice.springframework.mvc.recipe.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    private Category category;
    final Long ID = 100L;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setId(ID);
    }

    @Test
    public void getId() {
        assertEquals(ID, category.getId());
    }
}
