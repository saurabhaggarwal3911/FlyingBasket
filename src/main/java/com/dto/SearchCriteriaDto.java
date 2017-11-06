package com.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchCriteriaDto {
    private int pageNum;
    private int noOfRecord;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNoOfRecord() {
        return noOfRecord;
    }

    public void setNoOfRecord(int noOfRecord) {
        this.noOfRecord = noOfRecord;
    }

}
