package academy.softserve.museum.entities;

public class Audience extends Entity {
    private String name;

    public Audience(long id, String name) {
        super(id);
        this.name = name;
    }

    public Audience(String name) {
        this.name = name;
    }

    public Audience() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "name='" + name + '\'' +
                '}';
    }
}
