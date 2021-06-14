package com.example.aviaapplication.ui.flightHistory;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.FlightHistoryRecyclerViewAdapter;
import com.example.aviaapplication.api.models.Purchase;
import com.example.aviaapplication.api.models.RecentCity;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.Resource;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

public class FlightHistoryFragment extends Fragment {
    public ProgressBar progressBar;
    private FlightHistoryViewModel flightHistoryViewModel;
    private RecyclerView recyclerView;
    private FlightHistoryRecyclerViewAdapter flightHistoryRecyclerViewAdapter;
    private LinearLayout emptyHistoryLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_flights, container, false);
        flightHistoryViewModel = new ViewModelProvider(this).get(FlightHistoryViewModel.class);

        YandexMetrica.reportEvent(getString(R.string.event_user_history));

        initViews(view);
        setUpListeners();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews(View view){
        emptyHistoryLayout = view.findViewById(R.id.flights_history_ll);
        progressBar = view.findViewById(R.id.flight_history_pb);
        flightHistoryRecyclerViewAdapter =  new FlightHistoryRecyclerViewAdapter();
        recyclerView = view.findViewById(R.id.flight_history_rv);
        recyclerView.setAdapter(flightHistoryRecyclerViewAdapter);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyHistoryLayout.setVisibility(View.GONE);

        flightHistoryViewModel.getPurchases().observe(getViewLifecycleOwner(), this::updateList);
        flightHistoryViewModel.requestPurchaseHistList(getContext());
    }

    public void updateList(Resource<List<Purchase>> list){
        if (list.getStatus() == Resource.Status.SUCCESS && list.getData() != null) {
            progressBar.setVisibility(View.GONE);

            if (list.getData().isEmpty()) {
                emptyHistoryLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyHistoryLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                flightHistoryRecyclerViewAdapter.submitList(list.getData());
            }

        }else{

            emptyHistoryLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            CommonUtils.makeErrorToast(getContext(), list.getMessage());

        }
    }

    private void setUpListeners(){
        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            getParentFragmentManager().popBackStack();
        }
    };
}
