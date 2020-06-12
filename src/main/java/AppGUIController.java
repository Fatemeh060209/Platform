/*import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.management.Sensor;

import java.io.IOException;

public class AppGUIController implements Listener {

    public Button logIn; // referenser til controllers fra FXML-filen
    public Button load;
    public Label tempLabel;
    public TextField id;
    public TextArea TempDataOutput;

    public EkgDAO tempDAO = new TempDAOImpl(); // opretter en objekt af klaseem TempDAO


    public void temp(ActionEvent actionEvent) { // Event Driving Programming
        Sensor sensor = new Sensor(); // her oprettes en ny objekt af klassen sensor
        new Thread(sensor).start(); // en ny tråd sættes igang
        sensor.register(this); // AppGUIcontroller registrerer sig som listener(observere) i sensoren
    }

    public void back(ActionEvent actionEvent) throws IOException { // Event Driving Programming som skifter mellem scene
        Parent firstPaneLoader = FXMLLoader.load(getClass().getResource("/logIn.fxml"));
        Scene firstScene = new Scene(firstPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
        primaryStage.setTitle("Sundhedsplatformen");
        primaryStage.show();
    }

    public void patientFolder(ActionEvent actionEvent) throws IOException { //  Event Driving Programming,
        // som skifter mellem scene
        Parent secondPaneLoader = FXMLLoader.load(getClass().getResource("/patientFolder.fxml"));
        Scene secondScene = new Scene(secondPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        primaryStage.setTitle("Patient's folder");
        primaryStage.show();
    }

    public void load(ActionEvent actionEvent) throws IOException {
        Parent thirdPaneLoader = FXMLLoader.load(getClass().getResource("/Load.fxml"));
        Scene thirdScene = new Scene(thirdPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(thirdScene);
        primaryStage.setTitle("Data Base");
        primaryStage.show();
    }

    public void EkgNotify(EkgDTO ekgDTO) {
        public void inform(final TempDTO tempDTO) { // Her implemeteres interfacens metode
            Platform.runLater(new Runnable() { // her bliver der udskrevet løbende temperatur og tid til
                // graiskbrugergrænseflade hvis der er værdier fra sensoren + gemme data i databasen.
                public void run() {
                    tempLabel.setText(String.valueOf(tempDTO.getTemp())); // her bliver værdien angivet som hentes fra DTO
                    String text = TempDataOutput.getText();
                    text += "New Data! Temp:" + tempDTO.getTemp() + " °C" + ", TimeStamp: " + tempDTO.getTid() + "\r\n";
                    TempDataOutput.setText(text); // her bliver der oprettet en variabel/text, og tildeles hvad der skal stå
                    // i text area
                }
            });
            TempDTO saveDTO = new TempDTO(); // der laves en ny objekt af klassen TempDTO
            saveDTO.setId(Integer.parseInt(id.getText())); // værdien fra ID/textField sættes ind i setID
            saveDTO.setTemp(tempDTO.getTemp());// Temperatur
            saveDTO.setTid(tempDTO.getTid()); // Tid
            tempDAO.save(saveDTO); // her gemmes der værdier fra TempDTO
        }


    }

    public void PulsNotify(PulsDTO pulsDTO) {

    }
}


 */