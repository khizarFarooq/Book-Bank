package com.namaltechnologysolutions.bunny.bookbank.Fragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.namaltechnologysolutions.bunny.bookbank.R;

public class Login_Register_Fragment extends Fragment implements  View.OnClickListener{
    // Objects Declaration
    LoginRegisterFragmentInterface mListener;
    private FirebaseAuth authorization;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
View view=inflater.inflate(R.layout.fragment_login_register, container, false);
        getActivity().setTitle(getString(R.string.Login_Or_Register));
        // Objects Initialization
       authorization=FirebaseAuth.getInstance();
        if(authorization.getCurrentUser()!=null){
            mListener.LoginRegisterFragmentInterfaceMethod3();
        }
TextView signup_with_emailAdress=view.findViewById(R.id.signUp_with_emialAdress);
TextView login=view.findViewById(R.id.login);
signup_with_emailAdress.setOnClickListener(this);
login.setOnClickListener(this);
        return view;
    }
    // Navigation on Click Text View
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUp_with_emialAdress:
                mListener.LoginRegisterFragmentInterfaceMethod1();
                break;
            case R.id.login:
                mListener.LoginRegisterFragmentInterfaceMethod2();
                break;
        }
    }
// Interface method declaration
    public interface LoginRegisterFragmentInterface{
        void LoginRegisterFragmentInterfaceMethod1();
        void LoginRegisterFragmentInterfaceMethod2();
        void LoginRegisterFragmentInterfaceMethod3();
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginRegisterFragmentInterface) {
            mListener = (LoginRegisterFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginRegisterFragmentInterface_mListener");
        }
    }
}
