package Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Community.R;

import java.util.ArrayList;

import DataBaseHandling.DataBaseHelper;
import DataBaseHandling.FetchDataModal;
import MainPage.FrontPage;

public class Login_Fragment extends Fragment {
    View view;
    EditText editTextTextEmailAddress, editTextTextEmailPassword;
    TextView textViewAuthentic;
    Button submitView;
    Call_Frag call_frag;
    String email, password;
    DataBaseHelper dbHandler;
    ArrayList<FetchDataModal> fetchDataModals;
    Session_Management Session_Manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_, container, false);
        textViewAuthentic = view.findViewById(R.id.textViewAuthentic);
        editTextTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextEmailPassword = view.findViewById(R.id.editTextTextEmailPassword);
        submitView = view.findViewById(R.id.submitView);
        dbHandler = new DataBaseHelper(getContext());
        Session_Manager = new Session_Management(requireContext());
        fetchDataModals = dbHandler.fetchData();
        if (Session_Manager.isLoggedIn()) {
            System.out.println(email);
            navigateToMainActivity();
        } else {
            giveAccess();
        }
        onChangeFrag();
        return view;
    }

    public void setCall_frag(Call_Frag call_frag) {
        this.call_frag = call_frag;
    }

    public void onChangeFrag() {
        textViewAuthentic.setOnClickListener(view -> {
            if (call_frag != null) {
                call_frag.changeFragment();
            }
        });
    }

    public void giveAccess() {
        submitView.setOnClickListener(view -> {
            email = editTextTextEmailAddress.getText().toString();
            password = editTextTextEmailPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                if (!isValidEmail(email) && !isValidPassword(password)) {
                    Toast.makeText(getContext(), "Enter a valid email and password.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean emailAndPasswordMatch = false;
                    for (FetchDataModal modal : fetchDataModals) {
                        if (email.equals(modal.getUserEmail()) && password.equals(modal.getUserEmailPassword())) {
                            emailAndPasswordMatch = true;
                            break;
                        }
                    }
                    if (emailAndPasswordMatch) {
                        Toast.makeText(getContext(), "Welcome to the Community", Toast.LENGTH_SHORT).show();
                        Session_Manager.setLoggedIn(true);
                        Session_Manager.saveUserEmail(email);
                        navigateToMainActivity();
                    } else {
                        Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                StringBuilder errorMessage = new StringBuilder("Please fill in the following fields:");
                if (email.isEmpty()) {
                    errorMessage.append("\n- Email");
                }
                if (password.isEmpty()) {
                    errorMessage.append("\n- Password");
                }
                Toast.makeText(getContext(), errorMessage.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(requireContext(), FrontPage.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
