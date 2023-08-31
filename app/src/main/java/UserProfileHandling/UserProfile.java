package UserProfileHandling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Community.R;

import Authentication.Authentication;
import Authentication.Session_Management;
import DataBaseHandling.DataBaseHelper;
import DataBaseHandling.FetchDataModal;

public class UserProfile extends AppCompatActivity {

    private ImageView imageView, settings;
    private TextView userNameTextView, userEmailTextView;
    private LinearLayout cardView, cardView2;
    private Session_Management sessionManager;
    private DataBaseHelper dbHandler;
    private AlertDialog.Builder alert;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        sessionManager = new Session_Management(this);
        dbHandler = new DataBaseHelper(this);

        imageView = findViewById(R.id.backTo);
        settings = findViewById(R.id.settings);
        userNameTextView = findViewById(R.id.userName);
        userEmailTextView = findViewById(R.id.userId);

        imageView.setOnClickListener(view -> finish());

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, AppSettings.class));
            }
        });

        userEmail = sessionManager.getUserEmail();
        if (userEmail != null) {
            FetchDataModal userData = dbHandler.fetchUserData(userEmail);
            if (userData != null) {
                displayUserData(userData);
            }
        }

        setupLogoutCardView();
        setupDeleteAccount();
    }

    private void setupLogoutCardView() {
        cardView = findViewById(R.id.logout_accountId);
        alert = new AlertDialog.Builder(this);

        cardView.setOnClickListener(view -> {
            alert.setMessage("Are you sure,\nYou wanted to LogOut.").setTitle("Alert Dialog");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (sessionManager.isLoggedIn()) {
                        sessionManager.setLoggedIn(false);
                        sessionManager.saveUserEmail(null);
                        redirectToLogin();finish();
                    }
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();

        });
    }

    private void setupDeleteAccount() {
        cardView2 = findViewById(R.id.delete_accountId);
        alert = new AlertDialog.Builder(this);

        cardView2.setOnClickListener(view -> {
            alert.setMessage("Are you sure,\nYou wanted to Delete Your Account.").setTitle("Alert Dialog");
            alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                if (sessionManager.isLoggedIn()) {
                    dbHandler.deleteUser(userEmail);
                    sessionManager.setLoggedIn(false);
                    sessionManager.saveUserEmail(null);
                    redirectToLogin();finish();
                }
            });
            alert.setNegativeButton("No", (dialogInterface, i) -> {
                // Do nothing
            });
            alert.show();

        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(UserProfile.this, Authentication.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void displayUserData(FetchDataModal userData) {
        String fullName = userData.getFirstName() + " " + userData.getLastName();
        userNameTextView.setText(fullName);
        userEmailTextView.setText(userData.getUserEmail());
    }
}
