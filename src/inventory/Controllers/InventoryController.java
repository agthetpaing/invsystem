package inventory.Controllers;


import inventory.Enums.Mode;
import inventory.Models.Inventory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    Inventory inventory = new Inventory();


    @FXML
    private TextField txtUname;

    @FXML
    private PasswordField pflPass;

    @FXML
    private ComboBox cboMode;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblInfo;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.cboMode.setItems(FXCollections.observableArrayList(Mode.values()));
    }

    @FXML
    public void login(ActionEvent event){
        try{
            if (this.inventory.isAuthenticated(this.txtUname.getText(), this.pflPass.getText(), this.cboMode.getValue().toString())){

                Stage stage = (Stage)this.btnLogin.getScene().getWindow();
                stage.close();
                try{
                    Stage userStage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    Pane root = loader.load(getClass().getResource("/inventory/Views/customerindex.fxml").openStream());


                    Scene scene = new Scene(root);
                    userStage.setScene(scene);
                    userStage.setTitle("Inventory System");
                    userStage.setResizable(false);
                    userStage.show();

                }
                catch(IOException e){
                    e.printStackTrace();
                }

            }
            else{
                this.lblInfo.setText("Wrong username/password. \nPlease try again.");
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
