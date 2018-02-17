package tikape.runko.util;

import java.util.Map;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class ViewUtil {
    
    //Jos halutaan palauttaa html sivu niin nyt kutsutaan vain tämä methodi
    public static String render(Map<String, Object> model, String templatePath){
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    
}
