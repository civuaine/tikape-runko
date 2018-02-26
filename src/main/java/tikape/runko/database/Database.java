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
//        lista.add("CREATE TABLE Resepti (id integer PRIMARY KEY, nimi varchar(255), valmistusaika integer, ohje varchar(1024));");
        lista.add("CREATE TABLE Raaka_aine (id integer PRIMARY KEY, nimi varchar(255), UNIQUE(nimi));");
//        lista.add("CREATE TABLE ReseptiRaaka_aine (resepti_id integer, raaka_aine_id integer, jarjestys integer, maara varchar(255), FOREIGN KEY (raaka_aine_id) REFERENCES Raaka_aine(id), FOREIGN KEY (resepti_id) REFERENCES Resepti(id));");
//        lista.add("CREATE TABLE Kategoria (id integer PRIMARY KEY, nimi varchar(255));");
//        lista.add("CREATE TABLE ReseptiKategoria (resepti_id integer, kategoria_id integer, FOREIGN KEY (kategoria_id) REFERENCES Kategoria(id), FOREIGN KEY (resepti_id) REFERENCES Resepti(id));");
        return lista;
    }

    public void sqliteLisaaTestiDataa() {
        List<String> lista = new ArrayList<>();
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Aamiainen');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Lounas');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Välipala');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Päivällinen');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Jälkiruoka');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Vegaani');");
        lista.add("INSERT INTO Kategoria(nimi) VALUES('Gluteeniton');");
        //Lisätään testidataa tietokantaan
//        lista.add("INSERT INTO Resepti(nimi, valmistusaika) VALUES('kalakeitto', 20)");
//        lista.add("INSERT INTO Resepti(nimi, valmistusaika) VALUES('pinaattikeitto', 15)");
//        lista.add("INSERT INTO Resepti(nimi, valmistusaika) VALUES('kaalilaatikko', 40)");
//        lista.add("INSERT INTO Resepti(nimi, valmistusaika) VALUES('tomaattisalaatti', 5)");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('fetajuusto')");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('jäävuorisalaatti')");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('sipuli')");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('mustapippuri')");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('valkosipuli')");
//        lista.add("INSERT INTO Raaka_aine(nimi) VALUES('ruokakerma')");
//        lista.add("INSERT INTO ReseptiRaaka_aine(resepti_id, raaka_aine_id, jarjestys, maara)"
//                + "VALUES (4, 4, 1, 200, 'silppuna, paloina, suikaleinta tjtn')");
//        lista.add("INSERT INTO ReseptiRaaka_aine(resepti_id, raaka_aine_id, jarjestys, maara)"
//                + "VALUES (4, 2, 2, 100, 'lohkoina')");

        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lista) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

}
