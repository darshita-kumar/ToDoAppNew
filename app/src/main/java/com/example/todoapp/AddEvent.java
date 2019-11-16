package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity
{

    private static final String TAG = "AddEvent";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    DataBaseOpenHelper mydb = new DataBaseOpenHelper(AddEvent.this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Bundle extras = getIntent().getExtras();

        if(extras.getInt("SRNO")!=0)
        {
            DataBaseOpenHelper db = new DataBaseOpenHelper(this);
            Cursor res = db.getAllData();
            while (res.moveToNext()) {
                if (res.getString(1).equals(extras.getString("ti")))
                    break;
            }

            String title = res.getString(1);
            String desc = res.getString(2);
            String date = res.getString(3);
            String time = res.getString(4);
            String notify = res.getString(5);

            EditText et1 = (EditText)findViewById(R.id.Title);
            EditText et2 =  (EditText)findViewById(R.id.Description);
            TextView tv1 = (TextView)findViewById(R.id.tvDatePicker);
            TextView tv2 = findViewById(R.id.tvTimePicker);
            Switch sw = (Switch)findViewById(R.id.switch1);

            et1.setText(title);
            et2.setText(desc);
            tv1.setText(date);
            tv2.setText(time);
            if(notify.equals("y"))
                sw.setChecked(true);
        }

        mDisplayDate = (TextView)findViewById(R.id.tvDatePicker);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEvent.this,
                        android.R.style.Theme_Black,
                        mDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month++;
                Log.d(TAG, "onDateSet: MM/DD/YYYY: "+ month + "/" + dayOfMonth + "/" +year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        //Time picker dialog:
        final TextView displayTime = (TextView)findViewById(R.id.tvTimePicker);

        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(AddEvent.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true).show();
            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                displayTime.setText(time);
            }
        };

    }

    public void insertIntoDatabase(View view)
    {
        EditText et1 = (EditText)findViewById(R.id.Title);
        EditText et2 =  (EditText)findViewById(R.id.Description);
        TextView tv1 = (TextView)findViewById(R.id.tvDatePicker);
        TextView tv2 = findViewById(R.id.tvTimePicker);
        Switch sw = (Switch)findViewById(R.id.switch1);

        String check;
        if(sw.isChecked())
            check = "y";
        else
            check = "n";

        String title = et1.getText().toString();
        String desc = et2.getText().toString();
        String date = tv1.getText().toString();
        String time = tv2.getText().toString();

        Event event = new Event(title, desc, date, time, check);
        boolean isInserted = mydb.insert(event);
        if(isInserted == true)
            Toast.makeText(AddEvent.this,"Event Set", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddEvent.this,"Unable to set event. Try changing the name of the event",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(AddEvent.this, MainActivity.class);
        startActivity(intent);
    }

}
