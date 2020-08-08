package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = (Parent) FXMLLoader.load(getClass().getResource("Views/inventoryindex.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory System");
        primaryStage.show();

    }

    public static void main (String[] args){
        launch(args);
    }
}
