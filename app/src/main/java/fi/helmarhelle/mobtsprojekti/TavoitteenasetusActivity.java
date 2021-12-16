package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/**
 * <p>Aktiviteetti, jossa asetetaan kayttajan tavoitteet tulevalle viikolle.</p>
 * @author Reima
 * @since 9.12.2021
 * @version 15.12.2021
 */
public class TavoitteenasetusActivity extends AppCompatActivity {

    Viikkotavoitetietokanta viikkotavoitetietokanta;
    EditText uni, liikunta, ravinto, lenkki, sali;
    Switch lenkkiSwitch, saliSwitch;
    Button tallennus;

    /**
     * <p>Oncreate-metodissa se niin kutsuttu magia tapahtuu. Ensin aktiviteetin nakymat haetaan layoutista ja kartoitetaan.</p>
     * <p>Sitten asetetaan kuuntelija tallennusnapille, jota painettaessa aktiviteetin nakymiin kirjatuista tiedoista luodaan viikkotavoite-olio ja tallennetaan se viikkotavoitetietokantaan.</p>
     * <p>Lopuksi, jos tietojen tallennus onnistui, siirrytaan takaisin lomakeaktiviteettiin.</p>
     * @param savedInstanceState    Aktiviteetille intentin antama tietokasa.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tavoitteenasetus);
        viikkotavoitetietokanta = new Viikkotavoitetietokanta(TavoitteenasetusActivity.this);

        //View-kartoitus
        uni = findViewById(R.id.uniView_tavoite);
        liikunta = findViewById(R.id.liikuntaView_tavoite);
        ravinto = findViewById(R.id.ravintoView_tavoite);
        lenkki = findViewById(R.id.lenkkiView_tavoite);
        sali = findViewById(R.id.saliView_tavoite);

        lenkkiSwitch = findViewById(R.id.lenkkiSwitch_tavoite);
        saliSwitch = findViewById(R.id.saliSwitch_tavoite);
        tallennus = findViewById(R.id.tallennusButton_tavoite);


        //Kuuntelijat switcheille
        lenkkiSwitch.setOnClickListener(v -> {
            lenkki.setEnabled(lenkkiSwitch.isChecked());
        });
        saliSwitch.setOnClickListener(v -> {
            sali.setEnabled(saliSwitch.isChecked());
        });
        //Kuuntelija tallennusnapille
        tallennus.setOnClickListener(v -> {
            Viikkotavoite viikkotavoite;
            boolean onnistuiko = false;
            try {
                //Oletusarvoisesti käyttäjä ei käy lenkillä tai salilla. Alustetaan lenkit nolliksi
                viikkotavoite = new Viikkotavoite(Integer.parseInt(uni.getText().toString()),
                        Integer.parseInt(liikunta.getText().toString()), Integer.parseInt(ravinto.getText().toString()), 0, 0);

                //Jos käyttäjä käy lenkillä tai salilla, luetaan ja asetetaan arvot viikkotavoitteeseen
                if (lenkkiSwitch.isChecked()) {
                    viikkotavoite.setLenkitKM(Integer.parseInt(lenkki.getText().toString()));
                }
                if (saliSwitch.isChecked()) {
                    viikkotavoite.setSaliKaynnitKPL(Integer.parseInt(sali.getText().toString()));
                }

                //Tarkistetaan ovatko viikkotavoitteeseen asetetut tiedot järkeviä
                if (viikkotavoite.getUniH() > 0 && viikkotavoite.getLiikuntaKM() > 0 && viikkotavoite.getUlkonaSyonnitKPL() >= 0 && viikkotavoite.getLenkitKM() >= 0 && viikkotavoite.getSaliKaynnitKPL() >= 0) {
                    onnistuiko = true;
                }
            } catch (Exception e) {
                Toast.makeText(TavoitteenasetusActivity.this, "Viikkotavoitteiden tallennus epäonnistui. Tarkista että syötit kaikki tiedot oikein.", Toast.LENGTH_LONG).show();
                viikkotavoite = new Viikkotavoite(0,0,0,0,0);
            }
            //Jos viikkotavoitteen tiedot olivat järkeviä, kirjataan ne tietokantaan
            if (onnistuiko) {
                Viikkotavoitetietokanta viikkotavoitetietokanta = new Viikkotavoitetietokanta(TavoitteenasetusActivity.this);
                viikkotavoitetietokanta.lisaaTiedot(viikkotavoite);
                Intent intent = new Intent(TavoitteenasetusActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * <p>Kun aktiviteetti kaynnistetaan, asetetaan mahdollisesti loytyvat arvot lomakkeisiin.</p>
     * <p>Jos lenkki- tai saliSwitcheja ei ole checkattu, estetaan kayttajaa kirjoittamasta niihin lomakkeisiinkaan mitaan.</p>
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (viikkotavoitetietokanta.onkoTavoitettaKuluvalleViikolle()) {
            uni.setText(Integer.toString(viikkotavoitetietokanta.haeTamanViikonUniTavoite()));
            liikunta.setText(Integer.toString(viikkotavoitetietokanta.haeTamanViikonLiikuntaTavoite()));
            ravinto.setText(Integer.toString(viikkotavoitetietokanta.haeTamanViikonUlkonasyonnitTavoite()));
            lenkki.setText(Integer.toString(viikkotavoitetietokanta.haeTamanViikonLenkkiTavoite()));
            sali.setText(Integer.toString(viikkotavoitetietokanta.haeTamanViikonSaliTavoite()));
        }
        //Jos lenkkitavoite tai salitavoite on asetettu, asetetaan switchitkin päälle
        if (viikkotavoitetietokanta.onkoTavoitettaKuluvalleViikolle()) {
            if (!(viikkotavoitetietokanta.haeTamanViikonLenkkiTavoite() == 0)) {
                lenkkiSwitch.setChecked(true);
            }
            if (!(viikkotavoitetietokanta.haeTamanViikonSaliTavoite() == 0)) {
                saliSwitch.setChecked(true);
            }
        }

        //Jos lenkki- tai saliSwitcheja ei ole checkattu, estetaan kayttajaa kirjoittamasta niihin lomakkeisiinkaan mitaan.
        if (!saliSwitch.isChecked()) {
            sali.setEnabled(false);
        }
        if (!lenkkiSwitch.isChecked()) {
            lenkki.setEnabled(false);
        }

    }


}