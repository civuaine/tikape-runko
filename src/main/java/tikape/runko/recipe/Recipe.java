package tikape.runko.recipe;

public class Recipe {

    private Integer id;
    private String nimi;
    private Integer valmistusaika;
    private String ohje;

    public Recipe(Integer id, String nimi, Integer valmistusaika, String ohje) {
        this.id = id;
        this.nimi = nimi;
        this.valmistusaika = valmistusaika;
        this.ohje = ohje;
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

}
