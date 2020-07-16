package com.example.pethub_app.adopt;

public class PetData {
    public String getId() {
        return id;
    }

    private String id, breed, maleHeight, femaleHeight, maleWeight, femaleWeight, lifespan, temperament, petgroup, price, image;

    public PetData(){

    }

    public PetData(String id, String breed, String maleHeight, String femaleHeight, String maleWeight, String femaleWeight,
    String lifespan, String temperament, String petgroup, String price, String image){
        this.id=id;
        this.breed=breed;
        this.maleHeight=maleHeight;
        this.femaleHeight=femaleHeight;
        this.maleWeight=maleWeight;
        this.femaleWeight=femaleWeight;
        this.lifespan=lifespan;
        this.temperament=temperament;
        this.petgroup=petgroup;
        this.price=price;
        this.image=image;
    }


    public String getBreed() {
        return breed;
    }

    public String getMaleHeight() {
        return maleHeight;
    }

    public String getFemaleHeight() {
        return femaleHeight;
    }

    public String getMaleWeight() {
        return maleWeight;
    }

    public String getFemaleWeight() {
        return femaleWeight;
    }

    public String getLifespan() {
        return lifespan;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getPetgroup() {
        return petgroup;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
