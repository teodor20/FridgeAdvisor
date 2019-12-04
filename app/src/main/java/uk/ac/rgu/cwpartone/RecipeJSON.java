package uk.ac.rgu.cwpartone;


import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import java.class.Recepie;
/**
 * Class to process the Recepies JSON from API
 */
public class RecipeJSON {

    /**
     * JSON dumping into string with recipes based on products given by spoonacularAPI.
     * @param json recipes objects in JSON are encoded to string
     * @return Recipes from main ingredients products
     */
    public List<Recipe> parseRecipes(String json) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        try {
            JSONObject recepiesObj = new JSONObject(json);
            JSONObject recepieObj =  recepiesObj.getJSONObject("Recepies");
            JSONArray recepieArray = recepieObj.getJSONArray("Recepie");
            for (int i = 0, j = recepieArray.length(); i < j; i++) {
                JSONObject recepie = recepieArray.getJSONObject(i);

                //Check with Products here....

                Recipe rec = parseRecepieFromJson(recepie);
                // add this new Recipe to the recipes list
                recipes.add(rec);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseRecipes();
    }

    private Recipe parseRecepieFromJson(JSONObject recipe) throws JSONException {

        String name = recipe.getString("title");
        String missing = recipe.getString("missedIngredients");
        String using = recipe.getString(name: "usedIngredients");
        String image = recipe.getString(name: "image");
        String likes = recipe.getString("likes");

        // New Recipe Created
        Recipe rec = new Recipe();
        rec.setRecipeName(name);
        rec.setMissedProd(missing);
        rec.getUsedProd(using);
        rec.getRecipeImage(image);
        rec.setRecipeLikes(likes);
        rec.setSelected(false);

        return rec;
    }

}
