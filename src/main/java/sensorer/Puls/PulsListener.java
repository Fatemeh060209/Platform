package sensorer.Puls;

import dataBase.Puls.PulsDTO;

public interface PulsListener {
    void PulsNotify (PulsDTO pulsDTO);
}
