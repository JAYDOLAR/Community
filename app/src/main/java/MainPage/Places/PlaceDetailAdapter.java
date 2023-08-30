package MainPage.Places;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Community.R;

import java.util.ArrayList;
import java.util.List;

import MainPage.Place;

public class PlaceDetailAdapter extends RecyclerView.Adapter<PlaceDetailAdapter.ViewHolder> {

    Context context;
    List<Place> placeList;
    public PlaceDetailAdapter(Context context, List<Place> placeList){
        this.placeList = placeList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_detail_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceDetailAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(placeList.get(position).getImgUrl()).into(holder.placeImg);
/*
        holder.placeMap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                String placeName = placeList.get(clickedPosition).getName();
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(placeName));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                context.startActivity(mapIntent);
                return false;
            }
        });
*/
        if (placeList.get(position).getMapUrl() != null && !placeList.get(position).getMapUrl().isEmpty()) {
            holder.placeMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = holder.getAdapterPosition();
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeList.get(clickedPosition).getMapUrl()));
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            holder.placeMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "No map link available for this place.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        holder.placeName.setText(placeList.get(position).getName());
        holder.placeCategory.setText(placeList.get(position).getCategory());
        holder.placeRating.setText(placeList.get(position).getRating());
        holder.placeDescription.setText(placeList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void filterList(ArrayList<Place> filteredList) {
        placeList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImg;
        TextView placeName, placeCategory, placeRating, placeDescription, placeMap;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImg = itemView.findViewById(R.id.PlaceImg);

            placeName = itemView.findViewById(R.id.PlaceName);
            placeCategory = itemView.findViewById(R.id.PlaceCategory);
            placeRating = itemView.findViewById(R.id.PlaceRating);
            placeDescription = itemView.findViewById(R.id.PlaceDescription);
            placeMap = itemView.findViewById(R.id.PlaceMap);
        }
    }
}
