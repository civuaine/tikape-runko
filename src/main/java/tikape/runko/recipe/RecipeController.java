package tikape.runko.recipe;

import tikape.runko.util.ViewUtil;
import tikape.runko.database.Database;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import spark.*;
import tikape.runko.Main;
import tikape.runko.ingredient.Ingredient;
import tikape.runko.category.*;
import tikape.runko.util.Path;

public class RecipeController {

    public Database database;
    public RecipeDao recipeDao;

    public RecipeController(Database database) {
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
        
        Integer id = 0;
        try{
            id = Integer.parseInt(request.params(":id"));
        }catch(NumberFormatException ex){
            response.redirect(Path.Web.RECIPES);
        }
        
        Recipe yksiResepti = this.recipeDao.findOne(id);
        if(yksiResepti == null){
            response.redirect(Path.Web.RECIPES);
        }
        
        model.put("resepti", yksiResepti); //Napataan Requestin mukana tullut id ja etsitään sitä vastaava resepti

        model.put("INDEX", Path.Web.INDEX);
        model.put("poista_url", Path.Api.DELETE_RECIPE);
        
        return ViewUtil.render(model, Path.Template.RECIPE);
    };

    public Route serveAddOneRecipePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        List<Recipe> reseptit = this.recipeDao.findAll();
        //Seuraaavan voisi toteuttaa varmaan fiksumminkin.
        //Pitäisikö RecipeControllerille antaa itselleen IngredientController vai toteuttaa jotenkin muuten?
        List<Ingredient> raaka_aineet = Main.ingredientController.ingredientDao.findAll();
        List<Category> kategoriat = Main.categoryDao.findAll();
        
        model.put("reseptit", reseptit);
        model.put("raaka_aineet", raaka_aineet);
        model.put("etusivu_url", Path.Web.INDEX);
        model.put("lisää_resepti", Path.Api.ADD_RECIPE);
        model.put("kategoriat", kategoriat);
        //model.put("lisää_reseptiin_raaka_aine", Path.Api.??);
        model.put("lisää_raaka_aine", Path.Api.ADD_INGREDIENT);

        return ViewUtil.render(model, Path.Template.RECIPE_ADD);
    };

    public Route serveIndexPage = (Request request, Response reponse) -> {
        //Tämä palauttaa kotisivun home.html Tämä methodi pitäisi olla paikassa tikape/runko/index/IndexController.java, mutta en jaksanut tehdä sitä vielä
        Map<String, Object> model = new HashMap<>();
        model.put("request", request); //Ei haluta palauttaa mitään mallia, mutta koska methodi sitä vaatii niin palautetaan jotain turhaa

        model.put("RECIPES", Path.Web.RECIPES);
        model.put("lisää_resepti_sivu", Path.Web.ADD_RECIPE);
        model.put("lisää_raaka_aine_sivu", Path.Web.ADD_INGREDIENT);
        model.put("STATS", Path.Web.STATS);
        return ViewUtil.render(model, Path.Template.INDEX);
    };

    public Route addOneRecipe = (Request request, Response response) -> {
        
        System.out.println("LISÄTÄÄN");
        
        String nimi = "";
        Integer valmistusaika = 0;
        String ohje = "";
        String[] kategoriat = null;
        String[] raaka_aineet = null;
        String[] maarat = null;
        
        
        if(request.queryParams().contains("nimi")){
            nimi = request.queryParams("nimi"); //Napataan pyynnön mukana tullut arvo, joka löytyy avaimella "nimi"
        }else{
            return ViewUtil.render(new HashMap(), Path.Template.INDEX);
        }
        
        if(request.queryParams().contains("valmistusaika")){
            
            try{
                valmistusaika = Integer.parseInt(request.queryParams("valmistusaika"));
            }catch(NumberFormatException ex){
                response.redirect(Path.Web.RECIPES);
            }
            
        }else{
            return ViewUtil.render(new HashMap(), Path.Template.INDEX);
//            response.redirect(Path.Web.RECIPES);
           
        }
        
        if(request.queryParams().contains("ohje")){
            ohje = request.queryParams("ohje");
        }else{
            return ViewUtil.render(new HashMap(), Path.Template.INDEX);
        }
        
        if(request.queryParams().contains("kategoria")){
            kategoriat = request.queryParamsValues("kategoria");
        }
        
        if(request.queryParams().contains("raaka-aine")){
            raaka_aineet = request.queryParamsValues("raaka-aine");
        }else{
            return ViewUtil.render(new HashMap(), Path.Template.INDEX);
        }
        
        if(request.queryParams().contains("maara")){
            maarat = request.queryParamsValues("maara");
        }else{
            return ViewUtil.render(new HashMap(), Path.Template.INDEX);
        }  

  
        Integer recipe_id = this.recipeDao.addOne(nimi, valmistusaika, ohje);

        for (int x = 0; x < raaka_aineet.length; x++) {
            Integer ingredient_id = Main.ingredientController.ingredientDao.addOne(raaka_aineet[x]);
            Main.recipeIngredientDao.addOne(recipe_id, ingredient_id, x, maarat[x]);
        }
        response.redirect(Path.Web.RECIPES); //Uudelleen ohjataan käyttäjä
        return ViewUtil.render(new HashMap(), Path.Template.INDEX); //Tämä ei ikinä toteudu
    };
    
    
    public Route deleteRecipe = (Request request, Response response) -> {

        
        this.recipeDao.delete(Integer.parseInt(request.queryParams("id")));
        Main.recipeIngredientDao.delete(Integer.parseInt(request.queryParams("id")));
        response.redirect(Path.Web.RECIPES);
        
        
        return ViewUtil.render(new HashMap(), Path.Template.INDEX); //Tämä ei ikinä toteudu
    };
}
