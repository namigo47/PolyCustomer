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
import com.example.polycustomer.Model.Order;
import com.example.polycustomer.Model.Product;
import com.example.polycustomer.R;
import com.example.polycustomer.event.callBack;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context mContext;
    private List<Order> mUsers;
    private callBack even;


    public HistoryAdapter(Context mContext, List<Order> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.even = even;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {

        Order product = mUsers.get(position);
        holder.name.setText("Tên SP: "+product.getName());
        holder.price.setText("Giá: "+product.getPrice());
        holder.statusOder.setText(product.getStatus());
        if (!product.getNote().isEmpty()){
            holder.note.setText("Note : " + product.getNote());
        }
        else {
            holder.note.setText("");
        }
        holder.adress.setText("Địa chỉ : "+product.getAdress());

        Glide.with(mContext)
                .load(product.getImage())
                .into(holder.profile_image);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name , price,adress,note, statusOder;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);
            name= itemView.findViewById(R.id.txt_nameProduct);
            price = itemView.findViewById(R.id.txt_priceProduct);
            note = itemView.findViewById(R.id.txt_note);
            adress = itemView.findViewById(R.id.txt_adress);
            profile_image = itemView.findViewById(R.id.imgProduct);
            statusOder =itemView.findViewById(R.id.statusOder);

        }
    }
}
