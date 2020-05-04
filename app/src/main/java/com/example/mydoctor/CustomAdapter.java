package com.example.mydoctor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList patient_id, name, age, phone,address,date,symptoms,medicine,reaction,comments;

    CustomAdapter(Activity activity, Context context, ArrayList patient_id, ArrayList name, ArrayList age,
                  ArrayList phone,ArrayList address,ArrayList date,ArrayList symptoms ,ArrayList medicine,ArrayList reaction,ArrayList comments){
        this.activity = activity;
        this.context = context;
        this.patient_id = patient_id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.symptoms=symptoms;
        this.address=address;
        this.date=date;
        this.medicine=medicine;
        this.comments=comments;
        this.reaction=reaction;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.patient_id_txt.setText(String.valueOf(patient_id.get(position)));
        holder.name_txt.setText(String.valueOf(name.get(position)));
        holder.age_txt.setText(String.valueOf(age.get(position)));
        holder.phone_txt.setText(String.valueOf(phone.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(patient_id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("age", String.valueOf(age.get(position)));
                intent.putExtra("phone", String.valueOf(phone.get(position)));
                intent.putExtra("address", String.valueOf(address.get(position)));
                intent.putExtra("date",String.valueOf(date.get(position)));
                intent.putExtra("symptoms", String.valueOf(symptoms.get(position)));
                intent.putExtra("medicine", String.valueOf(medicine.get(position)));
                intent.putExtra("reaction", String.valueOf(reaction.get(position)));
                intent.putExtra("comments", String.valueOf(comments.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return patient_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView patient_id_txt, name_txt, age_txt, phone_txt,address_txt,medicine_txt,reaction_txt,comments_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_id_txt = itemView.findViewById(R.id.patient_id_txt);
            name_txt = itemView.findViewById(R.id.name_txt);
            age_txt = itemView.findViewById(R.id.age_txt);
            phone_txt = itemView.findViewById(R.id.phone_txt);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }


}
