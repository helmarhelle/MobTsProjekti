package fi.helmarhelle.mobtsprojekti;

/**
 * @author Reima
 * @since 7.12.2021
 * @version 14.12.2021
 * <p>Luokka viikkotavoite-olion luomiseen, jotka voidaan tallentaa Viikkotavoite-tietokantaan.</p>
 */
public class Viikkotavoite {

    private int uniH;
    private int liikuntaKM;
    private int ulkonaSyonnitKPL;
    private int lenkitKM;
    private int saliKaynnitKPL;

    /**
     * <p>Tavallinen konstruktori joka asettaa arvot instanssimuuttujille.</p>
     * @param uniH  Tavoiteltu unen maara vuorokautta kohden (tunteina).
     * @param liikuntaKM    Tavoiteltu liikunnan maara kilometreina.
     * @param ulkonaSyonnitKPL  Tavoiteltu ulkonasyontien maara.
     * @param lenkitKM  Tavoiteltu lenkkien yhteispituus kilometreina.
     * @param saliKaynnitKPL    Tavoiteltu salikayntien maara.
     */
    public Viikkotavoite(int uniH, int liikuntaKM, int ulkonaSyonnitKPL, int lenkitKM, int saliKaynnitKPL) {
        this.uniH = uniH;
        this.liikuntaKM = liikuntaKM;
        this.ulkonaSyonnitKPL = ulkonaSyonnitKPL;
        this.lenkitKM = lenkitKM;
        this.saliKaynnitKPL = saliKaynnitKPL;
    }
    //Getterit ja setterit
    /**
     * <p>Getteri unen maaralle Viikkotavoitetietokantaa varten.</p>
     * @return uni tunteina.
     */
    public int getUniH() {
        return uniH;
    }
    /**
     * <p>Getteri liikunnan maaralle Viikkotavoitetietokantaa varten.</p>
     * @return Liikunnan maaran kokonaislukuna (Km).
     */
    public int getLiikuntaKM() {
        return liikuntaKM;
    }
    /**
     * <p>Getteri ulkonasyontien maaralle Viikkotavoitetietokantaa varten.</p>
     * @return Ulkonasyonnit kokonaislukuna.
     */
    public int getUlkonaSyonnitKPL() {
        return ulkonaSyonnitKPL;
    }
    /**
     * <p>Getteri lenkkien pituudelle Viikkotavoitetietokantaa varten.</p>
     * @return Lenkkien pituuden kokonaislukuna (Km).
     */
    public int getLenkitKM() {
        return lenkitKM;
    }
    /**
     * <p>Getteri salikayntien maaralle Viikkotavoitetietokantaa varten.</p>
     * @return Salikaynnit kokonaislukuna.
     */
    public int getSaliKaynnitKPL() {
        return saliKaynnitKPL;
    }
    /**
     * <p>Setteri senkkien pituudelle Viikkotavoitetietokantaa varten.</p>
     */
    public void setLenkitKM(int lenkitKM) {
        this.lenkitKM = lenkitKM;
    }
    /**
     * <p>Setteri salikayntien maaralle Viikkotavoitetietokantaa varten.</p>
     */
    public void setSaliKaynnitKPL(int saliKaynnitKPL) {
        this.saliKaynnitKPL = saliKaynnitKPL;
    }
}
