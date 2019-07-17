package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Sign_Up_Fragment extends Fragment implements View.OnClickListener  {
    // Objects Declaration
    View view;
    Vibrator v ;
    private EditText sigUp_Name,signUp_EmailAddress,sigUp_Password,sigUp_confirm_Password;
    private Spinner signUpCitySpinner;
    private Button signUp_Button;
    private ArrayAdapter <String> signUpCity;
    // FireBase Authorization Instance declaration
    private FirebaseAuth authorization;
    signUpinterface mListener;
    // Progress DialogueBox declaration
    private ProgressDialog RegProcess;
    // FireBase Database declaration
    FirebaseDatabase firebaseDatabase;
    // FireBase Database Reference declaration
    DatabaseReference databaseReference;
    FirebaseUser user;
    String name,emailAddress,password,userSelectedCity="Select City",currentUserUid;
    String validEmailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +"(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+";
    public Sign_Up_Fragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        getActivity().setTitle(R.string.sign_up);
        // Objects Initialization
        v=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        TextView signIn=view.findViewById(R.id.signin);
        signIn.setOnClickListener(this);
        // Fire Base Instance initialization
        authorization = FirebaseAuth.getInstance();
        // Progress Dialogue instance initialization
        RegProcess=new ProgressDialog(getActivity());
        // Initialization
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        // Input Text Fields
        sigUp_Name = view.findViewById(R.id.signUpName);
        signUp_EmailAddress = view.findViewById(R.id.signupEmailAdress);
        sigUp_Password= view.findViewById(R.id.signUpPassword);
        sigUp_confirm_Password=view.findViewById(R.id.signUpConfirmPassword);
        signUpCitySpinner=view.findViewById(R.id.signUpCitySpinner);
        signUpCity=new ArrayAdapter<String>(getActivity(),R.layout.spinner_item_colour,getResources().getStringArray(R.array.cityList));
        signUpCity.setDropDownViewResource(R.layout.spinner_item_colour);
        signUpCitySpinner.setAdapter(signUpCity);
        signUpCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userSelectedCity=signUpCitySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        signUp_Button =view.findViewById(R.id.signUpButton);
        signUp_Button.setOnClickListener(this);
        return view;
    }
    // Sign up new user
    private void signUp_newUser(final String emailAdress, final String password) {
        authorization.createUserWithEmailAndPassword(emailAdress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // If user account created, update Sign Up Interface with the sign in Interface
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    sendVerificationEmial();
                    RegProcess.dismiss();
                    currentUserUid = user.getUid();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HashMap<String, String> signUp_HashMap = new HashMap<>();
                            signUp_HashMap.put("Name", name);
                            signUp_HashMap.put("E-Mail Adress", emailAdress);
                            signUp_HashMap.put("Password", password);
                            signUp_HashMap.put("City", userSelectedCity);
                            signUp_HashMap.put("Profile Image Url","None");
                            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).setValue(signUp_HashMap);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mListener.signUpinterfaceMethod1();

                }
                if(!task.isSuccessful()){
                    RegProcess.dismiss();
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    for(int i=0;i<2;i++) {
                        Toast toast = Toast.makeText(getActivity(), "Failed Registration due to " + e.getMessage(), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                }
            }
        });
    }
// For send verification email
    private void sendVerificationEmial() {
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast toast=Toast.makeText(getActivity(),"Check your Email Adress for verification",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });
        }
    }
// Navigation on click text views and button
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUpButton:
                fetch_User_Written_Info();
                break;
            case R.id.signin:
                mListener.signUpinterfaceMethod2();
                break;

        }
    }

    private void fetch_User_Written_Info() {
        name = sigUp_Name.getText().toString();
        emailAddress = signUp_EmailAddress.getText().toString().replace(" ","");
        Matcher mathcer= Pattern.compile(validEmailPattern).matcher(emailAddress);
        password = sigUp_Password.getText().toString();
        String confirmPassword=sigUp_confirm_Password.getText().toString();
        if(!userSelectedCity.equals("Select City")){
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(emailAddress) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword) && TextUtils.equals(password, confirmPassword)) {
                if (mathcer.matches()) {
                    RegProcess.setTitle("Registering User");
                    RegProcess.setMessage("Please Wait while we create your Account");
                    RegProcess.setCanceledOnTouchOutside(false);
                    RegProcess.show();
                    signUp_newUser(emailAddress, password);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Enter Valid Email Address.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        }
        else if(!(TextUtils.equals(password,confirmPassword))){
            Toast toast= Toast.makeText(getActivity(),"Your written password and confirm password does'nt matching",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(TextUtils.isEmpty(name)){
            sigUp_Name.setError("Name is empty.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
        else if(TextUtils.isEmpty(emailAddress)){
            signUp_EmailAddress.setError("Email Address is empty.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
        else if(TextUtils.isEmpty(password)){
            sigUp_Password.setError("Password is empty.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            sigUp_confirm_Password.setError("Confirm Password is empty.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
        if(userSelectedCity.equals("Select City")){
            Toast toast=Toast.makeText(getActivity(),"City is not selected.",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
    }

    public interface signUpinterface{
        void signUpinterfaceMethod1();
        void signUpinterfaceMethod2();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof signUpinterface) {
            mListener = (signUpinterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement signUpInterface_mListener");
        }
    }
}
