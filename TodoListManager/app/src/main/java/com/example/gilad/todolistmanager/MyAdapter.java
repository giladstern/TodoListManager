package com.example.gilad.todolistmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Gilad on 3/18/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<CharSequence> data;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public MyAdapter(List<CharSequence> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        final ViewHolder vh = new ViewHolder(textView);

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(textView.getText())
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeAt(vh.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                return true;
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        if (position % 2 == 0){
            holder.textView.setTextColor(Color.RED);
        } else {
            holder.textView.setTextColor(Color.BLUE);
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
