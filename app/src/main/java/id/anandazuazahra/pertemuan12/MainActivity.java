package id.anandazuazahra.pertemuan12;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button startButton =
                findViewById(R.id.startButton);

        Button stopButton =
                findViewById(R.id.stopButton);

        alarmManager =
                (AlarmManager)
                        getSystemService(Context.ALARM_SERVICE);

        Intent intent =
                new Intent(this, AlarmReceiver.class);

        intent.setAction(
                "id.septa.latihan12.ALARM_ACTION");

        pendingIntent =
                PendingIntent.getBroadcast(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

        startButton.setOnClickListener(v -> {

            long intervalMillis =
                    60 * 1000;

            long triggerTime =
                    System.currentTimeMillis()
                            + intervalMillis;

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    intervalMillis,
                    pendingIntent
            );

            Toast.makeText(
                    this,
                    "Pengingat aktif!",
                    Toast.LENGTH_SHORT
            ).show();

        });

        stopButton.setOnClickListener(v -> {

            if(alarmManager != null &&
                    pendingIntent != null){

                alarmManager.cancel(pendingIntent);

                Toast.makeText(
                        this,
                        "Pengingat dihentikan!",
                        Toast.LENGTH_SHORT
                ).show();
            }

        });

    }
}