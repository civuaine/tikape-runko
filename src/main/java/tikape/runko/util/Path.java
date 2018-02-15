/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.util;



public class Path {
    
    public static class Web {
        //Listataan URL osoitteet
        public static final String INDEX = "/index/";
        public static final String RECIPES = "/recipes/";
        public static final String ONE_RECIPE = "/recipe/:id";
    }
    
    public static class Template {
        //Listataan HTML mallit
        public static final String RECIPES_ALL = "/recipe/all";
        public static final String RECIPE = "/recipe/one";
    }
    
}
