package com.namaltechnologysolutions.bunny.bookbank.Fragment;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Update_Password_Fragment extends Fragment implements View.OnClickListener{
    // Objects Declaration
    Vibrator vibrator;

    EditText updatePasswordEmailAdress;
    String validEmailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +"\\@" +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +"(" +"\\." +"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +")+";
    String updatePasswordEmailAddress;
    Button sendVerificationEmailAdress;
    FirebaseAuth authorization;
    ProgressBar progressBar;
    Sprite Circle;

    private updatePasswordInterface mListener;

    public Update_Password_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static Update_Password_Fragment newInstance(String param1, String param2) {
        Update_Password_Fragment fragment = new Update_Password_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update_password, container, false);
        getActivity().setTitle(getString(R.string.update_password));
        // Objects Initialization
        vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        progressBar=view.findViewById(R.id.progressBar);
        Circle= new Circle();
        Circle.setColor(R.color.skyColor);
        progressBar.setIndeterminateDrawable(Circle);
        updatePasswordEmailAdress=view.findViewById(R.id.updatePasswordEmailAdress);
        sendVerificationEmailAdress=view.findViewById(R.id.resetPasswordButton);
        sendVerificationEmailAdress.setOnClickListener(this);
        progressBar=view.findViewById(R.id.progressBar);
        authorization=FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.updatePasswordInterfaceMethod();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resetPasswordButton:
                fetch_User_Written_Email_And_Send_Verification_Link();
                break;
        }
    }
// To fetch user written email address and password
    private void fetch_User_Written_Email_And_Send_Verification_Link() {
        updatePasswordEmailAddress=updatePasswordEmailAdress.getText().toString().replace(" ","");
        Matcher mathcer= Pattern.compile(validEmailPattern).matcher(updatePasswordEmailAddress);
        if(!TextUtils.isEmpty(updatePasswordEmailAddress)) {
            if(mathcer.matches()) {
                progressBar.setVisibility(View.VISIBLE);
                authorization.sendPasswordResetEmail(updatePasswordEmailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            mListener.updatePasswordInterfaceMethod();
                        }
                    }
                });
            }
            else {
                updatePasswordEmailAdress.setError("Enter Valid Email Address.");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(500);
                }
            }
        }
        else if(TextUtils.isEmpty(updatePasswordEmailAddress)){
            updatePasswordEmailAdress.setError("Email Address is empty.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }

    }

    public interface updatePasswordInterface {
        // TODO: Update argument type and name
        void updatePasswordInterfaceMethod();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof updatePasswordInterface) {
            mListener = (updatePasswordInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement updatePaswordInterface_mListener");
        }
    }
}
