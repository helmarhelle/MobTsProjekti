package fi.helmarhelle.mobtsprojekti;

public class Kayttaja {

    private int ika;
    private int paino;
    private int pituus;

    public Kayttaja(int ika, int paino, int pituus) {
        this.ika = ika;
        this.paino = paino;
        this.pituus = pituus;
    }

    //Getterit ja setterit
    public int getIka() {
        return ika;
    }

    public void setIka(int ika) {
        this.ika = ika;
    }

    public int getPaino() {
        return paino;
    }

    public void setPaino(int paino) {
        this.paino = paino;
    }

    public int getPituus() {
        return pituus;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }

    @Override
    public String toString() {
        return "Kayttaja{" +
                "ika=" + ika +
                ", paino=" + paino +
                ", pituus=" + pituus +
                '}';
    }
}
