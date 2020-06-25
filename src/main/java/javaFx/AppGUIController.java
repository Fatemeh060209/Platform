/** @author Osama*/
package javaFx;

import dataBase.EKG.EkgDAO;
import dataBase.EKG.EkgDAOImplement;
import dataBase.EKG.EkgDTO;
import dataBase.Patienter.PatienterDAO;
import dataBase.Patienter.PatienterDTO;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class AppGUIController implements EkgListener {

    public TextArea cprArea;
    public Button search;
    double x = 0;
    public Button logIn;
    public Button patientData;

    public Label pulsLabel;
    public Button pulsLoad;
    Instant start = Instant.now();

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
        Parent thirdPaneLoader = FXMLLoader.load(getClass().getResource("/Measurements.fxml"));
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
        boolean clear = false;
        List<Double> ekgPoints = new LinkedList<>();
        for (int i = 0; i < ekgDTOList.size(); i++) {
            EkgDTO ekgDTO = ekgDTOList.get(i);
            ekgPoints.add(x);
            ekgPoints.add((1500 - ekgDTO.getEKG_voltage()) / 10);
            ekgDTO.setPatient_id(Integer.parseInt(idEkg.getText()));
            x++;
        }
        if (x > 450) {
            new Thread(() -> {
                calculatePuls(ekgDTOList);
            }).start();
            x = 0;
            clear = true;
        }
        boolean finalClear = clear;
        Platform.runLater(() -> {
            if (finalClear) {
                ekgGraf.getPoints().clear();
            }
            ekgGraf.getPoints().addAll(ekgPoints);
        });
        new Thread(() -> {
            ekgDAO.savebatch(ekgDTOList);
        }).start();
    }

    private void calculatePuls(LinkedList<EkgDTO> ekgDTO) {
        double avg;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < ekgDTO.size(); i++) {
            if (ekgDTO.get(i).getEKG_voltage() < min) {
                min = ekgDTO.get(i).getEKG_voltage();
            }
            if (ekgDTO.get(i).getEKG_voltage() > max) {
                max = ekgDTO.get(i).getEKG_voltage();
            }
        }
        avg = 0.3 * min + 0.7 * max;
        boolean alreadyCounted = true;
        for (int i = 0; i < ekgDTO.size(); i++) {
            if (ekgDTO.get(i).getEKG_voltage() > avg) {
                if (!alreadyCounted) {
                    Timestamp ekg_time = ekgDTO.get(i).getEKG_time();
                    long diff = Duration.between(start, ekg_time.toInstant()).toMillis();
                    start = ekg_time.toInstant();
                    alreadyCounted = true;
                    double bpm = (60000.0 / diff) * 1.375;
                    PulsDTO pulsDTO = new PulsDTO();
                    pulsDTO.setPatient_id(Integer.parseInt(idEkg.getText()));
                    pulsDTO.setPuls_measurements(bpm);
                    pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
                    if (bpm > 20 && bpm < 300) {
                        Platform.runLater(() -> {
                            pulsLabel.setText(String.valueOf(pulsDTO.getPuls_measurements()));
                        });
                        pulsDAO.save(pulsDTO);
                    }
                    //System.out.println("bpm = " + bpm);
                } else {
                    alreadyCounted = false;
                }
            }
        }
    }

    public void search(ActionEvent actionEvent) {
        PatienterDAO patienterDAO = new PatienterDAO();
        List<PatienterDTO> patienterDTOList;
        patienterDTOList = patienterDAO.load();
        StringBuilder text = new StringBuilder();
        for (PatienterDTO patinterDTO : patienterDTOList) {
            text.append("ID: ").append(patinterDTO.getID()).append(",  Cpr: ").append(patinterDTO.getCpr()).append("\r\n");
        }
        cprArea.setText(text.toString());
    }
}
