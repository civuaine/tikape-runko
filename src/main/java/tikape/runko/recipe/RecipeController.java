package tikape.runko.recipe;

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
        
        
        model.put("RECIPES", Path.Web.RECIPES);
        model.put("etusivu_url", Path.Web.INDEX); //Lähetetää avaimlla "etusivu_url" URL etusivulle. Tällä tavalla jos vaihdetaan etusivun URL osoitetta, sitä ei erikseen tarvitse vaihtaa myös täällä
        
        return ViewUtil.render(model, Path.Template.RECIPES_ALL); //Palautetaan sivu RECIPES_ALL, joka viittaa html tiedostoon recipe/all.html. Viittaus löytyy Path.java tiedostosta
    };
    
    public Route fetchOneRecipe = (Request request, Response response) -> {
        
        Map<String, Object> model = new HashMap<>();
        model.put("resepti", this.recipeDao.findOne(Integer.parseInt(request.params(":id")))); //Napataan Requestin mukana tullut id ja etsitään sitä vastaava resepti
        
        model.put("INDEX", Path.Web.INDEX);
        
        return ViewUtil.render(model, Path.Template.RECIPE);
    };
    
    public Route serveAddOneRecipePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("request", request); //Ei haluta palauttaa mitään mallia, mutta koska methodi sitä vaatii niin palautetaan jotain turhaa
        
        model.put("lisää_resepti", Path.Api.ADD_RECIPE);
        
        return ViewUtil.render(model, Path.Template.RECIPE_ADD);
    };
    
    public Route serveIndexPage = (Request request, Response reponse) -> {
        //Tämä palauttaa kotisivun home.html Tämä methodi pitäisi olla paikassa tikape/runko/index/IndexController.java, mutta en jaksanut tehdä sitä vielä
        Map<String, Object> model = new HashMap<>();
        model.put("request", request); //Ei haluta palauttaa mitään mallia, mutta koska methodi sitä vaatii niin palautetaan jotain turhaa
        
        model.put("RECIPES", Path.Web.RECIPES);
        model.put("lisää_resepti_sivu", Path.Web.ADD_RECIPE);
        
        return ViewUtil.render(model, Path.Template.INDEX);
    };
    
    public Route addOneRecipe = (Request request, Response response) -> {
        String nimi = request.queryParams("nimi"); //Napataan pyynnön mukana tullut arvo, joka löytyy avaimella "nimi"
        Double luokka = Double.parseDouble(request.queryParams("luokka"));
        Integer valmistusaika = Integer.parseInt(request.queryParams("valmistusaika"));
        
        this.recipeDao.addOne(nimi, luokka, valmistusaika);
        
        response.redirect(Path.Web.RECIPES); //Uudelleen ohjataan käyttäjä
        return""; //Tämä ei ikinä toteudu
    };
    
}
