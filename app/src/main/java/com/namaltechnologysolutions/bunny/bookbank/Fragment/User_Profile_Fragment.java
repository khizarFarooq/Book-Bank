package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.namaltechnologysolutions.bunny.bookbank.R;
public class User_Profile_Fragment extends Fragment implements View.OnClickListener{
    // Objects Declaration
    Vibrator vibrator ;
    ImageView userProfile,profileImageChooser,editProfileName,editProfilePassword,editProfileCity;
    EditText userProfileName,userProfilePassword,userProfileCity;
    Button userProfileUpdateButton;
    Integer REQUEST_CAMERA=1,SELECT_FILE=0;
    Uri selectImageUri;
    ProgressBar userProfileProgressBar;
    String profileImageUrl,currentUserUid,currentUserName,currentUserPassword,currentUserCity,currentProfileImage;
    FirebaseAuth authorization;
    FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabaseRef;
    private DatabaseReference databaseReference;
    private StorageReference profileImageRef;
    private StorageTask uploadTask;
    public User_Profile_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static User_Profile_Fragment newInstance(String param1, String param2) {
        User_Profile_Fragment fragment = new User_Profile_Fragment();
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
        View view=inflater.inflate(R.layout.fragment_user_profile, container, false);
        getActivity().setTitle(R.string.my_account_drawer);
        // Objects Initialization
        vibrator=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        authorization=FirebaseAuth.getInstance();
        currentUser=authorization.getCurrentUser();
        firebaseDatabaseRef=firebaseDatabaseRef.getInstance();
        databaseReference=firebaseDatabaseRef.getReference();
        profileImageRef=FirebaseStorage.getInstance().getReference().child("Users Profile Images");
        profileImageChooser=view.findViewById(R.id.profileImageChooser);
        profileImageChooser.setOnClickListener(this);
        userProfile=view.findViewById(R.id.userProfile);
        userProfileName=view.findViewById(R.id.userProfileName);
        userProfilePassword=view.findViewById(R.id.userProfilePassword);
        userProfileCity=view.findViewById(R.id.userProfileLocation);
        editProfileName= view.findViewById(R.id.editProfileName);
        editProfileName.setOnClickListener(this);
        editProfilePassword=view.findViewById(R.id.editProfilePassword);
        editProfilePassword.setOnClickListener(this);
        editProfileCity=view.findViewById(R.id.editProfileCity);
        editProfileCity.setOnClickListener(this);
        userProfileUpdateButton=view.findViewById(R.id.userProfileUpdateButton);
        userProfileUpdateButton.setOnClickListener(this);
        loadCurrentUserInformation();
        userProfileProgressBar=view.findViewById(R.id.userProfileProgressBar);
        return view;
    }
// To update user profile Info
    private void updateUserProfileInfo() {
        currentUserName=userProfileName.getText().toString();
        currentUserPassword=userProfilePassword.getText().toString();
        currentUserCity=userProfileCity.getText().toString();
        if(!TextUtils.isEmpty(currentUserName)&& !TextUtils.isEmpty(currentUserPassword)&& !TextUtils.isEmpty(currentUserCity)){
            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).child("Name").setValue(currentUserName);
            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).child("Password").setValue(currentUserPassword);
            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).child("City").setValue(currentUserCity);
            authorization.getCurrentUser().updatePassword(currentUserPassword);
            Toast toast=Toast.makeText(getActivity(),"Your profile info has been update.",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if(TextUtils.isEmpty(currentUserName)){
            userProfileName.setError("Name is required");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }

        }
        else if(TextUtils.isEmpty(currentUserPassword)){
            userProfilePassword.setError("Password is required");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }
        else if(TextUtils.isEmpty(currentUserCity)){
            userProfileCity.setError("City is required");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }
        loadCurrentUserInformation();
    }
