package com.example.time_app;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Manual_menu extends AppCompatActivity {

    public  ListView lv;
    public  EditText nametxt;
    public Button addbtn,updatebtn,deletebtn,submit;
    public ArrayList<String> names=new ArrayList<String>();
    public ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_menu);

        lv=(ListView) findViewById(R.id.Listview1);
        nametxt=(EditText) findViewById(R.id.nametxt);
        addbtn=(Button) findViewById(R.id.addbtn);
        updatebtn=(Button) findViewById(R.id.updatebtn);
        deletebtn=(Button) findViewById(R.id.deletebtn);
        submit=(Button) findViewById(R.id.Submit_btn);

        //adapter
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,names);
        lv.setAdapter(adapter);




        //Set Selected Item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nametxt.setText(names.get(i));

            }
        });

        //add button event
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        //update button event
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        //delete button event
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });


        //clear button event
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityDecision();
            }
        });








    }


    //add
    private void add()
    {
        String name=nametxt.getText().toString();

        if(!name.isEmpty() && name.length()>0)
        {
            //Add
            adapter.add(name);

            //Refresh
            adapter.notifyDataSetChanged();

            nametxt.setText("");

            Toast.makeText(getApplicationContext(), "Added " +name,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "!!Nothing to Add " +name,Toast.LENGTH_SHORT).show();
        }

    }

    //Update
    private void update()
    {
        String name=nametxt.getText().toString();
        int pos=lv.getCheckedItemPosition();

        if(!name.isEmpty() && name.length()>0)
        {
            //Remove Item
            adapter.remove(names.get(pos));

            //insert
            adapter.insert(name,pos);

            //refresh
            adapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "Updated " +name,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "!!Nothing to Update " ,Toast.LENGTH_SHORT).show();
        }
    }

    //delete

    private void delete()
    {
        int pos=lv.getCheckedItemPosition();

        if(pos > -1)
        {
            //remove
            adapter.remove(names.get(pos));

            //refresh
            adapter.notifyDataSetChanged();

            nametxt.setText("");
            Toast.makeText(getApplicationContext(), "Deleted " ,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "!!Nothing to delete " ,Toast.LENGTH_SHORT).show();
        }
    }

    //Submit

    private void openActivityDecision()
    {
        Intent intent=new Intent(this, Browser.class);
        startActivity(intent);
    }
}
