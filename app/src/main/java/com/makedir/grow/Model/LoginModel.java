package com.makedir.grow.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public class Data {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("fcm")
        @Expose
        private String fcm;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("withdraw")
        @Expose
        private String wallet;
        @SerializedName("verified")
        @Expose
        private String verified;
        @SerializedName("isPrime")
        @Expose
        private String isPrime;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("invite_code")
        @Expose
        private String inviteCode;
        @SerializedName("friend_invite_code")
        @Expose
        private String friendInviteCode;
        @SerializedName("current")
        @Expose
        private String current;
        @SerializedName("remaining")
        @Expose
        private String remaining;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("ratio")
        @Expose
        private String ratio;
        @SerializedName("bankAdded")
        @Expose
        private String bankAdded;
        @SerializedName("with_password")
        @Expose
        private String with_password;

        public String getBankAdded() {
            return bankAdded;
        }

        public String getWith_password() {
            return with_password;
        }

        public void setWith_password(String with_password) {
            this.with_password = with_password;
        }

        public void setBankAdded(String bankAdded) {
            this.bankAdded = bankAdded;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFcm() {
            return fcm;
        }

        public void setFcm(String fcm) {
            this.fcm = fcm;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getIsPrime() {
            return isPrime;
        }

        public void setIsPrime(String isPrime) {
            this.isPrime = isPrime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getFriendInviteCode() {
            return friendInviteCode;
        }

        public void setFriendInviteCode(String friendInviteCode) {
            this.friendInviteCode = friendInviteCode;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }
    }
}
