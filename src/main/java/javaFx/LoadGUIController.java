package javaFx;

import dataBase.EkgDAO;
import dataBase.EkgDAOImplement;
import dataBase.EkgDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoadGUIController {

    public Button PulsSøg;  // referenser til controls fra FXML-filen
    public Button pulsBack;
    public Button EKGBack;
    public Button EKGSøg;
    public TextField idPulsField;
    public TextArea plusLoad;
    public TextField idEKGPuls;
    public TextArea EKGLoad;


    public void LoadEKGData(ActionEvent actionEvent) { //  Event Driving Programming

        // oprettes en ny variabel og tildeles værdien fra idField
        EkgDAO ekgDAO = new EkgDAOImplement(); // oprettes en objekt af TempImpl
        List<EkgDTO> tempData = ekgDAO.load(idPulsField.getText()); // listen som indeholder tempDTO'er udfyldes af resultset der
        // hentes fra laode
        String text = "";
        for (EkgDTO data : tempData) { // for hvert element i listen tempData tilføjes elements data til stringen text
            text += "ID: " + data.getPatient_Id() + ", Time: " + data.getEKG_time() + ", Temperature: " + data.getEKG_voltage() + " °C" + "\r\n";
        }
        plusLoad.setText(text); // indsætter i text area
    }

    public void LoadPulsData(ActionEvent actionEvent) { //  Event Driving Programming

        // oprettes en ny variabel og tildeles værdien fra idField
        EkgDAO ekgDAO = new EkgDAOImplement(); // oprettes en objekt af TempImpl
        List<EkgDTO> tempData = ekgDAO.load(idPulsField.getText()); // listen som indeholder tempDTO'er udfyldes af resultset der
        // hentes fra laode
        String text = "";
        for (EkgDTO data : tempData) { // for hvert element i listen tempData tilføjes elements data til stringen text
            text += "ID: " + data.getPatient_Id() + ", Time: " + data.getEKG_time() + ", Temperature: " + data.getEKG_voltage() + " °C" + "\r\n";
        }
        plusLoad.setText(text); // indsætter i text area
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

}
