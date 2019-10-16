package academy.softserve.museum.entities;

public class Excursion extends Entity {
    private String name;

    public Excursion(long id, String name) {
        super(id);
        this.name = name;
    }

    public Excursion(String name) {
        this.name = name;
    }

    public Excursion() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
