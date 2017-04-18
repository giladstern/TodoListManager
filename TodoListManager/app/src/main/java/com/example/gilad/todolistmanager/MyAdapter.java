package com.example.gilad.todolistmanager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Gilad on 3/18/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    List<DataEntry> data;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView task;
        public TextView date;
        public ViewHolder(ViewGroup v) {
            super(v);
            task = (TextView) v.findViewById(R.id.task);
            date = (TextView) v.findViewById(R.id.date);
        }
    }

    public MyAdapter(List<DataEntry> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        final ViewHolder vh = new ViewHolder(viewGroup);


        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String text = vh.task.getText().toString();
                builder.setTitle(text)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAt(vh.getAdapterPosition());

                        Collections.sort(data, new Comparator<DataEntry>() {
                            @Override
                            public int compare(DataEntry o1, DataEntry o2) {
                                return o1.getDate().compareTo(o2.getDate());
                            }
                        });
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                final String[] words = text.split(" ");
                if (words.length >= 2 && words[0].toLowerCase().equals("call")){
                    builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + words[1]));
                            parent.getContext().startActivity(intent);
                        }
                    });
                }

                builder.create().show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText(format.format(data.get(position).getDate().getTime()));
        holder.task.setText(data.get(position).getTask());

        if (data.get(position).getDate().before(Calendar.getInstance())){
            holder.date.setTextColor(Color.RED);
            holder.task.setTextColor(Color.RED);
        } else {
            holder.date.setTextColor(Color.GRAY);
            holder.date.setTextColor(Color.GRAY);
        }
    }

    public void removeAt(int index){
        data.remove(index);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    
}
