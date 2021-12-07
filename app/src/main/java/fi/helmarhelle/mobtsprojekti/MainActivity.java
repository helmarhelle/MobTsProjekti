package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Kayttajatietokanta kayttajatietokanta;
    Viikkotavoitetietokanta viikkotavoitetietokanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayttajatietokanta = new Kayttajatietokanta(MainActivity.this);
        viikkotavoitetietokanta = new Viikkotavoitetietokanta(MainActivity.this);

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
        }
        //Jos viikon tavoitetta ei ole asetettu, siirrytään tavoitteenasetusaktiviteettiin
        if (!viikkotavoitetietokanta.onkoTietokantaa()) {
            Log.d("Viikkotavoitetietokanta", "Ei löydy! Siirrytään Tavoitteenasetusaktiviteettiin...");
            Intent intent = new Intent(MainActivity.this, TavoitteenasetusActivity.class);
            startActivity(intent);
        }else {
            Log.d("Viikkotavoitetietokanta", "Löytyi!");
            Log.d("Viikkotavoitetietokanta", "Ikä: " + kayttajatietokanta.haeIka());
            Log.d("Viikkotavoitetietokanta", "Pituus: " + kayttajatietokanta.haePituus() + " cm");
            Log.d("Viikkotavoitetietokanta", "Paino: " + kayttajatietokanta.haePaino() + " Kg");

        }
    }

}