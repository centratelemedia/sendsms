package com.centratelemedia.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    Button btnSend;
    Button btnClear;

    EditText destinations;
    EditText etMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMsg=findViewById(R.id.etMessage);
        destinations=findViewById(R.id.etDest);
        btnClear=findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v->{
            destinations.getText().clear();
        });
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,"Send Message");
                sendSMS();
            }else{
                Log.d(TAG,"Request permission");
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"Request permission Granted");
        }else{
            Log.d(TAG,"Request permission Not Granted");
        }
    }

    void sendSMS(){
        String[] dests=destinations.getText().toString().split(";");
        String message=etMsg.getText().toString();

        SmsManager smsManager=SmsManager.getDefault();
        for(String dest:dests) {
            Log.d(TAG,"Send Message "+ message+" to "+dest);
            smsManager.sendTextMessage(dest,null,message,null,null);
        }
        Toast.makeText(getApplicationContext(),"Selesai, Silahkan cek hasil di sms",Toast.LENGTH_LONG).show();

    }
}