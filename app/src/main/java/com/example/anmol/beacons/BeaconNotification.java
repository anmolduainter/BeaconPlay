package com.example.anmol.beacons;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class BeaconNotification extends Application implements BootstrapNotifier {
    public static BeaconManager beaconManager;
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    public static Region region1;

    @Override
    public void onCreate() {
        super.onCreate();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_LONG).show();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                bluetoothIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(bluetoothIntent);
            }
        }

        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setForegroundScanPeriod(1100l);
        beaconManager.setForegroundBetweenScanPeriod(0l);
        beaconManager.setAndroidLScanningDisabled(true);
        beaconManager.setBackgroundBetweenScanPeriod(01);
        beaconManager.setBackgroundScanPeriod(1100l);

        try {
            beaconManager.updateScanPeriods();
        } catch (Exception e) {
        }
        // wake up the app when a beacon is seen
        region1 = new Region("backgroundRegion",
                null, null, null);
        regionBootstrap = new RegionBootstrap(this, region1);
        //for saving battery
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    @Override
    public void didEnterRegion(Region region) {
        try {
            Intent i = new   Intent(getApplicationContext(), BeaconService.class);
            startService(i);
        } catch (Exception e){}
    }

    @Override
    public void didExitRegion(Region region) {

        try {
            Intent k = new Intent(getApplicationContext(),BeaconService.class);
            startService(k);
        }
        catch (Exception e) {
    }

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

        try {
            Intent k = new Intent(getApplicationContext(), BeaconService.class);
            startService(k);
        }
        catch (Exception e) {
    }
    }
}