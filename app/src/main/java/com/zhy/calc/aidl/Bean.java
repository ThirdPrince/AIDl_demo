package com.zhy.calc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Bean implements Parcelable {
    public int pram1;
    public int pram2;
    public int sum;
    public Bean(){

    }

    protected Bean(Parcel in) {
        pram1 = in.readInt();
        pram2 = in.readInt();
        sum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pram1);
        dest.writeInt(pram2);
        dest.writeInt(sum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bean> CREATOR = new Creator<Bean>() {
        @Override
        public Bean createFromParcel(Parcel in) {
            return new Bean(in);
        }

        @Override
        public Bean[] newArray(int size) {
            return new Bean[size];
        }
    };
}
