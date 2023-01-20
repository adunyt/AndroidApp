package com.example.victorovdv;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {

    int number;
    int circulation;

    protected Ticket(int number, int circulation){
        this.number = number;
        this.circulation = circulation;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.number);
        dest.writeInt(this.circulation);
    }

    public static final Creator<Ticket> CREATOR=new Parcelable.Creator<Ticket>(){
        @Override
        public Ticket createFromParcel(Parcel in){
            int number = in.readInt();
            int circulation = in.readInt();
            return new Ticket(number, circulation);
        }

        @Override
        public Ticket[]newArray(int size){
            return new Ticket[size];
        }
    };

}