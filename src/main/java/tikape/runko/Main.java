//package tikape.runko;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.List;
//import java.util.ArrayList;
//import spark.ModelAndView;
//import static spark.Spark.*;
//import spark.template.thymeleaf.ThymeleafTemplateEngine;
//import tikape.runko.database.Database;
//import tikape.runko.database.ReseptiDao;
//import tikape.runko.database.OpiskelijaDao;
//import tikape.runko.domain.Resepti;
//
//public class Main {
//
//    public static void main(String[] args) throws Exception {
//        Database database = new Database("jdbc:sqlite:opiskelijat.db");
//        database.init();
//
//        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
//        
//        ReseptiDao reseptiDao = new ReseptiDao(database); //Alustetaan Dao resepteille
//        
//        
//        AddTestDataReseptit(reseptiDao, database); //Lisätään testidataa jos siihen on tarve(Resepti taulu on tyhjä)
//        
//        
//        System.out.println("Hello world");
//        System.out.println("Hello again world");
//        
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Resepti> reseptit = reseptiDao.findAll();
//            model.put("reseptit", reseptit);
//            return render(model, "home");
//        });
//
//        get("/opiskelijat", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelijat", opiskelijaDao.findAll());
//
//            return new ModelAndView(map, "opiskelijat");
//        }, new ThymeleafTemplateEngine());
//
//        get("/opiskelijat/:id", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));
//
//            return new ModelAndView(map, "opiskelija");
//        }, new ThymeleafTemplateEngine());
//    }
//    
//    public static String render(Map<String, Object> model, String templatePath){
//        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
//    }
//    
//    public static void AddTestDataReseptit(ReseptiDao reseptiDao, Database database) throws Exception{
//        
//        //Jos Resepti taulu on tyhjä niin lisätään sinne testidataa
//        
//        if(reseptiDao.findAll().size() == 0){
//            database.sqliteLisaaTestiDataa();
//        };
//        
//    }
//}

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