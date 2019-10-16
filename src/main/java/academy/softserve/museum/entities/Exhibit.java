package academy.softserve.museum.entities;

import java.util.List;

public class Exhibit extends Entity {

    private String name;
    private ExhibitType type;
    private String material;
    private String technique;
    private Audience audience;
    private List<Author> authors;

    public Exhibit(long id, ExhibitType type, String material, String technique, String name) {
        super(id);
        this.name = name;
        this.type = type;
        this.material = material;
        this.technique = technique;
    }

    public Exhibit(ExhibitType type, String material, String technique, String name) {
        this.name = name;
        this.type = type;
        this.material = material;
        this.technique = technique;
    }

    public Exhibit() {
    }

    public ExhibitType getType() {
        return type;
    }

    public Exhibit setType(ExhibitType type) {
        this.type = type;
        return this;
    }

    public String getMaterial() {
        return material;
    }

    public Exhibit setMaterial(String material) {
        this.material = material;
        return this;
    }

    public String getTechnique() {
        return technique;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Exhibit setTechnique(String technique) {
        this.technique = technique;
        return this;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
