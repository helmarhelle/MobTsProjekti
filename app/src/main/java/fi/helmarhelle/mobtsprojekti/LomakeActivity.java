package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LomakeActivity extends AppCompatActivity {

    Switch uniSwitch, liikuntaSwitch, syontiSwitch;
    TextView uniTeksti, liikuntaTeksti, syontiTeksti, lenkkiTeksti, saliTeksti;
    Button tallennusButton, edistymisButton, viikkotavoiteButton, tietojenmuutosButton;
    Viikkotavoitetietokanta viikkotavoitetietokanta;
    Paivalomaketietokanta paivalomaketietokanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomake);
        //Tietokantojen-alustukset
        viikkotavoitetietokanta = new Viikkotavoitetietokanta(LomakeActivity.this);
        paivalomaketietokanta = new Paivalomaketietokanta(LomakeActivity.this);

        //View-kartoitukset
        uniSwitch = findViewById(R.id.uniSwitch);
        liikuntaSwitch = findViewById(R.id.liikuntaSwitch);
        syontiSwitch = findViewById(R.id.syontiSwitch);

        uniTeksti = findViewById(R.id.uniText);
        liikuntaTeksti = findViewById(R.id.liikuntaText);
        syontiTeksti = findViewById(R.id.ulkonaSyontiText);
        lenkkiTeksti = findViewById(R.id.lenkkiText);
        saliTeksti = findViewById(R.id.saliText);

        tallennusButton = findViewById(R.id.tallennusButton_lomake);
        edistymisButton = findViewById(R.id.button_Graafeihin);
        viikkotavoiteButton = findViewById(R.id.button_Tavoitteenasetukseen);
        tietojenmuutosButton = findViewById(R.id.button_kayttajan_tietoihin);

        //Arvot viikkotavoitetietokannasta
        int viikonUniTavoite = viikkotavoitetietokanta.haeTamanViikonUniTavoite();
        float minimiLiikunta = (float) viikkotavoitetietokanta.haeTamanViikonLiikuntaTavoite()/7;
