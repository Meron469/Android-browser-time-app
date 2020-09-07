package com.example.time_app;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.example.time_app.Browser.ID_1;
import static com.example.time_app.Browser.ID_2;
import static com.example.time_app.Browser.ID_3;
import static com.example.time_app.Browser.ID_4;
import static com.example.time_app.Browser.ID_5;


public class GridViewActivity extends Activity {
    private GridView gridView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        //GridView
        gridView = (GridView) findViewById(R.id.gridView1);
        //Export button
        button = (Button) findViewById(R.id.Export);
        //ArrayList
        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        String ID = "";
        String Url = "";
        String Total = "";
        String Start = "";
        String Done = "";
        //add in to array list
        for (int i = 0; i < ID_1.size(); i++) {
            list.add(ID_1.get(i));
            list.add(ID_2.get(i));
            list.add(ID_3.get(i));
            list.add(ID_4.get(i));
            list.add(ID_5.get(i));
        }


        gridView.setAdapter(adapter);

        //update button event
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //writeDataAtOnce();

            }
        });


    }


    /*public void writeDataAtOnce() {

        String filename = "csvfile.csv";

      //  File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
     //   File logDir = new File (directoryDownload, "bpReader"); //Creates a new folder in DOWNLOAD directory
        logDir.mkdirs();
        File file = new File(logDir, filename);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, true);

            for (int i = 0; i < ID_1.size(); i += 5) {
                outputStream.write((ID_1.get(i) + ",").getBytes());
                outputStream.write((ID_2.get(i + 1) + ",").getBytes());
                outputStream.write((ID_3.get(i + 2) + ",").getBytes());
                outputStream.write((ID_4.get(i + 3) + ",").getBytes());
                outputStream.write((ID_5.get(i + 4) + "\n").getBytes());
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
        }
    }*/

}

