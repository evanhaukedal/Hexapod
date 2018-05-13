package com.jenevatechnologies.hexapod;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class BluetoothConfigure extends AppCompatActivity {

//    BluetoothAdapter mBluetoothAdapter;
//    Set<BluetoothDevice> pairedDevices;



    ArrayList<BluetoothDevice> pairedDevicesList;
    BluetoothDeviceAdapter bluetoothDevicesAdapter;

    BluetoothDevice currentlySelectedDevice;

    BluetoothApp bluetoothApp;
    ListView bluetoothDevicesListView;
    Button refreshButton;

//    Set<BluetoothDevice> pairedDevices = bluetoothApp.getBluetoothAdapter().getBondedDevices();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bluetooth_configure);

        bluetoothApp = ((BluetoothApp) getApplicationContext());

        refreshButton = findViewById(R.id.refreshButton);

        bluetoothDevicesListView = findViewById(R.id.availableBluetoothListview);
        pairedDevicesList = new ArrayList<BluetoothDevice>();
        bluetoothDevicesAdapter = new BluetoothDeviceAdapter(this, android.R.layout.simple_list_item_1, pairedDevicesList);
        bluetoothDevicesListView.setAdapter(bluetoothDevicesAdapter);


        loadDevices(this.findViewById(android.R.id.content));

        bluetoothDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (int i = 0; i < bluetoothDevicesListView.getChildCount(); i++) {
                    if(position == i ){
                        bluetoothDevicesListView.getChildAt(i).setBackgroundColor(Color.YELLOW);
                        currentlySelectedDevice = (BluetoothDevice) bluetoothDevicesListView.getItemAtPosition(position);
                    }else{
                        bluetoothDevicesListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
    }

    public void goBack(View view){
        this.finish();
    }

    public void loadDevices(View view){
        pairedDevicesList.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothApp.getBluetoothAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesList.add(device);
            }
        }
        bluetoothDevicesAdapter.notifyDataSetChanged();
    }

    public void connectToDevice(View view){
        bluetoothApp.setBluetoothDevice(currentlySelectedDevice.getAddress());
        bluetoothApp.connectToDevice();
    }

    public void testStream(View view){
        bluetoothApp.write("Hello Hexy");
    }
}

