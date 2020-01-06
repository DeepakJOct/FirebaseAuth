package app.com.firebaseauth.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.com.firebaseauth.LoginActivity;
import app.com.firebaseauth.ProfileActivity;
import app.com.firebaseauth.listeners.FirebaseListener;

public class FirebaseOperations {

    private static FirebaseOperations ourInstance = new FirebaseOperations();

    private static FirebaseAuth.AuthStateListener authListener;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser user;

    public static FirebaseOperations getInstance() {
        return ourInstance;
    }

    public void getUserDetails(Context context, FirebaseListener firebaselistener) {

        firebaseAuth = firebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {
            ((AppCompatActivity) context).finish();
        }
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        };

        FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    firebaselistener.onFirebaseResult(user, true);
                }
            }
        };
        listener.onAuthStateChanged(firebaseAuth);
    }

    public void signOut(Context context) {
        firebaseAuth.signOut();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    Toast.makeText(context, "Signed Out", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        };
        authStateListener.onAuthStateChanged(firebaseAuth);
    }

}
