package uk.ac.rgu.cwpartone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ProductsList extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, View.OnClickListener {

    List<String> data;
    MyRecyclerViewAdapter adapter;
    DatePickerDialog.OnDateSetListener DateSetListener;
    int position;
    private static final String sharedPrefFile = "uk.ac.rgu.cwpartone.demo";
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        final TextView DateText = findViewById(R.id.EdtDate);
        DateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar  cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DateDialogue = new DatePickerDialog(ProductsList.this,
                        android.R.style.Theme_DeviceDefault, DateSetListener, year, month, day);
                DateDialogue.show();
            }
        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d("Date values", "year is: " + year + " Day is: " + day + " Month is: " + month);
                month = month + 1;
                String Date = day + "/" + month + "/" + year;
                DateText.setText(Date);
            }
        };

        Button Addbutton = findViewById(R.id.BtnAdd);
        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
            }
        });



        // data to populate the RecyclerView with
        data = new ArrayList<>();
        Map<String,?> keys = sharedPrefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            data.add(entry.getValue().toString());

        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, final int position) {
        EditText NameText =  findViewById(R.id.EdtItemName);
        TextView DateText =  findViewById(R.id.EdtDate);
        String Data = data.get(position);
        String[] SplitData = Data.split("\\|");

        NameText.setText(SplitData[0]);
        DateText.setText(SplitData[1]);


        Log.d("map values","MADE IT TEST");

        Button DeleteButton = findViewById(R.id.BtnDelete);
        DeleteButton.setOnClickListener(this);

        Button UpdateButton = findViewById(R.id.BtnUpdate);
        UpdateButton.setOnClickListener(this);
    }

    private void insertItem() {
        EditText NameText = findViewById(R.id.EdtItemName);
        TextView DateText = findViewById(R.id.EdtDate);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        String PrefKey;
        String PrefText;
        if(DateText.length() == 0){
            Toast.makeText(this, "Error. Please enter a date to add a new item", Toast.LENGTH_LONG).show();
        }
        else if(NameText.length() == 0){
            Toast.makeText(this, "Error. Please enter a name to add a new item", Toast.LENGTH_LONG).show();
        }
        else {

            String NameItem = NameText.getText().toString();
            String DateItem = DateText.getText().toString();
            String item = NameItem + " | " + DateItem;
            int insertIndex = data.size();
            data.add(insertIndex, item);
            PrefKey = String.valueOf(insertIndex);
            PrefText = item;
            prefsEditor.putString(PrefKey, PrefText);
            prefsEditor.apply();
            Toast.makeText(this, "Added: " + PrefText + " to fridge", Toast.LENGTH_LONG).show();
            String PrefsData = sharedPrefs.getString(PrefKey, "not found");
            Map<String,?> keys = sharedPrefs.getAll();
            for(Map.Entry<String,?> entry : keys.entrySet()){
                Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            }
            adapter.notifyItemInserted(insertIndex);
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        String removedData = data.get(position);
        switch (view.getId()){
            case R.id.BtnDelete:
                data.remove(position);
                adapter.notifyItemRemoved(position);
                prefsEditor.remove(String.valueOf(position));
                prefsEditor.apply();
                Toast.makeText(ProductsList.this, "Removed: " + removedData, Toast.LENGTH_LONG).show();
                break;
            case R.id.BtnUpdate:
                prefsEditor = sharedPrefs.edit();
                removedData = data.get(position);
                data.remove(position);
                adapter.notifyItemRemoved(position);
                prefsEditor.remove(String.valueOf(position));
                prefsEditor.apply();
                Toast.makeText(ProductsList.this, "Removed: " + removedData, Toast.LENGTH_LONG).show();
                insertItem();
                break;
            default:
                break;
        }
    }


}
