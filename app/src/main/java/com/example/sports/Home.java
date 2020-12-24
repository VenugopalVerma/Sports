package com.example.sports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {

//    private TabLayout tabLayout;
    private static final int RC_SIGN_IN = 123;
    private long pressedTime;
    public static  FirebaseAuth mAuth;
    public static  FirebaseFirestore db;
    public  static FirebaseStorage storage;
    public static Uri temp;
    public FirebaseAuth.AuthStateListener mAuthStateListener;
    public static Bitmap bmp;
    public static NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
//        StorageReference pathReference = storageRef.child("Groundimages/bad.jpg");
//        StorageReference gsReference = storage.getReferenceFromUrl("gs://fir-rtc-4c5df.appspot.com/Groundimages/bad.jpg");

        storageRef.child("Groundimages/bad.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                temp = uri;
                Log.d("Image uri", "onSuccess: " + uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Image uri", "onFailure: ", e);
            }
        });

//        storageRef.child("Groundimages/bad.png").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Log.e("Image uri", "onFailure: ", exception);
//            }
//        });

        Calendar calendar = Calendar.getInstance();
        Log.d("TAG", "onCreate: " + calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, Locale.US));


        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUi();
            }
        };

        mAuth.getAccessToken(true).addOnSuccessListener(getTokenResult -> Log.d("Token", "onCreate: " + getTokenResult.getToken()));
        setBottomNav();
//        updateUi();

    }


    public void setBottomNav(){
        BottomNavigationView  bottomNavigationView= findViewById(R.id.bottomNavigationView);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.fragment);
//        assert navHostFragment != null : "nav host is null";
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    public void updateUi(){
        Log.d("Auth state change", "updateUi: Home Activity");
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null){
            showDialog();
        }
    }

    public void showDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Sign in to continue").setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createSignInIntent();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
//                new AuthUI.IdpConfig.TwitterBuilder().build()
        );

        // Create and launch sign-in intent

        this.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [END auth_fui_result]

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("Sign Out","Signed Out successful");
                    }
                });
        // [END auth_fui_signout]
        Toast.makeText(this,"User signed out",Toast.LENGTH_LONG).show();

    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {
            if (requestCode == RC_SIGN_IN) {
                // Successfully signed in
                IdpResponse response = IdpResponse.fromResultIntent(data);
                Log.d("Response Data", response.toString());

                if (resultCode == RESULT_OK) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (response.isNewUser()) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        CustomUser customUser = new CustomUser(user);
                        db.collection("users").add(customUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("User", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("User", "Error adding document", e);
                            }
                        });
                    }

//                    user = FirebaseAuth.getInstance().getCurrentUser();

                    Toast.makeText(this, "Signing In Success!",
                            Toast.LENGTH_LONG).show();
                    // ...
                } else {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                    // ...

                    Toast.makeText(this, "Error Signing In! Please SignIn again.",
                            Toast.LENGTH_LONG).show();

                    Log.d("Error in Signin", "Some error in sign in method"+ response.getError().getMessage());
                }
            }
        }
        else {
            Toast.makeText(this,"Sign In request Cancled",Toast.LENGTH_LONG).show();
        }
    }

//    public void onBackPressed() {
//
//        if (Navigation.findNavController(this,R.id.nav_host_fragment_container).getCurrentDestination().getId() == R.id.homeFragment);{
//            if (pressedTime + 2000 > System.currentTimeMillis()) {
//                super.onBackPressed();
//                finish();
//            } else {
//                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//            }
//            pressedTime = System.currentTimeMillis();
//        }
//        super.onBackPressed();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null){
            if (mAuth != null){
                mAuth.removeAuthStateListener(mAuthStateListener);
            }
        }
    }
}