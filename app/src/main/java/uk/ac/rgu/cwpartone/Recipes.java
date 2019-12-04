package uk.ac.rgu.cwpartone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Recipes extends AppCompatActivity implements View.OnClickListener{

    private static final String API_KEY = "ce4972dbcc8b4b67b42ef294d5062c4d";
    private static final String RECIPES_JSON_URL = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=";

    private List<Recipe> recipes;

    //private List<Products...> products;

    // views for each Recipe Field
    private ImageView imageViewRecipe;
    private TextView textViewName;
    private TextView textViewUsing;
    private TextView textViewMissing;
    private TextView textViewLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        //Get Products list as string separated by commas.

        // Send Request to Spoonacular API with "RECIPES_JSON_URL + Product List"

        // API Response should fill the recipe layout
        downloadRecipes();
    }

    private void downloadRecipes(){

        String url = String.format(RECIPES_JSON_URL);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView recipeCardView = findViewById(R.id.recipeCardView);

                        List<Recipe> recipes = new ArrayList<Recipe>();

                            try {
                                JSONObject responseObj = new JSONObject(response);
                                JSONObject recipeNameObj = responseObj.getJSONObject("title");
                                JSONObject recipeImgObj = responseObj.getJSONObject("image");
                                JSONObject recipeMissedObj = responseObj.getJSONObject("usedIngredients");
                                JSONObject recipeUsingObj = responseObj.getJSONObject("missedIngredients");
                                JSONObject recipeLikesObj = responseObj.getJSONObject("likes");


                            } catch (JSONException e) {
                                e.printStackTrace();
                        }
                    }
                }

        }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })

            // make the request to download the recipes
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);
        }

        }
