package tikape.runko.util;



public class Path {

    public static class Web {
        //Listataan URL osoitteet
        public static final String INDEX = "/";
        public static final String RECIPES = "/recipes/";
        public static final String ADD_RECIPE = "/recipes/add/";
        public static final String ONE_RECIPE = "/recipes/:id/";
        public static final String INGREDIENTS = "/ingredients/";
        public static final String ADD_INGREDIENT = "/ingredients/add/";
        public static final String ONE_INGREDIENT = "/ingredients/:id/";
        public static final String STATS = "/statistics/stats/";
    }

    public static class Api {
        //Listataan API osoitteet
        public static final String ADD_RECIPE = "api/recipe/add/";
        public static final String DELETE_RECIPE = "api/recipe/delete/";
        public static final String ADD_INGREDIENT = "api/ingredient/add/";
    }

    public static class Template {
        //Listataan HTML mallit
        public static final String RECIPES_ALL = "/recipe/all";
        public static final String RECIPE = "/recipe/one";
        public static final String RECIPE_ADD = "/recipe/add";
        public static final String INGREDIENTS_ALL = "/ingredient/all";
        public static final String INGREDIENT = "/ingredient/one";
        public static final String INGREDIENT_ADD = "/ingredient/add";
        public static final String INDEX = "/index/home";
        public static final String STAT = "/statistics/stats";
    }

}
