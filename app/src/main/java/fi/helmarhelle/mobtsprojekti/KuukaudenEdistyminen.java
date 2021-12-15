package fi.helmarhelle.mobtsprojekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class KuukaudenEdistyminen extends AppCompatActivity {
        float Unimuuttuja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuukauden_edistyminen);
        
        GraphView graph = (GraphView) findViewById(R.id.graphkkUni);

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
    }
}