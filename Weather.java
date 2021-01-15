
package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Weather extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{                             //основной метод, где создается основное окно
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));   //загрузка fxml файла
        primaryStage.setTitle("Узнать погоду");
        primaryStage.setScene(new Scene(root, 270, 356));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);                          //метод,который запускает start

    }
}
