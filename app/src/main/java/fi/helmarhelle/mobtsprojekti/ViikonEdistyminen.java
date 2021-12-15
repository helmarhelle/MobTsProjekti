package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ViikonEdistyminen extends AppCompatActivity {
    Viikkotavoitetietokanta vtt;
    Paivalomaketietokanta plt;
    float Unimuuttuja;
    float Uni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viikon_edistyminen);
        vtt = new Viikkotavoitetietokanta(ViikonEdistyminen.this);
        plt = new Paivalomaketietokanta(ViikonEdistyminen.this);
        Unimuuttuja = vtt.haeTamanViikonUniTavoite()*7;
        Uni = plt.haeTamanViikonSaavutukset("uni", ViikonEdistyminen.this).get(0);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Unimuuttuja/7),
                new DataPoint(2, Unimuuttuja*2/7),
                new DataPoint(3, Unimuuttuja*3/7),
                new DataPoint(4, Unimuuttuja*4/7),
                new DataPoint(5,Unimuuttuja*5/7),
                new DataPoint(6, Unimuuttuja*6/7),
                new DataPoint(7, Unimuuttuja),

        });
        graph.addSeries(series);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Uni),
                new DataPoint(2, Uni += 7),
                new DataPoint(3, Uni += 5),
                new DataPoint(4,  Uni += 11),
                new DataPoint(5, Uni += 8),
                new DataPoint(6, Uni += 7),
                new DataPoint(7, Uni += 9)
        });
        graph.addSeries(series2);
        series2.setColor(Color.RED);
        graph.setTitle("Viikon edistymisesi");

        GraphView graph1 = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Unimuuttuja/7),
                new DataPoint(2, Unimuuttuja*2/7),
                new DataPoint(3, Unimuuttuja*3/7),
                new DataPoint(4, Unimuuttuja*4/7),
                new DataPoint(5,Unimuuttuja*5/7),
                new DataPoint(6, Unimuuttuja*6/7),
                new DataPoint(7, Unimuuttuja),

        });
        graph1.addSeries(series3);


        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Uni),
                new DataPoint(2, Uni += 7),
                new DataPoint(3, Uni += 5),
                new DataPoint(4,  Uni += 11),
                new DataPoint(5, Uni += 8),
                new DataPoint(6, Uni += 7),
                new DataPoint(7, Uni += 9)
        });
        graph1.addSeries(series4);
        series4.setColor(Color.RED);
        graph.setTitle("Viikon edistymisesi");


        GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Unimuuttuja/7),
                new DataPoint(2, Unimuuttuja*2/7),
                new DataPoint(3, Unimuuttuja*3/7),
                new DataPoint(4, Unimuuttuja*4/7),
                new DataPoint(5,Unimuuttuja*5/7),
                new DataPoint(6, Unimuuttuja*6/7),
                new DataPoint(7, Unimuuttuja),

        });
        graph2.addSeries(series5);


        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Uni),
                new DataPoint(2, Uni += 7),
                new DataPoint(3, Uni += 5),
                new DataPoint(4,  Uni += 11),
                new DataPoint(5, Uni += 8),
                new DataPoint(6, Uni += 7),
                new DataPoint(7, Uni += 9)
        });
        graph2.addSeries(series6);
        series6.setColor(Color.RED);
        graph.setTitle("Viikon edistymisesi");


        GraphView graph3 = (GraphView) findViewById(R.id.graph3);
        LineGraphSeries<DataPoint> series7 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(1, Unimuuttuja/7),
                new DataPoint(2, Unimuuttuja*2/7),
                new DataPoint(3, Unimuuttuja*3/7),
                new DataPoint(4, Unimuuttuja*4/7),
                new DataPoint(5,Unimuuttuja*5/7),
                new DataPoint(6, Unimuuttuja*6/7),
                new DataPoint(7, Unimuuttuja),

        });
        graph3.addSeries(series7);


        LineGraphSeries<DataPoint> series8 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, Uni),
                new DataPoint(2, Uni += 7),
                new DataPoint(3, Uni += 5),
                new DataPoint(4,  Uni += 11),
                new DataPoint(5, Uni += 8),
                new DataPoint(6, Uni += 7),
                new DataPoint(7, Uni += 9)
        });
        graph3.addSeries(series8);
        series8.setColor(Color.RED);
        graph.setTitle("Viikon edistymisesi");


        if (vtt.haeTamanViikonSaliTavoite()>0){

            GraphView graph4 = (GraphView) findViewById(R.id.graph4);
            LineGraphSeries<DataPoint> series9 = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0,0),
                    new DataPoint(1, Unimuuttuja/7),
                    new DataPoint(2, Unimuuttuja*2/7),
                    new DataPoint(3, Unimuuttuja*3/7),
                    new DataPoint(4, Unimuuttuja*4/7),
                    new DataPoint(5,Unimuuttuja*5/7),
                    new DataPoint(6, Unimuuttuja*6/7),
                    new DataPoint(7, Unimuuttuja),

            });
            graph4.addSeries(series7);


            LineGraphSeries<DataPoint> series10 = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 0),
                    new DataPoint(1, Uni),
                    new DataPoint(2, Uni += 7),
                    new DataPoint(3, Uni += 5),
                    new DataPoint(4,  Uni += 11),
                    new DataPoint(5, Uni += 8),
                    new DataPoint(6, Uni += 7),
                    new DataPoint(7, Uni += 9)
            });
            graph4.addSeries(series8);
            series8.setColor(Color.RED);
            graph.setTitle("Viikon edistymisesi");
        } else{
            graph4.setVisibility(View.GONE);
        }



        String joku = "tää";
        TextView textView = findViewById(R.id.EdistyminenTeksti);
        textView.setText("Tällä viikolla kävelit yhteensä" + plt.haeTamanViikonSaavutukset("liikunta", ViikonEdistyminen.this).get(0)+ "ja tavoitteesi oli" + joku + ".\n Nukuit: " + joku + "tuntia ja tavoitteesi oli "+ vtt.haeTamanViikonUniTavoite()+ "\n Ulkona söit" + joku +"kertaa, kun tavoitteesi oli "+ vtt.haeTamanViikonUlkonasyonnitTavoite());



    }
}