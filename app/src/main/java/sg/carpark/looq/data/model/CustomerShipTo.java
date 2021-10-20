package sg.carpark.looq.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Created by TED on 04-Aug-20
 */
@Entity(primaryKeys = {"id_customer","id_shipto"})
public class CustomerShipTo {
    @NonNull
    private String id_customer;
    @NonNull
    private String id_shipto;
    private String name;
    private String address;
    private String city;
    private String postal_code;
    private String state;
    private String id_salesman;

    public CustomerShipTo() {
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_shipto() {
        return id_shipto;
    }

    public void setId_shipto(String id_shipto) {
        this.id_shipto = id_shipto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId_salesman() {
        return id_salesman;
    }

    public void setId_salesman(String id_salesman) {
        this.id_salesman = id_salesman;
    }
}
