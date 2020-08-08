package inventory.Controllers;


import database.dbConnection;
import inventory.Models.Category;
import inventory.Models.FreshProduct;
import inventory.Models.LongLastProduct;
import inventory.Models.Product;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductController implements Initializable{

    @FXML
    private TextField txtDesc, txtPrice, txtQuantity;

    @FXML
    private ComboBox cboCategory;

    @FXML
    private TableColumn<Product,String> colDesc, colPrice, colQuantity, colCategory;

    @FXML
    private Button btnCustomer, btnPurchase, btnSupplier, btnSale;

    @FXML
    private TableView<Product> tblProduct;

    private dbConnection db;

    private ObservableList<Product> prodData;

    private ObservableList<Category> categoryData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.db = new dbConnection();


        // populate category
        try{
            Connection conn = db.getConnection();
            categoryData = FXCollections.observableArrayList();
            String query = "SELECT * FROM Category";
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()){
                //categoryData.add(rs.getString("catdesc"));
                categoryData.add(new Category(Integer.parseInt(rs.getString("catid")), rs.getString("catdesc")));
            }
            this.cboCategory.setItems(FXCollections.observableArrayList(categoryData));
            this.cboCategory.setConverter(new StringConverter<Category>() {
                @Override
                public String toString(Category cat) {
                    return cat.getDesc();
                }

                @Override
                public Category fromString(String string) {
                    return null;
                }
            });

        }
        catch (SQLException e){
            e.printStackTrace();

        }
        loadData();

    }

    private void loadData(){

        this.prodData = getProd();

        this.colDesc.setCellValueFactory(new PropertyValueFactory<Product, String>("desc"));
        //this.colPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        this.colPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                return new SimpleStringProperty("$" + param.getValue().getPrice());
            }
        });
        this.colQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        this.colCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("catid"));

        this.tblProduct.setItems(this.prodData);
    }

    public ObservableList<Product> getProd(){
        ObservableList<Product> pData = FXCollections.observableArrayList();
        try{

            Connection conn = db.getConnection();
            pData = FXCollections.observableArrayList();

            String selQuery = "SELECT * FROM Product p LEFT JOIN Category c ON p.catid = c.catid";
            ResultSet rs = conn.createStatement().executeQuery(selQuery);
            Product product;

            while (rs.next()){

                if (Integer.parseInt(rs.getString("catid")) == Product.LONG_LIFE){
                    product = new LongLastProduct(Integer.parseInt(rs.getString("prodid")),rs.getString("proddesc"),
                            Integer.parseInt(rs.getString("prodqoh")),
                            Integer.parseInt(rs.getString("catid")), Double.parseDouble(rs.getString("prodprice")));
                    pData.add(product);

                }
                else if (Integer.parseInt(rs.getString("catid")) == Product.FRESH){
                    product = new FreshProduct(Integer.parseInt(rs.getString("prodid")),rs.getString("proddesc"), Integer.parseInt(rs.getString("prodqoh")),
                            Integer.parseInt(rs.getString("catid")), Double.parseDouble(rs.getString("prodprice")));
                    pData.add(product);

                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return pData;
    }


    @FXML
    private void add(ActionEvent event){

        String insertQuery = "INSERT INTO PRODUCT (proddesc, prodprice, catid, prodqoh)" +
                "VALUES (?,?,?,?)";

        try{
            Connection conn = db.getConnection();
            PreparedStatement stment = conn.prepareStatement(insertQuery);
            Category cat = (Category)this.cboCategory.getSelectionModel().getSelectedItem();

            stment.setString(1, this.txtDesc.getText());
            stment.setString(2, this.txtPrice.getText());
            stment.setString(3, String.valueOf(cat.getId()));
            stment.setString(4, this.txtQuantity.getText());

            stment.execute();
            loadData();
            conn.close();


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void reorder(Product product){
        String purQuery = "INSERT INTO PURCHASE (prodid, supid, qty, price) VALUES (?,?,?,?)";
        String proQuery = "UPDATE PRODUCT SET prodqoh = prodqoh + ? WHERE prodid = ?";

        try{

            Connection conn = db.getConnection();
            PreparedStatement stment = conn.prepareStatement(purQuery);
            stment.setString(1, String.valueOf(product.getProdid()));
            stment.setString(2, "1");
            stment.setString(3, String.valueOf(product.getQuantity()));
            stment.setString(4, String.valueOf(product.getPrice()));

            stment.execute();

            PreparedStatement proStment = conn.prepareStatement(proQuery);
            stment.setString(1, String.valueOf(product.REORDER_QTY));
            stment.setString(2, String.valueOf(product.getProdid()));

        }
        catch(SQLException e){
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
    public void viewSale(ActionEvent event){
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
