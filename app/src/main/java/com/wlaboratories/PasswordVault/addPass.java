package com.wlaboratories.PasswordVault;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addPass extends AppCompatActivity {
    public EditText pass_name;
    public EditText password;
    public Button addvault;
    public Spinner list;
    public String n,p;
    public Integer t;
    public ImageView chord_img;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_pass);

        list = findViewById(R.id.pass_types);
        chord_img = findViewById(R.id.password_type_img);
        ArrayAdapter<CharSequence> listadapter = ArrayAdapter.createFromResource(this, R.array.types_list, R.layout.spinner_item);
        listadapter.setDropDownViewResource(R.layout.spinner_selection);
        list.setAdapter(listadapter);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = list.getSelectedItemPosition ();
                switch (position){
                    case 0:
                        t = R.drawable.avatar;
                        chord_img.setImageResource(t);

                        break;
                    case 1:
                        t = R.drawable.discord;
                        chord_img.setImageResource(t);
                        break;
                    case 2:
                        t = R.drawable.facebook;
                        chord_img.setImageResource(t);
                        break;
                    case 3:
                        t = R.drawable.gmail;
                        chord_img.setImageResource(t);
                        break;
                    case 4:
                        t = R.drawable.google;
                        chord_img.setImageResource(t);
                        break;
                    case 5:
                        t = R.drawable.ig;
                        chord_img.setImageResource(t);
                        break;
                    case 6:
                        t = R.drawable.mail;
                        chord_img.setImageResource(t);
                        break;
                    case 7:
                        t = R.drawable.twitter;
                        chord_img.setImageResource(t);
                        break;
                    case 8:
                        t = R.drawable.yahoo;
                        chord_img.setImageResource(t);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                chord_img.setImageResource(R.drawable.avatar);
            }
        });

        pass_name = findViewById(R.id.labelpass);
        password = findViewById(R.id.pass_input);
        addvault = findViewById(R.id.addtovault);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

        Switch view_pass1 = findViewById(R.id.view_pass1);
        view_pass1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        addvault.setOnClickListener(view -> {
            if (pass_name.getText().toString().matches("") || password.getText().toString().matches("")) {
                Toast.makeText(this, "Please enter a label and a password!", Toast.LENGTH_SHORT).show();
            } else {
                n = pass_name.getText().toString();
                p = password.getText().toString();
                Cursor res = vault_page.DB.getdata();
                boolean use = false;
                if (res.getCount() > 0) {
                    while (res.moveToNext()){
                        if (res.getString(0).matches(n)) {
                            Toast.makeText(this, "Name already in use!", Toast.LENGTH_SHORT).show();
                            use = true;
                            break;
                        }
                    }
                }
                if (!use) {

                    vault_page.names.add(n);
                    vault_page.passes.add(p);
                    vault_page.images.add(t);
                    vault_page.dates.add(date);
                    Integer sequence = vault_page.vAdapter.getItemCount() + 1;
                    Boolean checkinsertdata = vault_page.DB.insertdata(n,p,t,sequence,date);
                    if (checkinsertdata) {
                        vault_page.vAdapter.notifyItemChanged(vault_page.images.size() - 1);
                        Toast.makeText(this, "Added " + n, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        Intent i = new Intent(addPass.this,mainpage.class);
        i.putExtra("return",'a');
        startActivity(i);
        finish();
    }
}