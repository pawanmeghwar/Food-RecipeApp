package com.madproject.madproject2.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.madproject.madproject2.R;
import com.madproject.madproject2.models.Recipe;

import java.io.File;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context mContext;
    private RecipeListener recipeListener;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        mContext = context;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item_row, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView titleLabel;
        ImageView thumbnail;
        ImageView overflow;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            titleLabel = itemView.findViewById(R.id.titleLabel);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            overflow = itemView.findViewById(R.id.overflow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recipeListener != null)
                        recipeListener.onShowRecipe(recipeList.get(RecipeViewHolder.this.getAdapterPosition()), new Pair[]{
                                Pair.create(thumbnail, "image_shared")
                        });
                }
            });
            overflow.setOnClickListener(this::showPopupMenu);
        }

        public void bind(Recipe recipe) {
            titleLabel.setText(recipe.getName());
            thumbnail.setImageURI(Uri.fromFile(new File(recipe.getImagePath())));
        }

        private void showPopupMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.grid_popup_menu);
            popup.setOnMenuItemClickListener(
                    new MyMenuItemClickListener(recipeList.get(getAdapterPosition())));
            popup.show();
        }

        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

            private Recipe recipe;

            public MyMenuItemClickListener(Recipe recipe) {
                this.recipe = recipe;
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_edit_recipe:
                        if (recipeListener != null)
                            recipeListener.onEditRecipe(recipe);
                        break;
                    case R.id.action_delete_recipe:
                        if (recipeListener != null)
                            recipeListener.onDeleteRecipe(recipe.getId());
                        return true;
                    default:
                }
                return false;
            }
        }
    }

    public void setRecipeListener(RecipeListener recipeListener) {
        this.recipeListener = recipeListener;
    }

    public interface RecipeListener {
        void onShowRecipe(Recipe recipe, Pair<View, String>[] pairs);
        void onEditRecipe(Recipe recipe);
        void onDeleteRecipe(long recipeId);
    }
}
