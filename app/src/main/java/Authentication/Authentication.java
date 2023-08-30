package Authentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Community.R;

public class Authentication extends AppCompatActivity implements Call_Frag{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        onAddFrag();
    }

    public void onAddFrag(){
        Login_Fragment loginFragment = new Login_Fragment();
        loginFragment.setCall_frag(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null).add(R.id.multipleFragments,loginFragment).commit();
    }

    public void onReplaceFrag(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null).replace(R.id.multipleFragments, new Registration_Fragment()).commit();
    }

    @Override
    public void changeFragment() {
        onReplaceFrag();
    }

/*
    public void loadFrag(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.multipleFragments,f).commit();
    }
*/
}