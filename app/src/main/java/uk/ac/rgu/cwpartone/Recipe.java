package uk.ac.rgu.cwpartone;

public class Recipe {
    private String recipeName;
    private String recipeMissed;
    private String recipeUsed;
    private String recipeImage;
    private String recipeLikes;

    private boolean isSelected;

    public Recipe(String name, String missNum,
                  String usedNum,  String image,
                  String likesNum, boolean selected){

        recipeName = name;
        recipeMissed = missNum;
        recipeUsed = usedNum;
        recipeImage = image;
        recipeLikes = likesNum;
        isSelected = selected;
    }

    // Getters
    public String getRecipeName(){
        return recipeName;
    }

    public String getMissedProd() {
        return recipeMissed;
    }

    public String getUsedProd() {
        return recipeUsed;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeLikes() {
        return recipeLikes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    // Setters

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setMissedProd(String recipeMissed) {
        this.recipeMissed = recipeMissed;
    }

    public void setUsedProd(String recipeUsed) {
        this.recipeUsed = recipeUsed;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public void setRecipeLikes(String recipeLikes) {
        this.recipeLikes = recipeLikes;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}


