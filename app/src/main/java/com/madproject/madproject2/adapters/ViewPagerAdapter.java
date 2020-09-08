package com.madproject.madproject2.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.madproject.madproject2.models.Recipe;
import com.madproject.madproject2.ui.fragments.ViewDirectionsFragment;
import com.madproject.madproject2.ui.fragments.ViewIngredientsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 2;
    private final Recipe recipe;
    private String[] tabTitles = {"Ingredients", "Directions"};

    public ViewPagerAdapter(FragmentManager fm, Recipe recipe) {
        super(fm);
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = ViewIngredientsFragment.newInstance(recipe.getIngredients());
                break;
            case 1:
                frag = ViewDirectionsFragment.newInstance(recipe.getDirections());
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
