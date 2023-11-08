package com.khataab.changeenglishlanguagecenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomMenuAdapter extends RecyclerView.Adapter<CustomMenuAdapter.ViewHolder> {
    private List<MenuItemData> menuItems; // Create a data structure to hold your menu items

    private OnItemClickListener itemClickListener;

    public CustomMenuAdapter(List<MenuItemData> menuItems) {
        this.menuItems = menuItems;
    }

    // Define an interface for item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Setter method to set the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItemData item = menuItems.get(position);
        holder.menuItemIcon.setImageResource(item.getIconResource());
        holder.menuItemText.setText(item.getText());
        // Add click listeners for menu items if needed
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuItemIcon;
        TextView menuItemText;

        public ViewHolder(View itemView) {
            super(itemView);
            menuItemIcon = itemView.findViewById(R.id.menu_item_icon);
            menuItemText = itemView.findViewById(R.id.menu_item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
