package id.anandazuazahra.pertemuan122;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TaskAlarmReceiver
        extends BroadcastReceiver {

    private static final String CHANNEL_ID =
            "task_reminder_channel";

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        Log.d("ALARM_RECEIVER", "Alarm masuk! Menerima tugas: " + intent.getStringExtra("task_name"));

        String taskName =
                intent.getStringExtra("task_name");

        if(android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O){

            CharSequence name =
                    "Task Reminder Channel";

            String description =
                    "Channel pengingat tugas";

            int importance =
                    NotificationManager.IMPORTANCE_HIGH; // Diubah ke HIGH

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            name,
                            importance
                    );
            
            channel.enableVibration(true);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(
                            NotificationManager.class
                    );

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent dialogIntent =
                new Intent(
                        context,
                        DialogActivity.class);

        dialogIntent.putExtra(
                "task_name",
                taskName);

        PendingIntent dialogPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        dialogIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )

                        .setSmallIcon(
                                android.R.drawable.ic_dialog_alert)

                        .setContentTitle(
                                "Pengingat Tugas")

                        .setContentText(
                                "Waktunya mengerjakan: "
                                        + taskName)

                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH) // Diubah ke HIGH
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(
                                dialogPendingIntent)

                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(
                2,
                builder.build()
        );

    }
}