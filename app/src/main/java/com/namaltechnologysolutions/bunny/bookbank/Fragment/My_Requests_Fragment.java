package com.namaltechnologysolutions.bunny.bookbank.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.Models.User_Request;
import com.namaltechnologysolutions.bunny.bookbank.Models.other_Request_Model_Class;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.ArrayList;


public class My_Requests_Fragment extends Fragment {
    // Objects Declaration
    View view;
    RecyclerView recyclerView;
    others_Request_Adapter request_adapter;
    private ArrayList<other_Request_Model_Class> requestlist;
    public My_Requests_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestlist =new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_requests, container, false);
        getActivity().setTitle(R.string.my_requests_drawer);
        // Firebase reference to retrieve user own requests
        FirebaseDatabase.getInstance().getReference().child("Users Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.ic_diller(TAG, "onDataChange: got something");
                for (DataSnapshot user: dataSnapshot.getChildren()){
                    //Log.ic_diller(TAG, "Users UID "+user);
                    if(user.getKey().equals("UID "+ FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        for (DataSnapshot request: user.getChildren()){
                            User_Request user_request= request.getValue(User_Request.class);
                           // Log.ic_diller(TAG, "This is user request "+user_request_icon.toString());
                            addRequest(user_request);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView=view.findViewById(R.id.myRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
// Method to add user previous request in recycler view
    private void addRequest(User_Request user_request) {
        requestlist.add(new other_Request_Model_Class(user_request.getProfile_Image_Url(),user_request.getName(),user_request.getContact_No(),user_request.getCurrent_Date(),user_request.getAddress(),user_request.getCity(),user_request.getRequest()));
        request_adapter=new others_Request_Adapter(requestlist);
        recyclerView.setAdapter(request_adapter);
        request_adapter.notifyItemInserted(requestlist.size()-1);
    }

}
