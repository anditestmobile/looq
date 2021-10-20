package sg.carpark.looq.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by TED on 04-Aug-20
 */
@Entity
public class Customer {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String address_1;
    private String address_2;
    private String address_3;
    private String address_4;

    @Ignore
    private ArrayList<CustomerShipTo> custShipTo;

    public Customer() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public ArrayList<CustomerShipTo> getCustShipTo() {
        return custShipTo;
    }

    public void setCustShipTo(ArrayList<CustomerShipTo> custShipTo) {
        this.custShipTo = custShipTo;
    }


    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getAddress_3() {
        return address_3;
    }

    public void setAddress_3(String address_3) {
        this.address_3 = address_3;
    }

    public String getAddress_4() {
        return address_4;
    }

    public void setAddress_4(String address_4) {
        this.address_4 = address_4;
    }
}
