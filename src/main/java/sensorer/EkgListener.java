package sensorer;

import dataBase.EkgDTO;

import java.util.LinkedList;

public interface EkgListener {
    void EkgNotify (LinkedList<EkgDTO> ekgDTO);
}
