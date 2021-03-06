package androidboys.com.heavensfoodadmin.Models;

import java.io.Serializable;

public class Absence implements Serializable {

    public String startDate;
    public String endDate;

    public Absence() {
    }

    public Absence(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
