package com.ORS.Online_reservation_System.dto;

import java.util.Date;

public class DateRangeDTO {

    private Date startDate;
    private Date endDate;

    public DateRangeDTO() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
