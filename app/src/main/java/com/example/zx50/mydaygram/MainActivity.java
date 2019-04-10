package com.example.zx50.mydaygram;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MainActivity extends AppCompatActivity {


    private RecyclerView DiaryList;
    private RecyclerView.LayoutManager DiaryLayoutManager;
    private DiaryAdapter DiaryAdapter;
    private DiaryAdapter2 DiaryAdapter2;

    private RecyclerView MonList;
    private LinearLayoutManager MonLayoutManager;
    private MonAdapter MonAdapter;

    private RecyclerView YearList;
    private LinearLayoutManager YearLayoutManager;
    private YearAdapter YearAdapter;

    private RecyclerView Tools;
    private LinearLayoutManager ToolsLayoutManager;
    private ToolsAdapter ToolsAdapter;

    private Dialog dialogMon;
    private Dialog dialogYear;

    HashMap<Integer, Diary> temp = new HashMap<>(); //获取日记中的数据
    ArrayList<Diary> diaries = new ArrayList<>();//读取的所有日记数据
    ArrayList<Diary> subDiaries = new ArrayList<>(); //内容不为空的日记数据
    ArrayList<Diary> monthDiaries = new ArrayList<>();//选定年和月份的日记数据
    DiariesOperator diariesOperator = new DiariesOperator();

    int[] btnResId = {R.drawable.month1, R.drawable.year1, R.drawable.add, R.drawable.change};
    String[] listMonth = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    //String[] listYear = {"2010","2011","2012","2013","2014","2015","2016","2017","2018"};
    ArrayList<String> listYear = new ArrayList<>();
    String[] monAndYear = null;
    static final int REQUEST_CODE_B = 123;
    static final int RESULT_OK = 123;

    int Type = 0; //Type = 0时显示第一种布局，等于1时显示第二种布局
    int currentYear;
    int currentMonth;
    int currentDate;
    int setYear; //选定年份
    int setMonth; //选定月份

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //InitData();

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        currentYear = Integer.parseInt(sDateFormat.format(new Date()));
        sDateFormat = new SimpleDateFormat("MM");
        currentMonth = Integer.parseInt(sDateFormat.format(new Date()));
        sDateFormat = new SimpleDateFormat("dd");
        currentDate = Integer.parseInt(sDateFormat.format(new Date()));
        setYear = currentYear;
        setMonth = currentMonth;

        DiaryList = (RecyclerView)findViewById(R.id.diarylist);
        Tools = (RecyclerView)findViewById(R.id.tools);

        diariesOperator = new DiariesOperator();
        diaries = diariesOperator.load(MainActivity.this);
        if (diaries == null)
            diaries = new ArrayList<>();
        setData();

        DiaryLayoutManager = new LinearLayoutManager(this);
        DiaryList.setLayoutManager(DiaryLayoutManager);
        DiaryAdapter = new DiaryAdapter(monthDiaries, this);
        //DiaryAdapter = new DiaryAdapter(diaries, this, Type);
        DiaryAdapter.setOnItemClickListener(new Diary1Click());
        DiaryList.addItemDecoration(new SpaceItemDecoration(30));
        DiaryList.setAdapter(DiaryAdapter);

        ToolsLayoutManager = new LinearLayoutManager(this);
        Tools.setLayoutManager(ToolsLayoutManager);
        ToolsLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        monAndYear = new String[]{getMonthText(setMonth), Integer.toString(setYear)};
        ToolsAdapter = new ToolsAdapter(this, btnResId, monAndYear);
        ToolsAdapter.setOnItemClickListener(new ToolsOnItemClickListener());
        Tools.setAdapter(ToolsAdapter);

    }

    class ToolsOnItemClickListener implements OnItemClickListener {

        @Override
        public void onClick(int position) {
            switch (position) {
                case 0:
                    //选月份
                    dialogMon = new Dialog(MainActivity.this, R.style.MyDialog);
                    dialogMon.setContentView(R.layout.list_month);
                    MonList = dialogMon.findViewById(R.id.list_month);
                    MonLayoutManager = new LinearLayoutManager(MainActivity.this);
                    MonList.setLayoutManager(MonLayoutManager);
                    MonLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    MonAdapter = new MonAdapter(MainActivity.this, listMonth, setMonth);
                    MonAdapter.setOnItemClickListener(new MonthClick());
                    MonList.addItemDecoration(new SpaceItemDecoration(10));
                    MonList.setAdapter(MonAdapter);
                    //SnapHelper snapHelper1 = new LinearSnapHelper();
                    //snapHelper1.attachToRecyclerView(MonList);
                    final WindowManager.LayoutParams paramsMon = dialogMon.getWindow().getAttributes();
                    dialogMon.show();
                    DisplayMetrics dm1 = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm1);
                    paramsMon.width = dm1.widthPixels;
                    //paramsMon.height = 150;
                    paramsMon.gravity = Gravity.BOTTOM;
                    dialogMon.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialogMon.getWindow().setDimAmount(0f);
                    dialogMon.getWindow().setAttributes(paramsMon);
                    break;
                case 1:
                    //选年份
                    for (int i = 2010; i <= currentYear; i++){
                        listYear.add(Integer.toString(i));
                    }
                    dialogYear = new Dialog(MainActivity.this, R.style.MyDialog);
                    dialogYear.setContentView(R.layout.list_year);
                    YearList = dialogYear.findViewById(R.id.list_year);
                    YearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    YearList.setLayoutManager(YearLayoutManager);
                    YearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    YearAdapter = new YearAdapter(MainActivity.this, listYear, setYear);
                    YearAdapter.setOnItemClickListener(new YearClick());
                    YearList.addItemDecoration(new SpaceItemDecoration(10));
                    YearList.setAdapter(YearAdapter);
                    final WindowManager.LayoutParams params = dialogYear.getWindow().getAttributes();
                    dialogYear.show();
                    DisplayMetrics dm2 = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm2);
                    params.width = dm2.widthPixels;
                    params.gravity = Gravity.BOTTOM;
                    //params.height = 100;
                    dialogYear.getWindow().setBackgroundDrawableResource(R.color.transparent);
                    dialogYear.getWindow().setDimAmount(0f);
                    dialogYear.getWindow().setAttributes(params);
                    break;
                case 2:
                    //编辑新日记
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, DiaryEdit.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("year", currentYear);
                    bundle.putInt("month", currentMonth);
                    bundle.putInt("date", currentDate);
                    boolean found = false;
                    for(Diary item : diaries){
                        if(item.getYear()==currentYear && item.getMonth()==currentMonth && item.getDate()==currentDate){
                            bundle.putString("diaryText", item.getText());
                            found = true;
                        }
                    }
                    if(!found){
                        bundle.putString("diaryText", null);
                    }
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_CODE_B, bundle);
                    break;
                case 3:
                    //改变显示样式
                    if(Type == 0){
                        //采用与设定type相反的显示方式
                        DiaryAdapter2 = new DiaryAdapter2(MainActivity.this, subDiaries);
                        DiaryAdapter2.setOnItemClickListener(new Diary2Click());
                        DiaryList.setAdapter(DiaryAdapter2);
                        Type = 1;
                    }
                    else if (Type == 1){
                        //采用与设定type相反的显示方式
                        DiaryAdapter = new DiaryAdapter(monthDiaries, MainActivity.this);
                        DiaryAdapter.setOnItemClickListener(new Diary1Click());
                        DiaryList.setAdapter(DiaryAdapter);
                        Type = 0;
                    }

                    break;
                default:
                    break;
            }
        }

        @Override
        public void onLongClick(int position) {

        }
    }

    private String getMonthText(int month) {
        switch (month) {
            case 1:
                return "JANUARY";
            case 2:
                return "FEBRUARY";
            case 3:
                return "MARCH";
            case 4:
                return "APRIL";
            case 5:
                return "MAY";
            case 6:
                return "JUNE";
            case 7:
                return "JULY";
            case 8:
                return "AUGUST";
            case 9:
                return "SEPTEMBER";
            case 10:
                return "OCTOBER";
            case 11:
                return "NOVEMBER";
            case 12:
                return "DECEMBER";
            default:
                return null;
        }
    }

    class MonthClick implements OnItemClickListener {

        @Override
        public void onClick(int position) {
            if(position + 1 <= currentMonth){
                setMonth = position + 1;
                monAndYear = new String[]{getMonthText(setMonth), Integer.toString(setYear)};
                ToolsAdapter = new ToolsAdapter(MainActivity.this, btnResId, monAndYear);
                ToolsAdapter.setOnItemClickListener(new ToolsOnItemClickListener());
                Tools.setAdapter(ToolsAdapter);
                setData();
                if(Type == 0){
                    DiaryAdapter = new DiaryAdapter(monthDiaries, MainActivity.this);
                    DiaryAdapter.setOnItemClickListener(new Diary1Click());
                    DiaryList.setAdapter(DiaryAdapter);
                }
                else if (Type == 1){
                    DiaryAdapter2 = new DiaryAdapter2(MainActivity.this, subDiaries);
                    DiaryAdapter.setOnItemClickListener(new Diary2Click());
                    DiaryList.setAdapter(DiaryAdapter2);
                }
                dialogMon.dismiss();
            }
        }

        @Override
        public void onLongClick(int position) {
        }
    }

    class YearClick implements OnItemClickListener {

        @Override
        public void onClick(int position) {
            setYear = 2010 + position;
            monAndYear = new String[]{getMonthText(setMonth), Integer.toString(setYear)};
            ToolsAdapter = new ToolsAdapter(MainActivity.this, btnResId, monAndYear);
            ToolsAdapter.setOnItemClickListener(new ToolsOnItemClickListener());
            Tools.setAdapter(ToolsAdapter);
            setData();
            if(Type == 0){
                DiaryAdapter = new DiaryAdapter(monthDiaries, MainActivity.this);
                DiaryAdapter.setOnItemClickListener(new Diary1Click());
                DiaryList.setAdapter(DiaryAdapter);
            }
            else if (Type == 1){
                DiaryAdapter2 = new DiaryAdapter2(MainActivity.this, subDiaries);
                DiaryAdapter2.setOnItemClickListener(new Diary2Click());
                DiaryList.setAdapter(DiaryAdapter2);
            }
            dialogYear.dismiss();


        }

        @Override
        public void onLongClick(int position) {

        }
    }

    class Diary1Click implements OnItemClickListener {

        @Override
        public void onClick(int position) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, DiaryEdit.class);
            Bundle bundle = new Bundle();
            bundle.putInt("year", monthDiaries.get(position).getYear());
            bundle.putInt("month", monthDiaries.get(position).getMonth());
            bundle.putInt("date", monthDiaries.get(position).getDate());
            bundle.putString("diaryText",monthDiaries.get(position).getText());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_B, bundle);
        }

        @Override
        public void onLongClick(int position) {

        }
    }

    class Diary2Click implements OnItemClickListener {

        @Override
        public void onClick(int position) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, DiaryEdit.class);
            Bundle bundle = new Bundle();
            bundle.putInt("year", subDiaries.get(position).getYear());
            bundle.putInt("month", subDiaries.get(position).getMonth());
            bundle.putInt("date", subDiaries.get(position).getDate());
            bundle.putString("diaryText", subDiaries.get(position).getText());
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE_B, bundle);
        }

        @Override
        public void onLongClick(int position) {

        }
    }

    public void setData(){
        temp = new HashMap<>();
        subDiaries = new ArrayList<>();
        monthDiaries = new ArrayList<>();
        for (Diary item : diaries) {
            if (item.getMonth() == setMonth && item.getYear() == setYear)
                temp.put(item.getDate(),item);
        }

        if (setMonth == currentMonth && setYear == currentYear){
            for (int i = 1; i <= currentDate; i++) {
                Diary tempDiary = temp.get(i);
                if (tempDiary != null) {
                    monthDiaries.add(tempDiary);
                    if ((!tempDiary.getText().equals("")) && tempDiary.getText()!=null){
                        subDiaries.add(tempDiary);
                    }
                }
                else {
                    tempDiary = new Diary();
                    tempDiary.setYear(setYear);
                    tempDiary.setMonth(setMonth);
                    tempDiary.setDate(i);
                    tempDiary.setText("");
                    monthDiaries.add(tempDiary);
                }
            }
        }
        else {
            String time;
            if (setMonth < 10)
                time = Integer.toString(setYear) + "0" + Integer.toString(setMonth);
            else
                time = Integer.toString(setYear) + Integer.toString(setMonth);
            int dayCount = getDayCount(time);
            for (int i = 1; i <= dayCount; i++){
                Diary tempDiary = temp.get(i);
                if (tempDiary != null) {
                    monthDiaries.add(tempDiary);
                    if ((!tempDiary.getText().equals("")) && tempDiary.getText()!=null){
                        subDiaries.add(tempDiary);
                    }
                }
                else {
                    tempDiary = new Diary();
                    tempDiary.setYear(setYear);
                    tempDiary.setMonth(setMonth);
                    tempDiary.setDate(i);
                    tempDiary.setText("");
                    monthDiaries.add(tempDiary);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_B:
                if (resultCode == RESULT_OK){
                    Bundle bundle;
                    bundle = data.getExtras();
                    int tmpYear = bundle.getInt("year");
                    int tmpMonth = bundle.getInt("month");
                    int tmpDate = bundle.getInt("date");
                    String tmpText = bundle.getString("diaryText");
                    if (tmpYear == currentYear && tmpMonth == currentMonth && tmpDate == currentDate){
                        setYear = tmpYear;
                        setMonth = tmpMonth;
                        monAndYear = new String[]{getMonthText(setMonth), Integer.toString(setYear)};
                        ToolsAdapter = new ToolsAdapter(MainActivity.this, btnResId, monAndYear);
                        ToolsAdapter.setOnItemClickListener(new ToolsOnItemClickListener());
                        Tools.setAdapter(ToolsAdapter);
                        setData();
                    }

                    for (Diary item : monthDiaries){
                        if (item.getDate() == tmpDate){
                            item.setText(tmpText);
                            break;
                        }
                    }
                    subDiaries = new ArrayList<>();
                    for (Diary item : monthDiaries){
                        if ((!item.getText().equals("")) && item.getText()!=null)
                            subDiaries.add(item);
                    }
                    boolean found = false;
                    if(diaries == null){
                        diaries = diariesOperator.load(MainActivity.this);
                    }
                    for (Diary item : diaries){
                        if(item.getYear()==tmpYear && item.getMonth()==tmpMonth && item.getDate()==tmpDate){
                            if(tmpText == null)
                                diaries.remove(item);
                            else
                                item.setText(tmpText);
                            item.setText(tmpText);
                            found = true;
                            break;
                        }
                    }
                    if(!found && tmpText!=null){
                        Diary tmp = new Diary();
                        tmp.setYear(tmpYear);
                        tmp.setMonth(tmpMonth);
                        tmp.setDate(tmpDate);
                        tmp.setText(tmpText);
                        diaries.add(tmp);
                    }
                    diariesOperator.save(MainActivity.this, diaries);
                    if(Type == 0){
                        DiaryAdapter = new DiaryAdapter(monthDiaries, MainActivity.this);
                        DiaryAdapter.setOnItemClickListener(new Diary1Click());
                        DiaryList.setAdapter(DiaryAdapter);
                        //Type = 1;
                    }
                    else if (Type == 1){
                        DiaryAdapter2 = new DiaryAdapter2(MainActivity.this, subDiaries);
                        DiaryAdapter2.setOnItemClickListener(new Diary2Click());
                        DiaryList.setAdapter(DiaryAdapter2);
                        //Type = 0;
                    }
                }
                break;
            default:
                break;
        }
    }

    private int getDayCount(String time){
        Calendar calendar = Calendar.getInstance();
        // 格式化日期--设置date
        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
        sdf.applyPattern("yyyyMM"); // 格式为201805
        try {
            calendar.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return count;
    }
}
