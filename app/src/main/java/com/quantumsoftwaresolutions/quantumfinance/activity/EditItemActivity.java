package com.quantumsoftwaresolutions.quantumfinance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.fragment.DatePickerFragment;
import com.quantumsoftwaresolutions.quantumfinance.helper.L;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {

    private Spinner typeSpinner;
    private EditText descriptionEditText;
    private Spinner frequencySpinner;
    private EditText amountEditText;
    private TextView dateTextView;

    private static char name;
    private static int id;
    private static char item;
    private static int type;
    private static String description;
    private static int frequency;
    private static double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        getIntentExtras();
        setUpActionbar();
        setUpViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                    onBackPressed();
            case R.id.action_settings :
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        item = intent.getCharExtra("ITEM", 'i');
        type = intent.getIntExtra("TYPE", 0);
        description = intent.getStringExtra("DESCRIPTION");
        frequency = intent.getIntExtra("FREQUENCY", 0);
        amount = intent.getDoubleExtra("AMOUNT", 0.0);
    }

    public void setUpActionbar(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_notabs);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayHomeAsUpEnabled(true);

            if (id != 0) {
                switch (item) {
                    case 'i':
                        ab.setTitle(R.string.addIncome);
                        break;
                    case 'e':
                        ab.setTitle(R.string.addExpense);
                        break;
                }
            }
            else{
                switch (item) {
                    case 'i':
                        ab.setTitle(R.string.editIncome);
                        break;
                    case 'e':
                        ab.setTitle(R.string.editIncome);
                        break;
                }
            }
        }
    }

    private void setUpViews() {
        typeSpinner = (Spinner) findViewById(R.id.type);
        descriptionEditText = (EditText) findViewById(R.id.description);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        frequencySpinner = (Spinner) findViewById(R.id.frequency);
        amountEditText = (EditText) findViewById(R.id.amount);

        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dateTextView.setText("Date:  " + day + "/" + month + "/" + year);

        if (id != 0)
        {
            final Button button = (Button)findViewById(R.id.submit);
            button.setText(R.string.editItem);

            typeSpinner.setSelection(type);
            descriptionEditText.setText(description);
            frequencySpinner.setSelection(frequency);
            amountEditText.setText(amount + "");
        }
    }

    public void onButtonClick(View view){
        Intent intent = new Intent();
        intent.putExtra("ID", id);
        intent.putExtra("TYPE", typeSpinner.getSelectedItem().toString().trim());
        intent.putExtra("DESCRIPTION", descriptionEditText.getText().toString().trim());
        intent.putExtra("DATE", dateTextView.getText().toString().substring(7));
        intent.putExtra("FREQUENCY", frequencySpinner.getSelectedItem().toString());
        intent.putExtra("AMOUNT", roundTwo(Double.parseDouble(amountEditText.getText().toString())));
        setResult(RESULT_OK, intent);
        finish();
    }

    public Double roundTwo(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }
}


