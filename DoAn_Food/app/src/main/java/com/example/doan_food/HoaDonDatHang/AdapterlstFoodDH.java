package com.example.doan_food.HoaDonDatHang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.MonAn.MonAnDetails;
import com.example.doan_food.R;
import com.example.doan_food.User.HoaDon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterlstFoodDH extends RecyclerView.Adapter<AdapterlstFoodDH.ViewHolder>
{
    private ArrayList<HoaDon> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_list_food_hoadon,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon item = data.get(position);

        holder.txtName.setText(item.getMonAn().getTenMonAn());
        holder.txtGia.setText(item.getMonAn().getGiaTien());
        holder.txtSL.setText(item.getSL());
        Picasso.get().load(item.getMonAn().getLink_image()).into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MonAnDetails.class);
                intent.putExtra("food",item.getMonAn());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtGia,txtSL;
        ImageView image;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_cart);
            txtName = itemView.findViewById(R.id.txtTenMonAn);
            txtGia = itemView.findViewById(R.id.txtGiaMonAn);
            txtSL = itemView.findViewById(R.id.txtSoLuong);
            layout = itemView.findViewById(R.id.layout_list);
        }
    }

    public AdapterlstFoodDH(Context context,ArrayList<HoaDon> items) {
        this.data = items;
        this.context = context;
    }
}
