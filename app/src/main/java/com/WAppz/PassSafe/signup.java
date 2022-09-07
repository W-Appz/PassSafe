package com.WAppz.PassSafe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;

public class signup extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_IkesPasswordVault);
        setContentView(R.layout.activity_signup);

        vault_page.names = new ArrayList<>();
        vault_page.passes = new ArrayList<>();
        vault_page.images = new ArrayList<>();

        Button signupbutton = findViewById(R.id.startButton);
        EditText password_input1 = findViewById(R.id.code1);
        EditText password_input2 = findViewById(R.id.code2);
        EditText hint_input = findViewById(R.id.pass_hint);

        signupbutton.setOnClickListener(view -> {
            String code1 = password_input1.getText().toString();
            String code2 = password_input2.getText().toString();
            String hint = hint_input.getText().toString();
            if (code1.equals("") || code2.equals("")) {
                Toast.makeText(getApplicationContext(), "Passwords cannot be blank!", Toast.LENGTH_SHORT).show();
            } else {
                if (code1.equals(code2)) {
                    hashHelper hashH = new hashHelper();
                    if (hint.matches("")) {
                        mainpage.fileH.writeToFile("code.txt",hashH.process(code1,code1));
                    } else {
                        mainpage.fileH.writeToFile("code.txt",hashH.process(code1,hint));
                    }
                    mainpage.fileH.writeToFile("hint.txt",hint);
                    Toast.makeText(getApplicationContext(),"Welcome!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, mainpage.class);
                    intent.putExtra("return","return");
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Switch view_pass1 = findViewById(R.id.view_pass1);
        Switch view_pass2 = findViewById(R.id.view_pass2);
        view_pass1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password_input1.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    password_input1.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
        view_pass2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password_input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    password_input2.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}