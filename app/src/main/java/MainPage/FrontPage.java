package MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Community.R;

import java.util.ArrayList;

import UserProfileHandling.UserProfile;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FrontPage extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<States> placeArray;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView;
    StateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        onSetImageView();
        onGoUserPage();
    }

    public void onSetImageView(){
        recyclerView = findViewById(R.id.list_columns);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        final Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        final Call<Example> call = methods.getAllData();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                placeArray = new ArrayList<>(response.body().getStates());
                recyclerView.setLayoutManager(new GridLayoutManager(FrontPage.this , 2));
                adapter = new StateAdapter(FrontPage.this, response.body().getStates());
                recyclerView.setAdapter(adapter);
                search();
            }
            @Override
            public void onFailure(Throwable t) {
            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

                Call<Example> refreshCall = methods.getAllData();
                refreshCall.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Response<Example> response, Retrofit retrofit) {
                        placeArray = new ArrayList<>(response.body().getStates());
                        recyclerView.setLayoutManager(new GridLayoutManager(FrontPage.this, 2));
                        adapter = new StateAdapter(FrontPage.this, response.body().getStates());
                        recyclerView.setAdapter(adapter);
                        search();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });
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

    public void filter(String text){
        ArrayList<States> filteredList = new ArrayList<>();
        for (States item : placeArray) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
    public void onGoUserPage(){
        imageView = findViewById(R.id.userImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPage.this, UserProfile.class);
                startActivity(intent);
            }
        });
    }
}