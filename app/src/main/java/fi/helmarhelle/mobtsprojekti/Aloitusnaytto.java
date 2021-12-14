package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Aloitusnaytto extends AppCompatActivity {
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aloitusnaytto);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent x = new Intent(Aloitusnaytto.this, MainActivity.class);
                startActivity(x);
                finish();
            }
        }, 2500);
    }
}
