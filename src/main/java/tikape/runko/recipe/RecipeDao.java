package tikape.runko.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.util.Dao;

public class RecipeDao implements Dao<Recipe, Integer> {

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
    
    // Tämän voi poistaa, jos tietää tavan jolla recipes/add.html "Lisää raaka-aine reseptiin" lomakkeen pudotusvalikosta
    // saa request.QueryParams haulla palautettua reseptin id:n nimen sijaan. (kts. RecipeIngredientController addOne...)
    public Recipe findOneByName(String name) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Resepti WHERE nimi = ?");
        stmt.setObject(1, name);

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

        Connection connection = this.database.getConnection();
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
    
    
    public void addOne(String nimi, Double luokka, Integer valmistusaika) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Resepti(nimi, luokitus, valmistusaika) VALUES (?, ?, ?);");
        
        stmt.setString(1, nimi);
        stmt.setDouble(2, luokka);
        stmt.setInt(3, valmistusaika);
        
        stmt.execute();
        stmt.close();
        connection.close();
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
