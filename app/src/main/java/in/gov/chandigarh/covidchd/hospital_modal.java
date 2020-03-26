package in.gov.chandigarh.covidchd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class hospital_modal{
    String name, address,contact_num;
    double latitude,longitude;
    //constructor
    public hospital_modal(){}

    //getter and setters press Alt+Insert

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }


    public String getcontact_num() {
        return contact_num;
    }

    public void setContact_no(String contact_num) {
        this.contact_num = contact_num;
    }

    public double getlatitude() {
        return latitude;
    }

    public double getlongitude() {
        return longitude;
    }

}
