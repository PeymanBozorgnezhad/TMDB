package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.databinding.BindingAdapter;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.util.UiUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class BindingAdapters
{

    @BindingAdapter("items")

    public static void setItems(ChipGroup view, List<Genre> genres) {

        if (genres == null

                // Since we are using liveData to observe data, any changes in that table(favorites)

                // will trigger the observer and hence rebinding data, which can lead to duplicates.

                || view.getChildCount() > 0)

            return;



        // dynamically create & add genre chips

        Context context = view.getContext();

        for (Genre genre : genres) {

            Chip chip = new Chip(context);

            chip.setText(genre.getName());

            chip.setChipStrokeWidth(UiUtils.dipToPixels(context, 1));

            chip.setChipStrokeColor(ColorStateList.valueOf(

                    context.getResources().getColor(R.color.md_blue_grey_200)));

            chip.setChipBackgroundColorResource(android.R.color.transparent);

            view.addView(chip);

        }

    }






}
