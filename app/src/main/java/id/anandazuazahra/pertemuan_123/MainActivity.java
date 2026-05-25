package id.anandazuazahra.pertemuan_123;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity
        extends AppCompatActivity {

    private AlarmManager alarmManager;

    private TimePicker timePicker;

    private EditText noteInput;

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        noteInput =
                findViewById(R.id.noteInput);

        timePicker =
                findViewById(R.id.timePicker);

        Button saveButton =
                findViewById(R.id.saveButton);

        // Request notification permission for Android 13+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) 
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                androidx.core.app.ActivityCompat.requestPermissions(this, 
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        alarmManager =
                (AlarmManager)
                        getSystemService(Context.ALARM_SERVICE);

        saveButton.setOnClickListener(v -> {

            String note =
                    noteInput.getText().toString();

            if(note.isEmpty()){

                Toast.makeText(
                        this,
                        "Masukkan catatan!",
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

            if(calendar.getTimeInMillis()
                    <= System.currentTimeMillis()){

                calendar.add(
                        Calendar.DAY_OF_MONTH,
                        1);
            }

            Intent intent =
                    new Intent(
                            this,
                            NoteReceiver.class);

            intent.setAction(
                    "com.example.catatanharian.ALARM_ACTION");

            intent.putExtra(
                    "note_text",
                    note);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            }

            Toast.makeText(
                    this,
                    "Pengingat berhasil dibuat!",
                    Toast.LENGTH_SHORT
            ).show();

        });

    }
}