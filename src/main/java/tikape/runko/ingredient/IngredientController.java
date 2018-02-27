package tikape.runko.ingredient;

import java.util.ArrayList;
import tikape.runko.util.ViewUtil;
import tikape.runko.database.Database;
import tikape.runko.util.Path;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import spark.*;
import tikape.runko.Main;

public class IngredientController {

    public Database database;
    public IngredientDao ingredientDao;

    public IngredientController(Database database) {
        this.database = database;
        this.ingredientDao = new IngredientDao(this.database);
    }

    public Route fetchAllIngredients = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        List<Ingredient> raaka_aineet = this.ingredientDao.findAll(); //Haetaan kaikki raaka_aineet
        model.put("raaka_aineet", raaka_aineet); //Tallennetaan kaikki raaka_aineet hakusanalle "raaka_aineet"

        model.put("INGREDIENTS", Path.Web.INGREDIENTS);
        model.put("etusivu_url", Path.Web.INDEX); //Lähetetää avaimlla "etusivu_url" URL etusivulle. Tällä tavalla jos vaihdetaan etusivun URL osoitetta, sitä ei erikseen tarvitse vaihtaa myös täällä

        return ViewUtil.render(model, Path.Template.INGREDIENTS_ALL); //Palautetaan sivu INGREDIENTS_ALL, joka viittaa html tiedostoon ingredient/all.html. Viittaus löytyy Path.java tiedostosta
    };

    public Route fetchOneIngredient = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        model.put("raaka_aine", this.ingredientDao.findOne(Integer.parseInt(request.params(":id")))); //Napataan Requestin mukana tullut id ja etsitään sitä vastaava raaka-aine

        model.put("INDEX", Path.Web.INDEX);

        return ViewUtil.render(model, Path.Template.INGREDIENT);
    };

    public Route serveAddOneIngredientPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        List<Ingredient> raaka_aineet = this.ingredientDao.findAll();
        model.put("request", request); //Ei haluta palauttaa mitään mallia, mutta koska methodi sitä vaatii niin palautetaan jotain turhaa
        model.put("raaka_aineet", raaka_aineet);

        model.put("lisää_raaka_aine", Path.Api.ADD_INGREDIENT);

        return ViewUtil.render(model, Path.Template.INGREDIENT_ADD);
    };

    public Route addOneIngredient = (Request request, Response response) -> {
        String nimi = request.queryParams("nimi"); //Napataan pyynnön mukana tullut arvo, joka löytyy avaimella "nimi"

        this.ingredientDao.addOne(nimi);

        response.redirect(Path.Web.INGREDIENTS); //Uudelleen ohjataan käyttäjä
        return ""; //Tämä ei ikinä toteudu
    };

    public Route serveStatsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Map<Ingredient, Integer> ing = new HashMap<>();
        List<Ingredient> raaka_aineet = this.ingredientDao.findAll();
        
        System.out.println(raaka_aineet.size());
        
        for (int i = 0; i < raaka_aineet.size(); i++) {
            ing.put(raaka_aineet.get(i), Main.recipeIngredientDao.findCountByIngredient(raaka_aineet.get(i).getId()));
        }
        
        
        Map<Ingredient, Integer> sortedMap = 
            ing.entrySet().stream()
           .sorted(Entry.comparingByValue((e1, e2) -> e2 - e1))
           .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                                     (e1, e2) -> e1, LinkedHashMap::new));
        
        model.put("raaka_aineet", sortedMap);
        model.put("etusivu_url", Path.Web.INDEX);
        
        
        return ViewUtil.render(model, Path.Template.STAT);
    };

}
