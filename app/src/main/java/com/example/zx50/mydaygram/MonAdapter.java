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

import java.time.Month;

public class MonAdapter extends RecyclerView.Adapter<MonAdapter.MyViewHolder> {

    int setMonth;
    Context context;
    String[] monthStr;
    private OnItemClickListener mItemClickListener;

    public MonAdapter(Context context, String[] monthStr, int setMonth) {
        this.context = context;
        this.monthStr = monthStr;
        this.setMonth = setMonth;
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_list_item, parent, false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.month_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.Month.setText(monthStr[position]);
        if (monthStr[position].equals(getMonthText(setMonth)))
            myViewHolder.Month.setBackgroundResource(R.drawable.circle_dark);
//        if(myViewHolder.Month.getText() == "") {
//            myViewHolder.Month = myViewHolder.itemView.findViewById(R.id.itemNull);
//        }
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
        return monthStr.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Month;

        public MyViewHolder(View view){
            super(view);
            Month = view.findViewById(R.id.monthItem);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "OPTIAgency-Gothic.otf");
            Month.setTypeface(tf);
        }
    }

    private String getMonthText(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return null;
        }
    }
}
