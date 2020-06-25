/** @author Osama*/
package ekgSensor;

import dataBase.EKG.EkgDTO;

import java.util.LinkedList;

public interface EkgListener {
    void EkgNotify (LinkedList<EkgDTO> ekgDTO);
}
