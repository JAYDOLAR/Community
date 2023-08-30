package MainPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class States {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("stateImg")
    @Expose
    private String stateImg;
    @SerializedName("places")
    @Expose
    private List<Place> places;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateImg() {
        return stateImg;
    }

    public void setStateImg(String stateImg) {
        this.stateImg = stateImg;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

}