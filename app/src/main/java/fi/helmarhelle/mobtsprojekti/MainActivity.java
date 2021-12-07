package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Kayttajatietokanta kayttajatietokanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayttajatietokanta = new Kayttajatietokanta(MainActivity.this);

    }

    @Override protected void onResume () {
        super.onResume();
        //Jos käyttäjän tietoja ei löydy, avataan aktiviteetti, jossa ne syötetään.
        if (!kayttajatietokanta.onkoTietokantaa()) {
            Log.d("Käyttäjätietokanta", "Ei löydy! Siirrytään Tavoitteenasetusaktiviteettiin...");
            Intent intent = new Intent(MainActivity.this, KayttajanTiedotActivity.class);
            startActivity(intent);
        }else {
            Log.d("Käyttäjätietokanta", "Löytyi!");
        }
    }

    //moikkamoi


}