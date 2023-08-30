package Authentication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Session_Management {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
//    private static final String[] KEY_EMAIL = new String[0];
    private static final ArrayList<String> KEY_EMAIL = new ArrayList<>();
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public Session_Management(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void saveUserEmail(String email) {
        editor.putString(String.valueOf(KEY_EMAIL), email);
        editor.apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(String.valueOf(KEY_EMAIL), null);
    }
}
