/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.recipe;

import tikape.runko.recipe.Recipe;
import tikape.runko.recipe.RecipeDao;
import tikape.runko.util.ViewUtil;
import tikape.runko.database.Database;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import spark.*;
import tikape.runko.util.Path;


public class RecipeController {
    
    public Database database;
    public RecipeDao recipeDao;
    
    public RecipeController(Database database){
        this.database = database;
        this.recipeDao = new RecipeDao(this.database);
    }
    
    public Route fetchAllRecipes = (Request request, Response response) -> {
        
        Map<String, Object> model = new HashMap<>();
        List<Recipe> reseptit = this.recipeDao.findAll(); //Haetaan kaikki reseptit
        model.put("reseptit", reseptit); //Tallennetaan kaikki reseptit hakusanalle "reseptit"
        
        return ViewUtil.render(model, Path.Template.RECIPES_ALL);
    };
    
    public Route fetchOneRecipe = (Request request, Response response) -> {
        
        Map<String, Object> model = new HashMap<>();
        model.put("resepti", this.recipeDao.findOne(Integer.parseInt(request.params(":id")))); //Napataan Requestin mukana tullut id ja etsitään sitä vastaava resepti
        
        return ViewUtil.render(model, Path.Template.RECIPE);
    };
    
    public Route serveAddOneRecipePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("request", request);
        
        return ViewUtil.render(model, Path.Template.RECIPE_ADD);
    };
    
    public Route serveIndexPage = (Request request, Response reponse) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("request", request);
        
        return ViewUtil.render(model, Path.Template.INDEX);
    };
    
    public Route addOneRecipe = (Request request, Response response) -> {
        String nimi = request.queryParams("nimi");
        Double luokka = Double.parseDouble(request.queryParams("luokka"));
        Integer valmistusaika = Integer.parseInt(request.queryParams("valmistusaika"));
        
        this.recipeDao.addOne(nimi, luokka, valmistusaika);
        
        response.redirect(Path.Web.RECIPES);
        return"";
    };
    
}
