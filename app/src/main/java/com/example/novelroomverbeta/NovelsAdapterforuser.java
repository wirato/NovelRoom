package com.example.novelroomverbeta;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class NovelsAdapterforuser extends RecyclerView.Adapter<NovelsAdapterforuser.ViewHolder>{
    private static final String TAG = "NovelsAdapter";
    private List<DataBook> foods;
    private ValueEventListener valueEventListener;

    private DatabaseReference mDatabase;

    private Context mContext;

    public NovelsAdapterforuser(List<DataBook> foods, Context context) {
        this.foods = foods;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_novel_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override

    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        DataBook upload = foods.get(position);
        holder.textViewName.setText(upload.getNovelname());
        holder.textViewPrice.setText("ผู้แต่ง : "+upload.getUsername());
        Glide.with(mContext).load(upload.getUrl()).into(holder.imageView);
        final DataBook dataBook = new  DataBook(upload.getNovelname(),upload.getTextdata(),upload.getUsername(),upload.getUrl());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NovelEpAll.class);
                Toast toast = Toast.makeText(mContext,dataBook.getNovelname(),Toast.LENGTH_SHORT);
                toast.show();
                intent.putExtra("Novel", dataBook);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    /*
       INNER class
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewPrice;
        public ImageView imageView;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewPrice = (TextView) itemView.findViewById(R.id.price);
            imageView = (ImageView) itemView.findViewById(R.id.icon);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}