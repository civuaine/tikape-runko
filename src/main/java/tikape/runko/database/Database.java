package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Resepti (id integer PRIMARY KEY, nimi varchar(255), luokitus double, valmistusaika integer);");
        lista.add("CREATE TABLE Raaka_aine (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("CREATE TABLE ReseptiRaaka_aine (resepti_id integer, raaka_aine_id integer, jarjestys integer, maara varchar(10), lisaohje varchar(255), FOREIGN KEY (raaka_aine_id) REFERENCES Raaka_aine(id), FOREIGN KEY (resepti_id) REFERENCES Resepti(id));");
        lista.add("CREATE TABLE Kategoria (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("CREATE TABLE ReseptiKategoria (resepti_id integer, kategoria_id integer, FOREIGN KEY (kategoria_id) REFERENCES Kategoria(id), FOREIGN KEY (resepti_id) REFERENCES Resepti(id));");
        return lista;
    }

}
