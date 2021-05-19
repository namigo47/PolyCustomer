package com.example.polycustomer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.polycustomer.Adapter.UserAdapter;
import com.example.polycustomer.Model.Product;
import com.example.polycustomer.ui.activity.HomeActivity;
import com.example.polycustomer.ui.activity.MessageActivity;
import com.example.polycustomer.Model.Canteen;
import com.example.polycustomer.Model.Chat;
import com.example.polycustomer.R;
import com.example.polycustomer.event.callBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersFragment extends Fragment implements callBack {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Canteen> mCanteens;
    private boolean flag = false;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase ref = FirebaseDatabase.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbReference;
    DatabaseReference userref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCanteens = new ArrayList<>();

        readUsers();
        return view;

    }

    private void readUsers() {
        DatabaseReference reference = ref.getReference("Canteen");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCanteens.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("nameCanteen").getValue().toString();
                    String avatar = dataSnapshot.child("avatar").getValue().toString();
                    String token = dataSnapshot.child("token").getValue().toString();
                    String id = dataSnapshot.child("id").getValue().toString();
                    Log.i("TAG", "onDataChange: "+name);
                    Canteen canteen = new Canteen(id,avatar, name,token);
                    assert canteen != null;
                    assert firebaseUser != null;

                    if (!canteen.getId().equals(firebaseUser.getUid())) {
                        mCanteens.add(canteen);
                    }
                }

                userAdapter = new UserAdapter(getContext(), mCanteens, UsersFragment.this);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void addChatFirbase(Canteen canteen) {

        String tokenU = FirebaseInstanceId.getInstance().getToken();

        String userID = firebaseUser.getUid();
        DatabaseReference reference = ref.getReference();
        Chat chat = new Chat(tokenU, canteen.getToken(), new ArrayList(), HomeActivity.NAME,HomeActivity.AVATAR);
        reference.child("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){

                    Log.i("TAG", "onDataChangekey: "+data.getKey());
                    Log.i("TAG", "onDataChangexzxcz: "+ userID + canteen.getId());
                    if (data.getKey().equals(userID + canteen.getId())){
                        flag = true;
                        return;
                    }
                }
                if(flag){
                    reference.child("Chats").child(userID+canteen.getId()).setValue(chat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbReference = firebaseDatabase.getReference("Canteen");
        userref = firebaseDatabase.getReference("Users");

        userref.child(userID).child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("TAG", "onDataChange: "+ userID+canteen.getId());

                for (DataSnapshot id : snapshot.getChildren()) {
                    Log.i("TAG", "onDataChange: "+ id.getValue().toString());
                    if (id.getValue().toString().equals(userID+canteen.getId())) {
                        flag = true;
                    }
                }

                if(!flag){
                    userDbReference.child(canteen.getId()).child("chat").push().setValue(userID+canteen.getId());
                    userref.child(userID).child("chat").push().setValue(userID+canteen.getId());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.putExtra("token", canteen.getToken());
        intent.putExtra("idcanteen", canteen.getId());
        intent.putExtra("idUser", userID);
        intent.putExtra("idChat", userID+canteen.getId());
        Objects.requireNonNull(getContext()).startActivity(intent);
    }

    @Override
    public void productCallBack(Product product) {

    }
}