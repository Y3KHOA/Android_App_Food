package com.example.doan_food.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.MonAn.MonAnDetails;
import com.example.doan_food.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>
{
    private ArrayList<HoaDon> data;
    private Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    SQL sql;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_list_food_cart,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        sql = new SQL(itemView.getContext(),"data_Food.db");
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

        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getSL().equals("1")) sql.deleteHD(mAuth.getCurrentUser().getUid(),item.getMonAn().getTenMonAn());
                else
                {
                    int sl = Integer.parseInt(holder.txtSL.getText().toString())-1;
                    sql.updateHD(mAuth.getCurrentUser().getUid(),item.getMonAn().getTenMonAn(),sl);
                    holder.txtSL.setText(sl+"");
                }
            }
        });

        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(holder.txtSL.getText().toString())+1;
                sql.updateHD(mAuth.getCurrentUser().getUid(),item.getMonAn().getTenMonAn(),sl);
                holder.txtSL.setText(sl+"");
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
        Button btnTang,btnGiam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_cart);
            txtSL = itemView.findViewById(R.id.txtSoLuong);
            txtName = itemView.findViewById(R.id.txtTenMonAn);
            txtGia = itemView.findViewById(R.id.txtGiaMonAn);
            layout = itemView.findViewById(R.id.layout_list);
            btnTang = itemView.findViewById(R.id.btntangsl);
            btnGiam = itemView.findViewById(R.id.btngiamsl);
        }
    }

    public GioHangAdapter(Context context,ArrayList<HoaDon> items) {
        this.data = items;
        this.context = context;
    }
}