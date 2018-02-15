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
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author lauri
 */
public class RecipeController {
    
    public Route fetchAllRecipes(Request request, Response response) throws Exception{
        RecipeDao recipeDao = new RecipeDao(new Database("aa"));
        ViewUtil viewUtil = new ViewUtil();
        
        Map<String, Object> model = new HashMap<>();
        List<Recipe> reseptit = recipeDao.findAll();
        model.put("reseptit", reseptit);
        return viewUtil.render(model, "home");
        
    }
    
}
