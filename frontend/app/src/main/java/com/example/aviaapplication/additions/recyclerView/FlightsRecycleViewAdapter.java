package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.favoriteFlights.FavoriteFlightsViewModel;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.foundFlights.FoundFlights;
import com.example.aviaapplication.utils.CommonUtils;
import com.yandex.metrica.YandexMetrica;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightsRecycleViewAdapter extends RecyclerView.Adapter<FlightsRecycleViewAdapter.FlightsViewHolder> {

    public static class FlightsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, priceTV, depDateTV, fromTimeTV, toTimeTV, fromCodeTV, toCodeTV, durationTV;

        public FlightsViewHolder(View view) {
            super(view);
            titleTV = view.findViewById(R.id.from_to_title_tv);
            priceTV = view.findViewById(R.id.ticket_cost);
            depDateTV = view.findViewById(R.id.flight_info_departure_date_tv);
            fromTimeTV = view.findViewById(R.id.flight_info_departure_time_tv);
            toTimeTV = view.findViewById(R.id.flight_info_arrival_time_tv);
            fromCodeTV = view.findViewById(R.id.departure_code_tv);
            toCodeTV = view.findViewById(R.id.arrival_code_tv);
            durationTV = view.findViewById(R.id.flight_time_duration_tv);
        }
    }

    private Fragment fragment;
    private FavoriteFlightsViewModel favoriteFlightsViewModel;

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
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        favoriteFlightsViewModel = new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(FavoriteFlightsViewModel.class);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsRecycleViewAdapter.FlightsViewHolder holder, int position) {
        Flight flight = differ.getCurrentList().get(position);

        DateFormat dateFormat = new SimpleDateFormat("d MMM");
        DateFormat timeFormat = new SimpleDateFormat("H:mm");
        DateFormat diff = new SimpleDateFormat("H час");

        holder.titleTV.setText(flight.getOriginPlace().getPlaceName() + " - " + flight.getDestinationPlace().getPlaceName());
        holder.priceTV.setText(flight.getCost().toString() + "₽");
        holder.depDateTV.setText(dateFormat.format(flight.getOutboundDate()));
        holder.fromTimeTV.setText(timeFormat.format(flight.getOutboundDate()));
        holder.toTimeTV.setText(timeFormat.format(flight.getInboundDate()));
        holder.fromCodeTV.setText(flight.getOriginPlace().getPlaceId());
        holder.toCodeTV.setText(flight.getDestinationPlace().getPlaceId());

        holder.durationTV.setText(diff.format(new Date(flight.getOutboundDate().getTime() - flight.getInboundDate().getTime())));

        FlightInfoFragment target = new FlightInfoFragment();
        target.setFlight(differ.getCurrentList().get(position));

        holder.itemView.setOnClickListener(v -> {
            YandexMetrica.reportEvent(holder.itemView.getContext().getString(R.string.event_user_selected_flight_using_search));
            CommonUtils.goToFragment(fragment.getParentFragmentManager(),
                R.id.nav_host_fragment, target);
            });
    }

    private AsyncListDiffer<Flight> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Flight> DIFF_CALLBACK = new DiffUtil.ItemCallback<Flight>() {
        @Override
        public boolean areItemsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.equals(newProduct);
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
