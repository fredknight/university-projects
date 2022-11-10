package car_collection;

public class Ownerships {

    private int id;
    private String numberPlate;
    private int userID;
    private int carID;
    private String colour;
    private int purchasePrice;

    public Ownerships(int id, String numberPlate, int userID, int carID, String colour, int purchasePrice) {
        super();
        this.id = id;
        this.numberPlate = numberPlate;
        this.userID = userID;
        this.carID = carID;
        this.colour = colour;
        this.purchasePrice = purchasePrice;
    }

    public int getId() {
        return id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public int getUserID() {
        return userID;
    }

    public int getCarID() {
        return carID;
    }

    public String getColour() {
        return colour;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public String toString() {
        return String
                .format("carOwnership [id=%s, numberPlate=%s, userID=%s, carID=%s, colour=%s, purchasePrice=%s]",
                        id, numberPlate, userID, carID, colour, purchasePrice);
    }
}
