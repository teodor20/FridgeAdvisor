package uk.ac.rgu.cwpartone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recipes extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    List<String> RecipeData = new ArrayList<>();;
    private final String sharedPrefFile = "uk.ac.rgu.cwpartone.demo";
    private SharedPreferences sharedPrefs;
    private String url = "https://api.spoonacular.com/recipes/search?apiKey=";
    private String APIKey = "b3fb43b66db84f0e82623c4711540d5a";
    private String query = "&query=";
    private String numberOfResultsString = "&number=";
    private String numberOfResultsValue = "15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        // data to populate the RecyclerView with
        getData();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public String getUrl() {
        return url;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getNumberOfResultsString() {
        return numberOfResultsString;
    }

    public String getNumberOfResultsValue() {
        return numberOfResultsValue;
    }

    public void getData() {
        ArrayList<String> productList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String productAndDate = sharedPrefs.getString(String.valueOf(i), "");
            if (productAndDate != null && !productAndDate.isEmpty()) {
                String query = this.getQuery();
                String product = productAndDate.substring( 0, productAndDate.indexOf("|")).trim();
                this.setQuery(query.concat(product).concat(","));

                productList.add(product);
            }
        }

        Log.e("Rest Response", String.valueOf(productList.size()));

        if (productList.size() > 0) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String EndPoint = this.getUrl()+this.getAPIKey()+this.getQuery()+this.getNumberOfResultsString()+this.getNumberOfResultsValue();
            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    EndPoint,
                    null,
                    new Response.Listener<JSONObject>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Rest Response", response.toString());
                            try {
                                setRecipeData(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            loadRecyclerView(RecipeData);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Rest Response", error.toString());
                            RecipeData.add("You are not connected to the internet or no recipes were found, sorry!");
                            loadRecyclerView(RecipeData);
                        }
                    }
            );

            requestQueue.add(objectRequest);
        } else {
            // data to populate the RecyclerView with
            RecipeData.add("You don't have any products in your fridge.");
            loadRecyclerView(RecipeData);

        }
    }

    public void loadRecyclerView(List<String> RecipeData) {
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, RecipeData);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setRecipeData(JSONObject response) throws JSONException {
        JSONArray arr = response.getJSONArray("results");
        int numberOfResults = Integer.parseInt(this.getNumberOfResultsValue());
        for (int i = 0; i < numberOfResults; i++) {
            JSONObject jObj = arr.getJSONObject(i);
            if (jObj != null) {
                String recipe = jObj.getString("title") + " | " +
                                "Preparation Time: " +
                                jObj.getString("readyInMinutes") + " min." + " | " +
                                "Servings: " +
                                jObj.getString("servings");
                RecipeData.add(recipe);
            }
        }
    }
}
