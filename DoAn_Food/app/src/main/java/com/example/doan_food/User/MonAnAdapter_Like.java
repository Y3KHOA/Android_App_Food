package com.example.doan_food.User;

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

import com.example.doan_food.MonAn.MonAn;
import com.example.doan_food.MonAn.MonAnDetails;
import com.example.doan_food.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MonAnAdapter_Like extends RecyclerView.Adapter<MonAnAdapter_Like.ViewHolder>
{
    private ArrayList<MonAn> data;
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Like");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_list_food_like,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn item = data.get(position);

        holder.txtName.setText(item.getTenMonAn());
        holder.txtGia.setText(item.getGiaTien());
        Picasso.get().load(item.getLink_image()).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MonAnDetails.class);
                intent.putExtra("food",item);
                context.startActivity(intent);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = myRef.child(mAuth.getCurrentUser().getUid()).orderByChild("tenMonAn").equalTo(item.getTenMonAn());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren())
                            data.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtGia,btnXoa;
        ImageView image;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnXoa=itemView.findViewById(R.id.btnXoaLike);
            image = itemView.findViewById(R.id.all_menu_image_Like);
            txtName = itemView.findViewById(R.id.lst_tenMonAn_Like);
            txtGia = itemView.findViewById(R.id.lst_Gia_Like);
            layout = itemView.findViewById(R.id.layout_list);
        }
    }

    public MonAnAdapter_Like(Context context,ArrayList<MonAn> items) {
        this.data = items;
        this.context = context;
    }
}