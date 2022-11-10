package car_collection;

public class Manufacturers {

    private int id;
    private String name;
    private String country;

    public Manufacturers(int id, String name, String country) {
        super();
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return String
                .format("Manufacturer [id=%s, name=%s, postcode=%s]",
                        id, name, country);
    }
}
