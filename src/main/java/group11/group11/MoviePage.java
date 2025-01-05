package group11.group11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MoviePage extends Application {
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/moviesearch.fxml"));

        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
