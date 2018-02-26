package tikape.runko;

import tikape.runko.database.Database;
import tikape.runko.util.Path;
import tikape.runko.util.Filters;
import tikape.runko.recipe.RecipeController;


import static spark.Spark.*;
import tikape.runko.ingredient.IngredientController;
import tikape.runko.category.*;
import tikape.runko.recipeingredient.*;

public class Main {
    public static Database database;
    public static RecipeController recipeController;
    public static IngredientController ingredientController;
    public static CategoryDao categoryDao;
    public static RecipeIngredientDao recipeIngredientDao;
    
    public static void main(String[] args) throws Exception{
        
        
        
        database = new Database("jdbc:sqlite:tietokanta.db"); //Alustetaan Tietokanta
        database.init();
//        database.sqliteLisaaTestiDataa()
        
        // Alustetaan kontrollerit kaikille tietokantaolioille(resepti, raaka-aine ym.)
        recipeController = new RecipeController(database);
        ingredientController = new IngredientController(database);
        
        categoryDao = new CategoryDao(database);
        recipeIngredientDao = new RecipeIngredientDao(database, recipeController.recipeDao, ingredientController.ingredientDao);
        
        before("*", Filters.addTrailingSlashes); //Kaikkien URL osoitteiden perään laitetaan nyt "/" jos sitä ei jo ole.
        

        //Listataan kaikki mahdolliset polut
        get(Path.Web.INDEX, recipeController.serveIndexPage);
        get(Path.Web.RECIPES, recipeController.fetchAllRecipes);
        get(Path.Web.ADD_RECIPE, recipeController.serveAddOneRecipePage);
        get(Path.Web.ONE_RECIPE, recipeController.fetchOneRecipe);
        get(Path.Web.INGREDIENTS, ingredientController.fetchAllIngredients);
        get(Path.Web.ADD_INGREDIENT, ingredientController.serveAddOneIngredientPage);
        get(Path.Web.ONE_INGREDIENT, ingredientController.fetchOneIngredient);
        
        
        //API osoitteet ovat sellaisia joita käyttäjä ei näe. Niitä käytetään jonkin ohjelman sisäisen toiminnan toteuttamiseen esim. lisäämiseen tai poistamiseen
        post(Path.Api.ADD_RECIPE, recipeController.addOneRecipe);
        post(Path.Api.DELETE_RECIPE, recipeController.deleteRecipe);
        post(Path.Api.ADD_INGREDIENT, ingredientController.addOneIngredient);
    }
}