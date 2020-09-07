package com.example.time_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    ImageView Start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Start=(ImageView) findViewById(R.id.button);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Go_Data();
            }
        });
    }

    public void Go_Data()
    {



        Intent intent=new Intent(this, File_menu.class);
        startActivity(intent);



    }



}

