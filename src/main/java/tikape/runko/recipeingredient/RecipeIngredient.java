
package tikape.runko.recipeingredient;

import tikape.runko.ingredient.Ingredient;
import tikape.runko.recipe.Recipe;


public class RecipeIngredient {
    private Recipe recipe;
    private Ingredient ingredient;
    private Integer order;
    private String quantity;
    private String instruction;
    
    public RecipeIngredient(Recipe r, Ingredient i, Integer order, String quantity, String info) {
        this.recipe = r;
        this.ingredient = i;
        this.order = order;
        this.quantity = quantity;
        this.instruction = info;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    
    
}
