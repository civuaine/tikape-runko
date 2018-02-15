/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.util;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.ReseptiDao;
import tikape.runko.database.OpiskelijaDao;
import tikape.runko.domain.Resepti;

/**
 *
 * @author lauri
 */
public class ViewUtil {
    
    
    public static String render(Map<String, Object> model, String templatePath){
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    
}
