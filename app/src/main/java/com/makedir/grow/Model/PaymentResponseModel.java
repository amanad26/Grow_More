package com.makedir.grow.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponseModel {
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("mtx")
    @Expose
    private String mtx;
    @SerializedName("attempts")
    @Expose
    private Integer attempts;
    @SerializedName("sub_accounts_id")
    @Expose
    private Object subAccountsId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("customer")
    @Expose
    private Customer customer;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMtx() {
        return mtx;
    }

    public void setMtx(String mtx) {
        this.mtx = mtx;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Object getSubAccountsId() {
        return subAccountsId;
    }

    public void setSubAccountsId(Object subAccountsId) {
        this.subAccountsId = subAccountsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public class Customer {

        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("email_id")
        @Expose
        private String emailId;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("entity")
        @Expose
        private String entity;

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEntity() {
            return entity;
        }

        public void setEntity(String entity) {
            this.entity = entity;
        }

    }
}
