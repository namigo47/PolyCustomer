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
import com.example.polycustomer.Model.Product;
import com.example.polycustomer.R;
import com.example.polycustomer.event.callBack;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductApdater extends RecyclerView.Adapter<ProductApdater.ViewHolder> {

    private Context mContext;
    private List<Product> mUsers;
    private callBack even;


    public ProductApdater(Context mContext, List<Product> mUsers, callBack even) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.even = even;
    }

    @NonNull
    @Override
    public ProductApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item,parent,false);
        return new ProductApdater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductApdater.ViewHolder holder, int position) {

        Product product = mUsers.get(position);
        holder.name.setText(product.getNameP());
        holder.price.setText(product.getPrice()+" VNĐ" );
        holder.description.setText(product.getType());
        Glide.with(mContext)
                .load(product.getAvatarP())
                .into(holder.profile_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                even.productCallBack(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name , price, description;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);
            name= itemView.findViewById(R.id.txt_nameProduct);
            price = itemView.findViewById(R.id.txt_priceProduct);
            description = itemView.findViewById(R.id.txt_statusProduct);
            profile_image = itemView.findViewById(R.id.imgProduct);
        }
    }
}
