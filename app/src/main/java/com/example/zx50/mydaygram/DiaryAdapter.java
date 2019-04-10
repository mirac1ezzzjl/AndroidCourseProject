package com.example.zx50.mydaygram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DiaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<Diary> diaries;
    ArrayList<Diary> subDiaries;
    private OnItemClickListener mItemClickListener;
    //文字形式
    public static final int Item1 = 0;
    //点形式
    public static final int Item2 = 1;
//    //第二种文字形式
//    public static final int Item3 = 2;

    DiaryAdapter(ArrayList<Diary> items, Context context){
        diaries = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case Item1:
                viewHolder = new MyViewHolder(layoutInflater.inflate(R.layout.diary_view_item, viewGroup, false));
                break;
            case Item2:
                viewHolder = new DotViewHolder(layoutInflater.inflate(R.layout.dot_view_item, viewGroup, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myViewHolder, int position) {
//
        if (myViewHolder instanceof MyViewHolder) {
            bindViewHolderDiary((MyViewHolder)myViewHolder, position);
        }
        else if (myViewHolder instanceof DotViewHolder) {
            bindViewHolderDot((DotViewHolder)myViewHolder, position);
        }

    }

    //重写此方法来实现显示混合类型item
    @Override
    public int getItemViewType(int position){
        int result = 0;
        if(diaries.get(position).getText() == null || diaries.get(position).getText().equals("")){
            result = Item2;
        }
        else{
            result = Item1;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView day;
        TextView date;
        TextView diary;

        public MyViewHolder(View view){
            super(view);
            day = (TextView)view.findViewById(R.id.itemDay);
            date = (TextView)view.findViewById(R.id.itemDate);
            diary = (TextView)view.findViewById(R.id.itemDiary);
        }
    }

    class DotViewHolder extends RecyclerView.ViewHolder{

        View dot;
        public DotViewHolder(View view){
            super(view);
            dot = (View)view.findViewById(R.id.dot);
        }
    }

    void bindViewHolderDiary(MyViewHolder myViewHolder, final int position) {
        int month = diaries.get(position).getMonth();
        int date = diaries.get(position).getDate();
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
        int year = diaries.get(position).getYear();
        String week = getWeek(Integer.toString(year)+monTemp+dateTemp);
        myViewHolder.day.setText(week);
        if (week.equals("SUN")){
            myViewHolder.day.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }
        else {
            myViewHolder.day.setTextColor(ContextCompat.getColor(context, R.color.colorGray));
        }
        myViewHolder.date.setText(String.valueOf(date));
        myViewHolder.diary.setText(diaries.get(position).getText());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
    }

    void bindViewHolderDot(DotViewHolder myViewHolder, final int position){
        int month = diaries.get(position).getMonth();
        int date = diaries.get(position).getDate();
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
        int year = diaries.get(position).getYear();
        String week = getWeek(Integer.toString(year)+monTemp+dateTemp);
        if (week.equals("SUN")){
            myViewHolder.dot.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_red));
        }
        else {
            myViewHolder.dot.setBackground(ContextCompat.getDrawable(context, R.drawable.circle));
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
    }

    private String getWeek(String Time) {


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
            Week += "SUN";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "MON";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "TUE";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "WED";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "THU";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "FRI";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "SAT";
        }

        return Week;
    }

}
