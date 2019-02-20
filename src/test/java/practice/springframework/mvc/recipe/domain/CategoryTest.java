package practice.springframework.mvc.recipe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    private Category category;
    final Long ID = 100L;

    @Before
    public void setUp() {
        category = new Category();
        category.setId(ID);
    }

    @Test
    public void getId() {
        assertEquals(ID, category.getId());
    }
}
