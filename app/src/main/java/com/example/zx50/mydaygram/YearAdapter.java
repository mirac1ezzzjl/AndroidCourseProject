package com.example.zx50.mydaygram;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Year;
import java.util.ArrayList;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.MyViewHolder> {

    int setYear;
    Context context;
    //String[] yearStr;
    ArrayList<String> yearStr;
    private OnItemClickListener mItemClickListener;

    public YearAdapter(Context context, ArrayList<String> yearStr, int setYear) {
        this.context = context;
        this.yearStr = yearStr;
        this.setYear = setYear;
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public YearAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YearAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.Year.setText(yearStr.get(position));
        if (yearStr.get(position).equals(Integer.toString(setYear)))
            myViewHolder.Year.setTextColor(ContextCompat.getColor(context, R.color.selectedColor));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemClickListener.onLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return yearStr.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Year;

        public MyViewHolder(View view){
            super(view);
            Year = view.findViewById(R.id.yearItem);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "OPTIAgency-Gothic.otf");
            Year.setTypeface(tf);
        }
    }
}
