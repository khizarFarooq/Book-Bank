package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.namaltechnologysolutions.bunny.bookbank.R;
import android.os.Vibrator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements View.OnClickListener {
    // Objects Declaration
    Vibrator vibrator ;
    private FirebaseAuth loginAuth;
    private EditText signIn_EmailAddress,signIn_Password;
    String validEmailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +"(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+";
    private CheckBox mCheckBoxRememberMe;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME="prefsFile";
    private Button signiInButton;
    private ProgressDialog LoginProcess;
    private loginInterface mListener;

    public Login_Fragment() {
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
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        getPreferenceData();
        ActionBar actionBar=getActivity().getActionBar();
        getActivity().setTitle(R.string.sign_in);
        // Objects Initialization
        vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        loginAuth=FirebaseAuth.getInstance();
        LoginProcess=new ProgressDialog(getActivity());
        signIn_EmailAddress=view.findViewById(R.id.siginEmailAdress);
        signIn_Password= view.findViewById(R.id.siginPassword);
        mCheckBoxRememberMe=view.findViewById(R.id.rememberMeCheckBox);
        signiInButton=view.findViewById(R.id.signIn);
        // Reference to on click method
        signiInButton.setOnClickListener(this);
        TextView signUp=view.findViewById(R.id.Sign_Up);
        signUp.setOnClickListener(this);
        TextView forgetPassword=view.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(this);
        return view;
    }

    private void getPreferenceData() {
        SharedPreferences getSavedSharedPreference=this.getActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if(getSavedSharedPreference.contains("prefs_Email_Address")){
            String userEmail=getSavedSharedPreference.getString("prefs_Email_Address","");
            signIn_EmailAddress.setText(userEmail.toString());

        }
        if(getSavedSharedPreference.contains("prefs_Password")){
            String userPassword=getSavedSharedPreference.getString("prefs_Password", "not found.");
            signIn_Password.setText(userPassword);

        }
        if(getSavedSharedPreference.contains("prefs_check")){
            Boolean rememberMe=getSavedSharedPreference.getBoolean("prefs_check", false);
            mCheckBoxRememberMe.setChecked(rememberMe);
        }
    }
    // For Login User
    public void login_user(String emailAdress,String password){

        loginAuth.signInWithEmailAndPassword(emailAdress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    FirebaseUser currentUser= loginAuth.getCurrentUser();
                    if (currentUser.isEmailVerified()) {
                        LoginProcess.dismiss();
                        mListener.loginInterfaceMethod2();


                    }
                    else{
                        LoginProcess.dismiss();
                        Toast toast=Toast.makeText(getActivity(),"Kindly Validate your Email Address by click on link in given Email.",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                if(!task.isSuccessful()){
                    LoginProcess.dismiss();
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    for(int i=0;i<2;i++) {
                        Toast toast = Toast.makeText(getActivity(), "Failed to Signing due to " + e.getMessage(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                }
            }
        });
    }
 // Navigation on Click Buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signIn:
                fetch_User_Written_Info();
                break;
            case R.id.Sign_Up:
                mListener.loginInterfaceMethod3();
                break;
            case R.id.forgetPassword:
                mListener.loginInterfaceMethod1();
                break;


        }
    }
// Fetch user written info from edit text
    private void fetch_User_Written_Info() {
        String signInEmailAddress=signIn_EmailAddress.getText().toString().replace(" ","");
        Matcher mathcer= Pattern.compile(validEmailPattern).matcher(signInEmailAddress);
        String signInPassword=signIn_Password.getText().toString();
        if(mCheckBoxRememberMe.isChecked()){
            Boolean boolIsChecked=mCheckBoxRememberMe.isChecked();
            SharedPreferences.Editor editor=mPrefs.edit();
            editor.putString("prefs_Email_Address",signInEmailAddress);
            editor.putString("prefs_Password",signInPassword);
            editor.putBoolean("prefs_check",boolIsChecked);
            editor.apply();
            Toast toast=Toast.makeText(getActivity(),"Setting have been saved.",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else{
            mPrefs.edit().clear().apply();
        }
        if(!TextUtils.isEmpty(signInEmailAddress)&&!TextUtils.isEmpty(signInPassword)&&mathcer.matches()){
            LoginProcess.setTitle("Logging In");
            LoginProcess.setMessage("Please Wait while we check your Given Credentials.");
            LoginProcess.setCanceledOnTouchOutside(false);
            LoginProcess.show();
            login_user(signInEmailAddress,signInPassword);
        }else if(TextUtils.isEmpty(signInEmailAddress)){
            signIn_EmailAddress.setError("Email Address is empty");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }

        }
        else if(TextUtils.isEmpty(signInPassword)){
            signIn_Password.setError("Password is Empty");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }

        }
        else {
            Toast toast = Toast.makeText(getActivity(), "Enter Valid Email Address.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public interface loginInterface {
        // TODO: Update argument type and name
        void loginInterfaceMethod1();
        void loginInterfaceMethod2();
        void loginInterfaceMethod3();
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        mPrefs=context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (context instanceof loginInterface) {
            mListener = (loginInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement loginInterface_mListener");
        }
    }
}
