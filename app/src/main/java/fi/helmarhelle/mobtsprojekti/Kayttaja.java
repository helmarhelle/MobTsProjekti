package fi.helmarhelle.mobtsprojekti;

import androidx.annotation.NonNull;

public class Kayttaja {

    private int ika;
    private int pituusCM;
    private float painoKG;

    public Kayttaja(int ika, int pituusCM, float painoKG) {
        this.ika = ika;
        this.pituusCM = pituusCM;
        this.painoKG = painoKG;
    }

    //Getterit ja setterit
    public int getIka() {
        return ika;
    }

    public void setIka(int ika) {
        this.ika = ika;
    }

    public float getPainoKG() {
        return painoKG;
    }

    public void setPainoKG(int painoKG) {
        this.painoKG = painoKG;
    }

    public int getPituusCM() {
        return pituusCM;
    }

    public void setPituusCM(int pituusCM) {
        this.pituusCM = pituusCM;
    }

    @NonNull
    @Override
    public String toString() {
        return "Kayttaja{" +
                "ika=" + ika +
                ", paino=" + painoKG +
                ", pituus=" + pituusCM +
                '}';
    }
}
