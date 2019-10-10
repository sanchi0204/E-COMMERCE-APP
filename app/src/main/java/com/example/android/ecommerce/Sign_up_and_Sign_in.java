package com.example.android.ecommerce;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sign_up_and_Sign_in extends AppCompatActivity {
    Button SignIn,SignUp;
    //Intent tSignUp;
    Intent tSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        RelativeLayout linearLayout = findViewById(R.id.relative_layout_sign_up);
//        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
//        animationDrawable.setEnterFadeDuration(1000);
//        animationDrawable.setExitFadeDuration(2000);
//        animationDrawable.start();
//        SignUp=(Button)findViewById(R.id.button_sign_up);
        SignIn=(Button)findViewById(R.id.button_sign_in);
        super.onCreate(savedInstanceState);
        //SignUp = (Button) findViewById(R.id.button_sign_up);
        SignIn = (Button) findViewById(R.id.button_sign_in);
//        tSignUp=new Intent(Sign_up_and_Sign_in.this,Authentication.class);

        setContentView(R.layout.activity_sign_up_and__sign_in);
        //SignUp=(Button)findViewById(R.id.button_sign_up);
        SignIn = (Button) findViewById(R.id.button_sign_in);
        tSignIn = new Intent(Sign_up_and_Sign_in.this, Authentication.class);
        //tSignUp=new Intent(Sign_up_and_Sign_in.this, FirstPage.class);

    }
//    public void toSignUp(View view)
//    {
//        startActivity(tSignUp);
//
//    }
    public void toSignIn(View view)
    {
       startActivity(tSignIn);

    }
}
