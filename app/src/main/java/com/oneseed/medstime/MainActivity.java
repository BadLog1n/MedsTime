package com.oneseed.medstime;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<Meds> meds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addNewMedButton = findViewById(R.id.addNewMedButton);
        Button addTimerBtn = findViewById(R.id.addTimerBtn);
        Button setTimerBtn = findViewById(R.id.setTimerBtn);
        Spinner countSpinner = findViewById(R.id.countSpinner);
        EditText newMedicineText = findViewById(R.id.newMedicineEditText);
        TextView madeByText = findViewById(R.id.madeByText);
        LinearLayout addMedsLayout = findViewById(R.id.addMedsLayout);
        LinearLayout addMedsLayoutHeader = findViewById(R.id.addMedsLayoutHeader);
        LinearLayout addMedsBtnLayout = findViewById(R.id.addMedsBtnLayout);
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        createNotificationChannel();
        setInitialData(dates());
        RecyclerView medsRecyclerView = findViewById(R.id.medsRc);
        MedsAdapter adapter = new MedsAdapter(this, meds);
        medsRecyclerView.setAdapter(adapter);

        addTimerBtn.setOnClickListener(v -> {
            if (addTimerBtn.getText().equals("Отмена")) {
                timePicker.setVisibility(View.GONE);
                addTimerBtn.setText("Добавить уведомление");
                medsRecyclerView.setVisibility(View.VISIBLE);
                setTimerBtn.setVisibility(View.GONE);
            } else {
                timePicker.setVisibility(View.VISIBLE);
                addTimerBtn.setText("Отмена");
                medsRecyclerView.setVisibility(View.GONE);
                setTimerBtn.setVisibility(View.VISIBLE);
            }
        });

        setTimerBtn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    //request permission
                    Toast.makeText(this, "Для получения уведомлений необходимо разрешение", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Intent intent = new Intent(MainActivity.this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            //устанавливает уведомление на каждый день
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            //устанавливает уведомление на текущий день
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            String minuteStr = String.valueOf(minute);
            if (minuteStr.length() < 10) {
                minuteStr = "0" + minute;
            }
            String time = hour + ":" + minuteStr;
            Toast.makeText(this, "Уведомление установлено на " + time, Toast.LENGTH_SHORT).show();
            timePicker.setVisibility(View.GONE);
            addTimerBtn.setText("Добавить уведомление");
            medsRecyclerView.setVisibility(View.VISIBLE);
            setTimerBtn.setVisibility(View.GONE);


        });


        addMedsBtnLayout.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.VISIBLE);
            addMedsBtnLayout.setVisibility(View.GONE);
        });
        addMedsLayoutHeader.setOnClickListener(v -> {
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
        });

        addNewMedButton.setOnClickListener(v -> {
            String newMedicineTextString = newMedicineText.getText().toString();
            String editTextNumberString = countSpinner.getSelectedItem().toString();
            if (newMedicineTextString.equals("")) {
                Toast.makeText(this, "Пожалуйста, введите название или информацию", Toast.LENGTH_SHORT).show();
                return;
            }
            int times = 1;
            if (!editTextNumberString.equals("")) {
                times = Integer.parseInt(editTextNumberString);
            }
            addMedsLayout.setVisibility(View.GONE);
            addMedsBtnLayout.setVisibility(View.VISIBLE);
            meds.add(new Meds(newMedicineTextString, times, 0));
            newMedicineText.setText("");
            countSpinner.setSelection(0);
            hideKeyboard();
        });


        madeByText.setOnClickListener(v -> {
            Intent browserIntent = new
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/badlog1n"));
            startActivity(browserIntent);
        });

    }

    private void setInitialData(boolean isTodayDate) {
        SharedPreferences settings = this.getSharedPreferences(getString(R.string.medsShared), Context.MODE_PRIVATE);
        int length = settings.getInt("length", -1);
        for (int i = 0; i <= length; i++) {
            String[] medsOne = settings.getString(String.valueOf(i), "").split(getString(R.string.splitArrayString));
            if (!medsOne[2].equals("deleted")) {
                if (isTodayDate) {
                    medsOne[2] = "0";
                }
                meds.add(new Meds(medsOne[0], Integer.parseInt(medsOne[1]), Integer.parseInt(medsOne[2])));
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = "notifyMeds";
            String channelName = "channel_name";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription("channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Boolean dates() {
        SharedPreferences settings = this.getSharedPreferences(getString(R.string.medsShared), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.ROOT);
        String str = formatter.format(date);
        editor.putString("date", str);
        if (settings.getString("lastDate", "").equals(str)) {
            return false;
        } else {
            editor.putString("lastDate", str);
            editor.apply();
            return true;
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}