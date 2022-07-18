package com.example.doan_food.MonAn;

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

import com.example.doan_food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MonAnAdapter_IC extends RecyclerView.Adapter<MonAnAdapter_IC.ViewHolder>
{
    private ArrayList<MonAn> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_food,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn item = data.get(position);

        holder.txt_Foodname.setText(item.getTenMonAn());

        Picasso.get().load(item.getLink_image()).into(holder.food_image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MonAnDetails.class);
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
        TextView txt_Foodname;
        ImageView food_image;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            food_image = itemView.findViewById(R.id.img_food);
            txt_Foodname = itemView.findViewById(R.id.txt_Foodname);
            layout = itemView.findViewById(R.id.layout_food);
        }
    }

    public MonAnAdapter_IC(Context context, ArrayList<MonAn> items) {
        this.data = items;
        this.context = context;
    }
}
