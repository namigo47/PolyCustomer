package com.example.polycustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polycustomer.Adapter.MessageAdapter;
import com.example.polycustomer.Model.Chat;
import com.example.polycustomer.Model.Message;
import com.example.polycustomer.Model.Notify;
import com.example.polycustomer.Model.NotifyModel;
import com.example.polycustomer.R;
import com.example.polycustomer.event.RetrofitService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity {


    CircleImageView profile_image;
    TextView username;
    Button btnDatHang;


    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbReference;
    DatabaseReference userref, chatRef, chatRefRead;

    EditText edt_text;
    ImageButton btn_send;
    Chat chat;
    String idCanteen, idChat, idUser;
    boolean flag = false;
    com.example.polycustomer.Model.Message message;
    Intent intent;
    MessageAdapter messageAdapter;
    List<Message> mMessage;
    RecyclerView recyclerView;
    private String BASE_URL = "https://fcm.googleapis.com/";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        idCanteen = intent.getStringExtra("idcanteen");
        idUser = intent.getStringExtra("idUser");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        idChat = intent.getStringExtra("idChat");
        String token = intent.getStringExtra("token");

        recyclerView = findViewById(R.id.recycler_view_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image_user);
        username = findViewById(R.id.username_user);
        btn_send = findViewById(R.id.btn_send_msg);
        edt_text = findViewById(R.id.edt_text_send);
        btnDatHang = findViewById(R.id.btnDatHang);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbReference = firebaseDatabase.getReference("Canteen");
        userref = firebaseDatabase.getReference("Users");

//        userref.child(idUser).child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.i("TAG", "onDataChange: "+ idChat);
//
//                for (DataSnapshot id : snapshot.getChildren()) {
//                    Log.i("TAG", "onDataChange: "+ id.getValue().toString());
//                    if (id.getValue().toString().equals(idChat)) {
//                        flag = true;
//                    }
//                }
//
//                if(!flag){
//                    userDbReference.child(idCanteen).child("chat").push().setValue(idChat);
//                    userref.child(idUser).child("chat").push().setValue(idChat);
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        btn_send.setOnClickListener(v -> {
            String msg = edt_text.getText().toString();
            if (msg.isEmpty()){
            }else {
                sendMessage(msg, token, idCanteen, idChat);
            }

        });
        btnDatHang.setOnClickListener(v -> {
            startActivity(new Intent(MessageActivity.this, ProductActivity.class));

        });


        Query query = userDbReference.orderByChild("id").equalTo(idCanteen);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = "" + dataSnapshot.child("nameCanteen").getValue();
                    String image = "" + dataSnapshot.child("avatar").getValue();
                    username.setText(name);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.iconavatar).into(profile_image);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.iconavatar).into(profile_image);
                    }
                }
                readMessage(idChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String msg, String token, String userid, String idChat) {
        Log.i("TOKEN", "Token" + token);
        message = new Message(msg, 0);
        chatRef = firebaseDatabase.getReference("Chats");
        chatRef.child(idChat).child("listMes").push().setValue(message);
        Log.i("TAG", "sendMessage: " + msg);
        edt_text.setText("");
        //send FCM firebase
        sendNotifyUser(token);


    }
    private void sendNotifyUser(String token){
        NotifyModel notifyModel = new NotifyModel(token, new Notify("Thông báo", "Tin nhắn mới"));
        Call<NotifyModel> userSend = provideService(provideRetrofit()).sendNotifycation(notifyModel);
        userSend.enqueue(new Callback<NotifyModel>() {
            @Override
            public void onResponse(Call<NotifyModel> call, Response<NotifyModel> response) {

            }

            @Override
            public void onFailure(Call<NotifyModel> call, Throwable t) {

            }
        });
    }

    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    @Singleton
    RetrofitService provideService(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);
    }

    private void readMessage(String idChat) {
        mMessage = new ArrayList<>();
        chatRefRead = firebaseDatabase.getReference("Chats");
        chatRefRead.child(idChat).child("listMes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessage.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    mMessage.add(message);
                }
                Log.i("TAG", "onDataChange: " + snapshot.child("listMes"));

                messageAdapter = new MessageAdapter(MessageActivity.this, mMessage);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}