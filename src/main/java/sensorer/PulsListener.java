package sensorer;

import dataBase.PulsDTO;

public interface PulsListener {
    void PulsNotify (PulsDTO pulsDTO);
}
