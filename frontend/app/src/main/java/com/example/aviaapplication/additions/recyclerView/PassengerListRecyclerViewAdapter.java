package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;

import com.example.aviaapplication.api.models.Passenger;

import java.util.List;

public class PassengerListRecyclerViewAdapter extends RecyclerView.Adapter<PassengerListRecyclerViewAdapter.PassengerListHolder> {
    @NonNull
    @Override
    public PassengerListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.element_of_passenger_list, parent, false);

        return new PassengerListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerListHolder holder, int position) {

    }

    private AsyncListDiffer<Passenger> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Passenger> DIFF_CALLBACK = new DiffUtil.ItemCallback<Passenger>() {
        @Override
        public boolean areItemsTheSame(@NonNull Passenger oldProduct, @NonNull Passenger newProduct) {
            return oldProduct.getId().equals(newProduct.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Passenger oldProduct, @NonNull Passenger newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    public void submitList(List<Passenger> products) {
        differ.submitList(products);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }


    public static class PassengerListHolder extends RecyclerView.ViewHolder{
        public PassengerListHolder(View view){super(view);}
    }
}
