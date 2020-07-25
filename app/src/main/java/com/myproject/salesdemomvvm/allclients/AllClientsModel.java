package com.myproject.salesdemomvvm.allclients;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class AllClientsModel implements Parcelable {

    public static String CLIENT = "CLIENT";
    public static String TICKET_NO = "TICKET_NO";
    public static String COMP_NAME = "COMP_NAME";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "by_username")
    private String executive_username;
    @ColumnInfo(name = "by_name")
    private String executive_name;
    @ColumnInfo
    private String ticket_no;
    @ColumnInfo
    private String company_name;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String contact;
    @ColumnInfo
    private String address;
    @ColumnInfo
    private String area;
    @ColumnInfo
    private String city;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private String status;
    @ColumnInfo
    private String next_followup;
    @ColumnInfo
    private String notify_count;

    public AllClientsModel(String ticket_no, String company_name, String name, String contact, String address, String area, String city, String date, String status, String next_followup, String notify_count) {
        this.ticket_no = ticket_no;
        this.company_name = company_name;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.area = area;
        this.city = city;
        this.date = date;
        this.status = status;
        this.next_followup = next_followup;
        this.notify_count = notify_count;
    }


    protected AllClientsModel(Parcel in) {
        ticket_no = in.readString();
        company_name = in.readString();
        name = in.readString();
        contact = in.readString();
        address = in.readString();
        area = in.readString();
        city = in.readString();
        date = in.readString();
        status = in.readString();
        next_followup = in.readString();
        notify_count = in.readString();
    }

    public static final Creator<AllClientsModel> CREATOR = new Creator<AllClientsModel>() {
        @Override
        public AllClientsModel createFromParcel(Parcel in) {
            return new AllClientsModel(in);
        }

        @Override
        public AllClientsModel[] newArray(int size) {
            return new AllClientsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getExecutive_username() {
        return executive_username;
    }

    public String getExecutive_name() {
        return executive_name;
    }

    public String getTicket_no() {
        return ticket_no;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getNext_followup() {
        return next_followup;
    }

    public String getNotify_count() {
        return notify_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ticket_no);
        dest.writeString(company_name);
        dest.writeString(name);
        dest.writeString(contact);
        dest.writeString(address);
        dest.writeString(area);
        dest.writeString(city);
        dest.writeString(date);
        dest.writeString(status);
        dest.writeString(next_followup);
        dest.writeString(notify_count);
    }
}
