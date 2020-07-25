package com.myproject.salesdemomvvm.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.myproject.salesdemomvvm.R;
import com.myproject.salesdemomvvm.home.HomeFragment;
import com.myproject.salesdemomvvm.utils.SessionManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        addLauncherFragment();
    }

    private void addLauncherFragment() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.base_container,new HomeFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_logout ){
            new SessionManager().logoutExecutive();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
