package com.example.samparksuchiapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ContactDetailsModel implements Serializable {
    private int recordId;
    private String memberCode;
    private String contactName;
    private String occupation;
    private String address;
    private String city;
    private String pinCode;
    private String state;
    private String phoneNumber;
    private String phonenNumber1;
    private String emailId;
    private String birthDate;
    private String anniversaryDate;
    private String photoUrl;
    public int dd;
    public int bMonth;
    public int AMonth;
    public int aDate;
    private String BMonthDate;
    private String AMonthDate;

    public String getBMonthDate() {
        return BMonthDate;
    }

    public void setBMonthDate(String BMonthDate) {
        this.BMonthDate = BMonthDate;
    }

    public String getAMonthDate() {
        return AMonthDate;
    }

    public void setAMonthDate(String AMonthDate) {
        this.AMonthDate = AMonthDate;
    }
    public int getbMonth() {
        return bMonth;
    }

    public void setbMonth(int bMonth) {
        this.bMonth = bMonth;
    }

    public int getAMonth() {
        return AMonth;
    }

    public void setAMonth(int AMonth) {
        this.AMonth = AMonth;
    }

    public int getaDate() {
        return aDate;
    }

    public void setaDate(int aDate) {
        this.aDate = aDate;
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }


    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhonenNumber1() {
        return phonenNumber1;
    }

    public void setPhonenNumber1(String phonenNumber1) {
        this.phonenNumber1 = phonenNumber1;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ContactDetailsModel{" +
                "recordId=" + recordId +
                ", memberCode='" + memberCode + '\'' +
                ", contactName='" + contactName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", state='" + state + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phonenNumber1='" + phonenNumber1 + '\'' +
                ", emailId='" + emailId + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", anniversaryDate='" + anniversaryDate + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", dd=" + dd +
                ", bMonth=" + bMonth +
                ", AMonth=" + AMonth +
                ", aDate=" + aDate +
                '}';
    }
}
