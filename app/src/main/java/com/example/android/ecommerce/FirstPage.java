package com.example.android.ecommerce;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.android.ecommerce.classesInfo.UserInfoForDatabase;
import com.firebase.ui.auth.data.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ecommerce.Adapters.CategoryAdapter;
import com.example.android.ecommerce.classesInfo.Category;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent tSignInSignUp;
    Intent Test;
    RecyclerView recyclerView;
    CategoryAdapter adapter;
    FirebaseDatabase database;
    List<Category> categories;
    TextView name;
    FirebaseUser userA = FirebaseAuth.getInstance().getCurrentUser();
    UserInfoForDatabase user=new UserInfoForDatabase();
    CircleImageView profilepic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstPage.this,Mycart.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        tSignInSignUp=new Intent(FirstPage.this,Sign_up_and_Sign_in.class);
        Test=new Intent(this,ProjectDescription.class);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categories=new ArrayList<>();
        profilepic=(CircleImageView)findViewById(R.id.profile_image);
        name=(TextView)findViewById(R.id.user_profile_name);
        DatabaseReference dbCategory=FirebaseDatabase.getInstance().getReference("Categories");

        dbCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Category category=ds.getValue(Category.class);
                    categories.add(category);
                }
                adapter=new CategoryAdapter(FirstPage.this,categories);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

//        DatabaseReference userInfo = FirebaseDatabase.getInstance().getReference();
//        //final UserInfoForDatabase user=new UserInfoForDatabase();
//        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child("Users").child("UserInfo").child(userA.getUid()).exists())
//                {
//                    PutNameAndImage(dataSnapshot);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

    }

//    private void PutNameAndImage(DataSnapshot dataSnapshot) {
//        for(DataSnapshot ds:dataSnapshot.child("Users").getChildren())
//        {
//            user=dataSnapshot.child("Users").child("UserInfo").child(userA.getUid()).getValue(UserInfoForDatabase.class);
//            Picasso.get().load(Uri.parse(user.getPicUri())).into(profilepic);
//            name.setText(user.getName());
//        }
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {

        }

        else if (id == R.id.nav_wishlist)
        {
            startActivity(new Intent(FirstPage.this, Wishlist.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        }
//        else if (id == R.id.nav_notification)
//        {
//
//        }

        else if (id == R.id.nav_My_order)
        {

           Intent order= new Intent(FirstPage.this,MyOrders.class);

        startActivity(order);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        else if (id == R.id.nav_My_cart)
        {
            startActivity(new Intent(FirstPage.this,Mycart.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        else if (id == R.id.nav_logout)
        {
            AuthUI.getInstance().signOut(FirstPage.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(FirstPage.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(FirstPage.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            startActivity(tSignInSignUp);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        else if (id == R.id.nav_help_centre)
        {
            Intent help= new Intent(FirstPage.this,help_centre.class);

            startActivity(help);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
//
        else if (id == R.id.nav_profile)
        {
            startActivity(new Intent(FirstPage.this,ProjectDescription.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
