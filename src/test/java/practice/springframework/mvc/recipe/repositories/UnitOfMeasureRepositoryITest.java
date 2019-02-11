package practice.springframework.mvc.recipe.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import practice.springframework.mvc.recipe.domain.UnitOfMeasure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryITest {

    @Autowired
    private UnitOfMeasureRepository repository;

    @Test
    @DirtiesContext
    public void findByTeaspoon() {
        Optional<UnitOfMeasure> teaspoon = repository.findByDescription("Teaspoon");
        assertThat("Teaspoon", equalTo(teaspoon.get().getDescription()));
    }

    @Test
    public void findByCup() {
        Optional<UnitOfMeasure> teaspoon = repository.findByDescription("Cup");
        assertThat("Cup", equalTo(teaspoon.get().getDescription()));
    }
}
