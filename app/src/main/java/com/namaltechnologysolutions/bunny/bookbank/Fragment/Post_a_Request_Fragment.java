package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namaltechnologysolutions.bunny.bookbank.Models.User_Request;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.text.DateFormat;
import java.util.Calendar;
public class Post_a_Request_Fragment extends Fragment implements View.OnClickListener{
    private int REQUEST_TIME_OUT=1900;
    ProgressBar progressBar;
    Sprite wave;
    EditText postARequestName, postARequestPhoneNumber, postARequestDescription, postARequestAddress;
    Button postARequestSubmitButton;
    private Spinner postARequestCitySpinner;
    private ArrayAdapter<String> posARequestCity;
    String userSelectedCity = "Select City", currentUserName, currentUserPhoneNumber, currentUserAddress, currentUserRequest, currentDate,currentUserProfileUrl;;
    // FireBase Database declaration
    FirebaseDatabase firebaseDatabase;
    // FireBase Database Reference declaration
    DatabaseReference databaseReference;
    // FireBase Authorization Instance declaration
    private FirebaseAuth authorization;
    private postARequestInterface mListener;
    public Post_a_Request_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static Post_a_Request_Fragment newInstance(String param1, String param2) {
        Post_a_Request_Fragment fragment = new Post_a_Request_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_a_request, container, false);
        getActivity().setTitle(R.string.post_a_request_drawer);
        progressBar=view.findViewById(R.id.post_A_Request_Progress_Bar);
        wave=new Wave();
        wave.setColor(R.color.skyColor);
        progressBar.setIndeterminateDrawable(wave);
        progressBar.setVisibility(view.GONE);
        postARequestName = view.findViewById(R.id.postARequestName);
        postARequestPhoneNumber = view.findViewById(R.id.postARequestphoneNumber);
        postARequestAddress = view.findViewById(R.id.postARequestAddress);
        postARequestDescription = view.findViewById(R.id.postARequestDescription);
        postARequestSubmitButton = view.findViewById(R.id.postARequestSubmitButton);
        postARequestSubmitButton.setOnClickListener(this);
        postARequestCitySpinner = view.findViewById(R.id.postARequestCitySpinner);
        posARequestCity = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_colour, getResources().getStringArray(R.array.cityList));
        posARequestCity.setDropDownViewResource(R.layout.spinner_item_colour);
        postARequestCitySpinner.setAdapter(posARequestCity);
        postARequestCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userSelectedCity = postARequestCitySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Users Data").child("Sign Up Info").child("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserProfileUrl=dataSnapshot.child("Profile Image Url").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
// Validation of user written request
    private void validateUserGivenInformation() {

        if (!TextUtils.isEmpty(currentUserName)&& !TextUtils.isEmpty(currentUserPhoneNumber) && !userSelectedCity.equals("Select City")&& !TextUtils.isEmpty(currentUserAddress) && !TextUtils.isEmpty(currentUserRequest) ) {
            Boolean check = isValidPhoneNumber(currentUserPhoneNumber);
            if(check.equals(true)) {
                postUserRequest();
            }
        } else if (TextUtils.isEmpty(currentUserName)) {
            postARequestName.setError("Name is required.");
        }else if (TextUtils.isEmpty(currentUserPhoneNumber)) {
            postARequestPhoneNumber.setError("Contact Number is required.");
        }else if (TextUtils.isEmpty(currentUserAddress)) {
            postARequestAddress.setError("Address is required.");
        }else if (userSelectedCity.equals("Select City")) {
            Toast toast = Toast.makeText(getActivity(), "City is not selected.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else if (TextUtils.isEmpty(currentUserRequest)) {
            postARequestDescription.setError("Description is required.");
        }

    }
    // Post user request
    private void postUserRequest() {
        progressBar.setVisibility(View.VISIBLE);
        String key = FirebaseDatabase.getInstance().getReference().child("Users Requests").child("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
        User_Request user_request = new User_Request(currentUserName,currentUserPhoneNumber,currentUserAddress,userSelectedCity,currentUserRequest,currentDate,currentUserProfileUrl);
        FirebaseDatabase.getInstance().getReference().child("Users Requests").child("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).setValue(user_request);
        FirebaseDatabase.getInstance().getReference().child("Notification").child("UID "+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Request");
        Toast toast = Toast.makeText(getActivity(), "Your Request has been Posted", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.post_a_request_sound);
        mp.start();
        new Handler().postDelayed(new Runnable() {

                                      @Override
                                      public void run() {
                                          mListener.postARequestInterfaceMethod();
                                      }
                                  },REQUEST_TIME_OUT);
    }
// Check user written contact no is valid or not
    private boolean isValidPhoneNumber(CharSequence currentUserPhoneNumber) {
        if (currentUserPhoneNumber.length()==11) {
            return Patterns.PHONE.matcher(currentUserPhoneNumber).matches();
        }else if(currentUserPhoneNumber.length()!=11){
            Toast toast = Toast.makeText(getActivity(), "Contact No is not valid", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return false;
    }
// On click listener implementation
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postARequestSubmitButton:
                fetch_User_Written_Request();
            break;
        }
    }
    // Fetch user written info in edit text
    private void fetch_User_Written_Request() {
        currentUserName = postARequestName.getText().toString();
        currentUserPhoneNumber = postARequestPhoneNumber.getText().toString().replace(" ","").replace("-","").replace("+92","0");
        currentUserRequest = postARequestDescription.getText().toString();
        currentUserAddress = postARequestAddress.getText().toString();
        Calendar calendar=Calendar.getInstance();
        currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        validateUserGivenInformation();
    }

    public interface postARequestInterface{
        void postARequestInterfaceMethod();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof postARequestInterface) {
            mListener = (postARequestInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement postARequestInterface_mListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
