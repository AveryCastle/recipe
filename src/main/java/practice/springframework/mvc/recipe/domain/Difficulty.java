package practice.springframework.mvc.recipe.domain;

public enum Difficulty {
    EASY("easy"), MODERATE("moderate"), KIND_OF_HARD("pretty hard"), HARD("hard");

    private String value;

    Difficulty(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
