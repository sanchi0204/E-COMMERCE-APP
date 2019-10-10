package com.example.android.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ecommerce.classesInfo.UserInfoForDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class ProjectDescription extends AppCompatActivity {
    //ImageView iconCart;
    private TextView ProductBrand, ProductName, ProductPrice, ProductMRP, ProductDiscount;
    private TextView WishList, Cart;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, editText12;

    private TextInputLayout textInput1, textInput2, textInput3, textInput4, textInput5, textInput6, textInput7, textInput8, textInput9, textInput10, textInput11, textInput12;
    private Button save;
    private LinearLayout profilePicLayout;
    private ImageView profilePic;
    private StorageReference profilePicRef;
    private FirebaseDatabase userInfo=FirebaseDatabase.getInstance();
    private DatabaseReference Mainref=userInfo.getReference();
    private DatabaseReference Existing_value=userInfo.getReference();

    private FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog loadingBar;
    private HashMap<String,Object>userInfomap=new HashMap<>();
    private UserInfoForDatabase userClass;
    private String name;
    private Uri imageUri;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String key;
    private String downloadImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_description);
      //  iconCart = findViewById(R.id.icon_cart);



        save = (Button) findViewById(R.id.Save_Button);

        editText1 = findViewById(R.id.temp_user_name);
        editText2 = findViewById(R.id.temp_user_mobile);
        editText3 = findViewById(R.id.temp_user_address);
        editText4 = findViewById(R.id.temp_user_city);
        editText5 = findViewById(R.id.temp_user_state);
        editText6 = findViewById(R.id.temp_user_email);
        editText7 = findViewById(R.id.temp_user_website_url);
        editText8=findViewById(R.id.temp_user_instagram_url);
        editText9=findViewById(R.id.temp_user_facebook_url);
        editText10 = findViewById(R.id.temp_user_linked_in_url);
        editText11 = findViewById(R.id.temp_user_designation);
        editText12 = findViewById(R.id.temp_user_work_type);

        textInput1 = findViewById(R.id.text_layout_name);
        textInput2 = findViewById(R.id.text_layout_mobile);
        textInput3 = findViewById(R.id.text_layout_address);
        textInput4 = findViewById(R.id.text_layout_city);
        textInput5 = findViewById(R.id.text_layout_state);
        textInput6 = findViewById(R.id.text_layout_email);
        textInput7 = findViewById(R.id.text_layout_website_url);
        textInput8=findViewById(R.id.text_layout_instagram_url);
        textInput9=findViewById(R.id.text_layout_facebook_url);
        textInput10 = findViewById(R.id.text_layout_linked_in_url);
        textInput11 = findViewById(R.id.text_layout_designation);
        textInput12 = findViewById(R.id.text_layout_work_type);

        loadingBar=new ProgressDialog(ProjectDescription.this);
        userClass=new UserInfoForDatabase();
        profilePicRef= FirebaseStorage.getInstance().getReference().child("userPicture");
        loadingBar.setTitle("Checking for previous Data");
        loadingBar.setMessage("Plaese Wait..");
        loadingBar.show();
        profilePicLayout=(LinearLayout)findViewById(R.id.profile_pic_layout);
        profilePic=(ImageView)findViewById(R.id.profile_pic);


        Mainref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if((dataSnapshot.child("Users").child("UserInfo").child(user.getUid()).exists()))
               {
                   putValuesInEditText(dataSnapshot);
               }
               else
               {
                   Toast.makeText(ProjectDescription.this, "No Existing Data Found", Toast.LENGTH_SHORT).show();
                   loadingBar.dismiss();
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifAnyDetailIsEmpty();
                userInfomap.clear();
            }
        });

        profilePicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent tGallery=new Intent();
        tGallery.setAction(Intent.ACTION_GET_CONTENT);
        tGallery.setType("image/*");
        startActivityForResult(tGallery,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK && data!=null);
        {
            assert data != null;
            imageUri=data.getData();
            profilePic.setImageURI(imageUri);

        }
    }

    private void ifAnyDetailIsEmpty() {
        int Name = editText1.getText().toString().trim().length();
        int phone = editText2.getText().toString().trim().length();
        int address = editText3.getText().toString().trim().length();
        int city = editText4.getText().toString().trim().length();
        int state = editText5.getText().toString().trim().length();
        int email = editText6.getText().toString().trim().length();
        int designation = editText11.getText().toString().trim().length();
        int work_type = editText12.getText().toString().trim().length();

        int flag = 0;

            if (Name == 0 || phone == 0 || address == 0 ||city==0||state==0|| email == 0 || designation == 0 || work_type == 0 || imageUri==null) {
                if (Name == 0) {
                    textInput1.setError("This field needs to be filled");

                }
                if (phone==0) {


                    textInput2.setError("This field needs to be filled");
                }
                if (address == 0)
                {

                    textInput3.setError("This field needs to be filled");
                }

                if (city == 0) {

                    textInput4.setError("This field needs to be filled");
                }

                if (state == 0) {

                    textInput5.setError("This field needs to be filled");
                }
                if (email == 0) {

                    textInput6.setError("This field needs to be filled");
                }
                if (designation == 0) {

                    textInput11.setError("This field needs to be filled");
                }
                if (work_type == 0) {

                    textInput12.setError("This field needs to be filled");
                }
                if (imageUri==null) {

                    Toast.makeText(this, "You need to add your Picture", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                createPicKey();
                loadingBar.setTitle("Saving data");
                loadingBar.setMessage("Please Wait..");
                loadingBar.show();

            }


     }

    private void createPicKey() {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,YYYY");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        key=saveCurrentDate + saveCurrentTime;

        final StorageReference filePath= profilePicRef.child(imageUri.getLastPathSegment()+key+".jpg");
        final UploadTask uploadTask= filePath.putFile(imageUri);
        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadImageUri=uri.toString().trim();
                        Toast.makeText(ProjectDescription.this,downloadImageUri, Toast.LENGTH_SHORT).show();
                        saveDataToDataBase();
                    }
                });

            }
        });

