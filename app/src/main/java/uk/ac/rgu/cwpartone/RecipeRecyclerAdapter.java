package uk.ac.rgu.cwpartone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private LayoutInflater inflater;


    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public RecipeViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public RecipeRecyclerAdapter(Context context, List<Recipe> recipes){
        super();
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recipes, parent, false);
        RecipeViewHolder vh = new RecipeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerAdapter.RecipeViewHolder holder, int position) {
        Recipe recipesToBeDisplayed = this.recipes.get(position);

        TextView textViewName = holder.itemView.findViewById(R.id.textViewName);
        textViewName.setText(recipesToBeDisplayed.getRecipeName());

        TextView textViewMissing = holder.itemView.findViewById(R.id.textViewMissing);
        textViewMissing.setText(recipesToBeDisplayed.getMissedProd());

        //will be src URL
        TextView imageViewRecipe = holder.itemView.findViewById(R.id.imageViewRecipe);
        imageViewRecipe.setText(recipesToBeDisplayed.getRecipeImage());

        TextView textViewUsing = holder.itemView.findViewById(R.id.textViewUsing);
        textViewUsing.setText(recipesToBeDisplayed.getUsedProd());

        TextView textViewLikes = holder.itemView.findViewById(R.id.textViewLikes);
        textViewLikes.setText(recipesToBeDisplayed.getRecipeLikes());

        holder.textView.setText(recipes.indexOf(position));
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
