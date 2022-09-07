package com.WAppz.PassSafe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.M)
public class mainpage<started> extends AppCompatActivity {
    public static fileHandler fileH;
    private DrawerLayout dLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        vault_page.names = new ArrayList<>();
        vault_page.passes = new ArrayList<>();
        vault_page.images = new ArrayList<>();
        vault_page.dates = new ArrayList<>();

        super.onCreate(savedInstanceState);

        setTheme(R.style.Theme_IkesPasswordVault);
        setContentView(R.layout.activity_mainpage);
        fileH = new fileHandler(getApplicationContext());
        if (!(getIntent().hasExtra("return"))) {
            if (fileH.fileExist("code.txt")) {
                Intent intent = new Intent(this, login.class);
                intent.putExtra("login", "login");
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, signup.class);
                startActivity(intent);
            }
            finish();
        }
        dLayout = (DrawerLayout) findViewById(R.id.dLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        mToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open,R.string.close);
        dLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //< get elements >
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        //</ get elements >
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Vault");
            } else {
                tab.setText("Hash");
            }
        }).attach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                break;
            case R.id.nav_settings:
                Intent i = new Intent(mainpage.this, accountSettings.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(mainpage.this);
                builder.setMessage("Are you sure you want to delete your account? All of your passwords will be deleted forever.\nThis cannot be undone!");
                builder.setPositiveButton("CONTINUE TO DELETE",delDB);
                builder.setNegativeButton("GO BACK",delDB);
                builder.show();
                break;
        }
        return true;
    }
    DialogInterface.OnClickListener delDB = new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(mainpage.this,login.class);
                    i.putExtra("delAcc","del");
                    startActivity(i);
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
