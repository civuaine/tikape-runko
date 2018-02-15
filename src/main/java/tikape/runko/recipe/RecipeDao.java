/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.recipe.Recipe;
import tikape.runko.database.Database;
import tikape.runko.util.Dao;
import tikape.runko.util.ViewUtil;

public class RecipeDao implements Dao<Recipe, Integer> {
//public class RecipeDao {

    private Database database;

    public RecipeDao(Database database) {
        this.database = database;
    }

    @Override
    public Recipe findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Resepti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        Double luokitus = rs.getDouble("luokitus");
        Integer valmistusaika = rs.getInt("valmistusaika");

        Recipe r = new Recipe(id, nimi, luokitus, valmistusaika);

        rs.close();
        stmt.close();
        connection.close();

        return r;
    }

    @Override
    public List<Recipe> findAll() throws SQLException {
        System.out.println("FINDDDDLLLLLLLLLLLLLLLLLLLLLLLLLL");

        Connection connection = this.database.getConnection();
        System.out.println("DATABASEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Resepti");

        ResultSet rs = stmt.executeQuery();
        List<Recipe> reseptit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Double luokitus = rs.getDouble("luokitus");
            Integer valmistusaika = rs.getInt("valmistusaika");

            reseptit.add(new Recipe(id, nimi, luokitus, valmistusaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return reseptit;
    }
    
    public List<Recipe> findA(){
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Resepti WHERE id = ?");
        
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

}
