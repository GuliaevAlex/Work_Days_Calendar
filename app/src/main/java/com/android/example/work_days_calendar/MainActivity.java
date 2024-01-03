package com.android.example.work_days_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocalDate firstDateLD;
    LocalDate secondDateLD;

    Button choseFirstDate;
    Button choseSecondDate;

    DatePicker firstDatePicker;
    DatePicker secondDatePicker;
    DatePicker officialNonWorkingsDaysPicker;

    TextView firstDateTextView;
    TextView secondDateTextView;
    TextView resultTextView;

    List weekendDays;
    List officialNonWorkingsDays;

    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        choseFirstDate = findViewById(R.id.firstDateButton);
        choseSecondDate = findViewById(R.id.secondDateButton);

        firstDateTextView = findViewById(R.id.firstDateTextView);
        secondDateTextView = findViewById(R.id.secondDateTextView);


        choseFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), dpd,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            // установка обработчика выбора даты
            DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear = monthOfYear + 1;
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    firstDateLD = LocalDate.of(year, monthOfYear, dayOfMonth);
                    firstDateTextView.setText(firstDateLD.getYear() + " / " +
                            firstDateLD.getMonthValue() + " / " + firstDateLD.getDayOfMonth());

                }
            };


        });

        choseSecondDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), dpd,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            // установка обработчика выбора даты
            DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear = monthOfYear + 1;
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    secondDateLD = LocalDate.of(year, monthOfYear, dayOfMonth);
                    secondDateTextView.setText(secondDateLD.getYear() + " / " +
                            secondDateLD.getMonthValue() + " / " + secondDateLD.getDayOfMonth());
                }
            };


        });
    }
}