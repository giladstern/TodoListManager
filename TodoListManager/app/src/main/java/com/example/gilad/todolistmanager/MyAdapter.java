package com.example.gilad.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

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
                builder.setTitle(vh.task.getText())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
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
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
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
