package com.example.aleksander.pocketpall2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import com.devspark.appmsg.AppMsg;
import com.example.aleksander.pocketpall2.Database.ExpenseTemplate;
import com.example.aleksander.pocketpall2.Database.IncomeTemplate;
import com.example.aleksander.pocketpall2.Database.Template;
import com.example.aleksander.pocketpall2.Dialogs.AddExpenseDialFrag;
import com.example.aleksander.pocketpall2.Dialogs.AddIncomeDialFrag;
import com.example.aleksander.pocketpall2.Dialogs.ChoseDialFrag;
import com.example.aleksander.pocketpall2.Dialogs.Command;
import com.example.aleksander.pocketpall2.Dialogs.EditComeDialFrag;
import com.example.aleksander.pocketpall2.Dialogs.Invoker;
import com.example.aleksander.pocketpall2.Fragments.Fragment1;
import com.example.aleksander.pocketpall2.Fragments.Fragment3;
import com.example.aleksander.pocketpall2.Fragments.PagerAdapter;
import com.example.aleksander.pocketpall2.Menu.MenuAdapter;
import com.example.aleksander.pocketpall2.Menu.MenuItom;
import com.example.aleksander.pocketpall2.RestoreAndBackup.ExpoStrategy;
import com.example.aleksander.pocketpall2.RestoreAndBackup.ImpoExpoContext;
import com.example.aleksander.pocketpall2.RestoreAndBackup.ImpoStrategy;


import java.util.ArrayList;
import java.util.Calendar;
import static com.example.aleksander.pocketpall2.Fragments.Fragment1.*;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ArrayList<Object> menuItems;
    private MenuAdapter menuAdapter;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public String dollar = " $";
    public String zloty = " zł";
    public static String currency = " zł";

    public static  String date =""; //date picker result

    public static Template incDB;
    public static Template expDB;

    private ImpoExpoContext ctx; // import and export

    public static Invoker invoker;
    public static Command addIncomeDialFrag;
    public static Command addExpenseDialFrag;
    public static Command choseDialFrag;
    public static Command editDialFrag;

    public static int checkedMenu = R.id.zloty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
        initBase();
        setDialogs();
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        addDrawerItems();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        invoker.setCommand(addIncomeDialFrag);
                        invoker.show();
                        break;
                    case 2:
                        invoker.setCommand(addExpenseDialFrag);
                        invoker.show();
                        break;
                    case 4:
                        ctx.setImpoExpoStrategy(new ExpoStrategy());
                        ctx.doIt();
                        AppMsg.makeText(MainActivity.this, "Backup created", new AppMsg.Style(2000, R.color.green)).show();
                        break;
                    case 5:
                        ctx.setImpoExpoStrategy(new ImpoStrategy());
                        ctx.doIt();
                        AppMsg.makeText(MainActivity.this, "Data restored", new AppMsg.Style(2000, R.color.green)).show();
                        Fragment1.refreshList(listView, fragment1);
                        break;
                    case 6:
                        incDB.delete();
                        AppMsg.makeText(MainActivity.this, "Database deleted", new AppMsg.Style(2000, R.color.green)).show();
                        Fragment1.refreshList(listView, fragment1);
                        AddIncomeDialFrag.refreshCharts();
                        Fragment3.refresh3Frag();
                        break;
                }
            }
        });
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setupDrawer();
    }

    private void initBase()
    {
        incDB = new IncomeTemplate(getApplicationContext());
        expDB = new ExpenseTemplate(getApplicationContext());
    }

    private void initVars()
    {
        ctx = new ImpoExpoContext();
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        settingUpToolbar();
        tabLayout = initTabs(); // initialising tabs
    }

    private void settingUpToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private TabLayout initTabs()
    {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Diagram"));
        tabLayout.addTab(tabLayout.newTab().setText("Statistics"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        return tabLayout;
    }

    private void setDialogs()
    {
        invoker = new Invoker();
        invoker.setFg(getFragmentManager());
        addIncomeDialFrag = new AddIncomeDialFrag();
        addExpenseDialFrag = new AddExpenseDialFrag();
        choseDialFrag = new ChoseDialFrag();
        editDialFrag = new EditComeDialFrag();

    }

    private void addDrawerItems() {
        menuItems = new ArrayList<>();
        menuItems.add("Management");
        menuItems.add(new MenuItom("Add Income", R.drawable.income1));
        menuItems.add(new MenuItom("Add Expense", R.drawable.expence));
        menuItems.add("Database");
        menuItems.add(new MenuItom("Create backup", R.drawable.save));
        menuItems.add(new MenuItom("Restore backup", R.drawable.restore));
        menuItems.add(new MenuItom("Delete database", R.drawable.delete_database));
        menuAdapter = new MenuAdapter(getApplicationContext(),
                menuItems);
        mDrawerList.setAdapter(menuAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity2_main, menu);
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem mi = menu.getItem(i);
            // check the Id as you wish
            if (mi.getItemId() == checkedMenu) {
                mi.setChecked(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.dollar:
                item.setChecked(true);
                currency = dollar;
                checkedMenu = R.id.dollar;
                Fragment1.refreshList(Fragment1.listView, Fragment1.fragment1);
                if (Fragment3.instant)
                Fragment3.refresh3Frag();
                return true;
            case R.id.zloty:
                item.setChecked(true);
                currency = zloty;
                checkedMenu = R.id.dollar;
                Fragment1.refreshList(Fragment1.listView, Fragment1.fragment1);
                if (Fragment3.instant)
                    Fragment3.refresh3Frag();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date = Integer.toString(day) + ":"+
                    Integer.toString(month) + ":" +
                    Integer.toString(year);
        }

        public static String getCurrentDate()
        {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return Integer.toString(day) + ":"+
                    Integer.toString(month) + ":" +
                    Integer.toString(year);
        }


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("Menu", checkedMenu);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        checkedMenu = savedInstanceState.getInt("Menu");
    }

}
