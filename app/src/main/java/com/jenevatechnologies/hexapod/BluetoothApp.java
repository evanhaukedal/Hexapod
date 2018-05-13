package com.jenevatechnologies.hexapod;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Evan on 5/10/2018.
 */

public class BluetoothApp extends Application {

    private UUID DEFAULT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    final private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream bluetoothOutputStream;
    private InputStream bluetoothInputStream;
    private Thread ConnectionThread;

    public BluetoothApp(){
        createThreads();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void createThreads(){
//        createConnectionThread();
//        createInputStreamThread();
//        createOutputStreamThread();
    }

    private void startConnectionThread(){
        Thread connectionThread = new Thread(new Runnable() {
            public void run() {
                try {
                    bluetoothSocket.connect();
                    bluetoothOutputStream = bluetoothSocket.getOutputStream();
                    bluetoothInputStream = bluetoothSocket.getInputStream();
                    Log.e(TAG, "Connection, input stream, output stream created.");
                } catch (IOException e){
                    Log.e(TAG, "Socket's create() method failed", e);
                };
            }
        });
        connectionThread.start();
    }

    public void setBluetoothDevice(String deviceAddress){
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
    }

    public BluetoothAdapter getBluetoothAdapter(){
        return bluetoothAdapter;
    }

    public void connectToDevice(){
        createSocket();
        if (bluetoothSocket != null){
            startConnectionThread();
        } else {
            Log.e(TAG, "Attempted to connect to device with no socket");
        }
    }

    private void createSocket(){
        BluetoothSocket tmpSocket = null;
        try{
            tmpSocket = bluetoothDevice.createRfcommSocketToServiceRecord(DEFAULT_UUID);
        } catch (IOException e){
            Log.e(TAG, "Socket's createRfcommSocketToServiceRecord() method failed", e);
        }
        bluetoothSocket = tmpSocket;
    }

    public void write(String s) {
        byte[] msgBuffer = s.getBytes();
        try {
            bluetoothOutputStream.write(msgBuffer);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);
        }
    }
}
