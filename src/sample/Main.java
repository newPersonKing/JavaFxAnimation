package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Particles;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Particles particles = new Particles();
        primaryStage.setScene(new Scene(particles, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
