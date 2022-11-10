package car_collection;

public class Users {

    private int id;
    private String name;
    private String postcode;

    public Users(int id, String name, String postcode) {
        super();
        this.id = id;
        this.name = name;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return String
                .format("Users [id=%s, name=%s, postcode=%s]",
                        id, name, postcode);
    }
}
