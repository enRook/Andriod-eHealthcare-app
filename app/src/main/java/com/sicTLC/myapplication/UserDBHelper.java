package com.sicTLC.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";

    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_USER_ROLE = "user_role";
    public static final String COLUMN_AGE = "age";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_BIRTHDATE + " TEXT, "
                + COLUMN_USER_ROLE + " TEXT, "
                + COLUMN_AGE + " INTEGER)";

        db.execSQL(CREATE_USERS_TABLE);
    }
    public void insertDefaultAdmin() {
        // Define default admin credentials
        String adminUsername = "admin";
        String adminPassword = "admin"; // You should hash passwords for security

        // Check if the admin already exists
        if (!doesUsernameExist(adminUsername)) {
            // Insert the admin user with appropriate details
            insertUser(adminUsername, "Admin", "User", adminPassword, "@ex", "1970-01-01", "Admin", "0");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Insert new user into the database

    public long insertUser(String username, String firstName, String lastName, String password, String email,
                           String birthdate, String userRole, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Put your values into ContentValues here
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_BIRTHDATE, birthdate);
        values.put(COLUMN_USER_ROLE, userRole);
        values.put(COLUMN_AGE, age);
        try {
            long id = db.insert(TABLE_USERS, null, values);
            db.close();
            Log.d("UserDBHelper", "User inserted with ID: " + id); // Add this line to log successful insertion
            return id;
        } catch (Exception e) {
            Log.e("UserDBHelper", "Error inserting user: " + e.getMessage()); // Log the error details
            e.printStackTrace();
            db.close();
            return -1; // Return an error code indicating failure

        }
    }





    // Check if a username already exists in the database
    public boolean doesUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Other methods for database operations (e.g., update, delete, query) can be added here

    // Add this method to your UserDBHelper class
    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userRole = null;

        if (db == null) {
            return userRole; // Return null if the database is not available
        }

        String[] columns = {COLUMN_USER_ROLE};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userRoleIndex = cursor.getColumnIndex(COLUMN_USER_ROLE);
            userRole = cursor.getString(userRoleIndex);
            cursor.close();
        }

        db.close();

        return userRole;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String birthdate = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDATE));
                String userRole = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE));
                String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));

                // Create User object and add it to the list
                User user = new User(id, username, firstName, lastName, email, birthdate, userRole, age);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

    public boolean deleteUserById(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_USERS, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();

        return deletedRows > 0; // Returns true if at least one row was affected (user was deleted)
    }

    // Check if a user ID already exists in the database
    public boolean doesUserIdExist(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateUser(long userId, String firstName, String lastName, String password, String email, String birthDate, String userType, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Update values for specific columns
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_BIRTHDATE, birthDate);
        values.put(COLUMN_USER_ROLE, userType);
        values.put(COLUMN_AGE, age);

        // Perform the update operation
        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0; // Return true if rows were affected (update successful)
    }



}

