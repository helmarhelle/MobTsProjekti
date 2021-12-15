package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Reima
 * @since 8.12.2021
 * @version 15.12.2021 <p>Aktiviteetti, jossa asetetaan kayttajan perustiedot.</p>
 */
public class KayttajanTiedotActivity extends AppCompatActivity {

    EditText ika, pituus, paino;
    Button tallennus;
    Kayttajatietokanta kayttajatietokanta;
    /**
     * <p>Oncreate-metodissa se niin kutsuttu magia tapahtuu. Ensin aktiviteetin nakymat haetaan layoutista ja kartoitetaan.</p>
     * <p>Sitten asetetaan kuuntelija tallennusnapille, jota painettaessa aktiviteetin nakymiin kirjatuista tiedoista luodaan kayttaja-olio ja tallennetaan se kayttajatietokantaan.</p>
     * <p>Lopuksi, jos tietojen tallennus onnistui, siirrytaan takaisin lomakeaktiviteettiin.</p>
     * @param savedInstanceState    Aktiviteetille intentin antama tietokasa.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayttajan_tiedot);

        //Tietokannan alustus
        kayttajatietokanta = new Kayttajatietokanta(KayttajanTiedotActivity.this);
        //View-kartoitukset
        ika = findViewById(R.id.ikaView);
        pituus = findViewById(R.id.pituusView);
        paino = findViewById(R.id.painoView);
        tallennus = findViewById(R.id.tallennusButton);

        //Kuuntelija tallennusnapille
        tallennus.setOnClickListener(v -> {
            Kayttaja kayttaja;
            boolean onnistuiko = false;
            try {
                kayttaja = new Kayttaja(Integer.parseInt(ika.getText().toString()),
                        Integer.parseInt(pituus.getText().toString()), Float.parseFloat(paino.getText().toString()));

                //Tarkistetaan ovatko kayttajaan asetetut tiedot järkeviä
                if (kayttaja.getIka() > 0 && kayttaja.getPituusCM() > 0 && kayttaja.getPainoKG() > 0) {
                    onnistuiko = true;
                }
            } catch (Exception e) {
                Toast.makeText(KayttajanTiedotActivity.this, "Käyttäjätietojen tallennus epäonnistui. Tarkista että syötit kaikki tiedot oikein.", Toast.LENGTH_LONG).show();
                kayttaja = new Kayttaja(0,0,0.0f);
            }

            //Jos kayttajan tiedot olivat järkeviä, kirjataan ne tietokantaan
            if (onnistuiko) {
                kayttajatietokanta.lisaaTiedot(kayttaja);
                Intent intent = new Intent(KayttajanTiedotActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Kun aktiviteetti käynnistetään, asetetaan mahdollisesti löytyvät arvot lomakkeisiin
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (kayttajatietokanta.onkoTietokantaa()) {
            ika.setText(Integer.toString(kayttajatietokanta.haeIka()));
            pituus.setText(Integer.toString(kayttajatietokanta.haePituus()));
            paino.setText(Float.toString(kayttajatietokanta.haePaino()));
        }
    }
}