package id.anandazuazahra.pertemuan12;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver
        extends BroadcastReceiver {

    private static final String CHANNEL_ID =
            "water_reminder_channel";

    @SuppressLint("MissingPermission")

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        if(android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O){

            CharSequence name =
                    "Water Reminder Channel";

            String description =
                    "Channel pengingat air";

            int importance =
                    NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            name,
                            importance
                    );

            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(
                            NotificationManager.class
                    );

            notificationManager
                    .createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )

                        .setSmallIcon(
                                android.R.drawable.ic_dialog_info)

                        .setContentTitle(
                                "Pengingat Minum Air")

                        .setContentText(
                                "Saatnya minum air!")

                        .setPriority(
                                NotificationCompat.PRIORITY_DEFAULT)

                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(
                1,
                builder.build()
        );

    }
}