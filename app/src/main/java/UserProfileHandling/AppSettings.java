package UserProfileHandling;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Community.R;

public class AppSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        ImageView imageView = findViewById(R.id.backTo);
        imageView.setOnClickListener(view -> finish());
    }
}