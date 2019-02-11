package practice.springframework.mvc.recipe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        final Long ID = 100L;
        category.setId(ID);

        assertEquals(ID, category.getId());
    }
}
