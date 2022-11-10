package car_collection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.border.*;

public class CarCollectionApplication extends JFrame {
    private JPanel userPane;
    private JPanel carPane;
    private JPanel manufacturerPane;
    private JPanel ownershipPane;
    private JTextField userNameTextField;
    private JTextField carIdTextField;
    private JTextField manufacturerNameTextField;
    private JTextField ownershipIdTextField;
    private JButton btnUserSearch;
    private JButton btnCarSearch;
    private JButton btnCarCreate;
    private JButton btnManufacturerSearch;
    private JButton btnOwnershipSearch;
    private JScrollPane userScrollPane;
    private JScrollPane carScrollPane;
    private JScrollPane manufacturerScrollPane;
    private JScrollPane ownershipScrollPane;
    private JTable userTable;
    private JTable carTable;
    private JTable manufacturerTable;
    private JTable ownershipTable;

    private JTabbedPane tabbedPane;

    private DataConnect dataConnect;

    public static void main(String[] args) {
        CarCollectionApplication frame = new CarCollectionApplication();
        frame.setVisible(true);
    }

    public CarCollectionApplication() {
        dataConnect = new DataConnect();

        setTitle("Car Collection Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 500, 350);

        //USER----------------------------------------------------------------------------------------------------------

        userPane = new JPanel();
        userPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        userPane.setLayout(new BorderLayout(0, 0));

        JPanel uPanel = new JPanel();
        FlowLayout uFlowLayout = (FlowLayout) uPanel.getLayout();
        uFlowLayout.setAlignment(FlowLayout.LEFT);
        userPane.add(uPanel, BorderLayout.NORTH);

        JLabel lblUserName = new JLabel("Enter Name");
        uPanel.add(lblUserName);

        userNameTextField = new JTextField();
        uPanel.add(userNameTextField);
        userNameTextField.setColumns(10);

        btnUserSearch = new JButton("Search");
        btnUserSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = userNameTextField.getText();
                    List<Users> users = null;
                    if (name != null && name.trim().length() > 0) {
                        users = dataConnect.searchUsers(name);
                    } else {
                        users = dataConnect.getAllUsers();
                    }
                    UserTable userModel = new UserTable(users);
                    userTable.setModel(userModel);
                } catch (Exception er) {
                    System.out.println(er);
                }
            }
        });

        uPanel.add(btnUserSearch);

        userScrollPane = new JScrollPane();
        userPane.add(userScrollPane, BorderLayout.CENTER);

        userTable = new JTable();
        userScrollPane.setViewportView(userTable);

        //CAR-----------------------------------------------------------------------------------------------------------

        carPane = new JPanel();
        carPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        carPane.setLayout(new BorderLayout(0, 0));

        JPanel cPanel = new JPanel();
        FlowLayout cFlowLayout = (FlowLayout) cPanel.getLayout();
        cFlowLayout.setAlignment(FlowLayout.LEFT);
        carPane.add(cPanel, BorderLayout.NORTH);

        JLabel lblCarID = new JLabel("Enter ID");
        cPanel.add(lblCarID);

        carIdTextField = new JTextField();
        cPanel.add(carIdTextField);
        carIdTextField.setColumns(10);

        btnCarSearch = new JButton("Search");
        btnCarCreate = new JButton("Create");
        btnCarSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = carIdTextField.getText();
                    List<Cars> cars = null;
                    if (id != null && id.trim().length() > 0) {
                        cars = dataConnect.searchCars(id);
                    } else {
                        cars = dataConnect.getAllCars();
                    }
                    CarTable carModel = new CarTable(cars);
                    carTable.setModel(carModel);
                } catch (Exception er) {
                    System.out.println(er);
                }
            }
        });
        btnCarCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Create();
                } catch (Exception er) {
                    System.out.println(er);
                }
            }
        });

        cPanel.add(btnCarSearch);
        cPanel.add(btnCarCreate);

        carScrollPane = new JScrollPane();
        carPane.add(carScrollPane, BorderLayout.CENTER);

        carTable = new JTable();
        carScrollPane.setViewportView(carTable);

        //MANUFACTURER--------------------------------------------------------------------------------------------------

        manufacturerPane = new JPanel();
        manufacturerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        manufacturerPane.setLayout(new BorderLayout(0, 0));

        JPanel mPanel = new JPanel();
        FlowLayout mFlowLayout = (FlowLayout) mPanel.getLayout();
        mFlowLayout.setAlignment(FlowLayout.LEFT);
        manufacturerPane.add(mPanel, BorderLayout.NORTH);

        JLabel lblManufacturerName = new JLabel("Enter Name");
        mPanel.add(lblManufacturerName);

        manufacturerNameTextField = new JTextField();
        mPanel.add(manufacturerNameTextField);
        manufacturerNameTextField.setColumns(10);

        btnManufacturerSearch = new JButton("Search");
        btnManufacturerSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = manufacturerNameTextField.getText();
                    List<Manufacturers> manufacturers = null;
                    if (name != null && name.trim().length() > 0) {
                        manufacturers = dataConnect.searchManufacturers(name);
                    } else {
                        manufacturers = dataConnect.getAllManufacturers();
                    }
                    ManufacturerTable manufacturerModel = new ManufacturerTable(manufacturers);
                    manufacturerTable.setModel(manufacturerModel);
                } catch (Exception er) {
                    System.out.println(er);
                }
            }
        });

        mPanel.add(btnManufacturerSearch);

        manufacturerScrollPane = new JScrollPane();
        manufacturerPane.add(manufacturerScrollPane, BorderLayout.CENTER);

        manufacturerTable = new JTable();
        manufacturerScrollPane.setViewportView(manufacturerTable);

        //OWNERSHIPS----------------------------------------------------------------------------------------------------

        ownershipPane = new JPanel();
        ownershipPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ownershipPane.setLayout(new BorderLayout(0, 0));

        JPanel oPanel = new JPanel();
        FlowLayout oFlowLayout = (FlowLayout) oPanel.getLayout();
        oFlowLayout.setAlignment(FlowLayout.LEFT);
        ownershipPane.add(oPanel, BorderLayout.NORTH);

        JLabel lblUserID = new JLabel("Enter User ID");
        oPanel.add(lblUserID);

        ownershipIdTextField = new JTextField();
        oPanel.add(ownershipIdTextField);
        ownershipIdTextField.setColumns(10);

        btnOwnershipSearch = new JButton("Search");
        btnOwnershipSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = ownershipIdTextField.getText();
                    List<Ownerships> ownerships = null;
                    if (id != null && id.trim().length() > 0) {
                        ownerships = dataConnect.searchOwnerships(id);
                    } else {
                        ownerships = dataConnect.getAllOwnerships();
                    }
                    OwnershipTable ownershipModel = new OwnershipTable(ownerships);
                    ownershipTable.setModel(ownershipModel);
                } catch (Exception er) {
                    System.out.println(er);
                }
            }
        });

        oPanel.add(btnOwnershipSearch);

        ownershipScrollPane = new JScrollPane();
        ownershipPane.add(ownershipScrollPane, BorderLayout.CENTER);

        ownershipTable = new JTable();
        ownershipScrollPane.setViewportView(ownershipTable);

        //TABS----------------------------------------------------------------------------------------------------------

        tabbedPane = new JTabbedPane();

        tabbedPane.add("user", userPane);
        tabbedPane.add("ownership", ownershipPane);
        tabbedPane.add("car", carPane);
        tabbedPane.add("manufacturer", manufacturerPane);

        add(tabbedPane);
    }

    public void Create() {
        JTextField modelField = new JTextField(5);
        JTextField bodyTypeField = new JTextField(5);
        JTextField buildYearField = new JTextField(5);
        JTextField retailPriceField = new JTextField(5);
        JTextField manufacturerIDField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Model (e.g. T-100):"));
        myPanel.add(modelField);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Body Type (e.g. Saloon):"));
        myPanel.add(bodyTypeField);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Build Year (e.g. 2020):"));
        myPanel.add(buildYearField);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Retail Price (e.g. 6000)"));
        myPanel.add(retailPriceField);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(new JLabel("Manufacturer ID (Found in 'Manufacturers' Tab"));
        myPanel.add(manufacturerIDField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Car Details", JOptionPane.OK_CANCEL_OPTION);

        String model = modelField.getText();
        String bodyType = bodyTypeField.getText();
        int buildYear = Integer.parseInt(buildYearField.getText());
        int retailPrice = Integer.parseInt(retailPriceField.getText());
        int manufacturerID = Integer.parseInt(manufacturerIDField.getText());

        if (result == JOptionPane.OK_OPTION) {
            dataConnect.createCar(model, bodyType, buildYear, retailPrice, manufacturerID);
        }
    }

}
