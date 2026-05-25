package id.anandazuazahra.pertemuan_123;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NoteReceiver
        extends BroadcastReceiver {

    private static final String CHANNEL_ID =
            "daily_note_channel";

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        String note =
                intent.getStringExtra("note_text");

        if(android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O){

            CharSequence name =
                    "Daily Note Channel";

            String description =
                    "Channel catatan harian";

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

        Intent dialogIntent =
                new Intent(
                        context,
                        DialogActivity.class);

        dialogIntent.putExtra(
                "note_text",
                note);

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
                                android.R.drawable.ic_dialog_info)

                        .setContentTitle(
                                "Catatan Harian")

                        .setContentText(note)

                        .setPriority(
                                NotificationCompat.PRIORITY_DEFAULT)

                        .setContentIntent(
                                dialogPendingIntent)

                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(
                3,
                builder.build()
        );

    }
}