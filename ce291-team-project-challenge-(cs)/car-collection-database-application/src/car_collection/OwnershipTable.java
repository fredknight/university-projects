package car_collection;

import java.util.*;
import javax.swing.table.*;

class OwnershipTable extends AbstractTableModel {

    private static final int ID_COL = 0;
    private static final int NUMBER_PLATE_COL = 1;
    private static final int USER_ID_COL = 2;
    private static final int CAR_ID_COL = 3;
    private static final int COLOUR_COL = 4;
    private static final int PURCHASE_PRICE_COL = 5;

    private String[] columnNames = {"ID", "Number Plate", "User ID", "Car ID", "Colour", "Purchase Price"};
    private List<Ownerships> ownerships;

    public OwnershipTable(List<Ownerships> theOwnerships) {
        ownerships = theOwnerships;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return ownerships.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Ownerships tempOwnership = ownerships.get(row);

        switch (col) {
            case ID_COL:
                return tempOwnership.getId();
            case NUMBER_PLATE_COL:
                return tempOwnership.getNumberPlate();
            case USER_ID_COL:
                return tempOwnership.getUserID();
            case CAR_ID_COL:
                return tempOwnership.getCarID();
            case COLOUR_COL:
                return tempOwnership.getColour();
            case PURCHASE_PRICE_COL:
                return tempOwnership.getPurchasePrice();
            default:
                return tempOwnership.getNumberPlate();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}