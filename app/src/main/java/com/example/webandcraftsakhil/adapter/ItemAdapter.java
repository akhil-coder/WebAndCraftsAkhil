package com.example.webandcraftsakhil.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webandcraftsakhil.R;
import com.example.webandcraftsakhil.requests.category.Categories;
import com.example.webandcraftsakhil.requests.category.Products;

import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements OnProuductClickListener {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Categories> itemList;
    private OnMainProductClickListener onMainProductClickListener;
    private Context context;

    public ItemAdapter(Context mContext) {
        this.context = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_category, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {
        final Categories item = itemList.get(i);
        itemViewHolder.tvItemTitle.setText(item.getTitle());

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new GridLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                2,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        if (item.getProducts() != null || item.getProducts().isEmpty()) {
            {
                itemViewHolder.rvSubItem.setVisibility(View.GONE);
            }
        } else {
            layoutManager.setInitialPrefetchItemCount(item.getProducts().size());
            // Create sub item view adapter
            SubItemAdapter subItemAdapter = new SubItemAdapter(item.getProducts(), this, context);
            itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
            itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
            itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
        }

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getExpanded() == null) {
                    item.setExpanded(true);
                    itemViewHolder.rvSubItem.setVisibility(View.GONE);
                } else if (!item.getExpanded()) {
                    item.setExpanded(true);
                    itemViewHolder.rvSubItem.setVisibility(View.VISIBLE);
                } else if (item.getExpanded()) {
                    item.setExpanded(false);
                    itemViewHolder.rvSubItem.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (itemList != null)
            return itemList.size();
        return 0;
    }

    @Override
    public void onProductClick(Products products) {
        onMainProductClickListener.onMainProductClick(products);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageArrow;
        private TextView tvItemTitle;
        private RecyclerView rvSubItem;
        private ConstraintLayout constraintLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_categories);
            rvSubItem = itemView.findViewById(R.id.rv_products);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            imageArrow = itemView.findViewById(R.id.image_arrow);
        }
    }

    public void setOnMainProductClickListener(OnMainProductClickListener onMainProductClickListener) {
        this.onMainProductClickListener = onMainProductClickListener;
    }

    public void setCategories(List<Categories> genre) {
        itemList = genre;
        notifyDataSetChanged();
    }
}
