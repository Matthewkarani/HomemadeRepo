package com.example.homemade;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homemade.profile.BakerProfile;
import com.example.homemade.location.MapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore fstore;
    private FirebaseAuth fAuth;
    String UserID;


    @SuppressLint("SoonBlockedPrivateApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.edit_text_email1);
        editTextPassword = findViewById(R.id.edit_text_password1);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        //get user info


    }




    public void Login_baker(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);
                            VerifyUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void VerifyUser() {

        UserID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fstore.collection("Profile Data").document(UserID);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    String Occupation  = document.getString("Occupation");
                    if (!(Occupation != null ? Occupation.equals("Baker") : false)){
                        sendtoCustomerMain();

                    }else{
                        sendtoBakerMain();
                    }

                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }

    private void sendtoBakerMain() {
        Intent intent = new Intent(LoginActivity.this, BakerProfile.class);
        startActivity(intent);
    }

    private void sendtoCustomerMain() {
        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
        startActivity(intent);
    }


    private void updateUI(FirebaseUser user) {

    }

    public void Forgot_Password(View view) {
        Intent intent = new Intent(LoginActivity.this, BakerProfile.class);
        startActivity(intent);


    }
}

//future works login with mobile number