package fi.helmarhelle.mobtsprojekti;


/**
 * @author Reima
 * @since 9.12.2021
 * @version 14.12.2021 <p>Luokka päivittäin täytettävien lomakkeiden luomiseksi oliomuotoon, jotka voidaan tallentaa Paivalomake-tietokantaan</p>
 */
public class PaivaLomake {

    //Booleanit kyllä/ei -kysymyksille
    private boolean nukuttuTarpeeksi;
    private boolean kaveltyTarpeeksi;
    private boolean syotyUlkona;

    //Tarkemmat tiedot tallennetaan kokonaislukuina
    private int uniH;
    private float liikuntaKM;
    private int ulkonaSyonnitKPL;
    private float lenkkiKilometritKM;
    private int saliKaynnitKPL;

    /**
     * <p>Tavallinen konstruktori joka asettaa arvot instanssimuuttujille.</p>
     * @param nukuttuTarpeeksi  Onko nukuttu tarpeeksi.
     * @param kaveltyTarpeeksi Onko kavelty tarpeeksi.
     * @param syotyUlkona   Onko syoty ulkona.
     * @param uniH  Unen maara (tunteina).
     * @param liikuntaKM    Liikunnan maara kilometreina.
     * @param ulkonaSyonnitKPL  Ulkonasyontien maara.
     * @param lenkkiKilometritKM  Lenkin yhteispituus kilometreina.
     * @param saliKaynnitKPL    Salikayntien maara.
     */
    public PaivaLomake(boolean nukuttuTarpeeksi, boolean kaveltyTarpeeksi, boolean syotyUlkona, int uniH, float liikuntaKM, int ulkonaSyonnitKPL, float lenkkiKilometritKM, int saliKaynnitKPL) {
        this.nukuttuTarpeeksi = nukuttuTarpeeksi;
        this.kaveltyTarpeeksi = kaveltyTarpeeksi;
        this.syotyUlkona = syotyUlkona;
        this.uniH = uniH;
        this.liikuntaKM = liikuntaKM;
        this.ulkonaSyonnitKPL = ulkonaSyonnitKPL;
        this.lenkkiKilometritKM = lenkkiKilometritKM;
        this.saliKaynnitKPL = saliKaynnitKPL;
    }

    //Getterit
    /**
     * <p>Getteri tarpeeksi nukkumiselle Paivalomaketietokantaa varten.</p>
     * @return onko nukuttu tarpeeksi vai ei.
     */
    public boolean isNukuttuTarpeeksi() {
        return nukuttuTarpeeksi;
    }
    /**
     * <p>Getteri tarpeeksi liikkumiselle Paivalomaketietokantaa varten.</p>
     * @return Onko liikuttu tarpeeksi vai ei.
     */
    public boolean isKaveltyTarpeeksi() {
        return kaveltyTarpeeksi;
    }
    /**
     * <p>Getteri ulkonasyonnille Paivalomaketietokantaa varten.</p>
     * @return Onko syoty ulkona vai ei.
     */
    public boolean isSyotyUlkona() {
        return syotyUlkona;
    }
    /**
     * <p>Getteri unen maaralle Paivalomaketietokantaa varten.</p>
     * @return uni tunteina.
     */
    public int getUniH() {
        return uniH;
    }
    /**
     * <p>Getteri liikunnan maaralle Paivalomaketietokantaa varten.</p>
     * @return Liikunnan maaran liukulukuna (Km).
     */
    public float getLiikuntaKM() {
        return liikuntaKM;
    }
    /**
     * <p>Getteri ulkonasyontien maaralle Paivalomaketietokantaa varten.</p>
     * @return Ulkonasyonnit kokonaislukuna.
     */
    public int getUlkonaSyonnitKPL() {
        return ulkonaSyonnitKPL;
    }
    /**
     * <p>Getteri lenkkien pituudelle Paivalomaketietokantaa varten.</p>
     * @return Lenkkien pituuden liukulukuna (Km).
     */
    public float getLenkkiKilometritKM() {
        return lenkkiKilometritKM;
    }
    /**
     * <p>Getteri salikayntien maaralle Paivalomaketietokantaa varten.</p>
     * @return Salikaynnit kokonaislukuna.
     */
    public int getSaliKaynnitKPL() {
        return saliKaynnitKPL;
    }

}
