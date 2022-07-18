package com.example.doan_food.HoaDonDatHang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_food.R;
import com.example.doan_food.User.DatHang;
import com.example.doan_food.User.HoaDon;

import java.util.ArrayList;


public class AdapterDatHang extends RecyclerView.Adapter<AdapterDatHang.ViewHolder>
{
    private ArrayList<DatHang> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_hoadon,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatHang item = data.get(position);

        holder.txtdiachi.setText(item.getDiaChi());
        holder.txtngayDat.setText(item.getNgayDat());
        holder.txtGia.setText(item.getTongTien());
        holder.txtThanhToan.setText(item.getThanhToan());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, lstDatHangActivity.class);
                ArrayList<HoaDon> lst = (ArrayList<HoaDon>)item.getHd();
                intent.putExtra("food",lst);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdiachi,txtngayDat,txtGia,txtThanhToan;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdiachi = itemView.findViewById(R.id.hoadon_diaChi);
            txtngayDat = itemView.findViewById(R.id.hoaDon_Ngay);
            txtGia = itemView.findViewById(R.id.hoaDon_tongTien);
            txtThanhToan = itemView.findViewById(R.id.hoadon_thanhToan);
            layout = itemView.findViewById(R.id.layout_chinh);
        }
    }

    public AdapterDatHang(Context context,ArrayList<DatHang> items) {
        this.data = items;
        this.context = context;
    }
}