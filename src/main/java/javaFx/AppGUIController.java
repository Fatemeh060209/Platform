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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import sensorer.EKG.EkgListener;
import sensorer.EKG.ThreadPC;
import sensorer.Puls.PulsGenerator;
import sensorer.Puls.PulsListener;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AppGUIController implements PulsListener, EkgListener {

    double x = 0;
    public Button logIn;
    public Button patientData;

    public Button pulsScene;
    public Button pulsStart;
    public Button loadPuls;
    public Button backPuls;
    public Label pulsLabel;
    public TextField idPuls;
    public TextArea pulsDataOutput;

    public Button ekgStart;
    public Button backEkg;
    public Button loadEkg;
    public Button ekgScene;
    public TextField idEkg;
    public Polyline ekgGraf;

    public EkgDAO ekgDAO = new EkgDAOImplement();
    public PulsDAO pulsDAO = new PulsDAOImplement();

    public void ekgSampler(ActionEvent actionEvent) {
        ThreadPC threadPC = new ThreadPC(this);
        Thread thread = new Thread(threadPC);
        thread.start();
    }

    public void pulsSampler(ActionEvent actionEvent) {
        PulsGenerator pulsGenerator = new PulsGenerator();
        new Thread(pulsGenerator).start();
        pulsGenerator.registerObserver(this);
    }

    public void LogIn(ActionEvent actionEvent) throws IOException {
        Parent firstPaneLoader = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
        Scene firstScene = new Scene(firstPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
        primaryStage.setTitle("Sundhedsplatformen");
        primaryStage.show();
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
        Platform.runLater(() -> {
            List<Double> ekgPoints = new LinkedList<>();
            for (int i = 0; i < ekgDTOList.size(); i++) {
                EkgDTO ekgDTO = ekgDTOList.get(i);
                ekgPoints.add(x);
                ekgPoints.add((1000 - ekgDTO.getEKG_voltage()) / 10);
                ekgDTO.setPatient_id(Integer.parseInt(idEkg.getText()));
                if (x > 1000) {
                    x = 0;
                    ekgGraf.getPoints().clear();
                }
                x++;
            }
            ekgGraf.getPoints().addAll(ekgPoints);
            Platform.runLater(() -> {
                ekgDAO.savebatch(ekgDTOList);
            });
        });
    }

    public void PulsNotify(PulsDTO pulsDTO) {
        Platform.runLater(() -> {
            pulsLabel.setText(String.valueOf(pulsDTO.getPuls_measurements()));
            StringBuilder text = new StringBuilder();
            text.append(new StringBuilder().append("New Data! Puls: ").append(pulsDTO.getPuls_measurements())
                    .append(" , TimeStamp: ").append(pulsDTO.getPuls_time()).append("\r\n"));
            pulsDataOutput.setText(text.toString());
        });
        pulsDTO.setPatient_id(Integer.parseInt(idPuls.getText()));
        pulsDTO.setPuls_measurements(pulsDTO.getPuls_measurements());
        pulsDTO.setPuls_time(pulsDTO.getPuls_time());
        pulsDAO.save(pulsDTO);
    }
}
