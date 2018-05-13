package com.jenevatechnologies.hexapod;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    TextView bluetoothOnOffText;
    TextView bluetoothConnectedTo;
    BluetoothApp bluetoothApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        bluetoothApp = ((BluetoothApp) getApplicationContext());
        bluetoothOnOffText = findViewById(R.id.bluetoothOnOffView);
        bluetoothConnectedTo = findViewById(R.id.connectButton);

        if (bluetoothApp.getBluetoothAdapter() != null) {
            if (!bluetoothApp.getBluetoothAdapter().isEnabled()) {
                bluetoothOnOffText.setText(R.string.bluetoothOff);
            } else {
                bluetoothOnOffText.setText(R.string.bluetoothOn);
            }
        } else{
            bluetoothOnOffText.setText(R.string.bluetoothNotSupported);
        }


    }

    public void goToBluetooth(View view){

//        BluetoothApp bluetoothApp = ((BluetoothApp) getApplicationContext());

        if (bluetoothApp.getBluetoothAdapter().isEnabled()){
            Intent openBluetoothConfigure = new Intent(this, BluetoothConfigure.class);
            openBluetoothConfigure.putExtra("BluetoothAdapter", "hello");


            this.startActivity(openBluetoothConfigure);
        } else {
            bluetoothOnOffText.setText(R.string.bluetoothOff);
            Toast.makeText(this, "Please turn bluetooth on first.", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToCalibrate(View view){

            Intent openCalibrate = new Intent(this, Calibrate.class);
            this.startActivity(openCalibrate);
    }



}