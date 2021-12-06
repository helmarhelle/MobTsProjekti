package fi.helmarhelle.mobtsprojekti;

import android.content.Context;

public class Dataolio {


    //Dataolion sisäiset oliot
    Kayttajatietokanta Kayttajatk;


    private static final Dataolio ourInstance = new Dataolio();

    public static Dataolio getInstance() { return ourInstance; }

    private Dataolio () {

        haeKayttajaTietokanta();

    }

    //Käyttäjätietokannan hakumetodi
    private void haeKayttajaTietokanta () {

    }


    //Käyttäjätietokannan luomismetodi
    public boolean luoKayttajaTietokanta (Context context, int ika, int pituusCM, float painoKG) {
        Kayttaja kayttaja = new Kayttaja(ika, pituusCM, painoKG);
        Kayttajatietokanta kayttajatietokanta = new Kayttajatietokanta (context);

        if(kayttajatietokanta.lisaaTiedot(kayttaja)) {
            return true;
        }

        return false;
    }


}
