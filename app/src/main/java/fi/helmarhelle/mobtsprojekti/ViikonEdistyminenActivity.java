package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * <p>Viikonedistymis aktiviteettia ajava luokka</p>
 * @author Helmar
 * @version 16.12.2021
 */

public class ViikonEdistyminenActivity extends AppCompatActivity {
    Viikkotavoitetietokanta vtt;
    Paivalomaketietokanta plt;
    float Unimuuttuja;
    float Kavelymuuttuja;
    float Juoksumuuttuja;
    float Salimuuttuja;
    float Syontimuuttuja;
    float Uni;
    float Kavely;
    int Syo;
    float Lenkki;
    int Sali;

    /**
     * <p>OnCreatella ajaa viikonedistyminen aktiviteetin, joka luo tarkistaa saavutuksista saadut arvot.</p>
     * <p>Tekee graafit ja tekstit edistymiselle.</p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viikon_edistyminen);
        vtt = new Viikkotavoitetietokanta(ViikonEdistyminenActivity.this);
        plt = new Paivalomaketietokanta(ViikonEdistyminenActivity.this);
        Unimuuttuja = vtt.haeTamanViikonUniTavoite();
        Kavelymuuttuja = vtt.haeTamanViikonLiikuntaTavoite();
        Syontimuuttuja = vtt.haeTamanViikonUlkonasyonnitTavoite();
        Salimuuttuja = vtt.haeTamanViikonSaliTavoite();
        Juoksumuuttuja = vtt.haeTamanViikonLenkkiTavoite();
        ArrayList<Float> uniSaavutukset = new ArrayList<>();
        ArrayList<Float> kavelySaavutukset = new ArrayList<>();
        ArrayList<Integer> syoSaavutukset = new ArrayList<>();
        ArrayList<Integer> saliSaavutukset = new ArrayList<>();
        ArrayList<Float> juoksuSaavutukset = new ArrayList<>();


         //Tarkistaa onko saavutuksissa olevat arvot kaytettavia graafissa ja lisää ne arraylistiin</p>

        int i = 0;
        //Testaa unen
        try {
            do {
                uniSaavutukset.add(plt.haeTamanViikonSaavutukset("uni", ViikonEdistyminenActivity.this).get(i));
                i++;
            } while (i<=6);

        } catch (Exception e) {
            Log.d("unisaavutustenhaku",  "Tapahtui virhe kieroksella " + i);
            while(i<=6){
                uniSaavutukset.add(0f);
                i++;
            }
        }
        i=0;
        //testaa kävely saavutukset
        try {
            do {
                kavelySaavutukset.add(plt.haeTamanViikonSaavutukset("liikunta", ViikonEdistyminenActivity.this).get(i));
                i++;
            } while (i<=6);

        } catch (Exception e) {
            Log.d("kavelysaavutustenhaku",  "Tapahtui virhe kieroksella " + i);
            while(i<=6){
                kavelySaavutukset.add(0f);
                i++;
            }
        }
        i=0;
        //testaa ruoka saavutukset
        try {
            do {
                syoSaavutukset.add(Math.round(plt.haeTamanViikonSaavutukset("syonti", ViikonEdistyminenActivity.this).get(i)));
                i++;
            } while (i<=6);

        } catch (Exception e) {
            Log.d("Syöntisaavutustenhaku",  "Tapahtui virhe kieroksella " + i);
            while(i<=6){
                syoSaavutukset.add(0);
                i++;
            }
        }
        i=0;
        try {
            do {
                juoksuSaavutukset.add(plt.haeTamanViikonSaavutukset("lenkki", ViikonEdistyminenActivity.this).get(i));
                i++;
            } while (i<=6);

        } catch (Exception e) {
            Log.d("Lenkkisaavutustenhaku",  "Tapahtui virhe kieroksella " + i);
            while(i<=6){
                juoksuSaavutukset.add(0f);
                i++;
            }
        }
        i=0;
        try {
            do {
                saliSaavutukset.add(Math.round(plt.haeTamanViikonSaavutukset("sali", ViikonEdistyminenActivity.this).get(i)));
                i++;
            } while (i<=6);

        } catch (Exception e) {
            Log.d("Salisaavutustenhaku",  "Tapahtui virhe kieroksella " + i);
            while(i<=6){
                saliSaavutukset.add(0);
                i++;
            }
        }
         //Tekee graafeja hyodyntaen edellisia try catch lauseista saatuja arraylisteja. Lenkki ja sali kohdassa piilottaa niihin liittyvät graafit ja tekstit

        //Graafi unen seurannalle
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Unimuuttuja),
                new DataPoint(2, Unimuuttuja*2),
                new DataPoint(3, Unimuuttuja*3),
                new DataPoint(4, Unimuuttuja*4),
                new DataPoint(5, Unimuuttuja*5),
                new DataPoint(6, Unimuuttuja*6),
                new DataPoint(7, Unimuuttuja*7)

        });
        graph.addSeries(series);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, uniSaavutukset.get(0)),
                new DataPoint(2, uniSaavutukset.get(0) + uniSaavutukset.get(1)),
                new DataPoint(3, uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2)),
                new DataPoint(4, uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2) + uniSaavutukset.get(3)),
                new DataPoint(5, uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2) + uniSaavutukset.get(3)+ uniSaavutukset.get(4)),
                new DataPoint(6, uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2) + uniSaavutukset.get(3)+ uniSaavutukset.get(4) + uniSaavutukset.get(5)),
                new DataPoint(7, uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2) + uniSaavutukset.get(3)+ uniSaavutukset.get(4) + uniSaavutukset.get(5)+ uniSaavutukset.get(6))
        });
        graph.addSeries(series2);
        series2.setColor(Color.RED);

        //Graafi kavelyn seurannalle
        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Kavelymuuttuja/7),
                new DataPoint(2, Kavelymuuttuja*2/7),
                new DataPoint(3, Kavelymuuttuja*3/7),
                new DataPoint(4, Kavelymuuttuja*4/7),
                new DataPoint(5,Kavelymuuttuja*5/7),
                new DataPoint(6, Kavelymuuttuja*6/7),
                new DataPoint(7, Kavelymuuttuja),

        });
        graph1.addSeries(series3);


        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, kavelySaavutukset.get(0)),
                new DataPoint(2, kavelySaavutukset.get(0) + kavelySaavutukset.get(1)),
                new DataPoint(3, kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2)),
                new DataPoint(4, kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2) + kavelySaavutukset.get(3)),
                new DataPoint(5, kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2) + kavelySaavutukset.get(3) + kavelySaavutukset.get(4)),
                new DataPoint(6, kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2) + kavelySaavutukset.get(3) + kavelySaavutukset.get(4) + kavelySaavutukset.get(5)),
                new DataPoint(7, kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2) + kavelySaavutukset.get(3) + kavelySaavutukset.get(4) + kavelySaavutukset.get(5) + kavelySaavutukset.get(6))
        });
        graph1.addSeries(series4);
        series4.setColor(Color.RED);


        //Graafi ulkona syömisen seurannalle
        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Syontimuuttuja/7),
                new DataPoint(2, Syontimuuttuja*2/7),
                new DataPoint(3, Syontimuuttuja*3/7),
                new DataPoint(4, Syontimuuttuja*4/7),
                new DataPoint(5,Syontimuuttuja*5/7),
                new DataPoint(6, Syontimuuttuja*6/7),
                new DataPoint(7, Syontimuuttuja),

        });
        graph2.addSeries(series5);


        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, syoSaavutukset.get(0)),
                new DataPoint(2, syoSaavutukset.get(0) + syoSaavutukset.get(1) ),
                new DataPoint(3, syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2)),
                new DataPoint(4, syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2) + syoSaavutukset.get(3)),
                new DataPoint(5, syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2) + syoSaavutukset.get(3) + syoSaavutukset.get(4)),
                new DataPoint(6, syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2) + syoSaavutukset.get(3) + syoSaavutukset.get(4) + syoSaavutukset.get(5)),
                new DataPoint(7, syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2) + syoSaavutukset.get(3) + syoSaavutukset.get(4) + syoSaavutukset.get(5) + syoSaavutukset.get(6))
        });
        graph2.addSeries(series6);
        series6.setColor(Color.RED);


        //Graafi juoksun seurannalle, joka katoaa riippuen käyttäjän inputista
        GraphView graph3 = (GraphView) findViewById(R.id.graph3);
        TextView juoksuteksti =  findViewById(R.id.Lenkki);
        TextView lenkkiTeksti = findViewById(R.id.Lenkki1);

        if (vtt.haeTamanViikonLenkkiTavoite()>0){
        LineGraphSeries<DataPoint> series7 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Juoksumuuttuja/7),
                new DataPoint(2, Juoksumuuttuja*2/7),
                new DataPoint(3, Juoksumuuttuja*3/7),
                new DataPoint(4, Juoksumuuttuja*4/7),
                new DataPoint(5, Juoksumuuttuja*5/7),
                new DataPoint(6, Juoksumuuttuja*6/7),
                new DataPoint(7, Juoksumuuttuja),

        });
        graph3.addSeries(series7);
        lenkkiTeksti.setText("Olet juossut " + Lenkki + " kilometriä ja tavoitteesi on " +vtt.haeTamanViikonLenkkiTavoite()+ " kilometriä.");
        }else {
            graph3.setVisibility(View.GONE);
            juoksuteksti.setVisibility(View.GONE);
            lenkkiTeksti.setVisibility(View.GONE);
        }


        LineGraphSeries<DataPoint> series8 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, juoksuSaavutukset.get(0)),
                new DataPoint(2, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1)),
                new DataPoint(3, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2)),
                new DataPoint(4, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2) + juoksuSaavutukset.get(3)),
                new DataPoint(5, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2) + juoksuSaavutukset.get(3) + juoksuSaavutukset.get(4)),
                new DataPoint(6, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2) + juoksuSaavutukset.get(3) + juoksuSaavutukset.get(4) + juoksuSaavutukset.get(5)),
                new DataPoint(7, juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2) + juoksuSaavutukset.get(3) + juoksuSaavutukset.get(4) + juoksuSaavutukset.get(5) + juoksuSaavutukset.get(6))
        });
        graph3.addSeries(series8);
        series8.setColor(Color.RED);

        //Graafi salikäynnin seurannalle, joka katoaa riippuen käyttäjän inputista
        GraphView graph4 = (GraphView) findViewById(R.id.graph4);
        TextView saliteksti =  findViewById(R.id.Sali);
        TextView saliTeksti1 = findViewById(R.id.Sali1);

        if (vtt.haeTamanViikonSaliTavoite()>0) {
            LineGraphSeries<DataPoint> series9 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, 0),
                    new DataPoint(1, Salimuuttuja/7),
                    new DataPoint(2, Salimuuttuja*2/7),
                    new DataPoint(3, Salimuuttuja*3/7),
                    new DataPoint(4, Salimuuttuja*4/7),
                    new DataPoint(5, Salimuuttuja*5/7),
                    new DataPoint(6, Salimuuttuja*6/7),
                    new DataPoint(7, Salimuuttuja),
            });
            graph4.addSeries(series9);


            LineGraphSeries<DataPoint> series10 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, 0),
                    new DataPoint(1, saliSaavutukset.get(0)),
                    new DataPoint(2, saliSaavutukset.get(0) + saliSaavutukset.get(1)),
                    new DataPoint(3, saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2)),
                    new DataPoint(4, saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2) + saliSaavutukset.get(3)),
                    new DataPoint(5, saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2) + saliSaavutukset.get(3) + saliSaavutukset.get(4)),
                    new DataPoint(6, saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2) + saliSaavutukset.get(3) + saliSaavutukset.get(4) + saliSaavutukset.get(5)),
                    new DataPoint(7, saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2) + saliSaavutukset.get(3) + saliSaavutukset.get(4) + saliSaavutukset.get(5) + saliSaavutukset.get(6) )
            });
            graph4.addSeries(series10);
            series10.setColor(Color.RED);
            saliTeksti1.setText("Olet käynyt " +Sali+ " kertaa salilla, kun tavoitteesi on " + vtt.haeTamanViikonSaliTavoite() + " kertaa.");
        }else{
                graph4.setVisibility(View.GONE);
                saliteksti.setVisibility(View.GONE);
                saliTeksti1.setVisibility(View.GONE);
            }

        //Laskee yhteen arvot teksteja varten

        Uni = uniSaavutukset.get(0) + uniSaavutukset.get(1) + uniSaavutukset.get(2) + uniSaavutukset.get(3)+ uniSaavutukset.get(4) + uniSaavutukset.get(5)+ uniSaavutukset.get(6);
        Kavely = kavelySaavutukset.get(0) + kavelySaavutukset.get(1) + kavelySaavutukset.get(2) + kavelySaavutukset.get(3) + kavelySaavutukset.get(4) + kavelySaavutukset.get(5) + kavelySaavutukset.get(6);
        Syo = syoSaavutukset.get(0) + syoSaavutukset.get(1) + syoSaavutukset.get(2) + syoSaavutukset.get(3) + syoSaavutukset.get(4) + syoSaavutukset.get(5) + syoSaavutukset.get(6);
        Lenkki = juoksuSaavutukset.get(0) + juoksuSaavutukset.get(1) + juoksuSaavutukset.get(2) + juoksuSaavutukset.get(3) + juoksuSaavutukset.get(4) + juoksuSaavutukset.get(5) + juoksuSaavutukset.get(6);
        Sali = saliSaavutukset.get(0) + saliSaavutukset.get(1) + saliSaavutukset.get(2) + saliSaavutukset.get(3) + saliSaavutukset.get(4) + saliSaavutukset.get(5) + saliSaavutukset.get(6);

        //Tekee textviewin edistymisen seurannalle
        TextView textView = findViewById(R.id.EdistyminenTeksti);
        textView.setText("Tällä viikolla olet nukkunut " + Uni + " tuntia. Tavoitteesi on " + vtt.haeTamanViikonUniTavoite() + " tuntia päivässä.\n Olet kävellyt " + Kavely + " kilometriä ja tavoitteesi on: "+ vtt.haeTamanViikonLiikuntaTavoite()+ " kilometriä\nUlkona olet syönyt " + Syo +" kertaa, kun tavoitteesi on pysyä "+ vtt.haeTamanViikonUlkonasyonnitTavoite() + " kerrassa.");
    }
}