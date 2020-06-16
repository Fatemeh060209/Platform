package javaFx;

import dataBase.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import sensorer.EkgListener;
import sensorer.PulsGenerator;
import sensorer.PulsListener;
import sensorer.ThreadPC;

import java.io.IOException;
import java.util.LinkedList;

;

public class AppGUIController implements PulsListener, EkgListener {

    public Button logIn; // referenser til controllers fra FXML-filen
    public Button loadPuls;
    public Label pulsLabel;
    public TextField idPuls;
    public TextArea pulsDataOutput;

    public EkgDAO ekgDAO = new EkgDAOImplement(); // opretter en objekt af klaseem TempDAO
    public PulsDAO pulsDAO = new PulsDAOImplement();
    public TextField EKGID;
    public Button EkgStart;
    public Button BackEKG;
    public Button loadEkg;
    public Polyline EKGGraf;
    public Button LogIn;
    public Button EKGScene;
    public Button PulsScene;
    public Button pulsStart;
    public TextArea ekgDataOutput;
    public Button BackPuls;

    public void ekgSampler(ActionEvent actionEvent) { // Event Driving Programming
        ThreadPC threadPC = new ThreadPC(this);
        Thread thread = new Thread(threadPC);
        thread.start();
    }

    public void pulsSampler(ActionEvent actionEvent) {
        PulsGenerator pulsGenerator = new PulsGenerator();
        new Thread(pulsGenerator).start();
        pulsGenerator.registerObserver(this);
    }

    public void LogIn(ActionEvent actionEvent) throws IOException { // Event Driving Programming som skifter mellem scene
        Parent firstPaneLoader = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
        Scene firstScene = new Scene(firstPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
        primaryStage.setTitle("Sundhedsplatformen");
        primaryStage.show();
    }

    public void patientData(ActionEvent actionEvent) throws IOException { //  Event Driving Programming,
        // som skifter mellem scene
        Parent secondPaneLoader = FXMLLoader.load(getClass().getResource("/PatientData.fxml"));
        Scene secondScene = new Scene(secondPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        primaryStage.setTitle("Patient's Data");
        primaryStage.show();
    }

    public void ekgScene(ActionEvent actionEvent) throws IOException {
        Parent thirdPaneLoader = FXMLLoader.load(getClass().getResource("/EKG.fxml"));
        Scene thirdScene = new Scene(thirdPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(thirdScene);
        primaryStage.setTitle("EKG");
        primaryStage.show();
    }

    public void pulsScene(ActionEvent actionEvent) throws IOException {
        Parent fourthPaneLoader = FXMLLoader.load(getClass().getResource("/Puls.fxml"));
        Scene fourthScene = new Scene(fourthPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(fourthScene);
        primaryStage.setTitle("Puls");
        primaryStage.show();
    }

    public void loadEkg(ActionEvent actionEvent) throws IOException {
        Parent fifthPaneLoader = FXMLLoader.load(getClass().getResource("/EKGLoad.fxml"));
        Scene fifthScene = new Scene(fifthPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(fifthScene);
        primaryStage.setTitle("EKG Data Base");
        primaryStage.show();
    }
    public void loadPuls(ActionEvent actionEvent) throws IOException {
        Parent sixthPaneLoader = FXMLLoader.load(getClass().getResource("/PulsLoad.fxml"));
        Scene sixthScene = new Scene(sixthPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(sixthScene);
        primaryStage.setTitle("Puls Data Base");
        primaryStage.show();
    }

    public void EkgNotify(LinkedList<EkgDTO> ekgDTOList) {
        // Her implemeteres interfacens metode
        Platform.runLater(new Runnable() { // her bliver der udskrevet løbende temperatur og tid til
            // graiskbrugergrænseflade hvis der er værdier fra sensoren + gemme data i databasen.
            public void run() {
                for (int i = 0; i < ekgDTOList.size(); i++) {
                    EkgDTO ekgDTO = ekgDTOList.get(i);
                    pulsLabel.setText(String.valueOf(ekgDTO.getEKG_voltage())); // her bliver værdien angivet som hentes fra DTO
                    String text = pulsDataOutput.getText();
                    text += "New Data! Temp:" + ekgDTO.getEKG_voltage() + " °C" + ", TimeStamp: " + ekgDTO.getEKG_time() + "\r\n";
                    pulsDataOutput.setText(text); // her bliver der oprettet en variabel/text, og tildeles hvad der skal stå
                    // i text area
                    ekgDTO.setPatient_Id(Integer.parseInt(idPuls.getText())); // værdien fra ID/textField sættes ind i setID
                }
                ekgDAO.savebatch(ekgDTOList); // her gemmes der værdier fra TempDTO
            }
        });
    }

    public void PulsNotify(PulsDTO pulsDTO) {
        Platform.runLater(new Runnable() { // her bliver der udskrevet løbende temperatur og tid til
            // graiskbrugergrænseflade hvis der er værdier fra sensoren + gemme data i databasen.
            public void run() {
                pulsLabel.setText(String.valueOf(pulsDTO.getPuls_Målinger())); // her bliver værdien angivet som hentes fra DTO
                String text = pulsDataOutput.getText();
                text += "New Data! Temp:" + pulsDTO.getPuls_Målinger() + " °C" + ", TimeStamp: " + pulsDTO.getPuls_time() + "\r\n";
                pulsDataOutput.setText(text); // her bliver der oprettet en variabel/text, og tildeles hvad der skal stå
                // i text area
            }
        });
        pulsDTO.setPatient_ID(Integer.parseInt(idPuls.getText())); // værdien fra ID/textField sættes ind i setID
        pulsDTO.setPuls_Målinger(pulsDTO.getPuls_Målinger());// Temperatur
        pulsDTO.setPuls_time(pulsDTO.getPuls_time()); // Tid
        pulsDAO.save(pulsDTO); // her gemmes der værdier fra TempDTO
    }
}
