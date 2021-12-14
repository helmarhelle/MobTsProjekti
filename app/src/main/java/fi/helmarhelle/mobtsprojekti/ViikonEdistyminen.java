package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ViikonEdistyminen extends AppCompatActivity {
    Viikkotavoitetietokanta vtt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viikon_edistyminen);
        vtt = new Viikkotavoitetietokanta(ViikonEdistyminen.this);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, vtt.haeTamanViikonUniTavoite()),
                new DataPoint(1, vtt.haeTamanViikonUniTavoite()),
                new DataPoint(2, 3),
                new DataPoint(3, 5),
                new DataPoint(4,12)

        });
        graph.addSeries(series);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(1, 12),
                new DataPoint(2, 9),
                new DataPoint(3, 10),
                new DataPoint(4, 4)
        });
        graph.addSeries(series2);
        series2.setColor(Color.RED);
        graph.setTitle("Viikon edistymisesi");

    }
}