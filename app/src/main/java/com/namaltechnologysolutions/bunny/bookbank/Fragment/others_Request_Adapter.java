package com.namaltechnologysolutions.bunny.bookbank.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.namaltechnologysolutions.bunny.bookbank.Activity.Navigation_Drawer_Home_Activity;
import com.namaltechnologysolutions.bunny.bookbank.Models.other_Request_Model_Class;
import com.namaltechnologysolutions.bunny.bookbank.R;

import java.util.ArrayList;

/**
 * Created by Bunny on 12/21/2018.
 */

public class others_Request_Adapter extends RecyclerView.Adapter<others_Request_Adapter.Myholder>{
    public ArrayList<other_Request_Model_Class> requestlist;

    public others_Request_Adapter(ArrayList<other_Request_Model_Class> requestlist) {
        this.requestlist = requestlist;
    }

    @Override
    public others_Request_Adapter.Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card_view,null);
        Myholder myholder=new Myholder(view);
        return myholder;
    }

    @Override
    public void onBindViewHolder(Myholder holder, final int position) {

        if (requestlist.get(position).getCurrentUserProfileUrl().equals("None"))
            holder.userProfileImage.setImageResource(R.drawable.ic_user_profile);
        else
            Glide.with(Navigation_Drawer_Home_Activity.context).load(requestlist.get(position).getCurrentUserProfileUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userProfileImage);
        holder.userName.setText(requestlist.get(position).getUserName());
        holder.otherRequestCallIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToDefaultDiller=new Intent(Intent.ACTION_DIAL);
                moveToDefaultDiller.setData(Uri.parse("tel:"+requestlist.get(position).getPhoneNo()));
                view.getContext().startActivity(moveToDefaultDiller);
            }
        });
        holder.location.setText(requestlist.get(position).getLocation());
        holder.date.setText(requestlist.get(position).getDate());
        holder.home.setText(requestlist.get(position).getHome());
        holder.userRequest.setText(requestlist.get(position).getUserRequest());

    }

    @Override
    public int getItemCount() {
        return requestlist.size();
    }
    public class Myholder extends RecyclerView.ViewHolder{
        TextView userName,date,home,location,userRequest;
        ImageView userProfileImage,otherRequestCallIcon;
        public Myholder(View itemView) {
            super(itemView);
            userProfileImage=itemView.findViewById(R.id.otherRequestProfile);
            userName=itemView.findViewById(R.id.otherRequestName);
            otherRequestCallIcon=itemView.findViewById(R.id.otherRequestCallIcon);
            date=itemView.findViewById(R.id.otherRequestDate);
            home=itemView.findViewById(R.id.otherRequestHome);
            location=itemView.findViewById(R.id.otherRequestLocation);
            userRequest=itemView.findViewById(R.id.userRequest);

        }
    }
}
