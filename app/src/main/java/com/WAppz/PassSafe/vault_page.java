package com.WAppz.PassSafe;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.zetetic.database.sqlcipher.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

public class vault_page extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public vault_page() {
        // Required empty public constructor
    }

    public static vault_page newInstance(String param1, String param2) {
        vault_page fragment = new vault_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vault_page, container, false);
    }

    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> passes = new ArrayList<>();
    public static ArrayList<String> dates = new ArrayList<>();
    public static ArrayList<Integer> images = new ArrayList<>();
    public static vaultAdapter vAdapter;
    public static RecyclerView vaultlst;
    public static DBHelper DB;
    public static final String password = signup.code1;
    FloatingActionButton add;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DB = new DBHelper();

        Cursor res = DB.getdata(mainpage.database);
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                names.add(res.getString(0));
                passes.add(res.getString(1));
                images.add(Integer.valueOf(res.getString(2)));
                dates.add(res.getString(4));
            }
        }

        vaultlst = view.findViewById(R.id.vault_lst);
        vAdapter = new vaultAdapter(getContext(), names, passes, images,dates);
        vaultlst.setAdapter(vAdapter);
        vaultlst.setLayoutManager(new LinearLayoutManager(getContext()));

        add = view.findViewById(R.id.fab);
        add.setOnClickListener(this::onClick);

        Spinner list = view.findViewById(R.id.sorting);
        ArrayAdapter<CharSequence> listadapter = ArrayAdapter.createFromResource(getContext(), R.array.sort_cat, R.layout.spinner_item);
        listadapter.setDropDownViewResource(R.layout.spinner_selection);
        list.setAdapter(listadapter);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               int list_pos = list.getSelectedItemPosition ();
               switch (list_pos){
                   case 0:
                       displayListASC();
                       break;
                   case 1:
                       displayListDESC();
                       break;
                   case 2:
                       displayListDateASC();
                       break;
                   case 3:
                       displayListDateDESC();
                       break;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), addPass.class);
        startActivity(i);
        getActivity().finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void displayListASC() {
        Cursor res = DB.sortdbASCName(mainpage.database);
        names = new ArrayList<>();
        passes = new ArrayList<>();
        images = new ArrayList<>();
        dates = new ArrayList<>();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                names.add(res.getString(0));
                passes.add(res.getString(1));
                images.add(Integer.valueOf(res.getString(2)));
                dates.add(res.getString(4));
            }
        }
        vAdapter = new vaultAdapter(getContext(), names, passes, images,dates);
        vaultlst.setAdapter(vAdapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void displayListDESC() {
        Cursor res = DB.sortdbDESCName(mainpage.database);
        names = new ArrayList<>();
        passes = new ArrayList<>();
        images = new ArrayList<>();
        dates = new ArrayList<>();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                names.add(res.getString(0));
                passes.add(res.getString(1));
                images.add(Integer.valueOf(res.getString(2)));
                dates.add(res.getString(4));
            }
        }
        vAdapter = new vaultAdapter(getContext(), names, passes, images,dates);
        vaultlst.setAdapter(vAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void displayListDateASC(){
        Cursor res = DB.sortdbASCSeq(mainpage.database);
        names = new ArrayList<>();
        passes = new ArrayList<>();
        images = new ArrayList<>();
        dates = new ArrayList<>();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                names.add(res.getString(0));
                passes.add(res.getString(1));
                images.add(Integer.valueOf(res.getString(2)));
                dates.add(res.getString(4));
            }
        }
        vAdapter = new vaultAdapter(getContext(), names, passes, images,dates);
        vaultlst.setAdapter(vAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void displayListDateDESC(){
        Cursor res = DB.sortdbDESCSeq(mainpage.database);
        names = new ArrayList<>();
        passes = new ArrayList<>();
        images = new ArrayList<>();
        dates = new ArrayList<>();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                names.add(res.getString(0));
                passes.add(res.getString(1));
                images.add(Integer.valueOf(res.getString(2)));
                dates.add(res.getString(4));
            }
        }
        vAdapter = new vaultAdapter(getContext(), names, passes, images,dates);
        vaultlst.setAdapter(vAdapter);
    }
}
