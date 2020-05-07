package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.week10.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


// TODO DONE 1. varattuja varauksia ei voi varata
// TODO 2. omia varauksia voi muokata
// TODO  DONE 3. omat varaukset ovat OMIA, muut eivät voi muokata niitä DONE
// TODO 4. kun spineristä valittu sali, päivitä
// TODO 5. katso muut TODO:t

public class MainActivity extends AppCompatActivity {

    Context context;
    Button log;
    Button sign;
    Button profile;
    Button calendarButton;
    Button refresh;
    Spinner hallSpinner;
    TextView calendar;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> hallArray = new ArrayList<String>();
    ArrayList<UserReservation> userReservationsArray;
    ArrayAdapter<String> hallAA;

    int spinner_value;
    String hall;
    int cYear;
    int cMonth;
    int cDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (Button) findViewById(R.id.refresh);
        log = (Button) findViewById(R.id.log);
        sign = (Button) findViewById(R.id.sign);
        profile = (Button) findViewById(R.id.profile);
        hallSpinner = (Spinner) findViewById(R.id.hallSpinner);
        calendar = (TextView) findViewById(R.id.calendar);
        calendarButton = (Button) findViewById(R.id.calendarButton);
        this.context = getApplicationContext();


        /* Check if user's logged and show or hide certain components */
        try {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                log.setText("KIRJAUDU SISÄÄN");
                sign.setVisibility(View.VISIBLE);
                profile.setVisibility(View.GONE);
            } else {
                log.setText("KIRJAUDU ULOS");
                sign.setVisibility(View.GONE);
                profile.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException np) { }


        /* Initialize hallSpinner */
        hallArray.add("Monitoimisali 1");
        hallArray.add("Monitoimisali 2");
        hallArray.add("Peilisali");
        hallArray.add("Tenniskenttä");
        hallArray.add("Kuntosali");
        hallAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hallArray);
        hallAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hallSpinner.setAdapter(hallAA);
        hallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = position;
                hall = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /* Show today's reservations */
        cDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        cMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        cYear = Calendar.getInstance().get(Calendar.YEAR);
        calendar.setText(cDay + "."+ cMonth +"."+ cYear);


        /* LOG-IN, LOG-OUT button and activities behind click */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.log)) {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        System.out.println("STATE 1 => 2: logged in");
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginIntent);

                    } else {
                        System.out.println("STATE 2 => 1: logged out");
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(getIntent());
                    }

                } else if (v == findViewById(R.id.sign)) {
                    System.out.println("REGISTER PRESSED");
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);

                 /* When (TextView) calendar is pressed can user choose a date */
                } else if (v == findViewById(R.id.calendarButton)) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth, mDateSetListener, year, month, day);
                    // TODO katso muitakin style.Theme
                    dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                    dialog.getDatePicker().setMaxDate(cal.getTimeInMillis() + (1000*60*60*24*21));
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //TODO ei toimi ekalla klikillä, miksi??
                            month++;
                            cYear = year;
                            cMonth = month;
                            cDay = dayOfMonth;

                            String date = dayOfMonth + "." + month + "." + year;
                            calendar.setText(date);
                        }
                    };
                } else if (v == findViewById(R.id.profile)){
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else if (v == findViewById(R.id.refresh)){
                    setRecycler(cDay, cMonth, cYear, hall);
                    calendar.setText(cDay + "."+ cMonth +"."+ cYear);
                }
            }
        };

        /* Buttons */
        log.setOnClickListener(listener);
        sign.setOnClickListener(listener);
        calendarButton.setOnClickListener(listener);
        profile.setOnClickListener(listener);
        refresh.setOnClickListener(listener);
    }

    /* Recycler view stuff */
    public void setRecycler(int dayOfMonth, int month, int year, String hall)  {

        try {
            String dateString = dayOfMonth + "." + month + "." + year;
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
            Date dt1 = format1.parse(dateString);
            DateFormat format2 = new SimpleDateFormat("EEEE"); //TODO käännä suomeksi
            String finalDay = format2.format(dt1);
            System.out.println(finalDay);


        } catch (ParseException ps){
            System.out.println("Kusi");
        }

        String dateStr = dayOfMonth+"."+month+"."+year;
        ArrayList<Reservation> reservationList = new ArrayList<>();
        userReservationsArray = new ArrayList<>();
        userReservationsArray = ReadAndWriteXML.readSpesificXML(context, hall);

        if (userReservationsArray.size() != 0) {
            for (int u = 0; u < userReservationsArray.size(); u++) {
                if (userReservationsArray.get(u).getHall().equals(hallArray.get(spinner_value))) {
                    for (int j = 0; j < 10; j++) {
                        reservationList.add(new Reservation("Vapaa vuoro", hallArray.get(spinner_value), j, dateStr));
                    }

                    for (int i = 0; i < userReservationsArray.size(); i++) {
                        if (userReservationsArray.get(i).getDate().equals(dateStr)) {
                            int timeid = getTimeId(userReservationsArray.get(i).getTime());
                            reservationList.set(timeid, new Reservation("VARATTU", hallArray.get(spinner_value), timeid, dateStr));
                            System.out.println("Hep!");
                        }
                    }
                } else {
                    for (int j = 0; j < 10; j++) {
                        reservationList.add(new Reservation("Vapaa vuoro", hallArray.get(spinner_value), j, dateStr));
                    }
                }
            }
        } else {
            for (int j = 0; j < 10; j++) {
                reservationList.add(new Reservation("Vapaa vuoro", hallArray.get(spinner_value), j, dateStr));
            }
        }



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new ReserveAdapter(reservationList, context);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public int getTimeId(String time){
        int timeId = 0;
        
        if (time.equals("10:00")){
            timeId = 0;
        } else if (time.equals("11:00")){
            timeId = 1;
        }  else if (time.equals("12:00")){
            timeId = 2;
        } else if (time.equals("13:00")){
            timeId = 3;
        } else if (time.equals("14:00")){
            timeId = 4;
        } else if (time.equals("15:00")){
            timeId = 5;
        } else if (time.equals("16:00")){
            timeId = 6;
        } else if (time.equals("17:00")){
            timeId = 7;
        } else if (time.equals("18:00")){
            timeId = 8;
        } else if (time.equals("19:00")){
            timeId = 9;
        } else if (time.equals("20:00")){
            timeId = 10;
        }
        return timeId;
    }
}