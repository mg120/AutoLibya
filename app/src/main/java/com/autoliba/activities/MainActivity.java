package com.autoliba.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.fragments.AutoNewsFrag;
import com.autoliba.fragments.AutoShowsFrag;
import com.autoliba.fragments.HomeFragment;
import com.autoliba.fragments.PartBrandsFrag;
import com.autoliba.fragments.ProfileFragment;
import com.autoliba.model.UserSignUpModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    public static UserSignUpModel.Message user_data;
    // Shared Preferences ...
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    Toolbar toolbar;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            user_data = getIntent().getExtras().getParcelable("user_data");
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.text_color));
        // You were missing this setHomeAsUpIndicator
        if (getSupportActionBar() != null) {
            Drawable drawable = getResources().getDrawable(R.drawable.menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            logoutOfApp();
        }
    }

    private void logoutOfApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.outofApp))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //super.onBackPressed();
                        dialogInterface.dismiss();
                        //-------------------------------------------------------

                        pref = getSharedPreferences(SignUp.MY_PREFS_NAME, Context.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LogIn.class));
                        finish();
                        // -------------------------------------------------------
                    }
                })
                .setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        item = menu.findItem(R.id.search_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_item) {
            startActivity(new Intent(MainActivity.this, Search.class));
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            toolbar.getMenu().findItem(R.id.search_item).setVisible(true);
            fragment = new HomeFragment();
            displaySelectedFragment(fragment);
        } else if (id == R.id.my_profile) {
            toolbar.getMenu().findItem(R.id.search_item).setVisible(false);
            fragment = new ProfileFragment();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_add_car) {
            toolbar.getMenu().findItem(R.id.search_item).setVisible(false);
            startActivity(new Intent(MainActivity.this, AddAds.class));

        } else if (id == R.id.nav_autoShow) {
            toolbar.getMenu().findItem(R.id.search_item).setVisible(false);
            fragment = new AutoShowsFrag();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_autoNews) {
            toolbar.getMenu().findItem(R.id.search_item).setVisible(false);
            fragment = new AutoNewsFrag();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_partBrands) {
            toolbar.getMenu().findItem(R.id.search_item).setVisible(false);
            fragment = new PartBrandsFrag();
            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_chat) {
            Intent intent = new Intent(MainActivity.this, Chat.class);
            startActivity(intent);

        } else if (id == R.id.nav_changePass) {
            Intent intent = new Intent(MainActivity.this, ChangePass.class);
            startActivity(intent);

        } else if (id == R.id.nav_policy) {
            startActivity(new Intent(MainActivity.this, Policy.class));

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, About.class));

        } else if (id == R.id.nav_logout) {
            logoutOfApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }
}
