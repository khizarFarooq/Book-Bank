package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.namaltechnologysolutions.bunny.bookbank.R;


public class Contact_Us_Fragment extends android.app.Fragment implements View.OnClickListener{
    // Objects Declaration
    ImageView dillerIcon,mailIcon;
    public Contact_Us_Fragment() {
        // Required empty public constructor
    }
    public static Contact_Us_Fragment newInstance(String param1, String param2) {

        Contact_Us_Fragment fragment = new Contact_Us_Fragment();

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
         View view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        getActivity().setTitle(R.string.contact_us_drawer);
        // Objects Initialization
         dillerIcon=view.findViewById(R.id.diller_icon);
         dillerIcon.setOnClickListener(this);
         mailIcon=view.findViewById(R.id.mail_icon);
         mailIcon.setOnClickListener(this);
        return view;
    }
// Navigation on Click Icons
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.diller_icon:
                Intent moveToDefaultDiller=new Intent(Intent.ACTION_DIAL);
                moveToDefaultDiller.setData(Uri.parse("tel:03065969263"));
                startActivity(moveToDefaultDiller);
                break;
            case R.id.mail_icon:
                Intent moveToEmailClient = new Intent(Intent.ACTION_SEND);
                moveToEmailClient.setType("text/plain");
                moveToEmailClient.putExtra(Intent.EXTRA_EMAIL, new String[] { "khizardogar6@gmail.com" });
                startActivity(Intent.createChooser(moveToEmailClient, ""));
                break;
        }

    }
}
