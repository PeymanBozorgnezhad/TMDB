package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ItemRowCastBinding;
import com.example.mytmdbclient.model.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.castViewHolder>
{
    private Context context;
    private List<Cast> castList;
    private Cast TempValues;
    private ItemRowCastBinding itemRowCastBinding;


    public CastAdapter(Context context, List<Cast> castList)
    {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public castViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemRowCastBinding itemRowCastBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_row_cast, parent, false);

        return new castViewHolder(itemRowCastBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull castViewHolder holder, int position)
    {
        TempValues = castList.get(position);
        holder.itemRowCastBinding.setCast(TempValues);

    }

    @Override
    public int getItemCount()
    {
        if (castList != null)
            return castList.size();
        else
            return 0;
    }

    public class castViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowCastBinding itemRowCastBinding;

        public castViewHolder(@NonNull ItemRowCastBinding itemRowCastBinding)
        {
            super(itemRowCastBinding.getRoot());
            this.itemRowCastBinding = itemRowCastBinding;

            itemRowCastBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });
        }
    }

    public void setItemRowCastBinding(ItemRowCastBinding itemRowCastBinding)
    {
        this.itemRowCastBinding = itemRowCastBinding;
    }
}
