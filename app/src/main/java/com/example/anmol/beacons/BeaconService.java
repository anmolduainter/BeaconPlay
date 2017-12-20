package com.example.anmol.beacons;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconService extends Service implements BootstrapNotifier{

    private BackgroundPowerSaver backgroundPowerSaver;
    private BeaconManager beaconManager;
    private RegionBootstrap regionBootstrap;

  //  public static boolean isServiceRunning = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
 //       if (!isServiceRunning){
 //           isServiceRunning = true;
            System.out.println("SERVICE CALLED ------------------------------------------------->");
            beaconManager = BeaconManager.getInstanceForApplication(this);
            backgroundPowerSaver = new BackgroundPowerSaver(this);
            Region region = new Region("com.example.myapp.boostrapRegion", null, null, null);
            regionBootstrap = new RegionBootstrap(this, region);
 //       }
        return super.onStartCommand(intent, flags, startId);
    }

    //show Notifications
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
 //       isServiceRunning = false;
 //       startBroadcasting();
    }

    @Override
    public void didEnterRegion(Region region) {
        showNotification("A new Beacon Found","Please check the app for more info");
 //       startBroadcasting();
    }

    @Override
    public void didExitRegion(Region region) {
        showNotification("Beacon Exit","Please check the app for more info");
 //       startBroadcasting();
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    public void startBroadcasting(){
        Intent broadcastIntent = new Intent("RestartBeaconService");
        sendBroadcast(broadcastIntent);
    }
}
