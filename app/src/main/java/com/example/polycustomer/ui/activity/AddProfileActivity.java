package com.example.polycustomer.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.polycustomer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;


public class AddProfileActivity extends AppCompatActivity {

    private TextInputLayout regName, regNumber, regClass, regAddress;
    private Button btnsaveInfor;

    FirebaseAuth auth;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        anhXa();

        auth = FirebaseAuth.getInstance();

        btnsaveInfor.setOnClickListener(v -> {
            String nameUser = regName.getEditText().getText().toString();
            String numberUser = regNumber.getEditText().getText().toString();
            String addressUser = regAddress.getEditText().getText().toString();
            String classUser = regClass.getEditText().getText().toString();

            if (!validateFullName() |!validateNumber() | !validateClass() | !validateAddress()){
                return;
            }else {
                register(nameUser,numberUser,addressUser,classUser);
            }
        });
    }

    private void register(String nameUser, String numberUser, String addressUser, String classUer) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

        String token = FirebaseInstanceId.getInstance().getToken();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", userID);
        hashMap.put("nameUser", nameUser);
        hashMap.put("numberUser", numberUser);
        hashMap.put("addressUser", addressUser);
        hashMap.put("classUser", classUer);
        hashMap.put("imageURl", "default");
        hashMap.put("token", token);
        hashMap.put("listChat", "");

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(AddProfileActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddProfileActivity.this, "Vui long thu lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void anhXa() {
        btnsaveInfor = findViewById(R.id.btnsaveInfor);
        regName = findViewById(R.id.reg_name);
        regNumber = findViewById(R.id.reg_number);
        regClass = findViewById(R.id.reg_class);
        regAddress = findViewById(R.id.reg_address);
    }

    private boolean validateFullName(){
        String val = regName.getEditText().getText().toString().trim();
        String regxFullName = "[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";


        if (val.isEmpty()){
            regName.setError("Không được để trống");
            regName.setHint("VD: Nhập tên của bạn?");
            regName.setBoxBackgroundColor(Color.WHITE);
            return false;
        }else if (!val.matches(regxFullName)){
            regName.setError("Sai định dạng tên ");
            regName.setBackgroundColor(Color.WHITE);
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateNumber(){
        String val = regNumber.getEditText().getText().toString().trim();
        String regexNumber = "[0]?[0-9]{9}";


        if (val.isEmpty()){
            regNumber.setError("Không được để trống");
            regNumber.setBoxBackgroundColor(Color.WHITE);
            return false;
        }else if (!val.matches(regexNumber)){
            regNumber.setError("Sai định dạng ");
            regNumber.setBoxBackgroundColor(Color.WHITE);
            return false;
        }else {
            regNumber.setError(null);
            regNumber.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateClass(){
        String val = regClass.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            regClass.setError("Không được để trống");
            regClass.setHint("Lớp học của bạn?");
            regClass.setBoxBackgroundColor(Color.WHITE);
            return false;
        }else {
            regClass.setError(null);
            regClass.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAddress(){
        String val = regAddress.getEditText().getText().toString().trim();

        if (val.isEmpty()){
            regAddress.setError("Không được để trống");
            regAddress.setHint("VD: Fpoly Hanoi");
            regAddress.setBoxBackgroundColor(Color.WHITE);
            return false;
        }else {
            regAddress.setError(null);
            regAddress.setErrorEnabled(false);
            return true;
        }
    }

}