package tikape.runko.recipe;

import tikape.runko.ingredient.Ingredient;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Recipe {

    private Integer id;
    private String nimi;
    private Integer valmistusaika;
    private String ohje;
    private Set<Ingredient> raaka_aineet;

    public Recipe(Integer id, String nimi, Integer valmistusaika, String ohje, Set<Ingredient> raaka_aineet) {
        this.id = id;
        this.nimi = nimi;
        this.valmistusaika = valmistusaika;
        this.ohje = ohje;
        this.raaka_aineet = raaka_aineet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValmistusaika() {
        return valmistusaika;
    }

    public void setValmistusaika(Integer valmistusaika) {
        this.valmistusaika = valmistusaika;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public String getOhje() {
        return ohje;
    }

    public void setOhje(String nimi) {
        this.ohje = ohje;
    }
    
    public Set<Ingredient> getRaaka_aineet() {
        return raaka_aineet;
    }

//    public void setOhje(String nimi) {
//        this.ohje = ohje;
//    }

}
