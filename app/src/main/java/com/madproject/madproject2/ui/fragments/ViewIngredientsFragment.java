package com.madproject.madproject2.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madproject.madproject2.R;
import com.madproject.madproject2.adapters.IngredientAdapter;
import com.madproject.madproject2.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewIngredientsFragment extends Fragment {

    private List<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;

    private RecyclerView ingredientRecyclerView;
    private TextView emptyView;

    public ViewIngredientsFragment() {
        // Required empty public constructor
    }

    public static ViewIngredientsFragment newInstance(List<Ingredient> ingredients) {
        ViewIngredientsFragment fragment = new ViewIngredientsFragment();
        if (ingredients == null)
            ingredients = new ArrayList<>();
        Bundle args = new Bundle();
        args.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_ingredients, container, false);

        Bundle args = getArguments();
        if (args != null)
            ingredientList = args.getParcelableArrayList("ingredients");

        ingredientRecyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);

        ingredientAdapter = new IngredientAdapter(getActivity(), ingredientList, false);
        toggleEmptyView();

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        return view;
    }

    private void toggleEmptyView() {
        if (ingredientList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            ingredientRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
