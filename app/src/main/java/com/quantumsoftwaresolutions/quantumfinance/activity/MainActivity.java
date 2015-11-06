package com.quantumsoftwaresolutions.quantumfinance.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.quantumsoftwaresolutions.quantumfinance.R;
import com.quantumsoftwaresolutions.quantumfinance.adapter.PagerAdapter;
import com.quantumsoftwaresolutions.quantumfinance.fragment.ExpensesFragment;
import com.quantumsoftwaresolutions.quantumfinance.fragment.IncomeFragment;
import com.quantumsoftwaresolutions.quantumfinance.helper.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_action_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            setupDrawerContent(navigationView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null)
            setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
            tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        if (item.getItemId() == android.R.id.home) {
            if (fm.getBackStackEntryCount() <= 1)
                drawerLayout.openDrawer(GravityCompat.START);
            else
                onBackPressed();
        }
        else {
            switch (item.getTitle().toString()) {
                case "Log out":
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                case "Add":
                    switch (viewPager.getCurrentItem()) {
                        case 0 :
                            startActivityForResult(new Intent(this, EditItemActivity.class), 0);
                            return true;
                        case 1 :
                            startActivityForResult(new Intent(this, EditItemActivity.class), 1);
                            return true;
                    }

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(IncomeFragment.newInstance(0), getString(R.string.income));
        adapter.addFragment(ExpensesFragment.newInstance(1), getString(R.string.expenses));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        Fragment fragment = null;
                        String title = (String) menuItem.getTitle();

                        switch (title) {
                            case "Home":
                                //fragment = new HomeFragment();
                                break;
                        }
                        drawerLayout.closeDrawers();

                       /* if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
                            //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            fragmentTransaction.commit();
                        }*/
                        return true;
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode <= 1){
            String type = data.getStringExtra("TYPE");
            String description = data.getStringExtra("DESCRIPTION");
            String date = data.getStringExtra("DATE");
            String frequency = data.getStringExtra("FREQUENCY");
            Double amount = data.getDoubleExtra("AMOUNT", 0.0);

            switch (requestCode){
                case 0 :
                    IncomeFragment income = (IncomeFragment) adapter.getItem(0);
                    income.addItem(type, description, date, frequency, amount);
                    break;
                case 1 :
                    ExpensesFragment expense = (ExpensesFragment) adapter.getItem(1);
                    expense.addItem(type, description, date, frequency, amount);
                    break;
            }
        }
    }

/*    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        L.d(count + "  countcount");
            if (count == 1 && getSupportActionBar() != null)
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        super.onBackPressed();
    }*/
}
