package tikape.runko.recipeingredient;

import spark.Request;
import spark.Response;
import spark.Route;
import tikape.runko.database.Database;
import tikape.runko.util.Path;

public class RecipeIngredientController {

    public Database database;
    public RecipeIngredientDao recipeIngredientDao;

    public Route addOneRecipeIngredient = (Request request, Response response) -> {
        
        String resepti = request.queryParams("resepti"); //Napataan pyynnön mukana tullut arvo, joka löytyy avaimella "resepti"
        String raaka_aine = request.queryParams("lisättävä_raaka_aine");
        Integer jarjestys = Integer.parseInt(request.queryParams("järjestys"));
        String maara = request.queryParams("määrä");
        String ohje = request.queryParams("ohje");

//        this.recipeIngredientDao.addOne(resepti, raaka_aine, jarjestys, maara, ohje);
        
        response.redirect(Path.Web.RECIPES); //Uudelleen ohjataan käyttäjä
        return ""; //Tämä ei ikinä toteudu
    };
}
