package DataBaseHandling;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserAuthenticationManager";
    private static final String TABLE_NAME = "UserManagement";
    private static final String KEY_FirstName = "FirstName";
    private static final String KEY_Lastname = "LastName";
    private static final String KEY_Email = "UserEmail";
    private static final String KEY_Password = "UserEmailPassword";

    private static final String KEY_registration_date = "Registration Date";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //user_id INT PRIMARY KEY AUTO_INCREMENT,
//    username VARCHAR(50) NOT NULL,
//    email VARCHAR(100) NOT NULL,
//    password VARCHAR(255) NOT NULL,
//    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_FirstName + " VARCHAR(50) NOT NULL, " +
                        KEY_Lastname + " VARCHAR(50) NOT NULL, " +
                        KEY_Email + " VARCHAR(100) NOT NULL PRIMARY KEY, " +
                        KEY_Password + " VARCHAR(255) NOT NULL, " +
                        KEY_registration_date + " DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void onAddNewData(String firstName, String lastName, String userEmail, String emailPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FirstName, firstName);
        values.put(KEY_Lastname, lastName);
        values.put(KEY_Email, userEmail);
        values.put(KEY_Password, emailPassword);

        db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<FetchDataModal> fetchData() {
        ArrayList<FetchDataModal> fetchDataModalArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") FetchDataModal fetchDataModal = new FetchDataModal(
                        cursor.getString(cursor.getColumnIndex(KEY_FirstName)),
                        cursor.getString(cursor.getColumnIndex(KEY_Lastname)),
                        cursor.getString(cursor.getColumnIndex(KEY_Email)),
                        cursor.getString(cursor.getColumnIndex(KEY_Password))
                );
                fetchDataModalArrayList.add(fetchDataModal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("TAG", "fetchData: " + fetchDataModalArrayList.size());
        return fetchDataModalArrayList;
    }
    public FetchDataModal fetchUserData(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_Email + "=?", new String[]{userEmail}, null, null, null);

        FetchDataModal userData = null;

        try {
            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(cursor.getColumnIndex(KEY_FirstName));
                String lastName = cursor.getString(cursor.getColumnIndex(KEY_Lastname));
                String email = cursor.getString(cursor.getColumnIndex(KEY_Email));
                String password = cursor.getString(cursor.getColumnIndex(KEY_Password));

                userData = new FetchDataModal(firstName, lastName, email, password);
            }
        } finally {
            cursor.close();
        }

        return userData;
    }

    public void deleteUser(String emailId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_Email + "=?", new String[]{emailId});
        db.close();
    }
}
