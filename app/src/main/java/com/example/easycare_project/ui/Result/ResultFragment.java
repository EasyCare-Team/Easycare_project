package com.example.easycare_project.ui.Result;

import androidx.lifecycle.ViewModelProviders;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare_project.DatabaseHelper;
import com.example.easycare_project.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;
import static com.example.easycare_project.Signin_Fragment.MY_PREFS_NAME;

public class ResultFragment extends Fragment {

    private ResultViewModel resultViewModel;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    int REQUEST_ENABLE_BLUETOOTH = 1;
    TextView myLabel;
    TextView Label;
    EditText text;
    String uname;
    String type;
    DatabaseHelper databaseHelper;
    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        resultViewModel =
                ViewModelProviders.of(this).get(ResultViewModel.class);
        View root = inflater.inflate(R.layout.result_fragment, container, false);
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
         uname = prefs.getString("uname", "No name defined");
         type = getArguments().getString("type");
        Button save =root.findViewById(R.id.save);
        Button measure = root.findViewById(R.id.start_measure);
        text = root.findViewById(R.id.entry);
        myLabel = root.findViewById(R.id.label);
        Label = root.findViewById(R.id.labell);
        measure.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    findBT();
                    openBT();
                }
                catch   (IOException ex) { }
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try
                {
                    //saveData();
                    sendData();
                }
                catch (IOException ex) { }
            }
        });
        return root;
    }
    void findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            myLabel.setText("No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BLUETOOTH);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
//        {
            for(BluetoothDevice device : pairedDevices) {
                ParcelUuid list[] = device.getUuids();
                if (device != null && device.getName().equals("raspberrypi")) {
                    mmDevice = device;
                    System.out.println(device.getName());

                    //  Log.e("hello",device.getName());
                    break;
                }

            }
    }

    void saveData(String value) {
        long val = databaseHelper.addMeasurement(uname, value, type);
        if (val > 0) {
            Toast.makeText(getContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
        }
    }
    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        // UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        mmSocket = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

        myLabel.setText("Bluetooth Opened");
    }

    void beginListenForData ()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {

                                final String data = new String(packetBytes, 0, packetBytes.length );
                                readBufferPosition = 0;

                                handler.post(new Runnable()
                                {
                                    public void run ()
                                    {
                                        myLabel.setText("Your measurement is: ");

                                        Label.setText(data);
//                                        try {
//                                            saveData(data);
//                                       }
//                                        catch (Exception e){
//
//                                        }

                                    }
                                });

                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    void sendData() throws IOException
    {
        String msg = text.getText().toString();
        msg += "\n";
        mmOutputStream.write(msg.getBytes());
        text.setText("");
        myLabel.setText("Data Sent");
    }





}
