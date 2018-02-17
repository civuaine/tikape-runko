package tikape.runko;

import tikape.runko.database.Database;
import tikape.runko.util.Path;
import tikape.runko.util.Filters;
import tikape.runko.recipe.RecipeController;


import static spark.Spark.*;

public class Main {
    public static Database database;
    public static RecipeController recipeController;
    
    public static void main(String[] args) throws Exception{
        
        
        
        database = new Database("jdbc:sqlite:opiskelijat.db"); //Alustetaan Tietokanta
        
        // Alustetaan kontrollerit kaikille tietokantaolioille(resepti, raaka-aine ym.)
        recipeController = new RecipeController(database);
        
        before("*", Filters.addTrailingSlashes); //Kaikkien URL osoitteiden perään laitetaan nyt "/" jos sitä ei jo ole.
        
        
        //Listataan kaikki mahdolliset polut
        get(Path.Web.INDEX, recipeController.serveIndexPage);
        get(Path.Web.RECIPES, recipeController.fetchAllRecipes);
        get(Path.Web.ADD_RECIPE, recipeController.serveAddOneRecipePage);
        get(Path.Web.ONE_RECIPE, recipeController.fetchOneRecipe);
        
        
        //API osoitteet ovat sellaisia joita käyttäjä ei näe. Niitä käytetään jonkin ohjelman sisäisen toiminnan toteuttamiseen esim. lisäämiseen tai poistamiseen
        post(Path.Api.ADD_RECIPE, recipeController.addOneRecipe);
        
    }
}