package Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Community.R;

import java.util.ArrayList;

import DataBaseHandling.DataBaseHelper;
import DataBaseHandling.FetchDataModal;
import MainPage.FrontPage;

public class Registration_Fragment extends Fragment {

    View view;
    Button submitView;
    EditText editTextTextEmailAddress2, editTextTextEmailPassword2, editText_lastname, editText_firstname;
    String email, password, first_name, last_name;
    DataBaseHelper dbHandler;
    ArrayList<FetchDataModal> fetchDataModals;
    private Session_Management Session_Manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        submitView = view.findViewById(R.id.submitView);
        editTextTextEmailAddress2 = view.findViewById(R.id.editTextEmailAddress2);
        editTextTextEmailPassword2 = view.findViewById(R.id.editText_Password2);
        editText_lastname = view.findViewById(R.id.editText_lastname);
        editText_firstname = view.findViewById(R.id.editText_firstname);
        dbHandler = new DataBaseHelper(getContext());
        Session_Manager = new Session_Management(requireContext());
        fetchDataModals = dbHandler.fetchData();
        if (Session_Manager.isLoggedIn()) {
            navigateToMainActivity();
        } else {
            giveAccess();
        }
        return view;
    }

    public void giveAccess() {
        submitView.setOnClickListener(view -> {
            email = editTextTextEmailAddress2.getText().toString();
            password = editTextTextEmailPassword2.getText().toString();
            first_name = editText_firstname.getText().toString();
            last_name = editText_lastname.getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && !first_name.isEmpty() && !last_name.isEmpty()) {
                System.out.println(first_name + " " + last_name + " " + email + " " + password);
                if (!isValidEmail(email) && !isValidPassword(password)) {
                    Toast.makeText(getContext(), "Input Valid email and Password..", Toast.LENGTH_SHORT).show();
                } else {
                    boolean emailExists = false;
                    for (FetchDataModal modal : fetchDataModals) {
                        if (email.equals(modal.getUserEmail())) {
                            emailExists = true;
                            break;
                        }
                    }
                    if (emailExists) {
                        Toast.makeText(getContext(), "This email is already in use", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHandler.onAddNewData(first_name, last_name, email, password);
                        Toast.makeText(getContext(), "Welcome to Community", Toast.LENGTH_SHORT).show();
                        Session_Manager.setLoggedIn(true);
                        Session_Manager.saveUserEmail(email);
                        navigateToMainActivity();
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
                if (first_name.isEmpty()) {
                    errorMessage.append("\n- First Name");
                }
                if (last_name.isEmpty()) {
                    errorMessage.append("\n- Last Name");
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
