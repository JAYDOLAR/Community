package MainPage.Places;


import static MainPage.Places.Constant.placesList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Community.R;

import java.util.ArrayList;

import MainPage.Place;

public class PlacesDetail extends AppCompatActivity {

    SearchView searchView;
    ArrayList<Place> placeArray;
    PlaceDetailAdapter recycleAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_detail);
        recyclerView  = findViewById(R.id.list_columns);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout1);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlacesDetail.this));
        recycleAdapter = new PlaceDetailAdapter(PlacesDetail.this, placesList);
        recyclerView.setAdapter(recycleAdapter);
        placeArray = new ArrayList<>(placesList);
        search();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                recyclerView  = findViewById(R.id.list_columns);
                swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout1);
                recyclerView.setLayoutManager(new LinearLayoutManager(PlacesDetail.this));
                recycleAdapter = new PlaceDetailAdapter(PlacesDetail.this, placesList);
                recyclerView.setAdapter(recycleAdapter);
                placeArray = new ArrayList<>(placesList);
                search();
            }
        });

    }

    public void search(){
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }
    public void filter(String text) {
        ArrayList<Place> filteredList = new ArrayList<>();
        for (Place item : placeArray) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recycleAdapter.filterList(filteredList);
    }
}