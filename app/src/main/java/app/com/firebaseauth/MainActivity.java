package app.com.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import app.com.firebaseauth.models.PreInvestigationDetails;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_agent)
    TextView tvAgent;
    @BindView(R.id.ll_main)
    ScrollView llMain;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_age)
    LinearLayout llAge;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.ll_gender)
    LinearLayout llGender;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.ll_marks)
    LinearLayout llMarks;
    @BindView(R.id.tv_marks)
    TextView tvMarks;
    @BindView(R.id.et_marks)
    EditText etMarks;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.et_add)
    EditText etAdd;
    @BindView(R.id.ll_dept)
    LinearLayout llDept;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.et_dept)
    EditText etDept;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.ll_profile_pic)
    LinearLayout llProfilePic;
    @BindView(R.id.tv_pic)
    TextView tvPic;
    @BindView(R.id.tv_upload_pic)
    TextView tvUploadPic;
    @BindView(R.id.chk_confirm)
    CheckBox chkConfirm;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    private boolean isLayoutShowing = false;
    Timer timer;
    private ProfileActivity profileActivity;

    String name, age, gender, address, marks, dept, contact;
    boolean isDetailsConfirmed;
    private Uri filePath;

    ProgressDialog pd;
    FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-auth-c86bb.appspot.com");
    private DatabaseReference mDatabase;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvUploadPic.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvHead.setOnClickListener(this);
        tvAgent.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int button) {
                RadioButton btn = findViewById(button);
                gender = btn.getText().toString().trim();
            }
        });

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        timer = new Timer();
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 300000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }*/

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        if (view == tvHead) {
            isLayoutShowing = !isLayoutShowing;
            if (isLayoutShowing) {
                llMain.setVisibility(View.GONE);
            } else {
                llMain.setVisibility(View.VISIBLE);
            }
        } else if (view == tvAgent) {
            openProfileActivity();
        } else if (view == tvUploadPic) {
            chooseImage();
        } else if (view == btnUpload) {
            uploadImage();
        } else if (view == btnSubmit) {
            if (validations()) {
                name = etName.getText().toString().trim();
                age = etAge.getText().toString().trim();
                address = etAdd.getText().toString().trim();
                marks = etMarks.getText().toString().trim();
                dept = etDept.getText().toString().trim();
                contact = etContact.getText().toString().trim();
                if (chkConfirm.isChecked()) {
                    isDetailsConfirmed = true;
                }
                Toast.makeText(MainActivity.this, "" + name + "\n"
                        + age + "\n"
                        + gender + "\n"
                        + address + "\n" + marks + "\n" + dept + "\n" + contact + "\n" + isDetailsConfirmed + "\n", Toast.LENGTH_SHORT).show();
                //send all these details to the firebase database
                PreInvestigationDetails investigatedData = new PreInvestigationDetails(
                        name, gender, marks, address, dept, contact, Integer.parseInt(age), isDetailsConfirmed
                );
                //Uploading mode data to firebase
                uploadData(investigatedData);
            }
        }
    }

    private void openProfileActivity() {
        Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
    }

    private boolean validations() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())
                || TextUtils.isEmpty(etAge.getText().toString().trim())
                || TextUtils.isEmpty(etAdd.getText().toString().trim())
                || TextUtils.isEmpty(etDept.getText().toString().trim())
                || TextUtils.isEmpty(etContact.getText().toString().trim())
                || TextUtils.isEmpty(etMarks.getText().toString().trim())) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return false;

        } else if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "There is no human without gender", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!chkConfirm.isChecked()) {
            Toast.makeText(this, "Could not submit if not confirmed", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etContact.getText().toString().matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")) {
            etContact.setError("Enter contact name and phone");
            return false;
        } else {
            return true;
        }
    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {
            //redirect user to login screen
            profileActivity.signOut();
            finish();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            Log.d("onActivityResult", "" + filePath);
            tvUploadPic.setText(filePath.getLastPathSegment());

            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }*/
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            pd = new ProgressDialog(this);
            pd.setTitle("Uploading...");
            pd.show();

            StorageReference childRef = storageRef.child("Photos").child(filePath.getLastPathSegment().toString());

            UploadTask uploadTask = childRef.putFile(filePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    btnUpload.setClickable(false);
                    tvUploadPic.setClickable(false);
                    btnUpload.setText("Uploaded");
                    btnUpload.setAlpha(0.5f);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(MainActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadData(PreInvestigationDetails data) {
        pd = new ProgressDialog(this);
        pd.setTitle("Saving...");
        pd.show();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("investigation_data").child(user.getDisplayName()).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
