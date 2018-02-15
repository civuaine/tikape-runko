package tikape.runko.recipe;

public class Recipe {

    private Integer id;
    private String nimi;
    private Double luokitus;
    private Integer valmistusaika;

    public Recipe(Integer id, String nimi, Double luokitus, Integer valmistusaika) {
        this.id = id;
        this.nimi = nimi;
        this.luokitus = luokitus;
        this.valmistusaika = valmistusaika;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLuokitus() {
        return luokitus;
    }

    public Integer getValmistusaika() {
        return valmistusaika;
    }

    public void setLuokitus(Double luokitus) {
        this.luokitus = luokitus;
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
