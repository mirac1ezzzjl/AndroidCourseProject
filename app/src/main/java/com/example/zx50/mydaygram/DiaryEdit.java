package com.example.zx50.mydaygram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DiaryEdit extends Activity {

    private TextView titleWeek;
    private TextView titleTime;
    private EditText editText;
    private Button backBtn;
    private int year;
    private int month;
    private int date;
    private String week;
    private String monthStr;
    private String diaryText;

    static final int REQUEST_CODE_B = 123;
    static final int RESULT_OK = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit);
        //接收主界面传来的消息
        getDataFromActivity();
        //自定义字体
        Typeface tf = Typeface.createFromAsset(getBaseContext().getAssets(), "OPTIAgency-Gothic.otf");
        //标题日期处理
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
        week = getWeekString(Integer.toString(year)+monTemp+dateTemp);
        titleWeek = (TextView)findViewById(R.id.title_week);
        titleWeek.setTypeface(tf);
        titleWeek.setText(week);
        if(week.equals("SUNDAY"))
            titleWeek.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorRed));
        titleTime = (TextView)findViewById(R.id.title_time);
        monthStr = getMonthString(month);
        titleTime.setText(getString(R.string.time_title, monthStr, date, year));
        titleTime.setTypeface(tf);
        //编辑栏
        editText = (EditText)findViewById(R.id.editText);
        editText.setText(diaryText);
        //下方灰色返回按钮
        backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new backClick());
    }

    class backClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            diaryText = editText.getText().toString();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("year", year);
            bundle.putInt("month", month);
            bundle.putInt("date", date);
            bundle.putString("diaryText", diaryText);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    //判断当前为星期几
    //time的格式如2018-09-01
    public String getWeekString(String time){
        String week = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                week += "SUNDAY";
                break;
            case 2:
                week += "MONDAY";
                break;
            case 3:
                week += "TUESDAY";
                break;
            case 4:
                week += "WEDNESDAY";
                break;
            case 5:
                week += "THURSDAY";
                break;
            case 6:
                week += "FRIDAY";
                break;
            case 7:
                week += "SATURDAY";
                break;
            default:
                break;
        }

        return week;
    }

    //将月份转化为字符串
    public String getMonthString(int month){
        switch (month){
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

    private void getDataFromActivity(){
        Bundle bundle;
        bundle = this.getIntent().getExtras();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        date = bundle.getInt("date");
        diaryText = bundle.getString("diaryText");

    }

    @Override
    public void onBackPressed() {
        diaryText = editText.getText().toString();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("date", date);
        bundle.putString("diaryText", diaryText);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
