package tikape.runko.recipe;

public class Recipe {

    private Integer id;
    private String nimi;
    private Integer valmistusaika;

    public Recipe(Integer id, String nimi, Integer valmistusaika) {
        this.id = id;
        this.nimi = nimi;
        this.valmistusaika = valmistusaika;
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

}
