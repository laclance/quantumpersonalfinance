package com.quantumsoftwaresolutions.quantumfinance.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.helper.L;
import com.quantumsoftwaresolutions.quantumfinance.helper.ManagePreferences;
import com.quantumsoftwaresolutions.quantumfinance.model.User;
import com.quantumsoftwaresolutions.quantumfinance.repository.database.UserDAO;
import com.quantumsoftwaresolutions.quantumfinance.repository.database.impl.UserDAOImpl;
import com.quantumsoftwaresolutions.quantumfinance.service.impl.UserServiceImpl;

public class LoginActivity extends AppCompatActivity {
    private UserDAO dao;
    private UserServiceImpl userService;

    private TextView progressTextView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberUserCheckBox;
    private Button submitButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dao = new UserDAOImpl(getApplicationContext());
        userService = new UserServiceImpl();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notabs);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();

        progressTextView = (TextView) findViewById(R.id.progressMessage);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        rememberUserCheckBox = (CheckBox) findViewById(R.id.rememberUser);
        submitButton = (Button) findViewById(R.id.submit);
        registerButton = (Button) findViewById(R.id.register);

        new CheckUserAsync().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onSubmitClick(View view) {
        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (rememberUserCheckBox.isChecked())
            ManagePreferences.saveToPreferences(getApplicationContext(), "REMEMBER_USER", true);
        else
            ManagePreferences.saveToPreferences(getApplicationContext(), "REMEMBER_USER", false);

        if (username.isEmpty()) {
            usernameError("Please enter a username");
        } else if (password.length() < 6) {
            passwordError("Password must be at least 6 characters");
        } else {
            User user = dao.findUserByUsername(username);

            if (submitButton.getText().equals("Register")) { //Register User
                if(user == null || user.getId() < 1)
                    new CreateUserAsync().execute(username, password);
                else
                    usernameError("Username already exists");
            } else { //Login User
                if(user == null || user.getId() < 1)
                    new LoginUserAsync().execute(username, password);
                else if (!user.getPassword().equals(password))
                    passwordError("Incorrect password");
                else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public void onRegisterClick(View view) {
        checkNewUser();
    }

    private void checkNewUser() {
        if (submitButton.getText().equals("Login")) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Register");
            submitButton.setText("Register");
            registerButton.setText("Login");
        }
        else{
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle("Login");
            submitButton.setText("Login");
            registerButton.setText("Register New");
        }
    }

    private void usernameError(String msg) {
        usernameEditText.requestFocus();
        usernameEditText.setError(msg);
    }
    private void passwordError(String msg) {
        passwordEditText.requestFocus();
        passwordEditText.setError(msg);
    }

    private class CheckUserAsync extends AsyncTask<Void, String, Boolean> {
        @Override
        protected void onPreExecute() {
            progressTextView.setText(R.string.loading);
            progressTextView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void ... params) {
            String user = ManagePreferences.readFromPreferences(getApplicationContext(), "USER", "");

            if (!user.equals("")){
                if (ManagePreferences.readFromPreferences(getApplicationContext(), "REMEMBER_USER", false))
                    publishProgress(user, dao.findUserByUsername(user).getPassword());
                return false;
            }
            else//new user
                return true;
        }

        @Override
        protected void onProgressUpdate(String ... values) {
            usernameEditText.setText(values[0]);
            passwordEditText.setText(values[1]);
            rememberUserCheckBox.setChecked(true);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                submitButton.setText("Login");
            checkNewUser();
            progressTextView.setVisibility(View.INVISIBLE);
        }
    }

    private class CreateUserAsync extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            progressTextView.setText(R.string.createNewUser);
            progressTextView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            UserServiceImpl userService = new UserServiceImpl();
            User user = userService.findByUsername(username);

            if (user == null) {
                userService.save(new User(username, password));
                dao.createUser(username, password);
                ManagePreferences.saveToPreferences(getApplicationContext(), "USER", username);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            progressTextView.setVisibility(View.INVISIBLE);
            if (success) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
                usernameError("Username already exists");
        }
    }

    private class LoginUserAsync extends AsyncTask<String, String, Integer> {
        @Override
        protected void onPreExecute() {
            progressTextView.setText(R.string.loggingIn);
            progressTextView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            UserServiceImpl userService = new UserServiceImpl();
            User user = userService.findByUsername(username);
            if (user == null)
                return 1;
            else if (!user.getPassword().equals(password))
                return 2;
            else {
                dao.createUser(username, password);
                ManagePreferences.saveToPreferences(getApplicationContext(), "USER", username);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressTextView.setVisibility(View.INVISIBLE);
            if (result == 1) {
                usernameError("Username does not exist");
            }
            else if (result == 2) {
                passwordError("Incorrect password");
            }
            else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
