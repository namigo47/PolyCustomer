package com.example.polycustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.polycustomer.Model.User;
import com.example.polycustomer.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {
    CircleImageView image_profile;
    private Button btnUpdatePerson;
    private TextView namePerson;
    private TextInputEditText nameUs, numberUs, classUs, addressUs;
    private DatabaseReference reference;
    private FirebaseUser fuser;
    FirebaseAuth auth;

    StorageReference storageReference;

    private CircleImageView avatarUser;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();

        anhXa();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                namePerson.setText(user.getNameUser());
                nameUs.setText(user.getNameUser());
                numberUs.setText(user.getNumberUser());
                classUs.setText(user.getClassUser());
                addressUs.setText(user.getAddressUser());

                if (user.getImageURl().equals("default")){
                    avatarUser.setImageResource(R.drawable.iconavatar);
                }else {
                    Glide.with(getApplicationContext()).load(user.getImageURl()).into(avatarUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        btnUpdatePerson.setOnClickListener(v -> {
            String nameUser = nameUs.getText().toString();
            String numberUser = numberUs.getText().toString();
            String addressUser = addressUs.getText().toString();
            String classUser = classUs.getText().toString();


            if (!validateFullName() |!validateNumber() | !validateClass() | !validateAddress()){
                return;
            }else {
                register(nameUser,numberUser,addressUser,classUser);
            }
        });

    }


    private void register(String nameUser, String numberUser, String addressUser, String classUer ){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        String token = FirebaseInstanceId.getInstance().getToken();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", userID);
        hashMap.put("nameUser", nameUser);
        hashMap.put("numberUser" , numberUser);
        hashMap.put("addressUser", addressUser);
        hashMap.put("classUser", classUer);
        hashMap.put("imageURl","default");
        hashMap.put("token", token);
        hashMap.put("listChat", "");

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    Toast.makeText(ProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Vui long thu lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void anhXa(){
        btnUpdatePerson = findViewById(R.id.btnUpdatePerson);
        namePerson = findViewById(R.id.namePerson);
        nameUs = findViewById(R.id.userName);
        numberUs = findViewById(R.id.numberUser);
        classUs = findViewById(R.id.classUser);
        addressUs = findViewById(R.id.addressUser);
        avatarUser = findViewById(R.id.imgPerson);
    }


    private boolean validateFullName(){
        String val = namePerson.getText().toString().trim();
        String regxFullName = "[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";


        if (val.isEmpty()){
            namePerson.setError("Không được để trống");
            namePerson.setHint("VD: Nhập tên của bạn?");
            namePerson.setBackgroundColor(Color.WHITE);
            return false;

        }else if (!val.matches(regxFullName)){
            namePerson.setError("Sai định dạng tên");
            namePerson.setBackgroundColor(Color.WHITE);
            return false;
        }else {
            namePerson.setError(null);
            return true;
        }
    }
    private boolean validateNumber(){
        String val = numberUs.getText().toString().trim();
        String regexNumber = "[0]?[0-9]{9}";


        if (val.isEmpty()){
            numberUs.setError("Không được để trống");
            numberUs.setBackgroundColor(Color.WHITE);
            return false;
        }else if (!val.matches(regexNumber)){
            numberUs.setError("Sai định dạng ");
            numberUs.setBackgroundColor(Color.WHITE);
            return false;
        }else {
            numberUs.setError(null);
            return true;
        }
    }
    private boolean validateClass(){
        String val = classUs.getText().toString().trim();

        if (val.isEmpty()){
            classUs.setError("Không được để trống");
            classUs.setHint("Lớp học của bạn?");
            classUs.setBackgroundColor(Color.WHITE);
            return false;
        }else {
            classUs.setError(null);
            return true;
        }
    }
    private boolean validateAddress(){
        String val = addressUs.getText().toString().trim();

        if (val.isEmpty()){
            addressUs.setError("Không được để trống");
            addressUs.setHint("VD: Fpoly Hanoi");
            addressUs.setBackgroundColor(Color.WHITE);
            return false;
        }else {
            addressUs.setError(null);
            return true;
        }
    }


}