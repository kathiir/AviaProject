package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.favoriteFlights.FavoriteFlightsFragment;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.utils.CommonUtils;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.impl.ob.Ya;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class FavoriteFlightsRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteFlightsRecyclerViewAdapter.FavoriteFlightsViewHolder> {
    public static class FavoriteFlightsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, priceTV, depDateTV, fromTimeTV, toTimeTV, fromCodeTV, toCodeTV, durationTV;
        private ImageView closeView;

        public FavoriteFlightsViewHolder(View view) {
            super(view);
            titleTV = view.findViewById(R.id.from_to_title_tv);
            priceTV = view.findViewById(R.id.ticket_cost);
            depDateTV = view.findViewById(R.id.flight_info_departure_date_tv);
            fromTimeTV = view.findViewById(R.id.flight_info_departure_time_tv);
            toTimeTV = view.findViewById(R.id.flight_info_arrival_time_tv);
            fromCodeTV = view.findViewById(R.id.departure_code_tv);
            toCodeTV = view.findViewById(R.id.arrival_code_tv);
            durationTV = view.findViewById(R.id.flight_time_duration_tv);
            closeView = view.findViewById(R.id.close_image_view);
        }
    }

    private FavoriteFlightsFragment fragment;

    public FavoriteFlightsRecyclerViewAdapter(FavoriteFlightsFragment fragment) {
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public FavoriteFlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_favorite_flights, parent, false);
        return new FavoriteFlightsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFlightsViewHolder holder, int position) {

        Flight flight = differ.getCurrentList().get(position).getFlight();

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


        holder.closeView.setOnClickListener(v -> {
            YandexMetrica.reportEvent("Removed FavoriteFlight");
            fragment.onRecycleViewCLick(position);
        });


        FlightInfoFragment target = new FlightInfoFragment();
        target.setFlight(differ.getCurrentList().get(position).getFlight());

        holder.itemView.setOnClickListener(v -> {
            YandexMetrica.reportEvent(holder.itemView.getContext().getString(R.string.event_user_selected_flight_in_favorite));
            CommonUtils.goToFragment(fragment.getParentFragmentManager(),
                    R.id.nav_host_fragment, target);

        });


    }

    private AsyncListDiffer<FavoriteFlight> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<FavoriteFlight> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteFlight>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavoriteFlight oldProduct, @NonNull FavoriteFlight newProduct) {
            return true;// oldProduct.getFlightId().equals(newProduct.getFlightId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull FavoriteFlight oldProduct, @NonNull FavoriteFlight newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    public void submitList(List<FavoriteFlight> products) {
        differ.submitList(products);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}
