package tp.project.aviaApplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import tp.project.aviaApplication.R;
import tp.project.aviaApplication.api.models.City;
import tp.project.aviaApplication.ui.cities.FragmentCitiesSearch;

import java.util.List;

public class CitiesRecycleViewAdapter extends RecyclerView.Adapter<CitiesRecycleViewAdapter.CitiesViewHolder> {

    public static class CitiesViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCityTv;

        public CitiesViewHolder(View view) {
            super(view);
            nameCityTv = view.findViewById(R.id.element_of_list_cities_name_tv);
        }
    }

    private FragmentCitiesSearch fragmentCitiesSearch;

    public CitiesRecycleViewAdapter(FragmentCitiesSearch fragmentCitiesSearch) {
        this.fragmentCitiesSearch = fragmentCitiesSearch;
    }

    private AsyncListDiffer<City> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldProduct, @NonNull City newProduct) {
            return oldProduct.getId().equals(newProduct.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull City city, @NonNull City city1) {
            return city.equals(city1);
        }
    };

    @NonNull
    @Override
    public CitiesRecycleViewAdapter.CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_cities, parent, false);
        return new CitiesRecycleViewAdapter.CitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecycleViewAdapter.CitiesViewHolder holder, int position) {
        City c = differ.getCurrentList().get(position);
        holder.nameCityTv.setText(c.getCityName());
        holder.itemView.setOnClickListener(v -> {
            fragmentCitiesSearch.sendChosenData(c);
        });
    }


    public void submitList(List<City> cityList) {
        differ.submitList(cityList);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}