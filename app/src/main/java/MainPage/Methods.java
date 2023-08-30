package MainPage;

import retrofit.Call;
import retrofit.http.GET;

public interface Methods {
    @GET("039b702124215ef4d0e4")
    Call<Example> getAllData();
}
