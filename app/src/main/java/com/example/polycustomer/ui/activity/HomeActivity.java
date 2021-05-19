package com.example.polycustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.polycustomer.Model.User;
import com.example.polycustomer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {
    private CardView profile, chatbox, payment,history;
    private CircleImageView profile_image;
    private TextView username;
    private ImageButton btnLogOut;
    public static String NAME = "";
    public static  String AVATAR = "";


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btnLogOut = findViewById(R.id.btnLogOut);

        profile = findViewById(R.id.profile);
        chatbox = findViewById(R.id.boxchat);
        payment = findViewById(R.id.payment);
        history = findViewById(R.id.history);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                NAME = user.getNameUser();
                AVATAR = user.getImageURl();
                username.setText(user.getNameUser());

                if (user.getImageURl().equals("default")) {
                    profile_image.setImageResource(R.drawable.iconavatar);
                } else {
                    Glide.with(HomeActivity.this).load(user.getImageURl()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnLogOut.setOnClickListener(v -> {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Xác nhận");
            b.setMessage("Bạn có muốn đăng xuất?");
            b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, ActivityLogIn.class));
                    finish();
                }
            });
            b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog al = b.create();
            al.show();
        });

        profile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        chatbox.setOnClickListener(v -> {
            Toast.makeText(this, "ChatBox", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, ChatActivity.class));
        });

        payment.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProductActivity.class));
        });
        history.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, HistoryActicity.class));
        });
    }

}