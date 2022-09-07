package com.WAppz.PassSafe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class login extends AppCompatActivity {
    boolean access;
    boolean deleteAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_IkesPasswordVault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vault_page.names = new ArrayList<>();
        vault_page.passes = new ArrayList<>();
        vault_page.images = new ArrayList<>();
        vault_page.dates = new ArrayList<>();

        access = getIntent().hasExtra("access");
        deleteAcc = getIntent().hasExtra("delAcc");

        Button login = findViewById(R.id.loginbutton);
        EditText password_input = (EditText) findViewById(R.id.password_login);
        TextView password_hint = findViewById(R.id.label2);

        if (deleteAcc) {
            login.setText("Delete My Account");
        }

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String content = mainpage.fileH.readFromFile("code.txt");
                String hint = mainpage.fileH.readFromFile("hint.txt");
                String pass_i = password_input.getText().toString();
                if (pass_i.matches("")) {
                    Toast.makeText(getApplicationContext(),"Wrong code. Please try again.",Toast.LENGTH_SHORT).show();
                    password_hint.setText("Password hint: " + hint);
                } else {
                    hashHelper hashH = new hashHelper();
                    String hint1;
                    if (hint.matches("")) {
                        hint1 = pass_i;
                    } else {hint1 = hint;}
                    String code_input = hashH.process(pass_i,hint1);
                    if (code_input.equals(content)) {
                        if (getIntent().hasExtra("login")) {
                            Toast.makeText(getApplicationContext(),"Welcome!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, mainpage.class);
                            intent.putExtra("return","return");
                            startActivity(intent);
                        } else if (access) {
                            String name,pass; int img;
                            Intent intent = new Intent(login.this, individualPass.class);
                            name = getIntent().getStringExtra("name");
                            pass = getIntent().getStringExtra("pass");
                            img = getIntent().getIntExtra("img",1);
                            intent.putExtra("name",name);
                            intent.putExtra("pass",pass);
                            intent.putExtra("img",img);
                            startActivity(intent);
                        } else if (deleteAcc) {
                            boolean delSuccess = mainpage.fileH.deleteCode(getApplicationContext());
                            if (delSuccess) {
                                boolean delDBsuccess = vault_page.DB.deleteAll();
                                if (delDBsuccess) {
                                    Intent i = new Intent(login.this, mainpage.class);
                                    Toast.makeText(getApplicationContext(),"Thank you for using PassSafe!",Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Could not delete database!  Please try re-installing the app or contact me at Github!",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"Could not delete account! Please try re-installing the app or contact me at Github!",Toast.LENGTH_SHORT).show();
                            }

                        }
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Wrong code. Please try again.",Toast.LENGTH_SHORT).show();
                        password_hint.setText("Password hint: " + hint);
                    }
                }
            }
        });
    }
}