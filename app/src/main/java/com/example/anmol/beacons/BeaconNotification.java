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

        // Getting the bluetooth adapter object
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Checking if bluetooth is supported by device or not
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_LONG).show();
        } else {
            // if bluetooth is supported but not enabled then enable it
            if (!mBluetoothAdapter.isEnabled()) {
                Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                bluetoothIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(bluetoothIntent);
            }
        }

        // getting beaconManager instance (object) for Application class which implements BootstrapNotifier interface.
        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        //Changes defaults scanning periods when ranging is performed.
        // Scan period times may be adjusted by internal algorithms or operating system.
        beaconManager.setForegroundScanPeriod(1100l);

        beaconManager.setForegroundBetweenScanPeriod(0l);

        //Allows disabling use of Android L BLE Scanning APIs on devices with API 21+
        // If set to false (default), devices with API 21+ will use the Android L APIs to scan for beacons
        beaconManager.setAndroidLScanningDisabled(true);

        //Sets the duration in milliseconds spent not scanning between each Bluetooth LE scan cycle
        // when no ranging/monitoring clients are in the foreground
        beaconManager.setBackgroundBetweenScanPeriod(01);

        //Sets the duration in milliseconds of each Bluetooth LE scan cycle to look for beacons.
        beaconManager.setBackgroundScanPeriod(1100l);

        try {
            //Updates an already running scan
            beaconManager.updateScanPeriods();
        } catch (Exception e) {
        }
        // wake up the app when a beacon is seen
        region1 = new Region("backgroundRegion",
                null, null, null);
        regionBootstrap = new RegionBootstrap(this, region1);

        // simply constructing this class and holding a reference to it in your custom Application
        // class will automatically cause the BeaconLibrary to save battery whenever the application
        // is not visible.  This reduces bluetooth power usage by about 60%.
        backgroundPowerSaver = new BackgroundPowerSaver(this);
    }

    /*
        This override method is runned when some beacon will come under the range of device.
     */
    @Override
    public void didEnterRegion(Region region) {
        try {
            Intent i = new   Intent(getApplicationContext(), BeaconService.class);
            startService(i);
        } catch (Exception e){}
    }

    /*
        This override method is runned when beacon that comes in the range of device
        ,now been exited from the range of device.
     */
    @Override
    public void didExitRegion(Region region) {

        try {
            Intent k = new Intent(getApplicationContext(),BeaconService.class);
            startService(k);
        }
        catch (Exception e) {
      }

    }

    /*
      This override method will Determine the state for the device , whether device is in range
      of beacon or not , if yes then i = 1 and if no then i = 0
     */
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