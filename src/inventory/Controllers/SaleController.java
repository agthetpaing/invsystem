package inventory.Controllers;


import database.dbConnection;
import inventory.Models.Category;
import inventory.Models.Customer;
import inventory.Models.Product;
import inventory.Models.Sale;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    @FXML
    private TextField txtPrice, txtQuantity;

    @FXML
    private ComboBox cboProduct, cboCustomer;

    @FXML
    private Button btnCustomer, btnPurchase, btnSupplier, btnProduct;

    @FXML
    private TableColumn<Sale,String> colProd, colCust, colQuantity, colPrice;

    @FXML
    private TableView<Sale> tblSale;

    private dbConnection db;

    private ObservableList<Sale> saleData;

    private ObservableList<Product> prodData;

    private ObservableList<Customer> custData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.db = new dbConnection();

        ProductController prodController = new ProductController();
        prodData = prodController.getProd();

        this.cboProduct.setItems(prodData);
        this.cboProduct.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product prod) {
                return prod.getDesc();
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });


        CustomerController custController = new CustomerController();
        custData = custController.getCust();
        this.cboCustomer.setItems(custData);
        this.cboCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer cust) {
                return (cust.getFname() + " " + cust.getLname());
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });

        loadData();



    }

    private void loadData() {

        this.saleData = FXCollections.observableArrayList();
        try{
            Connection conn = db.getConnection();
            String selQuery = "SELECT * FROM SALE";
            ResultSet rs = conn.createStatement().executeQuery(selQuery);

            while (rs.next()){
                saleData.add(new Sale(Integer.parseInt(rs.getString("prodid")), Integer.parseInt(rs.getString("custid")),
                        Integer.parseInt(rs.getString("qty")), Double.parseDouble(rs.getString("price"))));
            }

            this.colProd.setCellValueFactory(new PropertyValueFactory<Sale, String>("prodid"));
            this.colCust.setCellValueFactory(new PropertyValueFactory<Sale, String>("custid"));
            this.colQuantity.setCellValueFactory(new PropertyValueFactory<Sale, String>("qty"));
            this.colPrice.setCellValueFactory(new PropertyValueFactory<Sale, String>("price"));

            this.tblSale.setItems(saleData);


        }
        catch(SQLException e){
            e.printStackTrace();

        }

    }


    @FXML
    private void add(ActionEvent event) {

        String insertQuery = "INSERT INTO SALE (prodid, custid, qty, price) " +
                "VALUES (?,?,?,?)";

        String prodQuery = "UPDATE PRODUCT SET prodqoh = prodqoh - ? WHERE prodid = ?";

        try{
            Connection conn = db.getConnection();
            PreparedStatement pstment = conn.prepareStatement(insertQuery);
            Product prod = (Product) this.cboProduct.getSelectionModel().getSelectedItem();
            Customer cust = (Customer) this.cboCustomer.getSelectionModel().getSelectedItem();
            pstment.setString(1, String.valueOf(prod.getProdid()));
            pstment.setString(2, String.valueOf(cust.getCustid()));
            pstment.setString(3, this.txtQuantity.getText());
            pstment.setString(4, this.txtPrice.getText());

            pstment.execute();

            PreparedStatement prodstment = conn.prepareStatement(prodQuery);
            prodstment.setString(1, this.txtQuantity.getText());
            prodstment.setString(2, String.valueOf(prod.getProdid()));

            prodstment.execute();

            loadData();
            conn.close();


        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void viewCustomer(ActionEvent event){
        Stage stage = (Stage)this.btnCustomer.getScene().getWindow();
        stage.close();
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("/inventory/Views/customerindex.fxml").openStream());

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
}
