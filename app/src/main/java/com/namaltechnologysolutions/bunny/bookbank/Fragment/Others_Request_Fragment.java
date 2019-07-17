package com.namaltechnologysolutions.bunny.bookbank.Fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.Models.User_Request;
import com.namaltechnologysolutions.bunny.bookbank.Models.other_Request_Model_Class;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.ArrayList;
//
public class Others_Request_Fragment extends Fragment {
    // Objects Declaration
EditText keywords;
String userKeywords;
    User_Request user_request;
    View view;
    RecyclerView recyclerView;
   others_Request_Adapter request_adapter;
   private ArrayList<other_Request_Model_Class> requestlist;

    public Others_Request_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestlist =new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_others_requests, container, false);
        getActivity().setTitle(R.string.other_requests_drawer);
        // Objects Initialization
        keywords=view.findViewById(R.id.keywords);
        recyclerView=view.findViewById(R.id.othersRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadOthersRequest();
        keywords.addTextChangedListener(userKeywordsWatcher);
        return view;
    }
// Load other users requets from firebase database
    private void loadOthersRequest() {
        FirebaseDatabase.getInstance().getReference().child("Users Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Log.ic_diller(TAG, "onDataChange: got something");
                for (DataSnapshot user: dataSnapshot.getChildren()){
                    if(!user.getKey().equals("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        for (DataSnapshot request: user.getChildren()){
                            user_request= request.getValue(User_Request.class);
                            addRequest(user_request);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
// Method to Filter other users requests
    private TextWatcher userKeywordsWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            userKeywords=keywords.getText().toString().trim();
            if(!userKeywords.isEmpty()){
                searchRequests(userKeywords);
            }else if(userKeywords.isEmpty()){
                loadOthersRequest();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    // Method to search other request on base of city
    private void searchRequests(String userKeywords) {
        for(int i=0;i<requestlist.size();i++) {
            if(requestlist.get(i).getLocation().contains(userKeywords)||requestlist.get(i).getLocation().matches(userKeywords)||requestlist.get(i).getLocation().endsWith(userKeywords)||requestlist.get(i).getLocation().endsWith(userKeywords)||requestlist.get(i).getLocation().contentEquals(userKeywords)||requestlist.get(i).getLocation().contains(userKeywords.toUpperCase())||requestlist.get(i).getLocation().contains(userKeywords.toLowerCase())){
                showSearchRequest(requestlist.get(i));

            }
        }
    }
    // Method to show other filter requests
    private void showSearchRequest(other_Request_Model_Class other_request_model_class) {
        requestlist.clear();
        requestlist.add(other_request_model_class);
        request_adapter=new others_Request_Adapter(requestlist);
        recyclerView.setAdapter(request_adapter);
        request_adapter.notifyItemInserted(requestlist.size()-1);
    }
// Method to add other users requests in recycler view
    private void addRequest(User_Request request){
        requestlist.add(new other_Request_Model_Class(request.getProfile_Image_Url(),request.getName(),request.getContact_No(),request.getCurrent_Date(),request.getAddress(),request.getCity(),request.getRequest()));
        request_adapter=new others_Request_Adapter(requestlist);
        recyclerView.setAdapter(request_adapter);
        request_adapter.notifyItemInserted(requestlist.size()-1);

    }
}
