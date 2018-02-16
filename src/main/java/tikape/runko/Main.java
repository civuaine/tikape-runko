package tikape.runko;

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

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        
        ReseptiDao reseptiDao = new ReseptiDao(database); //Alustetaan Dao resepteille
        
        
        AddTestDataReseptit(reseptiDao, database); //Lisätään testidataa jos siihen on tarve(Resepti taulu on tyhjä)
        
        
        System.out.println("Hello world");
        System.out.println("Hello again world");
        
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Resepti> reseptit = reseptiDao.findAll();
            model.put("reseptit", reseptit);
            return render(model, "home");
        });
        
        // Reseptilistaus ja uusien reseptien lisäys
        get("/reseptit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Resepti> reseptit = reseptiDao.findAll();
            model.put("reseptit", reseptit);
            return new ModelAndView(model, "reseptit");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
    }
    
    public static String render(Map<String, Object> model, String templatePath){
        return new ThymeleafTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    
    public static void AddTestDataReseptit(ReseptiDao reseptiDao, Database database) throws Exception{
        
        //Jos Resepti taulu on tyhjä niin lisätään sinne testidataa
        
        if(reseptiDao.findAll().size() == 0){
            database.sqliteLisaaTestiDataa();
        };
        
    }
}
