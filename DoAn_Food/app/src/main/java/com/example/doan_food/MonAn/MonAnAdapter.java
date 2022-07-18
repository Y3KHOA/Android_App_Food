package com.example.doan_food.MonAn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder>
{
    private ArrayList<MonAn> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_list_food,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn item = data.get(position);

        holder.txtName.setText(item.getTenMonAn());
        holder.txtchuThich.setText(item.getChuThich());
        holder.txtGia.setText(item.getGiaTien());

        Picasso.get().load(item.getLink_image()).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,Xoa_Sua_Activity.class);
                intent.putExtra("food",item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtchuThich,txtGia;
        ImageView image;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.all_menu_image);
            txtName = itemView.findViewById(R.id.lst_tenMonAn);
            txtchuThich = itemView.findViewById(R.id.lst_chuThich);
            txtGia = itemView.findViewById(R.id.lst_Gia);
            layout = itemView.findViewById(R.id.layout_list);
        }
    }

    public MonAnAdapter(Context context,ArrayList<MonAn> items) {
        this.data = items;
        this.context = context;
    }
}