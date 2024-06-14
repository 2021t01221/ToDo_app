package com.example.todo_new;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_TASKS = "tasks";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + "(name TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(createUserTable);

        String createTasksTable = "CREATE TABLE " + TABLE_TASKS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "deadline TEXT, " +
                "start_time TEXT, " +
                "end_time TEXT, " +
                "remind TEXT, " +
                "repeat TEXT, " +
                "category TEXT)";
        sqLiteDatabase.execSQL(createTasksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(sqLiteDatabase);
    }

    public void register(String name, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_USERS, null, cv);
        if (result == -1) {
            Log.e("Database", "Failed to insert user");
        } else {
            Log.d("Database", "User inserted successfully");
        }
        db.close();
    }
    public int login(String email, String password) {
        int result = 0;
        String[] str = { email, password };
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close();
        db.close();
        Log.d("Database", "Login result: " + result);
        return result;
    }

    @SuppressLint("Range")
    public String getUserName(String email) {
        String userName = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM " + TABLE_USERS + " WHERE email=?", new String[]{email});
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        db.close();
        return userName;
    }
//Add task
    public void insertTask(String title, String deadline, String startTime, String endTime, String remind, String repeat, String category) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("deadline", deadline);
        cv.put("start_time", startTime);
        cv.put("end_time", endTime);
        cv.put("remind", remind);
        cv.put("repeat", repeat);
        cv.put("category", category);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_TASKS, null, cv);
        if (result == -1) {
            Log.e("Database", "Failed to insert task");
        } else {
            Log.d("Database", "Task inserted successfully");
        }
        db.close();
    }
// update_task
    public void updateTask(int id, String title, String deadline, String startTime, String endTime, String remind, String repeat, String category) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("deadline", deadline);
        cv.put("start_time", startTime);
        cv.put("end_time", endTime);
        cv.put("remind", remind);
        cv.put("repeat", repeat);
        cv.put("category", category);
        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(TABLE_TASKS, cv, "id=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Log.e("Database", "Failed to update task");
        } else {
            Log.d("Database", "Task updated successfully");
        }
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_TASKS, "id=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Log.e("Database", "Failed to delete task");
        } else {
            Log.d("Database", "Task deleted successfully");
        }
        db.close();
    }
//update user profile
    public void updateUserProfile(String oldEmail, String newName, String newEmail) {
        ContentValues cv = new ContentValues();
        cv.put("name", newName);
        cv.put("email", newEmail);
        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(TABLE_USERS, cv, "email=?", new String[]{oldEmail});
        if (result == -1) {
            Log.e("Database", "Failed to update user profile");
        } else {
            Log.d("Database", "User profile updated successfully");
        }
        db.close();
    }
//method to update password
    public void updatePassword(String email, String newPassword) {
        ContentValues cv = new ContentValues();
        cv.put("password", newPassword);
        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(TABLE_USERS, cv, "email=?", new String[]{email});
        if (result == -1) {
            Log.e("Database", "Failed to update password");
        } else {
            Log.d("Database", "Password updated successfully");
        }
        db.close();
    }

    // Method to validate the password
    public static boolean isValid(String password) {
        // Password must be exactly 8 characters long
        if (password.length() != 8) {
            return false;
        }

        // Regular expression to check if password includes letters, digits, and symbols
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8}$";
        return password.matches(regex);
    }



    //  retrieve tasks by category
    @SuppressLint("Range")
    public List<Task> getTasksByCategory(String category) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS + " WHERE category=?", new String[]{category});
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex("id")));
                task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                task.setDeadline(cursor.getString(cursor.getColumnIndex("deadline")));
                task.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
                task.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
                task.setRemind(cursor.getString(cursor.getColumnIndex("remind")));
                task.setRepeat(cursor.getString(cursor.getColumnIndex("repeat")));
                task.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}