// Load current user info from firebase
    private void loadCurrentUserInformation() {
        if(currentUser!=null){
            currentUserUid=currentUser.getUid();
            databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    currentUserName=(String) dataSnapshot.child("Name").getValue();
                    currentUserPassword=(String)dataSnapshot.child("Password").getValue();
                    currentUserCity=(String)dataSnapshot.child("City").getValue();
                    currentProfileImage=(String)dataSnapshot.child("Profile Image Url").getValue();
                    displayUserProfile();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    // Display user profile
    private void displayUserProfile() {
        if (!currentProfileImage.equals("None")){
            Glide.with(getActivity()/* context */)
                    .load(currentProfileImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(userProfile);
        }

        userProfileName.setText(currentUserName);
        userProfileName.setClickable(false);
        userProfileName.setEnabled(false);
        userProfilePassword.setText(currentUserPassword);
        userProfilePassword.setClickable(false);
        userProfilePassword.setEnabled(false);
        userProfileCity.setText(currentUserCity);
        userProfileCity.setClickable(false);
        userProfileCity.setEnabled(false);
    }
// For Image selection
    private void selectImage(){
        final CharSequence[] items={"Camera","Gallery","Cancel"};
    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
    builder.setTitle("Update Profile Image");
    builder.setItems(items, new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(items[i].equals("Camera")){
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CAMERA);

            }
            else if(items[i].equals("Gallery")){
                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);

            }
            else if(items[i].equals("Cancel")){
                dialogInterface.dismiss();
            }
        }
    });
        builder.show();
}
@Override
public void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if (resultCode== Activity.RESULT_OK){
        if(requestCode==REQUEST_CAMERA){
            Bundle bundle=data.getExtras();
            final Bitmap bitmap=(Bitmap) bundle.get("data");
            userProfile.setImageBitmap(bitmap);
        }else if(requestCode==SELECT_FILE&&data!=null&&data.getData()!=null){
            selectImageUri=data.getData();
            userProfile.setImageURI(selectImageUri);

        }
    }
}
// To update image to Firebase storage
  private void updateImageToFirebaseStorage() {
      userProfileProgressBar.setVisibility(View.VISIBLE);
       profileImageRef= profileImageRef.child("UID "+currentUserUid).child("Current Profile Image"+".jpg");
        if(selectImageUri!=null){
            uploadTask=profileImageRef.putFile(selectImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    userProfileProgressBar.setVisibility(View.GONE);
                    profileImageUrl=taskSnapshot.getDownloadUrl().toString();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            userProfileProgressBar.setProgress(0);
                        }
                    }, 5000);
                    Toast toast=Toast.makeText(getActivity(),"Your profile pic has been update",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Toast.makeText(getActivity(),profileImageUrl,Toast.LENGTH_SHORT);
                    databaseReference.child("Users Data").child("Sign Up Info").child("UID "+currentUserUid).child("Profile Image Url").setValue(profileImageUrl);
                   // databaseReference.child("Users Data").child("Public Profile Images").child("UID "+currentUserUid).setValue(profileImageUrl);
                }

            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast toast=Toast.makeText(getActivity(),"Unable to Update",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            userProfileProgressBar.setProgress((int)progress);
                        }
                    });

        }
        else{
            userProfileProgressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
// Configuration
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profileImageChooser:
                selectImage();
                break;
            case R.id.editProfileName:
                editProfileName.setColorFilter(android.R.color.holo_blue_light);
                userProfileName.setClickable(true);
                userProfileName.setEnabled(true);
                break;
            case R.id.editProfilePassword:
                editProfilePassword.setColorFilter(android.R.color.holo_blue_light);
                userProfilePassword.setClickable(true);
                userProfilePassword.setEnabled(true);
                break;
            case R.id.editProfileCity:
                editProfileCity.setColorFilter(android.R.color.holo_blue_light);
                userProfileCity.setClickable(true);
                userProfileCity.setEnabled(true);
                break;
            case R.id.userProfileUpdateButton:
                if(uploadTask!=null&&uploadTask.isInProgress()){
                    Toast toast= Toast.makeText(getActivity(),"Upload in Progress",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                    updateImageToFirebaseStorage();
                    if(userProfileName.isEnabled() || userProfilePassword.isEnabled() || userProfileCity.isEnabled()){
                        updateUserProfileInfo();
                    }

                }
                break;
        }
    }
}
