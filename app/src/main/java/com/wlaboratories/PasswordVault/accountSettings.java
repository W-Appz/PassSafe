package com.wlaboratories.PasswordVault;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class accountSettings extends AppCompatActivity {
    EditText password_input, newpass, cfm_newpass, newhint;
    Button submit;
    TextView currpassstat, currpassstat2,currpassstat3;
    DrawerLayout dLayout; NavigationView navView; ActionBarDrawerToggle mToggle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vault_page.names = new ArrayList<>();
        vault_page.passes = new ArrayList<>();
        vault_page.images = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        password_input = findViewById(R.id.currpass);
        newpass = findViewById(R.id.newpass);
        cfm_newpass = findViewById(R.id.cfmnewpass);
        newhint = findViewById(R.id.newhint);
        submit = findViewById(R.id.submitnewcode);
        currpassstat = findViewById(R.id.currpass_status);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nps, cnps, nhs, hint1, code_input;
                nps = newpass.getText().toString();
                cnps = cfm_newpass.getText().toString();
                nhs = newhint.getText().toString();
                String pass_i = password_input.getText().toString();
                hashHelper hashH = new hashHelper();

                String content = mainpage.fileH.readFromFile("code.txt");
                String hint = mainpage.fileH.readFromFile("hint.txt");

                if (hint.matches("")) {
                    hint1 = pass_i;
                } else {hint1 = hint;}

                code_input = hashH.process(pass_i,hint1);

                if (!code_input.equals(content)) {
                    currpassstat.setText("Incorrect current password.");
                } else {
                    currpassstat.setText("");
                    if (!nps.matches("") && !cnps.matches("") && nps.matches(cnps)) {
                        if (nps.matches(pass_i)){
                            Toast.makeText(accountSettings.this, "New code cannot be the same as previous code!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (nhs.matches("")) {
                                mainpage.fileH.writeToFile("code.txt",hashH.process(nps,nps));
                            } else {
                                mainpage.fileH.writeToFile("code.txt",hashH.process(nps,nhs));
                            }
                            mainpage.fileH.writeToFile("hint.txt",nhs);
                            Toast.makeText(accountSettings.this, "Codes changed!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(accountSettings.this,mainpage.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(accountSettings.this, "Invalid new codes!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        dLayout = (DrawerLayout) findViewById(R.id.dLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        mToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open,R.string.close);
        dLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Switch view_pass1 = findViewById(R.id.view_pass1);
        Switch view_pass2 = findViewById(R.id.view_pass2);
        Switch view_pass3 = findViewById(R.id.view_pass3);
        view_pass1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password_input.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    password_input.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
        view_pass2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    newpass.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
        view_pass3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cfm_newpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    cfm_newpass.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(accountSettings.this);
                builder.setMessage("Are you sure you want to delete your account? All of your passwords will be deleted forever.\nThis cannot be undone!");
                builder.setPositiveButton("CONTINUE TO DELETE",delDB);
                builder.setNegativeButton("GO BACK",delDB);
                builder.show();
                break;
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        Intent i = new Intent(accountSettings.this,mainpage.class);
        i.putExtra("return","return");
        startActivity(i);
        finish();
    }
    DialogInterface.OnClickListener delDB = new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(accountSettings.this,login.class);
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