package com.example.anmol.beacons.BeaconSimulator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anmol.beacons.R;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

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
        final String uuid1 =(int)Math.floor(Math.random()*8+1)+"f"+(int)Math.floor(Math.random()*(999999-100000) + 100000)+"-"+"cf"+(int)Math.floor(Math.random()*8+1)+"d-4a0f-adf2-f"+(int)Math.floor(Math.random()*9999-1000+1000)+"ba9ffa6";
        uuid.setText(uuid1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (major.getText().toString().equals("") || minor.getText().toString().equals("")){
                        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                                .setText("Please fill Major , Minor")
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                .setAnimations(Style.ANIMATIONS_POP).show();

                    }
                    else {
                        byte[] b = UrlBeaconUrlCompressor.compress("http://moviemagic.bitballoon.com/");
                        Identifier id = Identifier.fromBytes(b,0,b.length,false);
                        ArrayList<Identifier> array = new ArrayList<>();
                        array.add(id);
                        Beacon beacon = new Beacon.Builder()
                                .setId1(uuid1)
                                .setId2(String.valueOf(major.getText()))
                                .setId3(major.getText().toString())
                                .setManufacturer(0x0118)
                                .setTxPower(-69)
                                .setRssi(-66)
                                .setBluetoothName("Hall 1")
                                //   .setIdentifiers(array)
                                .setDataFields(Arrays.asList(new Long[] {0l}))
                                .build();
                        BeaconParser beaconParser = new BeaconParser()
                                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
                        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity(), beaconParser);
                        beaconTransmitter.startAdvertising(beacon);
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                                .setText("Successfully Started")
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                .setAnimations(Style.ANIMATIONS_POP).show();
                        b1.setEnabled(false);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
}
