package fi.helmarhelle.mobtsprojekti;


/**
 * @author Reima
 * @version 9.12.2021
 * @since 9.12.2021
 * <p>Luokka päivittäin täytettävien lomakkeiden luomiseksi oliomuotoon, jotka voidaan tallentaa Paivalomake-tietokantaan</p>
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

    //Getterit ja setterit
    public boolean isNukuttuTarpeeksi() {
        return nukuttuTarpeeksi;
    }

    public void setNukuttuTarpeeksi(boolean nukuttuTarpeeksi) {
        this.nukuttuTarpeeksi = nukuttuTarpeeksi;
    }

    public boolean isKaveltyTarpeeksi() {
        return kaveltyTarpeeksi;
    }

    public void setKaveltyTarpeeksi(boolean kaveltyTarpeeksi) {
        this.kaveltyTarpeeksi = kaveltyTarpeeksi;
    }

    public boolean isSyotyUlkona() {
        return syotyUlkona;
    }

    public void setSyotyUlkona(boolean syotyUlkona) {
        this.syotyUlkona = syotyUlkona;
    }

    public int getUniH() {
        return uniH;
    }

    public void setUniH(int uniH) {
        this.uniH = uniH;
    }

    public float getLiikuntaKM() {
        return liikuntaKM;
    }

    public void setLiikuntaKM(float liikuntaKM) {
        this.liikuntaKM = liikuntaKM;
    }

    public int getUlkonaSyonnitKPL() {
        return ulkonaSyonnitKPL;
    }

    public void setUlkonaSyonnitKPL(int ulkonaSyonnitKPL) {
        this.ulkonaSyonnitKPL = ulkonaSyonnitKPL;
    }

    public float getLenkkiKilometritKM() {
        return lenkkiKilometritKM;
    }

    public void setLenkkiKilometritKM(float lenkkiKilometritKM) {
        this.lenkkiKilometritKM = lenkkiKilometritKM;
    }

    public int getSaliKaynnitKPL() {
        return saliKaynnitKPL;
    }

    public void setSaliKaynnitKPL(int saliKaynnitKPL) {
        this.saliKaynnitKPL = saliKaynnitKPL;
    }
}
