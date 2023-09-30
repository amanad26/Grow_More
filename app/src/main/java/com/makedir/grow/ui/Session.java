package com.makedir.grow.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class Session extends Object {
    private static final String TAG = Session.class.getSimpleName();
    private static final String PREF_NAME = "Rapidine_pref2";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String FormStatus = "formstatus";
    private static final String Mobile = "mobile";
    private static final String Email = "email";
    private static final String UserId = "user_id";
    private static final String User_name = "user_name";
    private static final String role_ = "user_role";
    private static final String FireBaseToken = "fcmid";
    private static final String HOME_LAT = "home_lat";
    private static final String HOME_LONG = "home_long";
    private static final String ChatName = "chat_name";
    private static final String ChatImage = "chat_image";
    private static final String Quizsts = "quizsts";
    private static final String TodaysMood = "todaysmood";
    private static final String TodaysMoodTime = "todaysmoodtime";
    private static final String TodaysRewardTime = "todaysrewardtime";
    private static final String Membership = "membership";
    private static final String SuperLikeCount = "super_like_count";
    private static final String SWIPE = "swipe";
    private static final String CHATCOUNT = "swipe";
    private static final String BOOSTEDTIME = "boostedtime";
    private static final String BlockedUser = "BlockedUser";
    private static final String Distance = "distance";
    private static final String Age_rangeMin = "age_range_min";
    private static final String Age_rangeMax = "age_range_max";
    private static final String my_preferance = "my_preferance";
    private static final String passsword = "passsword";
    private static final String isVerified = "isVerified";
    private static final String isPrime = "isPrime";
    private static final String rememeberPass = "rememeberPass";
    private static final String rememeberEmail = "rememeberEmail";
    private static final String rememberPhone = "rememberPhone";
    private static final String current_amount = "current_amount";
    private static final String remainig_amount = "remainig_amount";
    private static final String userImage = "userImage";
    private static final String type = "type";
    private static final String myInviteCode = "myInviteCode";
    private static final String myWallet = "wallet";
    private static final String isWithdrable = "isWithdrable";
    private static final String isPlayStore = "isPlayStore";
    private static final String checkIn = "checkIn";
    private static final String withdrawcheckIn = "withdrawcheckIn";
    private static final String withdrawPssword = "withdrawPssword";
    private static final String accountNumber = "accountNumber";

    private Context _context;
    public SharedPreferences Rapidine_pref;
    private SharedPreferences.Editor editor;
    private String Passsword;

    public Session(Context context) {
        this._context = context;
        Rapidine_pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = Rapidine_pref.edit();
        editor.apply();
    }


    public void setMobile(String mobile) {
        editor.putString(Mobile, mobile);
        editor.apply();
        editor.commit();
    }


    public void setEmail(String email) {
        editor.putString(Email, email);
        editor.apply();
        editor.commit();
    }

    public void setCurrent_amount(String email) {
        editor.putString(current_amount, email);
        editor.apply();
        editor.commit();
    }

    public void setCheckIn(String email) {
        editor.putString(checkIn, email);
        editor.apply();
        editor.commit();
    }

    public void setWithdrawcheckIn(String email) {
        editor.putString(withdrawcheckIn, email);
        editor.apply();
        editor.commit();
    }

    public void setAccountNumber(String email) {
        editor.putString(accountNumber, email);
        editor.apply();
        editor.commit();
    }

    public void setRemainig_amount(String email) {
        editor.putString(remainig_amount, email);
        editor.apply();
        editor.commit();
    }

    public void setWithdrawPsswordh(String email) {
        editor.putString(withdrawPssword, email);
        editor.apply();
        editor.commit();
    }

    public void setIsPlayStore(boolean email) {
        editor.putBoolean(isPlayStore, email);
        editor.apply();
        editor.commit();
    }

    public void setType(String email) {
        editor.putString(type, email);
        editor.apply();
        editor.commit();
    }


    public void setRememeberPass(String email) {
        editor.putString(rememeberPass, email);
        editor.apply();
        editor.commit();
    }

    public void setRememberEmail(String email) {
        editor.putString(rememeberEmail, email);
        editor.apply();
        editor.commit();
    }

    public void setIsWithdrable(boolean email) {
        editor.putBoolean(isWithdrable, email);
        editor.apply();
        editor.commit();
    }

    public void setRememberPhone(String email) {
        editor.putString(rememberPhone, email);
        editor.apply();
        editor.commit();
    }

    public void setMyWallet(String email) {
        editor.putString(myWallet, email);
        editor.apply();
        editor.commit();
    }

    public void setIsVerified(boolean email) {
        editor.putBoolean(isVerified, email);
        editor.apply();
        editor.commit();
    }

    public void setIsPrime(boolean email) {
        editor.putBoolean(isPrime, email);
        editor.apply();
        editor.commit();
    }

    public void setUserImage(String email) {
        editor.putString(userImage, email);
        editor.apply();
        editor.commit();
    }

    public void setMyInviteCode(String email) {
        editor.putString(myInviteCode, email);
        editor.apply();
        editor.commit();
    }

    public boolean getIsVerified() {
        return Rapidine_pref.getBoolean(isVerified, false);
    }

    public boolean getIsPlayStore() {
        return Rapidine_pref.getBoolean(isPlayStore, false);
    }

    public boolean getIsWithdrable() {
        return Rapidine_pref.getBoolean(isWithdrable, false);
    }

    public String getType() {
        return Rapidine_pref.getString(type, "");
    }
    public String getWithdrawcheckIn() {
        return Rapidine_pref.getString(withdrawcheckIn, "");
    }

    public String getCheckIn() {
        return Rapidine_pref.getString(checkIn, "");
    }

    public String getMyWallet() {
        return Rapidine_pref.getString(myWallet, "");
    }

    public String getUserImage() {
        return Rapidine_pref.getString(userImage, "");
    }

    public String getRemainig_amount() {
        return Rapidine_pref.getString(remainig_amount, "0");
    }

    public String getMyInviteCode() {
        return Rapidine_pref.getString(myInviteCode, "");
    }

    public String getAccountNumber() {
        return Rapidine_pref.getString(accountNumber, "");
    }

    public String getWithdrawPssword() {
        return Rapidine_pref.getString(withdrawPssword, "");
    }

    public String getCurrent_amount() {
        return Rapidine_pref.getString(current_amount, "0");
    }

    public boolean getIsPrime() {
        return Rapidine_pref.getBoolean(isPrime, false);
    }

    public void setMy_preferance(String pref) {
        editor.putString(my_preferance, pref);
        editor.commit();
        editor.apply();
    }


    public String getMy_preferance() {
        return Rapidine_pref.getString(my_preferance, "");
    }

    public String getRememberEmail() {
        return Rapidine_pref.getString(rememeberEmail, "");
    }

    public String getRememberPhone() {
        return Rapidine_pref.getString(rememberPhone, "");
    }


    public String getRememeberPass() {
        return Rapidine_pref.getString(rememeberPass, "");
    }

    public String getMobile() {
        return Rapidine_pref.getString(Mobile, "");

    }


    public String getUser_name() {
        return Rapidine_pref.getString(User_name, "");

    }

    public void setUser_name(String user_name) {
        editor.putString(User_name, user_name);
        editor.commit();
        editor.apply();
    }

    public String getMembership() {
        return Rapidine_pref.getString(Membership, "");

    }

    public void setMembership(String membership) {
        editor.putString(Membership, membership);
        editor.commit();
        this.editor.apply();
    }

    public int getSuperLikeCount() {
        return Rapidine_pref.getInt(SuperLikeCount, 0);
    }

    public void setSuperLikeCount(int membership) {
        editor.putInt(SuperLikeCount, membership);
        editor.commit();
        this.editor.apply();
    }

    public int getSWIPECount() {
        return Rapidine_pref.getInt(SWIPE, 0);

    }

    public void setSWIPECount(int membership) {
        editor.putInt(SWIPE, membership);
        editor.commit();
        this.editor.apply();
    }


    public int getCHATCOUNT() {
        return Rapidine_pref.getInt(CHATCOUNT, 0);

    }

    public void setCHATCOUNT(int membership) {
        editor.putInt(CHATCOUNT, membership);
        this.editor.apply();
    }

    public String getBOOSTEDTIME() {
        return Rapidine_pref.getString(BOOSTEDTIME, "0");

    }

    public void setBOOSTEDTIME(String membership) {
        editor.putString(BOOSTEDTIME, membership);
        this.editor.apply();
    }

    public String getChatName() {
        return Rapidine_pref.getString(ChatName, "");

    }

    public void setChatName(String user_name) {
        editor.putString(ChatName, user_name);
        this.editor.apply();
    }

    public String getChatImage() {
        return Rapidine_pref.getString(ChatImage, "");

    }

    public void setChatImage(String user_name) {
        editor.putString(ChatImage, user_name);
        this.editor.apply();
    }

    public String get_role() {
        return Rapidine_pref.getString(role_, "");
    }

    public void set_role(String role) {
        editor.putString(role_, role);
        editor.apply();
        editor.commit();
    }

    public String get_FormStatus() {
        return Rapidine_pref.getString(FormStatus, "");
    }

    public void set_FormStatus(String fm) {
        editor.putString(FormStatus, fm);
        editor.apply();
        editor.commit();
    }

    public String getUserId() {
        return Rapidine_pref.getString(UserId, "");
    }

    public void setUserId(String userId) {
        editor.putString(UserId, userId);
        this.editor.apply();
    }

    public String getQuizsts() {
        return Rapidine_pref.getString(Quizsts, "");
    }

    public void setQuizsts(String qe) {
        editor.putString(Quizsts, qe);
        this.editor.apply();
    }


    public String getBlockedUser() {
        return Rapidine_pref.getString(BlockedUser, "");
    }

    public void setBlockedUser(String qe) {
        editor.putString(BlockedUser, qe);
        this.editor.apply();
    }

    public String getDistance() {
        return Rapidine_pref.getString(Distance, "0");
    }

    public void setDistance(String qe) {
        editor.putString(Distance, qe);
        this.editor.apply();
    }

    public String getAge_rangeMin() {
        return Rapidine_pref.getString(Age_rangeMin, "0");
    }

    public void setAge_rangeMin(String qe) {
        editor.putString(Age_rangeMin, qe);
        this.editor.apply();
    }

    public String getAge_rangeMax() {
        return Rapidine_pref.getString(Age_rangeMax, "0");
    }

    public void setAge_rangeMax(String qe) {
        editor.putString(Age_rangeMax, qe);
        this.editor.apply();
    }

    public String getTodaysMood() {
        return Rapidine_pref.getString(TodaysMood, "");
    }

    public void setTodaysMood(String tmd) {
        editor.putString(TodaysMood, tmd);
        this.editor.apply();
    }


    public String getTodaysRewardTime() {
        return Rapidine_pref.getString(TodaysRewardTime, "");
    }

    public void setTodaysRewardTime(String tm) {
        editor.putString(TodaysRewardTime, tm);
        this.editor.apply();
    }

    public String getTodaysMoodTime() {
        return Rapidine_pref.getString(TodaysMoodTime, "");
    }

    public void setTodaysMoodTime(String tm) {
        editor.putString(TodaysMoodTime, tm);
        this.editor.apply();
    }

    // HOME_LAT
    //   HOME_LONG
    public String getHOME_LAT() {
        return Rapidine_pref.getString(HOME_LAT, "");
    }

    public void setHOME_LAT(String userId) {
        editor.putString(HOME_LAT, userId);
        this.editor.apply();
    }

    public String getHOME_LONG() {
        return Rapidine_pref.getString(HOME_LONG, "");
    }

    public void setHOME_LONG(String userId) {
        editor.putString(HOME_LONG, userId);
        this.editor.apply();
    }

    public String getEmail() {
        return Rapidine_pref.getString(Email, "");
    }


    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();
        editor.apply();
    }

    public void setPassword(String passsword) {

        editor.putString(Passsword, passsword);

        editor.apply();
        editor.commit();
    }


    public boolean isLoggedIn() {
        return Rapidine_pref.getBoolean(IS_LOGGEDIN, false);
    }

    public boolean isOnline() {
        return Rapidine_pref.getBoolean(IS_LOGGEDIN, false);
    }

    public String getFireBaseToken() {
        return Rapidine_pref.getString(FireBaseToken, "");
    }

    public void setFireBaseToken(String fcmid) {
        editor.putString(FireBaseToken, fcmid);
        this.editor.apply();
    }

}