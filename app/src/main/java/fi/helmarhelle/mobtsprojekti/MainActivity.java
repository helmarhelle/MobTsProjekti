package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * <p>Tama aktiviteetti on vastuussa tietokantojen olemassaolon tarkistuksesta. Jos niita ei loydy, siirrytaan aina aktiviteettiin, jossa arvot asetetaan.</p>
 * <p>MainActivity toimii myos siirtymaAktiviteettina tietojen tallennusaktiviteettien valilla. Tama varmistaa, etta tietokannat loytyvat aina niita tarvitsevissa aktiviteeteissa.</p>
 * @author Reima
 * @since 6.12.2021
 * @version 15.12.2021
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
     * <p>Aina mainactivityn jatkuessa tarkistetaan vuorotellen, että tietokannat loytyvat ja lopulta palataan LomakeActivityyn.</p>
     */
    @Override protected void onResume () {
        super.onResume();
        //Jos käyttäjän tietoja ei löydy, avataan aktiviteetti, jossa ne syötetään.
        if (!kayttajatietokanta.onkoTietokantaa()) {
            Log.d("Käyttäjätietokanta", "Ei löydy! Siirrytään KayttajanTiedotaktiviteettiin...");
            Intent intent = new Intent(MainActivity.this, KayttajanTiedotActivity.class);
            startActivity(intent);
        }else {
            //Jos viikon tavoitetta ei ole asetettu, siirrytään tavoitteenasetusaktiviteettiin
            if (!viikkotavoitetietokanta.onkoTavoitettaKuluvalleViikolle()) {
                Log.d("Viikkotavoitetietokanta", "Ei löydy! Siirrytään Tavoitteenasetusaktiviteettiin...");
                Intent intent = new Intent(MainActivity.this, TavoitteenasetusActivity.class);
                startActivity(intent);
            }else {
                //Siirrytään lomakeActivityyn:
                Intent intent = new Intent(MainActivity.this, LomakeActivity.class);
                startActivity(intent);
            }
        }
    }

}