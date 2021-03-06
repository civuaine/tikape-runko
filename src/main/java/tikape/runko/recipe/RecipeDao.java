package tikape.runko.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import tikape.runko.database.Database;
import tikape.runko.util.Dao;
import tikape.runko.ingredient.*;

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
        Integer valmistusaika = rs.getInt("valmistusaika");
        String ohje = rs.getString("ohje");
        
        stmt.close();
        
        stmt = connection.prepareStatement("SELECT * FROM (ReseptiRaaka_aine INNER JOIN Raaka_aine ON ReseptiRaaka_aine.raaka_aine_id = Raaka_aine.id)"
                + "INNER JOIN Resepti ON ReseptiRaaka_aine.resepti_id = Resepti.id "
                + "WHERE Resepti.id = ? "
                + "ORDER BY ReseptiRaaka_aine.jarjestys ASC;");
        
        stmt.setObject(1, key);
        
        List<Ingredient> raaka_aineet = new ArrayList<>();
        
        rs = stmt.executeQuery();
        
//        hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
        
        while(rs.next()){
            raaka_aineet.add(new Ingredient(rs.getInt("id"), rs.getString("nimi"), rs.getString("maara")));
        }
        

        Recipe r = new Recipe(id, nimi, valmistusaika, ohje, raaka_aineet);

        rs.close();
        stmt.close();
        connection.close();

        return r;
    }
    
    // Tämän voi poistaa, jos tietää tavan jolla recipes/add.html "Lisää raaka-aine reseptiin" lomakkeen pudotusvalikosta
    // saa request.QueryParams haulla palautettua reseptin id:n nimen sijaan. (kts. RecipeIngredientController addOne...)
//    public Recipe findOneByName(String name) throws SQLException {
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Resepti WHERE nimi = ?");
//        stmt.setObject(1, name);
//
//        ResultSet rs = stmt.executeQuery();
//        boolean hasOne = rs.next();
//        if (!hasOne) {
//            return null;
//        }
//
//        Integer id = rs.getInt("id");
//        String nimi = rs.getString("nimi");
//        Integer valmistusaika = rs.getInt("valmistusaika");
//        String ohje = rs.getString("ohje");
//
//        Recipe r = new Recipe(id, nimi, valmistusaika, ohje);
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return r;
//    }

    @Override
    public List<Recipe> findAll() throws SQLException {

        Connection connection = this.database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Resepti");

        ResultSet rs = stmt.executeQuery();
        List<Recipe> reseptit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Integer valmistusaika = rs.getInt("valmistusaika");
            String ohje = rs.getString("ohje");

            reseptit.add(new Recipe(id, nimi, valmistusaika, ohje, null));
        }

        rs.close();
        stmt.close();
        connection.close();

        return reseptit;
    }
    
    
    public Integer addOne(String nimi, Integer valmistusaika, String ohje) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Resepti(nimi, valmistusaika, ohje) VALUES (?, ?, ?);");
        
        stmt.setString(1, nimi);
        stmt.setInt(2, valmistusaika);
        stmt.setString(3, ohje);
        
        stmt.execute();
        
        stmt = connection.prepareStatement("SELECT currval('resepti_id_seq')");
        ResultSet rs = stmt.executeQuery();
        
        Integer recipe_id = 0;
        while(rs.next()){
            recipe_id = rs.getInt(1);
        }
        
        stmt.close();
        connection.close();
        
        return recipe_id;
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