//        Toast.makeText(this, filePath.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();

//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ProjectDescription.this, "Error in uploading Pic"+ e, Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> UriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                        if(!task.isSuccessful())
//                        {
//                            throw Objects.requireNonNull(task.getException());
//                        }
//                        downloadImageUri=profilePicRef.child(imageUri.getLastPathSegment()+key+".jpg").getDownloadUrl().toString();
//                        Toast.makeText(ProjectDescription.this, downloadImageUri , Toast.LENGTH_SHORT).show();
//                        return filePath.getDownloadUrl();
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//
//                        if(task.isSuccessful())
//                        {
//                            saveDataToDataBase();
//                        }
//                    }
//                });
//            }
//        });

    }

    private void saveDataToDataBase() {
        final String Name = editText1.getText().toString();
        final String phone = editText2.getText().toString();
        final String address = editText3.getText().toString();
        final String city = editText4.getText().toString();
        final String state = editText5.getText().toString();
        final String email = editText6.getText().toString();
        final String website = editText7.getText().toString();
        final String instagram=editText8.getText().toString();
        final String facebook=editText9.getText().toString();
        final String linked_in=editText10.getText().toString();
        final String designation = editText11.getText().toString();
        final String work_type = editText12.getText().toString();
        final String uid=user.getUid().toString();
        userClass.setName(Name);
        userClass.setPhone(phone);
        userClass.setAddress(address);
        userClass.setCity(city);
        userClass.setState(state);
        userClass.setEmail(email);
        userClass.setWebsite(website);
        userClass.setInstagram(instagram);
        userClass.setFacebook(facebook);
        userClass.setLinkedIn(linked_in);
        userClass.setDesignation(designation);
        userClass.setWorktype(work_type);
        userClass.setPicUri(downloadImageUri);
        Mainref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                Mainref.child("Users").child("UserInfo").child(uid).setValue(userClass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ProjectDescription.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    //putValuesInEditText(dataSnapshot);
                                }
                                else
                                {
                                    Toast.makeText(ProjectDescription.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }
                        });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void putValuesInEditText(@org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.child("Users").getChildren())
        {
            userClass.setName((ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getName());
            userClass.setPhone(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getPhone());
            userClass.setAddress(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getAddress());
            userClass.setCity(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getCity());
            userClass.setState(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getState());
            userClass.setEmail(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getEmail());
            userClass.setWebsite(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getWebsite());
            userClass.setInstagram(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getInstagram());
            userClass.setFacebook(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getFacebook());
            userClass.setLinkedIn(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getLinkedIn());
            userClass.setDesignation(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getDesignation());
            userClass.setWorktype(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getWorktype());
            userClass.setPicUri(Objects.requireNonNull(ds.child(user.getUid()).getValue(UserInfoForDatabase.class)).getPicUri());
            editText1.setText(userClass.getName().toString());
            editText2.setText(userClass.getPhone().toString());
            editText3.setText(userClass.getAddress().toString());
            editText4.setText(userClass.getCity().toString());
            editText5.setText(userClass.getState().toString());
            editText6.setText(userClass.getEmail().toString());
            editText7.setText(userClass.getWebsite().toString());
            editText8.setText(userClass.getInstagram().toString());
            editText9.setText(userClass.getFacebook().toString());
            editText10.setText(userClass.getLinkedIn().toString());
            editText11.setText(userClass.getDesignation().toString());
            editText12.setText(userClass.getWorktype().toString());
            Picasso.get().load(Uri.parse(userClass.getPicUri())).into(profilePic);


            loadingBar.dismiss();
        }
    }

}

