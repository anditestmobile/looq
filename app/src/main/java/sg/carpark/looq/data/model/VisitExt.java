package sg.carpark.looq.data.model;

import androidx.room.Embedded;

/**
 * Created by TED on 01-Oct-20
 */
public class VisitExt {
    @Embedded
    private Visit visit;

    private String customerName;
    private String customerAddress;
    private Integer totalVisitPlan;

    public VisitExt() {
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Integer getTotalVisitPlan() {
        return totalVisitPlan;
    }

    public void setTotalVisitPlan(Integer totalVisitPlan) {
        this.totalVisitPlan = totalVisitPlan;
    }
}
