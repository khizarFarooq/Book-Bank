package com.namaltechnologysolutions.bunny.bookbank.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Others_Request_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Notification.Notification_Service;
import com.namaltechnologysolutions.bunny.bookbank.R;

public class Other_Request_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_request);
        Intent intent=new Intent(this, Notification_Service.class);
        stopService(intent);
        FragmentTransaction ft=getFragmentManager().beginTransaction();
        ft.replace(R.id.other_Request_FrameLayOut,new Others_Request_Fragment()).commit();
    }
}
