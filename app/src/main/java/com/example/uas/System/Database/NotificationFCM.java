package com.example.uas.System.Database;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.uas.Page.MainActivity;
import com.example.uas.R;
import com.example.uas.System.Object.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationFCM extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "fcm_default_channel";

    public static void getToken(Context ctx) {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    System.out.println("Fetching FCM registration token failed");
                    return;
                }
                String token = task.getResult();
                System.out.println("FCM Token: " + token);

                Toast.makeText(ctx, "Please head to your console for FCM token.", Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            addNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    public static void getNotificationList(NotificationListCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build());

        ArrayList<Post> notificationList = new ArrayList<>();
        db.collection("notifications")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            notificationList.add(new Post(0,
                                    document.getId(),
                                    document.getString("title"),
                                    document.getString("body")
                            ));
                        }
                        callback.onDataLoaded(notificationList);
                    } else {
                        callback.onDataError(true ,"Error getting notification list");
                    }
                });
    }

    public interface NotificationListCallback {
        void onDataLoaded(ArrayList<Post> postList);
        void onDataError(boolean status, @Nullable String errorMessage);
    }

    private void addNotification(String title, String body) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> post = new HashMap<>();
        post.put("title", title);
        post.put("body", body);

        db.collection("notifications")
                .add(post).addOnSuccessListener(documentReference -> {
                    System.out.println("Notification added to Firestore: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error adding notification to Firestore: " + e.getMessage());
                });
    }
}
