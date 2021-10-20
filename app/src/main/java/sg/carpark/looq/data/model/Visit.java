package sg.carpark.looq.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by TED on 03-Sep-20
 */

@Entity
public class Visit {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String id_employee;
    private String id_customer;
    private String id_mobile;
    private String date_mobile;
    private String type;
    private Long check_in_time;
    private Long check_out_time;
    private int duration_time;
    private String reason;
    private String lat_check_in;
    private String long_check_in;
    private String lat_check_out;
    private String long_check_out;
    private String customer_name;
    private String employee_name;

    private String address_1;
    private String address_2;
    private String address_3;
    private String address_4;
    private int one_time;
    private boolean on_progress;
    private String errorCause;
    private boolean newVisit;
    private Long lastSynced;
    private Long lastModified;

    //customer temp
    private String province;
    private String city;
    private String postal_code;
    private String nama_npwp;
    private String alamat_npwp;
    private String npwp;
    private String ktp;
    private String phone;
    private String email;

    public Visit() {
    }

    public Visit(String date){
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_mobile() {
        return id_mobile;
    }

    public void setId_mobile(String id_mobile) {
        this.id_mobile = id_mobile;
    }

    public String getDate_mobile() {
        return date_mobile;
    }

    public void setDate_mobile(String date_mobile) {
        this.date_mobile = date_mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(Long check_in_time) {
        this.check_in_time = check_in_time;
    }

    public Long getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(Long check_out_time) {
        this.check_out_time = check_out_time;
    }

    public int getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(int duration_time) {
        this.duration_time = duration_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLat_check_in() {
        return lat_check_in;
    }

    public void setLat_check_in(String lat_check_in) {
        this.lat_check_in = lat_check_in;
    }

    public String getLong_check_in() {
        return long_check_in;
    }

    public void setLong_check_in(String long_check_in) {
        this.long_check_in = long_check_in;
    }

    public String getLat_check_out() {
        return lat_check_out;
    }

    public void setLat_check_out(String lat_check_out) {
        this.lat_check_out = lat_check_out;
    }

    public String getLong_check_out() {
        return long_check_out;
    }

    public void setLong_check_out(String long_check_out) {
        this.long_check_out = long_check_out;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public int getOne_time() {
        return one_time;
    }

    public void setOne_time(int one_time) {
        this.one_time = one_time;
    }

    public boolean isOn_progress() {
        return on_progress;
    }

    public void setOn_progress(boolean on_progress) {
        this.on_progress = on_progress;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public boolean isNewVisit() {
        return newVisit;
    }

    public void setNewVisit(boolean newVisit) {
        this.newVisit = newVisit;
    }

    public Long getLastSynced() {
        return lastSynced;
    }

    public void setLastSynced(Long lastSynced) {
        this.lastSynced = lastSynced;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getNama_npwp() {
        return nama_npwp;
    }

    public void setNama_npwp(String nama_npwp) {
        this.nama_npwp = nama_npwp;
    }

    public String getAlamat_npwp() {
        return alamat_npwp;
    }

    public void setAlamat_npwp(String alamat_npwp) {
        this.alamat_npwp = alamat_npwp;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
