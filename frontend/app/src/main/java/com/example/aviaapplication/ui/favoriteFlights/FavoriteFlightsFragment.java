package com.example.aviaapplication.ui.favoriteFlights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.FavoriteFlightsRecyclerViewAdapter;
import com.example.aviaapplication.additions.recyclerView.FlightsRecycleViewAdapter;
import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.RecyclerviewOnClickListener;
import com.example.aviaapplication.utils.Resource;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.impl.ob.V;

import java.util.List;

public class FavoriteFlightsFragment extends Fragment implements RecyclerviewOnClickListener {
    private FavoriteFlightsViewModel favoriteFlightsViewModel;
    private RecyclerView recyclerView;
    public ProgressBar progressBar;
    private FavoriteFlightsRecyclerViewAdapter mAdapter;
    private LinearLayout emptyFavoriteListLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        favoriteFlightsViewModel = new ViewModelProvider(this).get(FavoriteFlightsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_favorite_flights, container, false);
        initViews(view);

        if(!favoriteFlightsViewModel.isLoggedIn(getContext())) {
            CommonUtils.makeErrorToast(getContext(), getString(R.string.login_error));
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.navigation_main_search);
        }

        YandexMetrica.reportEvent(getString(R.string.event_user_went_to_favorites));

        return view;
    }

    private void initViews(View view) {
        emptyFavoriteListLL = view.findViewById(R.id.favorite_flights_ll);
        progressBar = view.findViewById(R.id.favorite_flights_pb);
        mAdapter = new FavoriteFlightsRecyclerViewAdapter(this);
        recyclerView = view.findViewById(R.id.favorite_flights_rv);
        recyclerView.setAdapter(mAdapter);

        recyclerView.setVisibility(View.GONE);
        emptyFavoriteListLL.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        favoriteFlightsViewModel.getFavoriteFlightLiveData().observe(getViewLifecycleOwner(),
                listResource -> {

                    if (listResource.getStatus() == Resource.Status.SUCCESS){
                        updateList(listResource.getData());
                    }
                    else
                        CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
//                        CommonUtils.makeErrorToast(getContext(),
//                                listResource.getMessage());

                });

        favoriteFlightsViewModel.getIsDeletedLiveData().observe(getViewLifecycleOwner(),
                booleanResource -> {

                if(booleanResource.getStatus() == Resource.Status.SUCCESS)
                    favoriteFlightsViewModel.requestFavoriteList(getContext());
                else
                    CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
//                    CommonUtils.makeErrorToast(getContext(), booleanResource.getMessage());
                });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_main_search);
            }
        });

        favoriteFlightsViewModel.requestFavoriteList(getContext());
    }

    public void updateList(List<FavoriteFlight> list) {
        progressBar.setVisibility(View.GONE);
        if (list.isEmpty()) {
            emptyFavoriteListLL.setVisibility(View.VISIBLE);
        } else {
            emptyFavoriteListLL.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter.submitList(list);
        }
    }

    @Override
    public void onRecycleViewCLick(int position) {
        favoriteFlightsViewModel.removeFavoriteFromList(position);
    }
}
