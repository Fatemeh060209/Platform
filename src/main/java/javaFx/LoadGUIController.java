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

    public Button søg;  // referenser til controls fra FXML-filen
    public Button back;
    public TextField idField;
    public TextArea TempLoad;

    public void LoadData(ActionEvent actionEvent) { //  Event Driving Programming

        // oprettes en ny variabel og tildeles værdien fra idField
        EkgDAO ekgDAO = new EkgDAOImplement(); // oprettes en objekt af TempImpl
        List<EkgDTO> tempData = ekgDAO.load(idField.getText()); // listen som indeholder tempDTO'er udfyldes af resultset der
        // hentes fra laode
        String text = "";
        for (EkgDTO data : tempData) { // for hvert element i listen tempData tilføjes elements data til stringen text
            text += "ID: " + data.getPatient_Id() + ", Time: " + data.getEKG_time() + ", Temperature: " + data.getEKG_voltage() + " °C" + "\r\n";
        }
        TempLoad.setText(text); // indsætter i text area
    }

    public void patientFolder(ActionEvent actionEvent) throws IOException { //   Event Driving Programming,
        // som skifter mellem scene
        Parent secondPaneLoader = FXMLLoader.load(getClass().getResource("/patientFolder.fxml"));
        Scene secondScene = new Scene(secondPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        primaryStage.setTitle("Patient's folder");
        primaryStage.show();
    }
}
