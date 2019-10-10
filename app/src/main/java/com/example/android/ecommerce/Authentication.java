package com.example.android.ecommerce;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.List;

public class Authentication extends AppCompatActivity {

    Intent goToFirstPage;
    FirebaseDatabase userInfo=FirebaseDatabase.getInstance();
    DatabaseReference ref=userInfo.getReference();

    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



        setContentView(R.layout.activity_main);
        goToFirstPage=new Intent(Authentication.this,FirstPage.class);

        setContentView(R.layout.activity_main);
        goToFirstPage=new Intent(Authentication.this,FirstPage.class);


        //Init provider
        providers= Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );


        showSignInOptions();

    }


    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );
    }


    private void startActivityForResult() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MY_REQUEST_CODE)
        {
            IdpResponse response=IdpResponse.fromResultIntent(data);
            if(resultCode== RESULT_OK)
            {
                //Get User
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                //show email on toast
                Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
                Toast.makeText(this, ""+user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                //ref.child(user.getUid()).setValue(user.getEmail());
                //ref.child(user.getUid()).setValue(user.getPhoneNumber());
                GoToFirstPage();
            }
            else{
                Toast.makeText(this,""+response.getError().getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GoToFirstPage() {
        startActivity(goToFirstPage);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
