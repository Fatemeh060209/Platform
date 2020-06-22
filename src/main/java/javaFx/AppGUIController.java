package javaFx;

import dataBase.EKG.EkgDAO;
import dataBase.EKG.EkgDAOImplement;
import dataBase.EKG.EkgDTO;
import dataBase.Puls.PulsDAO;
import dataBase.Puls.PulsDAOImplement;
import dataBase.Puls.PulsDTO;
import ekgSensor.EkgListener;
import ekgSensor.ThreadPC;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AppGUIController implements EkgListener {


    double x = 0;
    public Button logIn;
    public Button patientData;

    public Label pulsLabel;
    public Button pulsLoad;

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

    public void loadEkg(ActionEvent actionEvent) throws IOException {
        Parent fourthPaneLoader = FXMLLoader.load(getClass().getResource("/EKGLoad.fxml"));
        Scene fourthScene = new Scene(fourthPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(fourthScene);
        primaryStage.setTitle("EKG Data Base");
        primaryStage.show();
    }

    public void loadPuls(ActionEvent actionEvent) throws IOException {
        Parent fifthPaneLoader = FXMLLoader.load(getClass().getResource("/PulsLoad.fxml"));
        Scene fifthScene = new Scene(fifthPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(fifthScene);
        primaryStage.setTitle("Puls Data Base");
        primaryStage.show();
    }

    public void EkgNotify(LinkedList<EkgDTO> ekgDTOList) {

        Platform.runLater(() -> {
            List<Double> ekgPoints = new LinkedList<>();
            for (int i = 0; i < ekgDTOList.size(); i++) {
                EkgDTO ekgDTO = ekgDTOList.get(i);
                ekgPoints.add(x);
                ekgPoints.add((1500 - ekgDTO.getEKG_voltage()) / 10);
                ekgDTO.setPatient_id(Integer.parseInt(idEkg.getText()));
                x++;
            }
            if (x >= 540) {
                for (int i = 0; i < ekgDTOList.size(); i++) {
                    calculatePuls(ekgDTOList.get(i).getEKG_voltage());
                }
                x = 0;
                ekgGraf.getPoints().clear();
            }
            ekgGraf.getPoints().addAll(ekgPoints);
        });
        new Thread(() -> {
            ekgDAO.savebatch(ekgDTOList);
        }).start();
    }

    private void calculatePuls(double ekgPoints) {

        double avg = 0;
        double beats = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = 1; i < ekgPoints.size(); i += 2) {
            avg += ekgPoints.get(i) / (ekgPoints.size() / 2);
            if (ekgPoints.get(i) < min) {
                min = ekgPoints.get(i);
            }
            if (ekgPoints.get(i) > max) {
                max = ekgPoints.get(i);
            }
            avg = 0.2 * min + 0.8 * max;
        }
        boolean alreadyCounted = true;
        for (int i = 1; i < ekgPoints.size(); i += 2) {
            if (ekgPoints.get(i) > avg) {
                if (!alreadyCounted) {
                    beats++;
                    alreadyCounted = true;
                }
            } else {
                alreadyCounted = false;
            }
        }
        double bpm = beats * 45;
        PulsDTO pulsDTO = new PulsDTO();
        //pulsDTO.setPatient_id(Integer.parseInt(idEkg.getText()));
        pulsDTO.setPuls_measurements(bpm);
        //pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
        pulsLabel.setText(String.valueOf(pulsDTO.getPuls_measurements()));
        //pulsDAO.save(pulsDTO);
        System.out.println("bpm = " + bpm);
    }
}
