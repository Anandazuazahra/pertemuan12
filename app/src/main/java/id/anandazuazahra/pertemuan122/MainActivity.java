package id.anandazuazahra.pertemuan122;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.provider.Settings;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity
        extends AppCompatActivity {

    private AlarmManager alarmManager;

    private TimePicker timePicker;

    private EditText taskInput;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        taskInput =
                findViewById(R.id.taskInput);

        timePicker =
                findViewById(R.id.timePicker);

        Button scheduleButton =
                findViewById(R.id.scheduleButton);

        alarmManager =
                (AlarmManager)
                        getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        scheduleButton.setOnClickListener(v -> {

            String taskName =
                    taskInput.getText().toString();

            if(taskName.isEmpty()){

                Toast.makeText(
                        this,
                        "Masukkan nama tugas!",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Calendar calendar =
                    Calendar.getInstance();

            calendar.set(
                    Calendar.HOUR_OF_DAY,
                    timePicker.getHour());

            calendar.set(
                    Calendar.MINUTE,
                    timePicker.getMinute());

            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND, 0);

            if(calendar.getTimeInMillis()
                    <= System.currentTimeMillis()){

                calendar.add(
                        Calendar.DAY_OF_MONTH,
                        1);
            }

            Intent intent =
                    new Intent(
                            this,
                            TaskAlarmReceiver.class);

            intent.setAction(
                    "id.anandazuazahra.pertemuan122.TASK_ALARM_ACTION");

            intent.putExtra(
                    "task_name",
                    taskName);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

            Log.d("ALARM_SET", "Menyetel alarm untuk: " + calendar.getTime().toString());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Intent intentSettings = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    intentSettings.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intentSettings);
                    Toast.makeText(this, "Berikan izin Alarm Tepat Waktu di pengaturan", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );

            Toast.makeText(
                    this,
                    "Pengingat dibuat untuk: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE),
                    Toast.LENGTH_SHORT
            ).show();

        });

    }
}