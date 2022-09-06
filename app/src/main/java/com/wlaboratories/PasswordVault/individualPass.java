package com.wlaboratories.PasswordVault;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class individualPass extends AppCompatActivity {
    ImageView img;
    EditText password;
    TextView label;
    FloatingActionButton clipboard,delete;
    Switch view_pass;

    String name,pass; int image;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_pass);

        img = findViewById(R.id.imgDisplay);
        label = findViewById(R.id.labelpass);
        password = findViewById(R.id.pass_input);
        clipboard = findViewById(R.id.copypass);
        view_pass= findViewById(R.id.view_pass);
        delete = findViewById(R.id.delete);

        getData();
        setData();

        clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cb = (ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("pass",password.getText().toString());
                cb.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
            }
        });
        view_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        DialogInterface.OnClickListener dClickL = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Boolean checkdeletedata = vault_page.DB.deletedata(name);
                        if (checkdeletedata) {
                            vault_page.names.remove(name);
                            vault_page.passes.remove(pass);
                            vault_page.images.remove(Integer.valueOf(image));
                            vault_page.vAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"Deleted!",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(getApplicationContext(),"Could not delete!",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(individualPass.this);
                builder.setMessage("Are you sure you want to delete " + "name" + "?\nThis cannot be undone!");
                builder.setPositiveButton("DELETE",dClickL);
                builder.setNegativeButton("CANCEL",dClickL);
                builder.show();
            }
        });

    }

    public void getData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("pass") && getIntent().hasExtra("img")) {
            name = getIntent().getStringExtra("name");
            pass = getIntent().getStringExtra("pass");
            image = getIntent().getIntExtra("img",1);
        } else {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
    }
    public void setData() {
//        StringBuilder builder = new StringBuilder();
//        if (name.length() > 25) {
//            builder.append(name.substring(0,22));
//            builder.append("...");
//            name = builder.toString();
//        }
        label.setText(name);
        password.setText(pass);
        img.setImageResource(image);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        Intent i = new Intent(individualPass.this, mainpage.class);
        i.putExtra("return",'a');
        startActivity(i);
        finish();
    }
}