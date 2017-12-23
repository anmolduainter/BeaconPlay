package com.example.anmol.beacons.BeaconSimulator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
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

/*
   This Fragment makes one Bluetooth Supported device to act as beacon
 */
public class BeaconSimu extends Fragment {

    // Edit Text for taking both (Major , Minor)
    private EditText major,minor;

    // Text View for displaying UUID
    private TextView uuid;

    // Button for making device Beacon
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

        View v =  inflater.inflate(R.layout.beacon_simu, container, false);

        // Intializing Layout

        // EditText (Major)
        major = v.findViewById(R.id.major_simu);

        // EditText (Minor)
        minor = v.findViewById(R.id.minor_simu);

        //TextView (UUID)
        uuid = v.findViewById(R.id.uuid_simu);

        //Button
        b1 = v.findViewById(R.id.make_beacon);

        // Making of random UUID
        final String uuid1 =(int)Math.floor(Math.random()*8+1)+"f"+(int)Math.floor(Math.random()*(999999-100000) + 100000)+"-"+"cf"+(int)Math.floor(Math.random()*8+1)+"d-4a0f-adf2-f"+(int)Math.floor(Math.random()*9999-1000+1000)+"ba9ffa6";
        // Displaying the random UUID to TextView
        uuid.setText(uuid1);

        // Clicking the Make Beacon Button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // Checking if user has entered the values of major and minor or not.

                    // if user has not entered then display him a toast message to enter respective fields.
                    if (major.getText().toString().equals("") || minor.getText().toString().equals("")){
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                                .setText("Please fill Major , Minor")
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                .setAnimations(Style.ANIMATIONS_POP).show();

                    }

                    // if user has entered
                    else {

                        /* Building up a beacon object
                           Setting up to make this beacon following things :

                           1. UUID
                           2. Major
                           3. Minor
                           4. Manufacturer
                           5. TxPower
                           6. RSSI
                           7. BluetoothName
                           8. DataFields
                         */

                        Beacon beacon = new Beacon.Builder()
                                .setId1(uuid1)
                                .setId2(major.getText().toString())
                                .setId3(minor.getText().toString())
                                .setManufacturer(0x0118)
                                .setTxPower(-69)
                                .setRssi(-66)
                                .setBluetoothName("Hall 1")
                                .setDataFields(Arrays.asList(new Long[] {0l}))
                                .build();

                        BeaconParser beaconParser = new BeaconParser()
                                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");

                        // Making the instance of BeaconTransmitter
                        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getActivity(), beaconParser);

                        // Start Advertising the above Beacon Object
                        beaconTransmitter.startAdvertising(beacon);

                        // Toasting the message that beacon succesfully made
                        SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                                .setText("Successfully Started")
                                .setDuration(Style.DURATION_LONG)
                                .setFrame(Style.FRAME_LOLLIPOP)
                                .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                .setAnimations(Style.ANIMATIONS_POP).show();


                        // Making some animation and changing the text of button
                        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
                        alphaAnimation.setDuration(2000);
                        alphaAnimation.setFillEnabled(true);
                        alphaAnimation.setInterpolator(new BounceInterpolator());
                        b1.startAnimation(alphaAnimation);
                        b1.setText("Beacon Successfully Made");
                    }
            }
        });
        return v;
    }
}
