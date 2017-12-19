package com.example.anmol.beacons.BeaconSearch;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.anmol.beacons.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class BeaconSearc extends Fragment implements BeaconConsumer{
    RelativeLayout rl;
    private BeaconManager beaconManager;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    public BeaconSearc() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.beacon_search, container, false);
        initialize(v);
        return v;
    }
    public void initialize(View v){
        rl = v.findViewById(R.id.Relative_One);
        rl.setVisibility(View.GONE);
        rv = v.findViewById(R.id.search_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
    }
    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                System.out.println("ENTER ------------------->");
            }

            @Override
            public void didExitRegion(Region region) {
                System.out.println("EXIT----------------------->");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                System.out.println( "I have just switched from seeing/not seeing beacons: "+state);
            }
        });
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
                    for (Beacon b:beacons){
                        String uuid = String.valueOf(b.getId1());
                        String major = String.valueOf(b.getId2());
                        String minor = String.valueOf(b.getId3());
                        String distance = String.valueOf(b.getDistance());
                        ArrayList<String> arr = new ArrayList<String>();
                        arr.add(uuid);
                        arr.add(major);
                        arr.add(minor);
                        arr.add(distance);
                        arrayList.add(arr);
                    }
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);
                    rv.setAdapter(recyclerAdapter);
                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            //beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }
    @Override
    public Context getApplicationContext() {
        return null;
    }
    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }
    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.bind(this);
    }
}
