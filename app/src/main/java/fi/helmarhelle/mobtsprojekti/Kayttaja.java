package fi.helmarhelle.mobtsprojekti;

/**
 * @author Reima
 * @since 6.12.2021
 * @version 14.12.2021
 * <p>Luokka kayttaja-olion luomiseen, jotka voidaan tallentaa Kayttaja-tietokantaan.</p>
 */
public class Kayttaja {

    private int ika;
    private int pituusCM;
    private float painoKG;

    /**
     * <p>Tavallinen konstruktori joka asettaa arvot instanssimuuttujille.</p>
     * @param ika   Kayttajan ika vuosina.
     * @param pituusCM  Kayttajan pituus senttimetreina.
     * @param painoKG   Kayttajan paino kilogrammoina.
     */
    public Kayttaja(int ika, int pituusCM, float painoKG) {
        this.ika = ika;
        this.pituusCM = pituusCM;
        this.painoKG = painoKG;
    }

    //Getterit
    /**
     * <p>Getteri kayttajan ialle.</p>
     * @return  Palauttaa kayttajan ian kokonaislukuna.
     */
    public int getIka() {
        return ika;
    }
    /**
     * <p>Getteri kayttajan painolle.</p>
     * @return  Palauttaa kayttajan painon liukulukuna (Kg).
     */
    public float getPainoKG() {
        return painoKG;
    }
    /**
     * <p>Getteri kayttajan pituudelle.</p>
     * @return  Palauttaa kayttajan pituuden senttimetreina.
     */
    public int getPituusCM() {
        return pituusCM;
    }
}
