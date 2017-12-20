package com.example.anmol.beacons.BeaconSimulator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anmol.beacons.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

public class BeaconSimu extends Fragment {

    private EditText major,minor;
    private TextView uuid;
    private Button b1;
    public BeaconSimu() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.beacon_simu, container, false);
        major = v.findViewById(R.id.major_simu);
        minor = v.findViewById(R.id.minor_simu);
        uuid = v.findViewById(R.id.uuid_simu);
        b1 = v.findViewById(R.id.make_beacon);
        final String uuid1 = Math.floor(Math.random()*8+1)+"f"+Math.floor(Math.random()*(999999-100000) + 100000)+"-"+"cf"+Math.floor(Math.random()*8+1)+"d-4a0f-adf2-f"+Math.floor(Math.random()*9999-1000+1000)+"ba9ffa6";
        uuid.setText(uuid1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    byte[] b = UrlBeaconUrlCompressor.compress("MyBeacon 1");
                    Identifier id = Identifier.fromBytes(b,0,b.length,false);
                    ArrayList<Identifier> array = new ArrayList<>();
                    array.add(id);
                    Beacon beacon = new Beacon.Builder()
                            .setId1(uuid1)
                            .setId2(String.valueOf(major.getText()))
                            .setId3(major.getText().toString())
                            .setManufacturer(0x0118)
                            .setTxPower(-59)
                            .setIdentifiers(array)
                            .setDataFields(Arrays.asList(new Long[] {0l}))
                            .build();
                    BeaconParser beaconParser = new BeaconParser()
                            .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
                    BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity(), beaconParser);
                    beaconTransmitter.startAdvertising(beacon);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
}
