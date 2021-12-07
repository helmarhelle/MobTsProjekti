package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TavoitteenasetusActivity extends AppCompatActivity {
    Kayttajatietokanta kayttajatietokanta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tavoitteenasetus);
        kayttajatietokanta = new Kayttajatietokanta(TavoitteenasetusActivity.this);

    }


}