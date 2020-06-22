package javaFx;

import dataBase.EKG.EkgDAO;
import dataBase.EKG.EkgDAOImplement;
import dataBase.EKG.EkgDTO;
import dataBase.Puls.PulsDAO;
import dataBase.Puls.PulsDAOImplement;
import dataBase.Puls.PulsDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LoadGUIController {

    public Button pulsSearch;  // referenser til controls fra FXML-filen
    public Button pulsBack;
    public TextField idPulsField;
    public TextArea plusLoad;

    public Button ekgBack;
    public Button ekgSearch;
    public TextField idEkgField;
    public Polyline ekgGraf;

    public void LoadEKGData(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            EkgDAO ekgDAO = new EkgDAOImplement();
            LinkedList<Double> ekgPoints = new LinkedList<>();
            List<EkgDTO> ekgDTOS = ekgDAO.load(idEkgField.getText());
            ekgGraf.getPoints().clear();
            for (int i = 0; i < ekgDTOS.size(); i++) {
                ekgPoints.add(Double.valueOf(i));
                ekgPoints.add((1500 - ekgDTOS.get(i).getEKG_voltage()) / 10);
            }
            ekgGraf.getPoints().addAll(ekgPoints);
        });
    }

    public void LoadPulsData(ActionEvent actionEvent) {
        PulsDAO pulsDAO = new PulsDAOImplement();
        List<PulsDTO> pulsDTOS = pulsDAO.load(idPulsField.getText());
        StringBuilder text = new StringBuilder();
        for (PulsDTO pulsDTO : pulsDTOS) {
            text.append("ID: ").append(pulsDTO.getPatient_id()).append(" , Time: ").append(pulsDTO.getPuls_time())
                    .append(" , Puls: ").append(pulsDTO.getPuls_measurements()).append("\r\n");
        }
        plusLoad.setText(text.toString());
    }

    public void patientData(ActionEvent actionEvent) throws IOException {
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
}
