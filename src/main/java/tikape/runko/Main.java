package tikape.runko;

import tikape.runko.database.Database;
import tikape.runko.database.ReseptiDao;
import tikape.runko.util.Path;
import tikape.runko.recipe.RecipeController;
import tikape.runko.recipe.RecipeDao;


import spark.*;
import static spark.Spark.*;

public class Main {
    public static Database database;
    public static RecipeController recipeController;
    
    public static void main(String[] args) throws Exception{
        
        
        
        database = new Database("jdbc:sqlite:opiskelijat.db"); //Alustetaan Tietokanta

        // Alustetaan kontrollerit kaikille tietokantaolioille(resepti, raaka-aine ym.)
        recipeController = new RecipeController(database);

        //Listataan kaikki mahdolliset polut
        get(Path.Web.INDEX, recipeController.serveIndexPage);
        get(Path.Web.RECIPES, recipeController.fetchAllRecipes);
        get(Path.Web.ONE_RECIPE, recipeController.fetchOneRecipe);
        get(Path.Web.ADD_RECIPE, recipeController.serveAddOneRecipePage);
        post(Path.Api.ADD_RECIPE, recipeController.addOneRecipe);
        
    }
}