package com.example.lab9memoriestracker.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class MemoryModel implements Parcelable {
    int MemoryID;
    String Title, Description, Location, MemoryDate;
    byte[] image;

    public MemoryModel(int memoryID, String title, String description, String location, String memoryDate, byte[] image) {
        MemoryID = memoryID;
        Title = title;
        Description = description;
        Location = location;
        MemoryDate = memoryDate;
        this.image = image;
    }

    public int getMemoryID() {
        return MemoryID;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getLocation() {
        return Location;
    }

    public String getMemoryDate() {
        return MemoryDate;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.MemoryID);
        dest.writeString(this.Title);
        dest.writeString(this.Description);
        dest.writeString(this.Location);
        dest.writeString(this.MemoryDate);
        dest.writeByteArray(this.image);
    }

    protected MemoryModel(Parcel in) {
        this.MemoryID = in.readInt();
        this.Title = in.readString();
        this.Description = in.readString();
        this.Location = in.readString();
        this.MemoryDate = in.readString();
        this.image = in.createByteArray();
    }

    public static final Creator<MemoryModel> CREATOR = new Creator<MemoryModel>() {
        @Override
        public MemoryModel createFromParcel(Parcel source) {
            return new MemoryModel(source);
        }

        @Override
        public MemoryModel[] newArray(int size) {
            return new MemoryModel[size];
        }
    };
}
