package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * @author Reima
 * @since 6.12.2021
 * @version 15.12.2021 <p>Tama aktiviteetti on vastuussa tietokantojen olemassaolon tarkistuksesta. Jos niita ei loydy, siirrytaan aina aktiviteettiin, jossa arvot asetetaan.</p>
 * <p>MainActivity toimii myos siirtymaAktiviteettina tietojen tallennusaktiviteettien valilla. Tama varmistaa, etta tietokannat loytyvat aina niita tarvitsevissa aktiviteeteissa.</p>
 */
public class MainActivity extends AppCompatActivity {

    Kayttajatietokanta kayttajatietokanta;
    Viikkotavoitetietokanta viikkotavoitetietokanta;
    Paivalomaketietokanta paivalomaketietokanta;

    /**
     * <p>Oncreatessa alustetaan tietokantaoliot.</p>
     * @param savedInstanceState Aktiviteetille intentin antama tietokasa.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayttajatietokanta = new Kayttajatietokanta(MainActivity.this);
        viikkotavoitetietokanta = new Viikkotavoitetietokanta(MainActivity.this);
        paivalomaketietokanta = new Paivalomaketietokanta(MainActivity.this);

    }

    /**
     * <p>Aina mainactivityn jatkuessa tarkistetaan vuorotellen, että tietokannat löytyvät ja lopulta palataan LomakeActivityyn.</p>
     */
    @Override protected void onResume () {
        super.onResume();
        //Jos käyttäjän tietoja ei löydy, avataan aktiviteetti, jossa ne syötetään.
        if (!kayttajatietokanta.onkoTietokantaa()) {
            Log.d("Käyttäjätietokanta", "Ei löydy! Siirrytään KayttajanTiedotaktiviteettiin...");
            Intent intent = new Intent(MainActivity.this, KayttajanTiedotActivity.class);
            startActivity(intent);
        }else {
            Log.d("Käyttäjätietokanta", "Löytyi!");
            Log.d("Käyttäjätietokanta", "Ikä: " + kayttajatietokanta.haeIka());
            Log.d("Käyttäjätietokanta", "Pituus: " + kayttajatietokanta.haePituus() + " cm");
            Log.d("Käyttäjätietokanta", "Paino: " + kayttajatietokanta.haePaino() + " Kg");

            //Jos viikon tavoitetta ei ole asetettu, siirrytään tavoitteenasetusaktiviteettiin
            if (!viikkotavoitetietokanta.onkoTavoitettaKuluvalleViikolle()) {
                Log.d("Viikkotavoitetietokanta", "Ei löydy! Siirrytään Tavoitteenasetusaktiviteettiin...");
                Intent intent = new Intent(MainActivity.this, TavoitteenasetusActivity.class);
                startActivity(intent);
            }else {
                Log.d("Viikkotavoitetietokanta", "Löytyi!");
                Log.d("Viikkotavoitetietokanta", "Unitavoite: " + viikkotavoitetietokanta.haeTamanViikonUniTavoite() + " h");
                Log.d("Viikkotavoitetietokanta", "Liikuntatavoite: " + viikkotavoitetietokanta.haeTamanViikonLiikuntaTavoite() + " Km");
                Log.d("Viikkotavoitetietokanta", "Ulkonasyöntitavoite: " + viikkotavoitetietokanta.haeTamanViikonUlkonasyonnitTavoite() + " Kpl");
                Log.d("Viikkotavoitetietokanta", "Lenkkitavoite: " + viikkotavoitetietokanta.haeTamanViikonLenkkiTavoite() + " Km");
                Log.d("Viikkotavoitetietokanta", "Salikäyntitavoite: " + viikkotavoitetietokanta.haeTamanViikonSaliTavoite()+ " Kpl");

                //Testataan Päivälomaketietokannan toimivuuttaa
                if (!paivalomaketietokanta.onkoLomakettaTallePaivalle()) {
                    Log.d("Paivalomaketietokanta", "Ei löydy merkintää tälle päivälle!");
                }else {
                    Log.d("Paivalomaketietokanta", "Löytyi!");
                    Log.d("Paivalomaketietokanta", "Unimäärä: " + paivalomaketietokanta.haeTamanPaivanUnenKesto() + " h");
                    Log.d("Paivalomaketietokanta", "Liikunnan määrä: " + paivalomaketietokanta.haeTamanPaivanLiikunnanPituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Ulkonasyöntejä: " + paivalomaketietokanta.haeTamanPaivanUlkonaSyonnit() + " Kpl");
                    Log.d("Paivalomaketietokanta", "Lenkin pituus: " + paivalomaketietokanta.haeTamanPaivanLenkinpituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Salikäyntejä: " + paivalomaketietokanta.haeTamanPaivanSaliKaynnit()+ " Kpl");


                    //Muita testejä
                    Log.d("KUUKAUDEN UNITAVOITTEET", "Viikko 1: " + viikkotavoitetietokanta.haeKuukaudenTavoitteet(12, "uni").get(0)+ " tuntia");
                    Log.d("VIIKON UNISAAVUTUKSET", "Päivä 1: " + paivalomaketietokanta.haeTamanViikonSaavutukset("uni", MainActivity.this).get(0)+ " tuntia");
                }
                //Siirrytään joka tapauksessa lomakeActivityyn:
                Intent intent = new Intent(MainActivity.this, LomakeActivity.class);
                startActivity(intent);
            }
        }


    }

}