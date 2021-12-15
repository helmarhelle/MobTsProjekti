package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>Toimii visuaalisena main-Aktiviteettina josta paasee kaikkiin muihin visuaalisiin aktiviteetteihin nappia painamalla.</p>
 * <p>Paatavoitteena on, etta kayttaja tayttaa paivan lomakkeeseen tiedot.</p>
 * @author Reima
 * @since 15.12.2021
 */
public class LomakeActivity extends AppCompatActivity {

    Switch uniSwitch, liikuntaSwitch, syontiSwitch;
    EditText uniTeksti, liikuntaTeksti, syontiTeksti, lenkkiTeksti, saliTeksti;
    TextView otsikko;
    Button tallennusButton, edistymisButton, viikkotavoiteButton, tietojenmuutosButton;
    Viikkotavoitetietokanta viikkotavoitetietokanta;
    Paivalomaketietokanta paivalomaketietokanta;
    /**
     * <p>Oncreate-metodissa se niin kutsuttu magia tapahtuu. Ensin aktiviteetin nakymat haetaan layoutista ja kartoitetaan.</p>
     * <p>Sitten asetetaan kuuntelija tallennusnapille, jota painettaessa aktiviteetin nakymiin kirjatuista tiedoista luodaan paivalomake-olio ja tallennetaan se paivalomaketietokantaan.</p>
     * <p>Jos tietojen tallennus onnistui, estetaan kayttajalta lomakkeen enempi muokkaus. Taman jalkeen kayttaja voi ainoastaan siirtya napeilla muihin aktiviteetteihin.</p>
     * @param savedInstanceState    Aktiviteetille intentin antama tietokasa.
     */
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

        otsikko = findViewById(R.id.otsikko_lomakeaktiviteetti);
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
        uniSwitch.setOnClickListener(v -> {
            uniTeksti.setText("" + viikonUniTavoite);
            uniTeksti.setEnabled(!uniTeksti.isEnabled());
        });

        liikuntaSwitch.setOnClickListener(v -> {
            liikuntaTeksti.setText("" + minimiLiikunta);
            liikuntaTeksti.setEnabled(!liikuntaTeksti.isEnabled());
        });

        syontiSwitch.setOnClickListener(v -> {
            syontiTeksti.setText(""+ 1);
            syontiTeksti.setEnabled(!syontiTeksti.isEnabled());
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

                    otsikko.setText(R.string.lomake_otsikko_tayttetty);
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
                Toast.makeText(LomakeActivity.this, "Tietojen tallennus epäonnistui. Raportoi bugista kehittäjälle", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(LomakeActivity.this, ViikonEdistyminen.class);
            startActivity(intent);
        });

    }
//-------------------------/Onclick listenerit-------------------------------------------------------
    /**
     * <p>Aina lomakeaktiviteetin jatkuessa tarkistetaan onko tiedot syotetty talle paivalle. Jos on, lomaketta ei voi muokata.</p>
     * <p>Haetaan samalla myos viikkotavoitetietokannasta oikeat arvot switchien teksteihin.</p>
     * <p>Jos kayttaja ei ole asettanut itselleen lenkki- tai salitavoitteita kuluvalle viikolle ei kysyta niita.</p>
     */
    @Override
    protected void onResume() {
        super.onResume();
        //Lomakkeen muokkauksen esto
        if (paivalomaketietokanta.onkoLomakettaTallePaivalle()) {
            otsikko.setText(R.string.lomake_otsikko_tayttetty);
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
            otsikko.setText(R.string.lomake_otsikko);
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
        //Switchien tekstit
        int viikonUniTavoite = viikkotavoitetietokanta.haeTamanViikonUniTavoite();
        uniSwitch.setText("Nukuitko väh." + viikonUniTavoite + " tuntia?");

        float paivanLiikuntaTavoite = (float) viikkotavoitetietokanta.haeTamanViikonLiikuntaTavoite() / 7;
        liikuntaSwitch.setText("Kävelitkö väh." + paivanLiikuntaTavoite + " Km?");

        /*Liikunta- ja salitavoitteiden tekstikentät muokattavissa, jos lomaketta ei ole tallennettu
        tälle päivälle ja jos tavoite on asetettu.*/
        if (viikkotavoitetietokanta.haeTamanViikonLenkkiTavoite() == 0) {
            lenkkiTeksti.setEnabled(false);
        } else if (!paivalomaketietokanta.onkoLomakettaTallePaivalle()){
            lenkkiTeksti.setEnabled(true);
        }

        if (viikkotavoitetietokanta.haeTamanViikonSaliTavoite() == 0) {
            saliTeksti.setEnabled(false);
        } else if (paivalomaketietokanta.onkoLomakettaTallePaivalle()){
            saliTeksti.setEnabled(true);
        }
    }
}