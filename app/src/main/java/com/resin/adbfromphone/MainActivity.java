package com.resin.adbfromphone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button devices, swipe, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        devices = (Button)findViewById(R.id.button_devices);
        swipe = (Button)findViewById(R.id.button_swipe);
        key = (Button)findViewById(R.id.button_key);

        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result2 = adbCommandExe(new String[] {"adb", "devices"});
                Toast.makeText(MainActivity.this, result2, Toast.LENGTH_SHORT).show();
            }
        });

        swipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = adbCommandExe(
                        new String[] {"adb", "shell", "input", "swipe", "0", "0", "0", "1000", "200"}); // notify bar
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });

        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = adbCommandExe(
                        new String[] {"adb", "shell", "input","keyevent", "3"});    // home
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String adbCommandExe(String[] command) {

        StringBuffer sBuffer = new StringBuffer();
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process proc = processBuilder.start();
            InputStream iStream = proc.getInputStream();
            InputStreamReader isReader = new InputStreamReader(iStream);
            BufferedReader bufferedReader = new BufferedReader(isReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuffer.append(line + "\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return sBuffer.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
