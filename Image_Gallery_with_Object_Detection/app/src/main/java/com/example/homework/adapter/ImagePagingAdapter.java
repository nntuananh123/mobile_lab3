// adapter/ImagePagingAdapter.java
package com.example.homework.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homework.R;
import com.example.homework.models.ImageItem;

public class ImagePagingAdapter extends PagingDataAdapter<ImageItem, ImagePagingAdapter.ImageViewHolder> {

    public ImagePagingAdapter() {
        super(COMPARATOR);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageItem item = getItem(position);
        if (item != null) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getPreviewURL())
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .into(holder.imageView);

            holder.tagsTextView.setText(item.getTags());
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tagsTextView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            tagsTextView = itemView.findViewById(R.id.tags_text_view);
        }
    }

    private static final DiffUtil.ItemCallback<ImageItem> COMPARATOR =
            new DiffUtil.ItemCallback<ImageItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull ImageItem oldItem, @NonNull ImageItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ImageItem oldItem, @NonNull ImageItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }
            };
}