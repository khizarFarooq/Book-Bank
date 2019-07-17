package com.namaltechnologysolutions.bunny.bookbank.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Email_Verification_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Login_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Login_Register_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.R;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Sign_Up_Fragment;
import com.namaltechnologysolutions.bunny.bookbank.Fragment.Update_Password_Fragment;

public class Login_Register_Activity extends AppCompatActivity implements Sign_Up_Fragment.signUpinterface,Login_Register_Fragment.LoginRegisterFragmentInterface,Login_Fragment.loginInterface,Email_Verification_Fragment.emailVerificationInterface,Update_Password_Fragment.updatePasswordInterface {
    // Objects Declaration
    private Login_Register_Fragment login_register_fragment;
    private Sign_Up_Fragment sign_up_fragment;
    private Login_Fragment login_fragment;
    private Email_Verification_Fragment email_verification_fragment;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        // Objects Initialization
        login_register_fragment = new Login_Register_Fragment();
        sign_up_fragment = new Sign_Up_Fragment();
        login_fragment = new Login_Fragment();
        email_verification_fragment = new Email_Verification_Fragment();
        // Fragment Replacement
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, login_register_fragment);
        ft.addToBackStack(null).commit();
    }
// Some methods to replace fragments and some to change activities.
    public void signUpinterfaceMethod1() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, login_fragment);
        ft.addToBackStack(null).commit();
    }

    @Override
    public void signUpinterfaceMethod2() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, login_fragment);
        ft.addToBackStack(null).commit();
    }


    @Override
    public void LoginRegisterFragmentInterfaceMethod1() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, sign_up_fragment);
        ft.addToBackStack(null).commit();
    }

    @Override
    public void LoginRegisterFragmentInterfaceMethod2() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, login_fragment);
        ft.addToBackStack(null).commit();
    }

    @Override
    public void LoginRegisterFragmentInterfaceMethod3() {

        Intent moveToMainActivity = new Intent(Login_Register_Activity.this, Navigation_Drawer_Home_Activity.class);
        startActivity(moveToMainActivity);
        finish();
    }

    @Override
    public void loginInterfaceMethod1() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, new Update_Password_Fragment());
        ft.addToBackStack(null).commit();
    }

    @Override
    public void loginInterfaceMethod2() {
        Intent moveToMainActivity = new Intent(Login_Register_Activity.this, Navigation_Drawer_Home_Activity.class);
        startActivity(moveToMainActivity);
        finish();
    }

    @Override
    public void loginInterfaceMethod3() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, new Sign_Up_Fragment());
        ft.addToBackStack(null).commit();
    }



    @Override
    public void updatePasswordInterfaceMethod() {
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.login_register_fragment, email_verification_fragment);
        ft.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {

        FrameLayout frameLayout = findViewById(R.id.login_register_fragment);
        if (frameLayout.getChildCount() == 1) {
            super.onBackPressed();
            if (frameLayout.getChildCount() == 0) {
                finish();
            }
        }
    }

    @Override
    public void emailVerificationInterface_MoveToLoginMethod() {
        ft = getFragmentManager().beginTransaction();
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
    }
}