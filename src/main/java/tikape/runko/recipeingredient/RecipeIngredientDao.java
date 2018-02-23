package tikape.runko.recipeingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.ingredient.Ingredient;
import tikape.runko.ingredient.IngredientDao;
import tikape.runko.recipe.Recipe;
import tikape.runko.recipe.RecipeDao;
import tikape.runko.util.Dao;

public class RecipeIngredientDao implements Dao<RecipeIngredient, Integer> {

    private Database database;
    private RecipeDao recipeDao;
    private IngredientDao ingredientDao;

    public RecipeIngredientDao(Database db, RecipeDao rdao, IngredientDao iDao) {
        this.database = db;
        this.recipeDao = rdao;
        this.ingredientDao = iDao;
    }

    public List<Ingredient> findByRecipe(Integer key) throws SQLException {
        //Palauttaa listan raaka-aineita joita käytetään tietyssä reseptissä
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ReseptiRaaka_aine WHERE resepti_id = ?");

        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Ingredient> ingredients = new ArrayList<>();

        while (rs.next()) {
            Ingredient i = ingredientDao.findOne(rs.getInt("raaka_aine_id"));
            ingredients.add(i);
        }

        rs.close();
        stmt.close();
        connection.close();

        return ingredients;
    }

    public List<Recipe> findByIngredient(Integer key) throws SQLException {
        //Palauttaa listan reseptejä joissa käytetään tiettyä raaka-ainetta
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ReseptiRaaka_aine WHERE raaka_aine_id = ?");

        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();

        List<Recipe> recipes = new ArrayList<>();

        while (rs.next()) {
            Recipe r = recipeDao.findOne(rs.getInt("raaka_aine_id"));
            recipes.add(r);
        }

        rs.close();
        stmt.close();
        connection.close();

        return recipes;
    }

    @Override
    public RecipeIngredient findOne(Integer key) throws SQLException {
        Connection connection = this.database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ReseptiRaaka_aine WHERE id = ?");

        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Recipe r = recipeDao.findOne(rs.getInt("resepti_id"));
        Ingredient i = ingredientDao.findOne(rs.getInt("raaka_aine_id"));

        RecipeIngredient ri = new RecipeIngredient(r, i, rs.getInt("jarjestys"), rs.getInt("maara"), rs.getString("lisaohje"));

        rs.close();
        stmt.close();
        connection.close();

        return ri;

    }

    @Override
    public List<RecipeIngredient> findAll() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ReseptiRaaka_aine");

        ResultSet rs = stmt.executeQuery();

        List<RecipeIngredient> reseptiAinekset = new ArrayList<>();

        while (rs.next()) {

            Recipe r = recipeDao.findOne(rs.getInt("resepti_id"));
            Ingredient i = ingredientDao.findOne(rs.getInt("raaka_aine_id"));

            RecipeIngredient ri = new RecipeIngredient(r, i, rs.getInt("jarjestys"), rs.getInt("maara"), rs.getString("lisaohje"));

            reseptiAinekset.add(ri);
        }

        rs.close();
        stmt.close();
        connection.close();

        return reseptiAinekset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}