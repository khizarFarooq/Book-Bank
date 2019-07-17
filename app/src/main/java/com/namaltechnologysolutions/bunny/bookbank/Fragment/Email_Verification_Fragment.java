package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namaltechnologysolutions.bunny.bookbank.R;

public class Email_Verification_Fragment extends android.app.Fragment implements View.OnClickListener {
    // Objects Declaration
    private TextView login;
    private emailVerificationInterface mListener;

    public Email_Verification_Fragment() {
        // Required empty public constructor
    }
    public static Email_Verification_Fragment newInstance(String param1, String param2) {
        Email_Verification_Fragment fragment = new Email_Verification_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_email_verification, container, false);
        getActivity().setTitle(R.string.e_mail_verification);
        // Objects Initialization
        login=view.findViewById(R.id.emailVerificationLogin_Button);
        login.setOnClickListener(this);
        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.emailVerificationInterface_MoveToLoginMethod();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof emailVerificationInterface) {
            mListener = (emailVerificationInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    // Navigation on Click Button
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.emailVerificationLogin_Button:
                mListener.emailVerificationInterface_MoveToLoginMethod();
                break;
        }
    }

    public interface emailVerificationInterface {
        // TODO: Update argument type and name
        void emailVerificationInterface_MoveToLoginMethod();
    }
}
