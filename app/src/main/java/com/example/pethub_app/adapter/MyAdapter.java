package com.example.pethub_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pethub_app.R;
import com.example.pethub_app.adopt.DetailActivity;
import com.example.pethub_app.adopt.PetData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<PetDataViewHolder>{

    private Context context;
    private List<PetData> myPetList;

    public MyAdapter(Context context, List<PetData> myPetList) {
        this.context = context;
        this.myPetList = myPetList;
    }

    @NonNull
    @Override
    public PetDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pet,viewGroup,false);

        return new PetDataViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PetDataViewHolder petDataViewHolder, int position) {
        Glide.with(context)
                .load(myPetList.get(position).getImage())
                .into(petDataViewHolder.petImage);
        petDataViewHolder.petName.setText(myPetList.get(position).getBreed());
        petDataViewHolder.petPrice.setText(myPetList.get(position).getPrice());
        petDataViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", myPetList.get(petDataViewHolder.getAdapterPosition()).getImage());
                intent.putExtra("Breed", myPetList.get(petDataViewHolder.getAdapterPosition()).getBreed());
                intent.putExtra("Id", myPetList.get(petDataViewHolder.getAdapterPosition()).getId());
                intent.putExtra("MaleHeight", myPetList.get(petDataViewHolder.getAdapterPosition()).getMaleHeight());
                intent.putExtra("FemaleHeight", myPetList.get(petDataViewHolder.getAdapterPosition()).getFemaleHeight());
                intent.putExtra("MaleWeight", myPetList.get(petDataViewHolder.getAdapterPosition()).getMaleWeight());
                intent.putExtra("FemaleWeight", myPetList.get(petDataViewHolder.getAdapterPosition()).getFemaleWeight());
                intent.putExtra("Lifespan", myPetList.get(petDataViewHolder.getAdapterPosition()).getLifespan());
                intent.putExtra("Temperament", myPetList.get(petDataViewHolder.getAdapterPosition()).getTemperament());
                intent.putExtra("Price", myPetList.get(petDataViewHolder.getAdapterPosition()).getPrice());
                intent.putExtra("PetGroup", myPetList.get(petDataViewHolder.getAdapterPosition()).getPetgroup());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPetList.size();
    }
}

class PetDataViewHolder extends RecyclerView.ViewHolder{

    ImageView petImage;
    TextView petName, petPrice;
    CardView mCardView;

    public PetDataViewHolder(View itemView) {
        super(itemView);

        petImage = itemView.findViewById(R.id.petImageView);
        petName = itemView.findViewById(R.id.textViewTitle);
        petPrice = itemView.findViewById(R.id.textPrice);
        mCardView = itemView.findViewById(R.id.petCardView);
    }
}
