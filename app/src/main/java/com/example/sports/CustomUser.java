package com.example.sports;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class CustomUser {
    private String uid,displayName, email,phoneNumber;
    private String photoUrl;

    public CustomUser(){}



    public CustomUser(FirebaseUser user) {

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                if (providerId.equals("firebase")){
                    uid = profile.getUid();
                }

                // Name, email address, and profile photo Url
                if (!(profile.getDisplayName() == null || profile.getDisplayName().equals(""))) {
                    displayName = profile.getDisplayName();
                }
                if (!(profile.getEmail() == null || profile.getEmail().equals(""))) {
                    email = profile.getEmail();
                }
                if (!(profile.getPhotoUrl() == null || profile.getPhotoUrl().toString().equals(""))) {
                    photoUrl = profile.getPhotoUrl().toString();
                }

                if (!(profile.getPhoneNumber() == null || profile.getPhoneNumber().equals(""))) {
                    phoneNumber = profile.getPhoneNumber();
                }


                Log.d("Custom User", "CustomUser: " + providerId + ":" + uid);

            }
        }
//        this.uid = user.getUid();
//        this.displayName = user.getDisplayName();
//        this.email = user.getEmail();
//        this.phoneNumber = user.getPhoneNumber();
//        this.photoUrl = user.getPhotoUrl();
    }

//    public Uri getPhotoUrl() {
//        return photoUrl;
//    }
//
//    public void setPhotoUrl(Uri photoUrl) {
//        this.photoUrl = photoUrl;
//    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