//-------------------------Onclick listenerit-------------------------------------------------------
        //Jos Switchit checkataan, asetetaan lomakkeisiin ne arvot, jotka tultaisiin tallentamaan tietokantaankin.
        syontiSwitch.setOnClickListener(v -> {
            uniTeksti.setText("" + viikonUniTavoite);
        });

        liikuntaSwitch.setOnClickListener(v -> {
            liikuntaTeksti.setText("" + minimiLiikunta);
        });

        syontiSwitch.setOnClickListener(v -> {
            syontiTeksti.setText(""+ 1);
        });

        //Lomakkeen tallennuskuuntelija
        tallennusButton.setOnClickListener(v -> {
            PaivaLomake paivaLomake;
            boolean onnistuiko = false;
            int unenMaara = 0;
            float liikunnanMaara = 0f;
            int ulkonaSyontienMaara = 0;
            float lenkinPituus = 0f;
            int saliKayntienMaara = 0;
            try {
                //Tarkastetaan switchien tila ja asetetaan muuttujille oikeat arvot, jos switchejä ei ole checkattu.
                if (!uniSwitch.isChecked()) {
                    unenMaara = Integer.parseInt(uniTeksti.getText().toString());
                }
                if (!liikuntaSwitch.isChecked()) {
                    liikunnanMaara = Float.parseFloat(liikuntaTeksti.getText().toString());
                }
                if (!syontiSwitch.isChecked()) {
                    ulkonaSyontienMaara = Integer.parseInt(syontiTeksti.getText().toString());
                }

                //lenkki- ja salikäynnit kirjataan ylös vain, jos ne on merkattu viikkotavoitteeseen.
                if (viikkotavoitetietokanta.haeTamanViikonLenkkiTavoite() > 0) {
                    lenkinPituus = Float.parseFloat(lenkkiTeksti.getText().toString());
                }
                if (viikkotavoitetietokanta.haeTamanViikonSaliTavoite() > 0) {
                    saliKayntienMaara = Integer.parseInt(saliTeksti.getText().toString());
                }
                paivaLomake = new PaivaLomake(uniSwitch.isChecked(), liikuntaSwitch.isChecked(), syontiSwitch.isChecked(),
                        unenMaara, liikunnanMaara, ulkonaSyontienMaara, lenkinPituus, saliKayntienMaara);

                if (paivaLomake.getUniH() >= 0 && paivaLomake.getLiikuntaKM() >= 0 && paivaLomake.getUlkonaSyonnitKPL() >= 0 && paivaLomake.getLenkkiKilometritKM() >= 0 && paivaLomake.getSaliKaynnitKPL() >= 0) {
                    onnistuiko = true;
                }

            } catch (Exception e) {
                Toast.makeText(LomakeActivity.this, "Päivän lomakkeen tallennus epäonnistui. Tarkista, että syötit kaikki tiedot oikein.", Toast.LENGTH_LONG).show();
                paivaLomake = new PaivaLomake(false, false, false,0,0f,0,0f,0);
            }

            //Jos paivalomakkeeseen tallennetut tiedot olivat järkeviä, kirjataan ne tietokantaan ja evätään käyttäjältä enempi muokkaus.
            if (onnistuiko) {
                if (paivalomaketietokanta.lisaaTiedot(paivaLomake, LomakeActivity.this)) {
                    Toast.makeText(LomakeActivity.this, "Tietojen tallennus onnistui", Toast.LENGTH_SHORT).show();
                    uniSwitch.setEnabled(false);
                    uniTeksti.setEnabled(false);
                    liikuntaSwitch.setEnabled(false);
                    liikuntaTeksti.setEnabled(false);
                    syontiSwitch.setEnabled(false);
                    syontiTeksti.setEnabled(false);
                    lenkkiTeksti.setEnabled(false);
                    saliTeksti.setEnabled(false);
                    tallennusButton.setEnabled(false);
                }
            } else {
                Toast.makeText(LomakeActivity.this, "Tietoja ei tallennettu. Jos sinulla on viikolle lenkki- tai salitavoite, aseta lomakkeisiin numero.", Toast.LENGTH_SHORT).show();
            }
        });

        //Painikkeet, joilla voidaan siirtyä toisiin aktiviteetteihin.

        tietojenmuutosButton.setOnClickListener(v -> {
            Intent intent = new Intent(LomakeActivity.this, KayttajanTiedotActivity.class);
            startActivity(intent);
        });

        viikkotavoiteButton.setOnClickListener(v -> {
            Intent intent = new Intent(LomakeActivity.this, TavoitteenasetusActivity.class);
            startActivity(intent);
        });

        edistymisButton.setOnClickListener(v -> {
            Intent intent = new Intent(LomakeActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
//-------------------------/Onclick listenerit-------------------------------------------------------
    /**
     * Aina lomakeaktiviteetin jatkuessa tarkistetaan onko tiedot syotetty talle paivalle. Jos on, lomaketta ei voi muokata.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (paivalomaketietokanta.onkoLomakettaTallePaivalle()) {
            uniSwitch.setEnabled(false);
            uniTeksti.setEnabled(false);
            liikuntaSwitch.setEnabled(false);
            liikuntaTeksti.setEnabled(false);
            syontiSwitch.setEnabled(false);
            syontiTeksti.setEnabled(false);
            lenkkiTeksti.setEnabled(false);
            saliTeksti.setEnabled(false);
            tallennusButton.setEnabled(false);
        } else {
            uniSwitch.setEnabled(true);
            uniTeksti.setEnabled(true);
            liikuntaSwitch.setEnabled(true);
            liikuntaTeksti.setEnabled(true);
            syontiSwitch.setEnabled(true);
            syontiTeksti.setEnabled(true);
            lenkkiTeksti.setEnabled(true);
            saliTeksti.setEnabled(true);
            tallennusButton.setEnabled(true);
        }
    }
}