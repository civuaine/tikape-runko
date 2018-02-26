package tikape.runko.ingredient;

public class Ingredient {

    private Integer id;
    private String nimi;
    private String maara;

    public Ingredient(Integer id, String nimi, String maara) {
        this.id = id;
        this.nimi = nimi;
        this.maara = maara;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public String getMaara() {
        return maara;
    }

    public void setMaara(String nimi) {
        this.maara = maara;
    }

}
