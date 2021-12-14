package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Kayttajatietokanta kayttajatietokanta;
    Viikkotavoitetietokanta viikkotavoitetietokanta;
    Paivalomaketietokanta paivalomaketietokanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayttajatietokanta = new Kayttajatietokanta(MainActivity.this);
        viikkotavoitetietokanta = new Viikkotavoitetietokanta(MainActivity.this);
        paivalomaketietokanta = new Paivalomaketietokanta(MainActivity.this);

    }

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
                    Log.d("Paivalomaketietokanta", "Ei löydy! Asetetaan testiarvot...");

                    PaivaLomake paivaLomake = new PaivaLomake(false, true, false, 9,2.4f,2,3.2f,0);
                    paivalomaketietokanta.lisaaTiedot(paivaLomake, MainActivity.this);

                    Log.d("Paivalomaketietokanta", "Unimäärä: " + paivalomaketietokanta.haeTamanPaivanUnenKesto() + " h");
                    Log.d("Paivalomaketietokanta", "Liikunnan määrä: " + paivalomaketietokanta.haeTamanPaivanLiikunnanPituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Ulkonasyöntejä: " + paivalomaketietokanta.haeTamanPaivanUlkonaSyonnit() + " Kpl");
                    Log.d("Paivalomaketietokanta", "Lenkin pituus: " + paivalomaketietokanta.haeTamanPaivanLenkinpituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Salikäyntejä: " + paivalomaketietokanta.haeTamanPaivanSaliKaynnit()+ " Kpl");

                }else {
                    Log.d("Paivalomaketietokanta", "Löytyi!");
                    Log.d("Paivalomaketietokanta", "Unimäärä: " + paivalomaketietokanta.haeTamanPaivanUnenKesto() + " h");
                    Log.d("Paivalomaketietokanta", "Liikunnan määrä: " + paivalomaketietokanta.haeTamanPaivanLiikunnanPituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Ulkonasyöntejä: " + paivalomaketietokanta.haeTamanPaivanUlkonaSyonnit() + " Kpl");
                    Log.d("Paivalomaketietokanta", "Lenkin pituus: " + paivalomaketietokanta.haeTamanPaivanLenkinpituus() + " Km");
                    Log.d("Paivalomaketietokanta", "Salikäyntejä: " + paivalomaketietokanta.haeTamanPaivanSaliKaynnit()+ " Kpl");
                    Log.d("KUUKAUDEN UNITAVOITTEET", "Viikko 1: " + viikkotavoitetietokanta.haeKuukaudenTavoitteet(12, "uni").get(0)+ " tuntia");
                    Log.d("VIIKON UNISAAVUTUKSET", "Päivä 1: " + paivalomaketietokanta.haeTamanViikonSaavutukset("uni", MainActivity.this).get(0)+ " tuntia");




                }
            }
        }


    }

}