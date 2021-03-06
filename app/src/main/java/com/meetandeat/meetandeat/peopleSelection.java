package com.meetandeat.meetandeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

import java.util.ArrayList;

//http://hmkcode.com/android-custom-listview-items-row/

public class peopleSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_selection);

        //creates the adapter and puts the array into it
        ListAdapter peopleAdapter = new customListView(this, generateData());
        ListView peopleSelectionListView = (ListView) findViewById(R.id.peopleSelectionListView);
        peopleSelectionListView.setAdapter(peopleAdapter);

        //Click listener to send the array item that is clicked on to show as a toast notification
        peopleSelectionListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        String test = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(peopleSelection.this, test, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Intent j = new Intent();
    }

    public ArrayList<listViewVariables> generateData(){
        ArrayList<listViewVariables> listItems = new ArrayList<listViewVariables>();
        listItems.add(new listViewVariables("Test One","Test Restaurant"));
        listItems.add(new listViewVariables("Test Two", "Test Rest"));
        return listItems;
    }

    public void peopleMatcher(){
        /*
        To compare the two people against each other.
        Two arrays and an integer variable as a counter

        for(int i =0; i<5; i++){
            if((rOne[0].equals(rTwo[0])) ||
                    (rOne[1].equals(rTwo[1])) ||
                    (rOne[2].equals(rTwo[2])) ||
                    (rOne[3].equals(rTwo[3])) ||
                    (rOne[4].equals(rTwo[4]))){
                priority = priority + 1;
            }
        }
         */
    }
}
