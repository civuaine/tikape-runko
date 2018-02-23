package tikape.runko.ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.util.Dao;

public class IngredientDao implements Dao<Ingredient, Integer> {

    private Database database;

    public IngredientDao(Database database) {
        this.database = database;
    }

    @Override
    public Ingredient findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raaka_aine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Ingredient i = new Ingredient(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return i;
    }
    
    // Tämän voi poistaa, jos tietää tavan jolla recipes/add.html "Lisää raaka-aine reseptiin" lomakkeen pudotusvalikosta
    // saa request.QueryParams haulla palautettua raaka-aineen id:n nimen sijaan (kts. RecipeIngredientController addOne...)
    public Ingredient findOneByName(String name) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raaka_aine WHERE nimi = ?");
        stmt.setObject(1, name);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Ingredient i = new Ingredient(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return i;
    }
    
    @Override
    public List<Ingredient> findAll() throws SQLException {
        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Raaka_aine");

        ResultSet rs = stmt.executeQuery();
        List<Ingredient> raaka_aineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            raaka_aineet.add(new Ingredient(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return raaka_aineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Raaka_aine WHERE id = ?");
        
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
        
    }

    void addOne(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Raaka_Aine(nimi) VALUES (?);");
        
        stmt.setString(1, nimi);
        
        stmt.execute();
        stmt.close();
        connection.close();
        
    }

}
