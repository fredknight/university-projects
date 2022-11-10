package car_collection;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class CarTable extends AbstractTableModel {

    private static final int ID_COL = 0;
    private static final int MODEL_COL = 1;
    private static final int BODY_TYPE_COL = 2;
    private static final int BUILD_YEAR_COL = 3;
    private static final int RETAIL_PRICE_COL = 4;
    private static final int MANUFACTURER_ID_COL = 5;

    private String[] columnNames = {"ID", "Model", "Body Type", "Build Year", "Retail Price", "Manufacturer ID"};
    private List<Cars> cars;

    public CarTable(List<Cars> theCars) {
        cars = theCars;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return cars.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Cars tempCar = cars.get(row);

        switch (col) {
            case ID_COL:
                return tempCar.getId();
            case MODEL_COL:
                return tempCar.getModel();
            case BODY_TYPE_COL:
                return tempCar.getBodyType();
            case BUILD_YEAR_COL:
                return tempCar.getBuildYear();
            case RETAIL_PRICE_COL:
                return tempCar.getRetailPrice();
            case MANUFACTURER_ID_COL:
                return tempCar.getManufacturerID();
            default:
                return tempCar.getId();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}