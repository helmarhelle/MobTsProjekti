package fi.helmarhelle.mobtsprojekti;

/**
 * @author Reima
 * @version 7.12.2021
 * @since 7.12.2021
 * <p>Luokka viikkotavoite-olion luomiseen, jotka voidaan tallentaa viikkotavoite-tietokantaan</p>
 */
public class Viikkotavoite {

    private int uniH;
    private int liikuntaKM;
    private int ulkonaSyonnitKPL;
    private int lenkitKM;
    private int saliKaynnitKPL;

    //Konstruktori
    public Viikkotavoite(int uniH, int liikuntaKM, int ulkonaSyonnitKPL, int lenkitKM, int saliKaynnitKPL) {
        this.uniH = uniH;
        this.liikuntaKM = liikuntaKM;
        this.ulkonaSyonnitKPL = ulkonaSyonnitKPL;
        this.lenkitKM = lenkitKM;
        this.saliKaynnitKPL = saliKaynnitKPL;
    }

    //Getterit ja setterit
    public int getUniH() {
        return uniH;
    }

    public void setUniH(int uniH) {
        this.uniH = uniH;
    }

    public int getLiikuntaKM() {
        return liikuntaKM;
    }

    public void setLiikuntaKM(int liikuntaKM) {
        this.liikuntaKM = liikuntaKM;
    }

    public int getUlkonaSyonnitKPL() {
        return ulkonaSyonnitKPL;
    }

    public void setUlkonaSyonnitKPL(int ulkonaSyonnitKPL) {
        this.ulkonaSyonnitKPL = ulkonaSyonnitKPL;
    }

    public int getLenkitKM() {
        return lenkitKM;
    }

    public void setLenkitKM(int lenkitKM) {
        this.lenkitKM = lenkitKM;
    }

    public int getSaliKaynnitKPL() {
        return saliKaynnitKPL;
    }

    public void setSaliKaynnitKPL(int saliKaynnitKPL) {
        this.saliKaynnitKPL = saliKaynnitKPL;
    }
}
