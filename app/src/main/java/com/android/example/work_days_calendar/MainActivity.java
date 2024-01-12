package com.android.example.work_days_calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import de.galgtonold.jollydayandroid.Holiday;
import de.galgtonold.jollydayandroid.HolidayCalendar;
import de.galgtonold.jollydayandroid.HolidayManager;

public class MainActivity extends AppCompatActivity {

    private LocalDate firstDateLD, secondDateLD;
    private Button choseFirstDate, choseSecondDate, result;
    private TextView firstDateTextView, secondDateTextView, resultTextView;
    private Calendar calendar;
    private HolidayManager holidayManager;

    private ArrayAdapter arrayAdapterForHolidayList;
    private ListView listOfHolidays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstDateLD = LocalDate.now();
        secondDateLD = LocalDate.now();

        choseFirstDate = findViewById(R.id.firstDateButton);
        choseSecondDate = findViewById(R.id.secondDateButton);
        result = findViewById(R.id.resultButton);

        firstDateTextView = findViewById(R.id.firstDateTextView);
        secondDateTextView = findViewById(R.id.secondDateTextView);
        resultTextView = findViewById(R.id.resultTextView);

        calendar = Calendar.getInstance();

        holidayManager = HolidayManager.getInstance(HolidayCalendar.GERMANY);

        listOfHolidays = findViewById(R.id.listView);




        choseFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), dpdFirst,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            // установка обработчика выбора даты
            DatePickerDialog.OnDateSetListener dpdFirst = new DatePickerDialog.OnDateSetListener() {

                @SuppressLint("SetTextI18n")
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
                new DatePickerDialog(v.getContext(), dpdSecond,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            // установка обработчика выбора даты
            DatePickerDialog.OnDateSetListener dpdSecond = new DatePickerDialog.OnDateSetListener() {

                @SuppressLint("SetTextI18n")
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

        result.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                long betweenDays = ChronoUnit.DAYS.between(firstDateLD, secondDateLD);
                long betweenWorkingDays = betweenDays - daysWithoutWeekends(firstDateLD, secondDateLD);
                long totalyWorkingDays = betweenWorkingDays - daysWithoutHolidays(firstDateLD, secondDateLD);

                arrayAdapterForHolidayList = new ArrayAdapter<>
                        (getApplicationContext(),android.R.layout.simple_list_item_1,holidaysList(firstDateLD,secondDateLD));
                listOfHolidays.setAdapter(arrayAdapterForHolidayList);


                resultTextView.setText(
                        "Проміжок  часу: " + "\n" +
                                "Календарних днів: " + betweenDays
                                + "\n" + "Робочих днів (без вихідних): " + betweenWorkingDays
                                + "\n" + "Робочих днів (без вихідних і свят): " + totalyWorkingDays
                );


            }
        });
    }

    private int daysWithoutWeekends(LocalDate first, LocalDate second) {
        int weekDays = 0;
        if (first.equals(second)) {
            return weekDays;
        }

        while (first.isBefore(second)) {
            if (DayOfWeek.SATURDAY.equals(first.getDayOfWeek()) ||
                    DayOfWeek.SUNDAY.equals(first.getDayOfWeek())) {
                weekDays++;
            }
            first = first.plusDays(1);
        }
        return weekDays;
    }

    int daysWithoutHolidays(LocalDate first, LocalDate second) {
        org.joda.time.Interval interval = new Interval(DateTime.parse(first.toString()), DateTime.parse(second.toString()));
        Set<Holiday> holidaySet = holidayManager.getHolidays(interval);
        return holidaySet.size();
    }

    List<Holiday> holidaysList(LocalDate first, LocalDate second) {
        org.joda.time.Interval interval = new Interval(DateTime.parse(first.toString()), DateTime.parse(second.toString()));
        return new ArrayList<>( holidayManager.getHolidays(interval));
    }


}