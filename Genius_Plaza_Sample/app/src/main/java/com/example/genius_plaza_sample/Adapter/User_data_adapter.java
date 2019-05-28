package com.example.genius_plaza_sample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.genius_plaza_sample.Model.UserPojo;
import com.example.genius_plaza_sample.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class User_data_adapter extends RecyclerView.Adapter<User_data_adapter.CustomViewHolder> {


    private List<UserPojo> UserData_dataList;
    private Context mContext;


    public User_data_adapter(Context context, List<UserPojo> UserData_dataList) {
        this.UserData_dataList = UserData_dataList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_user_list, null);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder customViewHolder, int i) {

        UserPojo bean = UserData_dataList.get(i);


        customViewHolder.txt_appliance_mac.setText(bean.User_FirstName+" "+bean.User_LastName);
        Picasso.get().load(bean.getUser_avatar()).placeholder((int) R.drawable.user_sample).into(customViewHolder.img_appliance_click);

    }
    @Override
    public int getItemCount() {
        return UserData_dataList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_appliance_mac;
        public ImageView img_appliance_click;

        CustomViewHolder(View view) {
            super(view);

            txt_appliance_mac=view.findViewById(R.id.txtView_User_name);

            img_appliance_click=view.findViewById(R.id.imgView_User);

        }
    }


}
