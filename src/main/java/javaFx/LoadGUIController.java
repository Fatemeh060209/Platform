package javaFx;

import dataBase.EKG.EkgDAO;
import dataBase.EKG.EkgDAOImplement;
import dataBase.EKG.EkgDTO;
import dataBase.Puls.PulsDAO;
import dataBase.Puls.PulsDAOImplement;
import dataBase.Puls.PulsDTO;
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

    public Button pulsSearch;  // referenser til controls fra FXML-filen
    public Button pulsBack;
    public TextField idPulsField;
    public TextArea plusLoad;

    public Button ekgBack;
    public Button ekgSearch;
    public TextField idEkgField;
    public TextArea ekgLoad;

    public void LoadEKGData(ActionEvent actionEvent) { //  Event Driving Programming

        // oprettes en ny variabel og tildeles værdien fra idField
        EkgDAO ekgDAO = new EkgDAOImplement(); // oprettes en objekt af TempImpl
        List<EkgDTO> ekgDTOS = ekgDAO.load(idEkgField.getText()); // listen som indeholder tempDTO'er udfyldes af resultset der
        // hentes fra laode
        String text = "";
        for (EkgDTO ekgDTO : ekgDTOS) { // for hvert element i listen ekgDTOS tilføjes elements ekgDTO til stringen text
            text += "ID: " + ekgDTO.getPatient_Id() + ", Time: " + ekgDTO.getEKG_time() + ", EKG voltages: " + ekgDTO.getEKG_voltage() + " V" + "\r\n";
        }
        ekgLoad.setText(text); // indsætter i text area
    }

    public void LoadPulsData(ActionEvent actionEvent) { //  Event Driving Programming

        // oprettes en ny variabel og tildeles værdien fra idField
        PulsDAO pulsDAO = new PulsDAOImplement(); // oprettes en objekt af TempImpl
        List<PulsDTO> pulsDTOS = pulsDAO.load(idPulsField.getText()); // listen som indeholder tempDTO'er udfyldes af resultset der
        // hentes fra laode
        String text = "";
        for (PulsDTO pulsDTO : pulsDTOS) { // for hvert element i listen pulsDTOS tilføjes elements pulsDTO til stringen text
            text += "ID: " + pulsDTO.getPatient_ID() + ", Time: " + pulsDTO.getPuls_time() + ", Puls: " + pulsDTO.getPuls_Measurements() + "\r\n";
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
