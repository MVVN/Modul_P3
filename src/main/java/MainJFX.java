import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * normally used to run with maven
 * but gives error: JavaFX runtime components are missing
 */
public class MainJFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = new KontoOberflaeche();
//        Scene scene = new Scene(root, 500, 500);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Konto");
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
