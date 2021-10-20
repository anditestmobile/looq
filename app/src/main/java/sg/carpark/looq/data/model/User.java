package sg.carpark.looq.data.model;

import java.io.Serializable;

/**
 * Created by TED on 29-Nov-20
 */
public class User implements Serializable {
    private String name;
    private String gender;
    private String[] partner_id;
    private String id;
    private String email;
    private String phone;
    private String image_1920;
    private String[] company_id;


    public User() {
    }

    public User(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String[] partner_id) {
        this.partner_id = partner_id;
    }

    public String[] getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String[] company_id) {
        this.company_id = company_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_1920() {
        return image_1920;
    }

    public void setImage_1920(String image_1920) {
        this.image_1920 = image_1920;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
