package com.example.zx50.mydaygram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static com.example.zx50.mydaygram.MainActivity.REQUEST_CODE_B;

public class ToolsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String[] monAndYear;
    int[] resId;
    private OnItemClickListener mItemClickListener;
    private int type1 = 0; //图
    private int type2 = 1; //字

    ToolsAdapter(Context context, int[] resId, String[] monAndYear){
        this.context = context;
        this.resId = resId;
        this.monAndYear = monAndYear;
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder myHolder = null;
        if (viewType == type1) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tool_view_item, viewGroup, false);
            myHolder = new MyHolder(view);
        }
        else if (viewType == type2) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tool_view_text, viewGroup, false);
            myHolder = new MyHolderText(view);
        }

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myHolder, final int position) {
        int i = getItemViewType(position);
        if (i == type1) {
            bindViewHolder1((MyHolder)myHolder,position);
        }
        else if (i == type2){
            bindViewHolder2((MyHolderText)myHolder, position);
        }

    }

    private void bindViewHolder1(MyHolder myHolder, final int position){
        myHolder.btn.setImageResource(resId[position]);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
        myHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemClickListener.onLongClick(position);
                return false;
            }
        });
    }

    private void bindViewHolder2(MyHolderText myHolderText, final int position){
        myHolderText.btn2.setBackgroundResource(resId[position]);
        myHolderText.btn2.setText(monAndYear[position]);
        myHolderText.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
        myHolderText.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemClickListener.onLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return resId.length;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView btn;

        MyHolder(View view){
            super(view);
            btn = view.findViewById(R.id.toolbtn);
        }
    }

    class MyHolderText extends RecyclerView.ViewHolder {
        TextView btn2;

        MyHolderText(View view){
            super(view);
            btn2 = view.findViewById(R.id.tools_text);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "OPTIAgency-Gothic.otf");
            btn2.setTypeface(tf);
        }
    }

    @Override
    public int getItemViewType(int position){
        int type = 0;
        if (position == 0 || position == 1) {
            type = type2;
        }
        else {
            type = type1;
        }
        return type;
    }
}
