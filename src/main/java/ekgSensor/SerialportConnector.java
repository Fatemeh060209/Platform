/** @author Osama & Majd*/
package ekgSensor;

import dataBase.EKG.EkgDTO;
import jssc.SerialPort;
import jssc.SerialPortList;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SerialportConnector {
    private SerialPort serialPort = null;
    private String result = null;
    private String bufferString = "";

    public SerialportConnector(int portNumber) {
        String[] portnames = null;
        try {
            portnames = SerialPortList.getPortNames();
            serialPort = new SerialPort(portnames[portNumber]);
            serialPort.openPort();
            serialPort.setRTS(true);
            serialPort.setDTR(true);
            serialPort.setParams(115200, 8, 1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<EkgDTO> getData() {
        try {
            if (serialPort.getInputBufferBytesCount() >= 12) {
                result = serialPort.readString();
                bufferString += result;
                int lastIndex = bufferString.lastIndexOf(' ');
                result = bufferString.substring(0, lastIndex);
                bufferString = bufferString.substring(lastIndex);
                String[] rawValues;
                rawValues = result.split(" ");
                List<EkgDTO> values = new LinkedList<>();
                for (int i = 0; i < rawValues.length; i++) {
                    if (!Objects.equals(rawValues[i], "")) {
                        EkgDTO ekgDTO = new EkgDTO();
                        ekgDTO.setEKG_voltage(Double.parseDouble(rawValues[i]));
                        if (ekgDTO.getEKG_voltage() < 2048 && ekgDTO.getEKG_voltage() > -2048) {
                            ekgDTO.setEKG_time(new Timestamp(System.currentTimeMillis()));
                            values.add(ekgDTO);
                        }
                    }
                }
                return values;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}