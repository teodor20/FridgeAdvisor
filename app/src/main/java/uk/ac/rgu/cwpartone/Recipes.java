package uk.ac.rgu.cwpartone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    List<String> RecipeData = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Log.d("MADE IT CHECKS", "MADE IT PAST ON CREATE");
        // data to populate the RecyclerView with
        RecipeData.add("TESTITEM");
        Log.d("MADE IT CHECKS", "MADE IT PAST ADDING ITEM");
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvRecipes);
        Log.d("MADE IT CHECKS", "MADE IT PAST SETTING RECYCLER");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("MADE IT CHECKS", "MADE IT PAST SETTING LAYOUT");
        adapter = new MyRecyclerViewAdapter(this, RecipeData);
        Log.d("MADE IT CHECKS", "MADE IT PAST CREATING ADAPTER");
        adapter.setClickListener(this);
        Log.d("MADE IT CHECKS", "MADE IT PAST CLICK LISTENGER");
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
