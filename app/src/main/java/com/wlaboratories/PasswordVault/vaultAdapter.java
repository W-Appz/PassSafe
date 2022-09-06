package com.wlaboratories.PasswordVault;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class vaultAdapter extends RecyclerView.Adapter<vaultAdapter.viewHolder> {

    ArrayList<String> nameslst;
    ArrayList<String> passlst;
    ArrayList<Integer> images;
    ArrayList<String> dates;
    Context context;

    public vaultAdapter(Context ct, ArrayList<String> names, ArrayList<String> passes, ArrayList<Integer> img, ArrayList<String> date) {
        context = ct;
        nameslst = names;
        passlst = passes;
        images = img;
        dates = date;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vaultrow,parent,false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        fileHandler fileH = new fileHandler(context);
        int pos = holder.getAbsoluteAdapterPosition();

        holder.cv.setBackgroundColor(context.getResources().getColor(R.color.vaultrow));
        holder.passname.setText(nameslst.get(pos));
        holder.img.setImageResource(images.get(pos));
        holder.dateAdded.setText(dates.get(pos));

        String pass_str = passlst.get(pos);
        if (pass_str.length() > 24) {
            holder.pass1.setText(pass_str.substring(0,24));
        } else {
            holder.pass1.setText(pass_str);
        }

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,login.class);
                intent.putExtra("access","access");
                intent.putExtra("name",nameslst.get(pos));
                intent.putExtra("pass",passlst.get(pos));
                intent.putExtra("img",images.get(pos));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameslst.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cv;
        TextView passname, pass1, dateAdded;
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            passname = itemView.findViewById(R.id.pass_names);
            pass1 = itemView.findViewById(R.id.passwords);
            img = itemView.findViewById(R.id.imageviews);
            cv = itemView.findViewById(R.id.cardView);
            dateAdded = itemView.findViewById(R.id.dateAdded);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
