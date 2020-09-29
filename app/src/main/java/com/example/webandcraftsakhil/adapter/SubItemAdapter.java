package com.example.webandcraftsakhil.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.webandcraftsakhil.R;
import com.example.webandcraftsakhil.requests.category.Products;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {
    private static final String TAG = "SubItemAdapter";
    private List<Products> subItemList;
    private OnProuductClickListener prouductClickListener;
    private Context context;
    SubItemAdapter(List<Products> subItemList, OnProuductClickListener onProuductClickListener, Context mContext) {
        this.subItemList = subItemList;
        prouductClickListener = onProuductClickListener;
        this.context = mContext;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_product, viewGroup, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder subItemViewHolder, int i) {
        final Products subItem = subItemList.get(i);
        subItemViewHolder.tvSubItemTitle.setText(subItem.getTitle());
        subItemViewHolder.tvProductPrice.setText(String.valueOf(subItem.getPrice()));
        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_launcher_background)
                .load(subItem.imageUrl)
                .into(subItemViewHolder.imageView);
        subItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prouductClickListener.onProductClick(subItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder  {
        TextView tvSubItemTitle;
        TextView tvProductPrice;
        ImageView imageView;

        SubItemViewHolder(View itemView) {
            super(itemView);
            tvSubItemTitle = itemView.findViewById(R.id.tv_title);
            tvProductPrice = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.imageView);
        }

    }


}
