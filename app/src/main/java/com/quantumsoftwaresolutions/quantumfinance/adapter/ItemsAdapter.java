package com.quantumsoftwaresolutions.quantumfinance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.model.Item;

import java.util.List;

public class ItemsAdapter extends
        RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView typeTextView;
        public final TextView descriptionTextView;
        public final TextView frequencyTextView;
        public final TextView amountTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            typeTextView = (TextView) itemView.findViewById(R.id.item_type);
            descriptionTextView = (TextView) itemView.findViewById(R.id.item_description);
            frequencyTextView = (TextView) itemView.findViewById(R.id.item_frequency);
            amountTextView = (TextView) itemView.findViewById(R.id.item_amount);
        }
    }

    private final List<Item> items;
    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View areaView = inflater.inflate(R.layout.item_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(areaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder viewHolder, int position) {
        Item item = items.get(position);

        TextView typeView = viewHolder.typeTextView;
        typeView.setText(item.getBasicInfo().getType());

        TextView descriptionView = viewHolder.descriptionTextView;
        descriptionView.setText(item.getBasicInfo().getDescription());

        TextView frequencyView = viewHolder.frequencyTextView;
        frequencyView.setText(item.getFrequency());

        TextView amountView = viewHolder.amountTextView;
        String amount = String.format("R%.2f", item.getAmount()).replace('.', ',');
        amountView.setText(amount);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position){
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        this.notifyItemInserted(items.size() - 1);
    }

    public void editItem(String type, String description, String date, String freq, double amount, int position) {
        Item item = items.get(position - 1);
        item.editItem(type, description, date, freq, amount);
        items.set(position - 1, item);
        this.notifyItemChanged(position - 1);
    }

    public void deleteItem(int position) {
        items.remove(position - 1);
        this.notifyItemRemoved(position - 1);
    }
}