package car_collection;

import java.util.*;
import javax.swing.table.*;

class UserTable extends AbstractTableModel {

    private static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int POSTCODE_COL = 2;

    private String[] columnNames = {"ID", "Name", "Postcode"};
    private List<Users> users;

    public UserTable(List<Users> theUsers) {
        users = theUsers;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        Users tempUser = users.get(row);

        switch (col) {
            case ID_COL:
                return tempUser.getId();
            case NAME_COL:
                return tempUser.getName();
            case POSTCODE_COL:
                return tempUser.getPostcode();
            default:
                return tempUser.getName();
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}