package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Kayttajatietokanta kayttajatietokanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kayttajatietokanta = new Kayttajatietokanta(MainActivity.this);
        Kayttaja erkki = new Kayttaja(12, 156, 55.6f);
        kayttajatietokanta.lisaaTiedot(erkki);

    }

    @Override protected void onResume () {
        super.onResume();
        if (!kayttajatietokanta.onkoTietokantaa()) {
            Log.d("Käyttäjätietokanta", "Ei löydy! Siirrytään Tavoitteenasetusaktiviteettiin...");
        }else {
            Log.d("Käyttäjätietokanta", "Löytyi!");
        }
    }

    //moikkamoi


}