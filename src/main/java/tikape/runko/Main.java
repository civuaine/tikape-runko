package tikape.runko;

import java.util.HashMap;
import java.util.List;
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
            HashMap map = new HashMap<>();
            HashMap reseptit = new HashMap<>();
            
            for(Resepti resepti: reseptiDao.findAll()){
                reseptit.put(resepti.getId(), resepti.getNimi());
            }
            
            map.put("reseptit", reseptit);
            
            return new ModelAndView(map, "home");
        }, new ThymeleafTemplateEngine());
        
//        get("/", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("viesti", "tervehdys");
//
//            return new ModelAndView(map, "index");
//        }, new ThymeleafTemplateEngine());

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
    
    public static void AddTestDataReseptit(ReseptiDao reseptiDao, Database database) throws Exception{
        
        //Jos Resepti taulu on tyhjä niin lisätään sinne testidataa
        
        if(reseptiDao.findAll().size() == 0){
            database.sqliteLisaaTestiDataa();
        };
        
    }
}
