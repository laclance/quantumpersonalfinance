package com.quantumsoftwaresolutions.quantumfinance.repository.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.quantumsoftwaresolutions.quantumfinance.model.User;
import com.quantumsoftwaresolutions.quantumfinance.repository.database.DBAdapter;
import com.quantumsoftwaresolutions.quantumfinance.repository.database.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private SQLiteDatabase database;
    private final DBAdapter dbHelper;

    public UserDAOImpl(Context context) {
        dbHelper = new DBAdapter(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    @Override
    public void createUser(String username, String password) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBAdapter.COLUMN_USERNAME, username);
        values.put(DBAdapter.COLUMN_PASSWORD, password);

        database.insert(DBAdapter.TABLE_USER, null, values);
        close();
    }

    @Override
    public void updateUser(User user) {
        open();
        ContentValues values = new ContentValues();

        values.put(DBAdapter.COLUMN_USERNAME, user.getUsername());
        values.put(DBAdapter.COLUMN_PASSWORD, user.getPassword());

        database.update(DBAdapter.TABLE_USER, values, DBAdapter.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(user.getId())});
        close();
    }

    @Override
    public User findUserById(long id) {
        open();
         Cursor cursor = database.query(DBAdapter.TABLE_USER, new String[]{
            DBAdapter.COLUMN_ID, DBAdapter.COLUMN_USERNAME, DBAdapter.COLUMN_PASSWORD
        }, DBAdapter.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null, null);

        User user = new User();
         if (cursor != null){
             cursor.moveToFirst();
             user.setId(cursor.getInt(0));
             user.setUsername(cursor.getString(1));
             user.setPassword(cursor.getString(2));
             cursor.close();
        }

        close();
         return user;
    }

    @Override
    public User findUserByUsername(String username) {
        open();
        Cursor cursor = database.query(DBAdapter.TABLE_USER, new String[]{
                DBAdapter.COLUMN_ID, DBAdapter.COLUMN_USERNAME, DBAdapter.COLUMN_PASSWORD
        }, DBAdapter.COLUMN_USERNAME + " = ? ", new String[]{username}, null, null, null, null);

        User user = new User();
        if (cursor != null){
            if (cursor.moveToFirst()) {
                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
            }
            cursor.close();
        }

        close();
        return user;
    }

     @Override
    public void deleteUser(User user) {
         open();
         database.delete(DBAdapter.TABLE_USER, DBAdapter.COLUMN_ID + " = ? ",
         new String[]{String.valueOf(user.getId())});
         close();
    }

    @Override
    public List<User> getUserList() {
          String selectQuery = "SELECT " + DBAdapter.COLUMN_ID + ", " + DBAdapter.COLUMN_USERNAME + ", " + DBAdapter.COLUMN_PASSWORD + " FROM " + DBAdapter.TABLE_USER;
          List<User> users = new ArrayList<>();
          open();
          Cursor cursor = database.rawQuery(selectQuery, null);

          if (cursor.moveToFirst () ) {
              do {
                  final User user = new User();
                  user.setId(cursor.getInt(0));
                  user.setUsername(cursor.getString(1));
                  user.setPassword(cursor.getString(2));
                  users.add(user);
              } while (cursor.moveToNext());
          }
        cursor.close();
          close();
          return users;
    }
}