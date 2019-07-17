package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namaltechnologysolutions.bunny.bookbank.R;

public class Home_Fragment extends Fragment implements View.OnClickListener

{
    // Objects Declaration
    ImageView postRequest_Icon,contactUs_icon,ownRequests_icon,otherRequest_Icon;
    Home_Fragment_Interface  mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle(R.string.home_drawer);
        // Objects Initialization
        otherRequest_Icon=view.findViewById(R.id.otherRequest_Icon);
        otherRequest_Icon.setOnClickListener(this);
        postRequest_Icon=view.findViewById(R.id.postRequest_Icon);
        postRequest_Icon.setOnClickListener(this);
        contactUs_icon=view.findViewById(R.id.contactUs_icon);
        contactUs_icon.setOnClickListener(this);
        ownRequests_icon=view.findViewById(R.id.ownRequests);
        ownRequests_icon.setOnClickListener(this);
        return view;
    }
    // Navigation on Click Home Fragment display Icons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.otherRequest_Icon:
                mListener.Home_Fragment_Interface_Method_OtherReuest_Icon();
                break;
            case R.id.postRequest_Icon:
                mListener.Home_Fragment_Interface_Method_Post_Request_Icon();
                break;
            case R.id.contactUs_icon:
                mListener.Home_Fragment_Interface_Method_ContactUs_Icon();
                break;
            case R.id.ownRequests:
                mListener.Home_Fragment_Interface_Method_ownRequests_Icon();
                break;
        }
    }
// Interface Method declaration
    public interface Home_Fragment_Interface{
        void Home_Fragment_Interface_Method_Post_Request_Icon();
        void Home_Fragment_Interface_Method_ContactUs_Icon();
        void Home_Fragment_Interface_Method_ownRequests_Icon();
        void Home_Fragment_Interface_Method_OtherReuest_Icon();
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Home_Fragment_Interface) {
            mListener= (Home_Fragment_Interface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Home_Fragment_Interface_mListener");
        }
    }

}
