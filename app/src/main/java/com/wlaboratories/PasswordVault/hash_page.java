package com.wlaboratories.PasswordVault;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class hash_page extends Fragment {

    public static hash_page newInstance(String param1, String param2) {
        return new hash_page();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hash_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView helpsalt;
        EditText pass_input, salt, hash_password;
        Button submit;
        FloatingActionButton clipboard;

        helpsalt = view.findViewById(R.id.helpsalt);
        pass_input = view.findViewById(R.id.password_hash);
        salt = view.findViewById(R.id.salt);
        submit = view.findViewById(R.id.hash_button);
        clipboard = view.findViewById(R.id.copypass);
        hash_password = view.findViewById(R.id.hashed_pass);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashHelper hashH = new hashHelper();
                String hashed_password = hashH.process(pass_input.getText().toString(),salt.getText().toString());
                hash_password.setText(hashed_password);
            }
        });

        clipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cb = (ClipboardManager) getActivity().getSystemService(getContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("hashed",hash_password.getText().toString());
                cb.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
                hash_password.setText("");
            }
        });

        helpsalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Salt");
                builder.setMessage("A salt is a piece of random data added to a password before it is hashed and stored. Adding a salt to stored passwords is a security process used alongside the hashing of passwords before they are stored for extra security.");
                builder.setNeutralButton("Sounds cool", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        Switch view_pass1 = view.findViewById(R.id.view_pass1);
        view_pass1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass_input.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    pass_input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });
    }
}