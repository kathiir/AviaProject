package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.foundFlights.FoundFlights;
import com.example.aviaapplication.utils.CommonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightsRecycleViewAdapter extends RecyclerView.Adapter<FlightsRecycleViewAdapter.FlightsViewHolder> {
    public static class FlightsViewHolder extends RecyclerView.ViewHolder {
        public FlightsViewHolder(View view) {
            super(view);
        }
    }

    private Fragment fragment;

    public FlightsRecycleViewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FlightsRecycleViewAdapter.FlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_found_flights, parent, false);
        return new FlightsRecycleViewAdapter.FlightsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsRecycleViewAdapter.FlightsViewHolder holder, int position) {
        Flight flight = differ.getCurrentList().get(position);

        TextView titleTV = holder.itemView.findViewById(R.id.from_to_title_tv);
        TextView priceTV = holder.itemView.findViewById(R.id.ticket_cost);
        TextView depDateTV = holder.itemView.findViewById(R.id.flight_info_departure_date_tv);

        TextView fromTimeTV = holder.itemView.findViewById(R.id.flight_info_departure_time_tv);
        TextView toTimeTV = holder.itemView.findViewById(R.id.flight_info_arrival_time_tv);

        TextView fromCodeTV = holder.itemView.findViewById(R.id.departure_code_tv);
        TextView toCodeTV = holder.itemView.findViewById(R.id.arrival_code_tv);

        TextView durationTV = holder.itemView.findViewById(R.id.flight_time_duration_tv);

        DateFormat dateFormat = new SimpleDateFormat("d MMM");
        DateFormat timeFormat = new SimpleDateFormat("H:mm");
        DateFormat diff = new SimpleDateFormat("H час");

        titleTV.setText(flight.getDepCity().getCityName() + " - " + flight.getArrivalCity().getCityName());
        priceTV.setText(flight.getEconomyPrice().toString() + "₽");
        depDateTV.setText(dateFormat.format(flight.getDepartureDate()));
        fromTimeTV.setText(timeFormat.format(flight.getDepartureDate()));
        toTimeTV.setText(timeFormat.format(flight.getArrivalDate()));
        fromCodeTV.setText(flight.getDepCity().getCityCode());
        toCodeTV.setText(flight.getArrivalCity().getCityCode());

        durationTV.setText(diff.format(new Date(flight.getDepartureDate().getTime()- flight.getArrivalDate().getTime())));

        Fragment target = FlightInfoFragment.getInstance(flight.getFlightId());
        holder.itemView.setOnClickListener(v -> CommonUtils.goToFragment(fragment.getParentFragmentManager(),
                R.id.nav_host_fragment, target));


    }

    private AsyncListDiffer<Flight> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Flight> DIFF_CALLBACK = new DiffUtil.ItemCallback<Flight>() {
        @Override
        public boolean areItemsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.getFlightId().equals(newProduct.getFlightId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    public void submitList(List<Flight> products) {
        differ.submitList(products);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}
