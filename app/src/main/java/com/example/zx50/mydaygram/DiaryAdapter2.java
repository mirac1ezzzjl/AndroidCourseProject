package com.example.zx50.mydaygram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DiaryAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Diary> subDiaries;
    private OnItemClickListener mItemClickListener;

    DiaryAdapter2(Context context, ArrayList<Diary> subDiaries){
        this.context = context;
        this.subDiaries = subDiaries;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new MyViewHolder2(layoutInflater.inflate(R.layout.diary_view_item_2, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        bindViewHolderDiary2((MyViewHolder2)viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return subDiaries.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView date2;
        TextView week;
        TextView cutoff;
        TextView diary2;
        public MyViewHolder2(@NonNull View view) {
            super(view);
            date2 = (TextView)view.findViewById(R.id.list_date_2);
            week = (TextView)view.findViewById(R.id.list_week_2);
            cutoff = (TextView)view.findViewById(R.id.cutoff);
            diary2 = (TextView)view.findViewById(R.id.list_text_2);
        }
    }

    void bindViewHolderDiary2(MyViewHolder2 myViewHolder2, final int position) {
        int month = subDiaries.get(position).getMonth();
        int date = subDiaries.get(position).getDate();
        String monTemp;
        String dateTemp;
        if(month < 10)
            monTemp = "0" + Integer.toString(month);
        else
            monTemp = Integer.toString(month);
        if(date < 10)
            dateTemp = "0" + Integer.toString(date);
        else
            dateTemp = Integer.toString(date);
        int year = subDiaries.get(position).getYear();
        String week = getWeek2(Integer.toString(year)+monTemp+dateTemp);
        myViewHolder2.date2.setText(String.valueOf(date) + " ");
        myViewHolder2.week.setText(week);
        if(week.equals("Sunday"))
            myViewHolder2.week.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        myViewHolder2.diary2.setText(subDiaries.get(position).getText());
        myViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });

    }

    private String getWeek2(String Time) {


        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(Time));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "Sunday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "Monday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "Tuesday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "Wednesday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "Thursday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "Friday";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "Saturday";
        }

        return Week;
    }
}
