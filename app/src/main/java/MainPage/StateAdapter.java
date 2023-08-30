package MainPage;

import static MainPage.Places.Constant.placesList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Community.R;

import java.util.ArrayList;
import java.util.List;

import MainPage.Places.PlacesDetail;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    Context context;
    List<States> stateModuleList;
    public StateAdapter(Context context, List<States> stateModuleArrayList) {
        this.context = context;
        this.stateModuleList = stateModuleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView_state.setText(stateModuleList.get(position).getName());
        Log.e("ifjekj", " "+ stateModuleList.get(position).getStateImg());
        Glide.with(context).load(stateModuleList.get(position).getStateImg()).into(holder.imageView_state);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placesList = stateModuleList.get(position).getPlaces();
                context.startActivity(new Intent(context, PlacesDetail.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateModuleList.size();
    }

    public void filterList(ArrayList<States> filteredList) {
        stateModuleList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_state;
        TextView textView_state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_state = itemView.findViewById(R.id.idIVCourse);
            textView_state = itemView.findViewById(R.id.idTVCourse);
        }
    }
}
