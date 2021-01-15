package sample;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;
import org.json.JSONObject;

/**сделать время,в зависимости от города*/

public class Controller {
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField city;

       @FXML
         private Text temp;

        @FXML
         private Text  time;

         @FXML
         private Text  date;

        @FXML
        private Button getWeather;

         @FXML
         private AnchorPane anchorpane;

    @FXML
        void Controller(ActionEvent event) {
        }

        @FXML
        void initialize() {
           getWeather.setOnAction(event -> {        //нажимаем на кнопку "Узнать погоду"
               String userCity = city.getText().trim();      //узнаем название города
             try {
                 String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + userCity + "&APPID=8a9e689683884a05f18680f065758b65&units=metric");
                    String dateNtime = getUrlContent("http://api.geonames.org/timezoneJSON?"+ getLatLng()+"&username=dwarfsobaka");
                 //System.out.println(dateNtime);
                 if (!output.isEmpty() ) {
                     JSONObject obj = new JSONObject(output);
                     JSONObject datetime = new JSONObject(dateNtime);

                     /*составляем строку, показывающую температуру. Из JSON берем поле "main". Оно показывает температуру*/
                     temp.setText("Температура: " + obj.getJSONObject("main").getInt("temp") + " \u00B0C");
                     time.setText("Время: " + datetime.getString("time").substring(11));
                     date.setText("Дата: " + datetime.getString("time").substring(0,10));

                            if (obj.getJSONObject("main").getInt("temp") > 30 ){
                                anchorpane.setStyle("-fx-background-color: #FFA07A;");
                            }
                            else if (obj.getJSONObject("main").getInt("temp") >= 18 && obj.getJSONObject("main").getInt("temp") <=30 ){
                                anchorpane.setStyle("-fx-background-color: #FFD700;");
                            }
                            else if (obj.getJSONObject("main").getInt("temp")>=5 && obj.getJSONObject("main").getInt("temp") < 18 ){
                                anchorpane.setStyle("-fx-background-color: #98FB98;");
                            }
                            else if (obj.getJSONObject("main").getInt("temp")<5 && obj.getJSONObject("main").getInt("temp") >= -10 ){
                                anchorpane.setStyle("-fx-background-color: #87CEEB;");
                            }
                            else if (obj.getJSONObject("main").getInt("temp") <-10 ){
                                anchorpane.setStyle("-fx-background-color: #4682B4;");
                            }
                 }
                // getLatLng();
             }
             catch (Exception e){
                 temp.setText("Такого города не существует!");
                temp.setStyle("-fx-text-alignment: center;");
                 time.setText("");
                 date.setText("");
             }

           });
        }
//            private static String setTime (){
//            SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm a");
//            return formatForDateNow.format(new Date());
//}
//            private String setDate(){
//                String userCity = city.getText().trim();
//                String str = String.format("Дата:  %td.%tm.%ty", new Date(), new Date(), new Date());
//                return str;
//}

/*получаем данные в формате JSON*/
              private static String getUrlContent (String urlAdress) throws Exception{     //передаем адрес сайта,с которого узнаем погоду
              StringBuffer content =  new StringBuffer();

              URL url = new URL(urlAdress);
              URLConnection urlCon = url.openConnection();
              BufferedReader buffresd = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));   //открываем поток на чтение данных с сайта

              String line;
              while ((line = buffresd.readLine()) != null){
                  content.append(line+ "\n");                  //составляем строку в формате JSON данных о погоде
                     }
                 buffresd.close();
                 return content.toString();
        }

/*получаем данные о координатах города*/
private String getLatLng () throws Exception{
    String userCity = city.getText().trim();
    String url = getUrlContent("http://api.geonames.org/searchJSON?name_equals=" + userCity + "&cities1500&username=dwarfsobaka");
   // System.out.println(url);

    Pattern r = Pattern.compile("lng\":\"-?\\d+\\p{Punct}\\d+");
    Matcher m = r.matcher(url);
    m.find();
    String lng = m.group().replace("\":\"", "=");	//сохраняем в строку lng
       // System.out.println(lng);

     r =  Pattern.compile("lat\":\"-?\\d+\\p{Punct}\\d+");
     m = r.matcher(url);
     m.find();
    String lat = m.group().replace("\":\"", "=");
        //System.out.println(lat);

    String latAndLng = lat + "&" + lng;
    //System.out.println(latAndLng)
    return latAndLng;

}
}
