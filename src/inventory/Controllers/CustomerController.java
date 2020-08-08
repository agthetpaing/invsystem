package inventory.Controllers;


import database.dbConnection;
import inventory.Models.*;
import inventory.Enums.State;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TextField  txtFName, txtLName, txtPhone,
            txtEmail, txtStreet, txtSuburb, txtPcode;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private ComboBox cboState;

    @FXML
    private TableColumn<Customer, String> colFName,
            colLName, colEmail, colPhone, colAddress;

    @FXML
    private Button btnProduct, btnPurchase, btnSupplier, btnSale;

    private dbConnection db;

    private ObservableList<Customer> custData;

    private String query = "SELECT * FROM Customer";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();

        this.cboState.setItems(FXCollections.observableArrayList(State.values()));

        loadData();

    }

    private void loadData(){
        this.custData = getCust();
        this.colFName.setCellValueFactory(new PropertyValueFactory<Customer, String>("fname"));
        this.colLName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lname"));
        this.colPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.colAddress.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Customer, String> param) {
                return new SimpleStringProperty(param.getValue().getStreet() + ",  \n"
                        + param.getValue().getSuburb() + ", " + param.getValue().getState() + " " + param.getValue().getPcode());
            }
        });

        this.tblCustomer.setItems(this.custData);

    }

    public ObservableList<Customer> getCust() {
        ObservableList<Customer> cData = FXCollections.observableArrayList();
        try {

            Connection conn = db.getConnection();
            cData = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                Customer cust;

                // polymorphically create each type of customer
                if (rs.getString("custstate").equals(State.VIC.name())) {
                    cust = new VIPCustomer(Integer.parseInt(rs.getString("custid")),rs.getString("custfname"), rs.getString("custlname"),
                            rs.getString("custphone"), rs.getString("custemail"), rs.getString("custstreet"),
                            rs.getString("custsuburb"), rs.getString("custstate"), rs.getString("custpcode"));
                    cData.add(cust);

                } else if (rs.getString("custstate").equals(State.NSW.name())) {
                    cust = new RegularCustomer(Integer.parseInt(rs.getString("custid")),rs.getString("custfname"), rs.getString("custlname"),
                            rs.getString("custphone"), rs.getString("custemail"), rs.getString("custstreet"),
                            rs.getString("custsuburb"), rs.getString("custstate"), rs.getString("custpcode"));
                    cData.add(cust);
                } else {
                    cust = new NewCustomer(Integer.parseInt(rs.getString("custid")),rs.getString("custfname"), rs.getString("custlname"),
                            rs.getString("custphone"), rs.getString("custemail"), rs.getString("custstreet"),
                            rs.getString("custsuburb"), rs.getString("custstate"), rs.getString("custpcode"));
                    cData.add(cust);
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return cData;
    }

    @FXML
    private void add(ActionEvent event){
        String insertQuery = "INSERT INTO Customer( custfname, custlname, custphone, " +
                "custemail, custstreet, custsuburb, custstate, custpcode )" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stment = conn.prepareStatement(insertQuery);

            stment.setString(1, this.txtFName.getText());
            stment.setString(2, this.txtLName.getText());
            stment.setString(3, this.txtPhone.getText());
            stment.setString(4, this.txtEmail.getText());
            stment.setString(5, this.txtStreet.getText());
            stment.setString(6, this.txtSuburb.getText());
            stment.setString(7, this.cboState.getValue().toString());
            stment.setString(8, this.txtPcode.getText());

            stment.execute();
            loadData();
            conn.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void viewProduct(ActionEvent event){
        Stage stage = (Stage)this.btnProduct.getScene().getWindow();
        stage.close();
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/inventory/Views/productindex.fxml").openStream());

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void viewPurchase(ActionEvent event){
        Stage stage = (Stage)this.btnPurchase.getScene().getWindow();
        stage.close();

        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/inventory/Views/purchaseindex.fxml").openStream());

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void viewSupplier(ActionEvent event){
        Stage stage = (Stage)this.btnSupplier.getScene().getWindow();
        stage.close();

        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/inventory/Views/supplierindex.fxml").openStream());

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void viewSale(ActionEvent event){
        Stage stage = (Stage)this.btnSale.getScene().getWindow();
        stage.close();

        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/inventory/Views/saleindex.fxml").openStream());

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }






}
