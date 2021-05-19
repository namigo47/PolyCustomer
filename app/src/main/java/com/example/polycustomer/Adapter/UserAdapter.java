package com.example.polycustomer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polycustomer.Model.Canteen;
import com.example.polycustomer.Model.Message;
import com.example.polycustomer.R;
import com.example.polycustomer.event.callBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<Canteen> mUsers;
    private callBack even;


    public UserAdapter(Context mContext, List<Canteen> mUsers, callBack even) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.even = even;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Canteen canteen = mUsers.get(position);
        holder.username.setText(canteen.getNameCanteen());

        if (canteen.getAvatar().equals("default")){
            holder.profile_image.setImageResource(R.drawable.iconavatar);
        }else{
            Glide.with(mContext).load(canteen.getAvatar()).into(holder.profile_image);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                even.addChatFirbase( canteen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private ImageView profile_image;
        private TextView last_msg;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.usernameItem);
            profile_image = itemView.findViewById(R.id.profile_imageItem);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

}
