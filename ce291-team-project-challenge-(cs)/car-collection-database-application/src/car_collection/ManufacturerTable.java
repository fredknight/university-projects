package car_collection;

import java.util.*;
import javax.swing.table.*;

class ManufacturerTable extends AbstractTableModel {

    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int COUNTRY_COL = 2;

    private String[] columnNames = {"ID", "Name", "Country"};
    private List<Manufacturers> manufacturers;

    public ManufacturerTable(List<Manufacturers> theManufacturers) {
        manufacturers = theManufacturers;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return manufacturers.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Manufacturers tempManufacturer = manufacturers.get(row);

        switch (col) {
            case ID_COL:
                return tempManufacturer.getId();
            case NAME_COL:
                return tempManufacturer.getName();
            case COUNTRY_COL:
                return tempManufacturer.getCountry();
            default:
                return tempManufacturer.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}