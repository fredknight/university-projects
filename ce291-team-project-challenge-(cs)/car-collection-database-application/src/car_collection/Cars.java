package car_collection;

public class Cars {

    private int id;
    private String model;
    private String bodyType;
    private int buildYear;
    private int retailPrice;
    private int manufacturerID;

    public Cars(int id, String model, String bodyType, int buildYear, int retailPrice, int manufacturerID) {
        super();
        this.id = id;
        this.model = model;
        this.bodyType = bodyType;
        this.buildYear = buildYear;
        this.retailPrice = retailPrice;
        this.manufacturerID = manufacturerID;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    @Override
    public String toString() {
        return String
                .format("carOwnership [id=%s, model=%s, bodyType=%s, buildYear=%s, retailPrice=%s, manufacturerID=%s]",
                        id, model, bodyType, buildYear, retailPrice, manufacturerID);
    }
}
