package sensorer;

import dataBase.EkgDTO;
import jssc.SerialPort;
import jssc.SerialPortList;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class SerialportConnector {
    private SerialPort serialPort = null;
    private String result = null;

    public SerialportConnector(int portNummer) {
        //konstruktør oprettes
        String[] portnames = null;//oprettelse af StringArray
        try {
            portnames = SerialPortList.getPortNames();//her hentes navnene til portene der er tilkoblet computeren
            serialPort = new SerialPort(portnames[portNummer]);//objektet serialPort tildeles den første port
            serialPort.openPort();//porten åbnes
            serialPort.setRTS(true);//klar til at sende(ReadyToSend = true)
            serialPort.setDTR(true);//klar til at modtage(DataToReceive = true)
            serialPort.setParams(115200, 8, 1, SerialPort.PARITY_NONE);//parametre bestemmes
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);//kontrolere flowet af data
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<EkgDTO> getData() {//metoden oprettes
        try {
            if (serialPort.getInputBufferBytesCount() >= 12) {//kontrolstruktur
                result = serialPort.readString();//strengen aflæses og tildeles result
                String[] rawValues;
                if (result != null && result.charAt(result.length() - 1) == ' ') {//result kontroleres
                    result = result.substring(0, result.length() - 1);//her fjernes det sidste index(#)
                    rawValues = result.split(" ");//nu splittes strengen og gemmes i et array
                    List<EkgDTO> values = new LinkedList<>();
                    for (int i = 0; i < rawValues.length; i++) {
                        EkgDTO ekgDTO = new EkgDTO();
                        ekgDTO.setEKG_voltage(Integer.parseInt(rawValues[i]));
                        ekgDTO.setEKG_time(new Timestamp(System.currentTimeMillis()));
                        values.add(ekgDTO);
                    }
                    return values;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//returnArray returneres
    }
}