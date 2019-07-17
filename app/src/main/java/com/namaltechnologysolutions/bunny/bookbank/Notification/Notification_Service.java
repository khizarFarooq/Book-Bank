package com.namaltechnologysolutions.bunny.bookbank.Notification;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.Activity.Other_Request_Activity;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.ArrayList;

import static com.namaltechnologysolutions.bunny.bookbank.Notification.Notification_Channel.CHANNEL_id;

public class Notification_Service extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent move_To_OtherRequest_Activity = new Intent(getApplicationContext(), Other_Request_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, move_To_OtherRequest_Activity ,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_id).setContentTitle("Click Here !").setContentText("Someone has post a new Request.")
                    .setSmallIcon(R.drawable.ic_app).setContentIntent(pendingIntent).build();
            startForeground(1, notification);
            notification.flags |=Notification.FLAG_AUTO_CANCEL;
            return START_REDELIVER_INTENT;
        }

}
