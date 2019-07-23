package app.com.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.tv_provider)
    TextView tvProvider;
    @BindView(R.id.old_email)
    EditText oldEmail;
    @BindView(R.id.new_email)
    EditText newEmail;
    @BindView(R.id.new_name)
    EditText newName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    /*
     * oldEmail
     * newEmail
     * newName
     * password
     * newPassword
     * confirmPassword
     *
     * btnSubmitName
     * btnSubmitEmail
     * btnSubmitPassword
     * btnSendPasswordResetEmail
     * btnRemoveTheUser
     * */

    @BindView(R.id.change_email_button)
    Button btnChangeEmail;
    @BindView(R.id.change_name_button)
    Button changeNameButton;
    @BindView(R.id.change_password_button)
    Button btnChangePassword;
    @BindView(R.id.sending_pass_reset_button)
    Button btnSendResetEmail;

    @BindView(R.id.remove_user_button)
    Button btnRemoveUser;
    @BindView(R.id.btnSubmitEmail)
    Button btnSubmitEmail;
    @BindView(R.id.btnSubmitName)
    Button btnSubmitName;
    @BindView(R.id.btnSubmitPassword)
    Button btnSubmitPassword;
    @BindView(R.id.btnResetEmail)
    Button btnSendPasswordResetEmail;
    @BindView(R.id.btnRemoveUser)
    Button btnRemoveTheUser;
    @BindView(R.id.sign_out)
    Button signOut;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;


    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private String mName, mEmail, mUserId;
    private String mExtra;
    private boolean isName, isEmail, isPassword, isSendPasswordResetEmail, isRemoveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);*/
        btnChangeEmail.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnSendResetEmail.setOnClickListener(this);
        btnRemoveUser.setOnClickListener(this);

        btnSubmitEmail.setOnClickListener(this);
        btnSubmitPassword.setOnClickListener(this);
        changeNameButton.setOnClickListener(this);
        btnSubmitName.setOnClickListener(this);
        btnSendPasswordResetEmail.setOnClickListener(this);
        btnRemoveTheUser.setOnClickListener(this);
        signOut.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //If user session has timed out, send the user to login page.
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserDetails();
            }
        });


        getUserDetails();


        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sign_out) {
            signOut();
        } else if (view.getId() == R.id.change_email_button) {
            changeEmail();
        } else if (view.getId() == R.id.change_password_button) {
            changePassword();
        } else if (view.getId() == R.id.sending_pass_reset_button) {
            sendPasswordResetEmail();
        } else if (view.getId() == R.id.remove_user_button) {
            removeUser();
        } else if (view.getId() == R.id.btnSubmitEmail) {
            submitChangedEmail();
        } else if (view.getId() == R.id.btnSubmitPassword) {
            submitChangedPassword();
        } else if (view.getId() == R.id.btnResetEmail) {
            submitEmailForPasswordChange();
        } else if (view.getId() == R.id.btnRemoveUser) {
            submitDetailsToRemoveUser();
        } else if (view == changeNameButton) {
            changeName();
        } else if (view == btnSubmitName) {
            submitChangedName();
        }

    }

    private void submitDetailsToRemoveUser() {
    }

    private void submitEmailForPasswordChange() {
    }

    private void submitChangedPassword() {
    }

    private void removeUser() {
        isRemoveUser = true;
        buttonVisibility();
    }

    private void sendPasswordResetEmail() {
        isSendPasswordResetEmail = true;
        buttonVisibility();
    }

    private void changePassword() {
        isPassword = true;
        buttonVisibility();
    }

    //get user details from firebase
    private void getUserDetails() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mEmail = user.getEmail();
                    mName = user.getDisplayName();
                    mUserId = user.getUid();
                    mExtra = user.getProviderId();
                }
            }
        };

        authStateListener.onAuthStateChanged(firebaseAuth);
        tvName.setText(mName);
        tvEmail.setText(mEmail);
        tvUserId.setText(mUserId);
        tvProvider.setText(mExtra);
    }

    /*
     * Changing email*/

    private void changeEmail() {
        isEmail = true;
        buttonVisibility();
    }

    private void submitChangedEmail() {
        progressBar.setVisibility(View.VISIBLE);
        if (user != null && !newEmail.getText().toString().trim().equals("")) {
            user.updateEmail(newEmail.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                signOut();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        } else if (newEmail.getText().toString().trim().equals("")) {
            newEmail.setError("Enter Email");
            progressBar.setVisibility(View.GONE);
        }
    }

    /*
     * Changing name*/

    private void changeName() {
        isName = true;
        buttonVisibility();
    }

    private void submitChangedName() {
        progressBar.setVisibility(View.VISIBLE);
        if (user != null && !newName.getText().toString().trim().equals("")) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName.getText().toString().trim())
                    .build();

            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Successfully changed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }


    /*
     * Changing signout*/

    public void signOut() {
        firebaseAuth.signOut();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };
        authStateListener.onAuthStateChanged(firebaseAuth);
    }

    private void buttonVisibility() {
        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        newName.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        confirmPassword.setVisibility(View.GONE);
        btnSubmitName.setVisibility(View.GONE);
        btnSubmitEmail.setVisibility(View.GONE);
        btnSubmitPassword.setVisibility(View.GONE);
        btnSendPasswordResetEmail.setVisibility(View.GONE);
        btnRemoveTheUser.setVisibility(View.GONE);
        if (isEmail) {
            isEmail = false;
            newEmail.setVisibility(View.VISIBLE);
            btnSubmitEmail.setVisibility(View.VISIBLE);
        } else if (isName) {
            isName = false;
            newName.setVisibility(View.VISIBLE);
            btnSubmitName.setVisibility(View.VISIBLE);

        } else if (isPassword) {
            isPassword = false;
            password.setVisibility(View.VISIBLE);
            newPassword.setVisibility(View.VISIBLE);
            confirmPassword.setVisibility(View.VISIBLE);
            btnSubmitPassword.setVisibility(View.VISIBLE);

        } else if (isRemoveUser) {
            isRemoveUser = false;
            oldEmail.setVisibility(View.VISIBLE);
            btnRemoveTheUser.setVisibility(View.VISIBLE);

        } else if (isSendPasswordResetEmail) {
            isSendPasswordResetEmail = false;
            oldEmail.setVisibility(View.VISIBLE);
            btnSendPasswordResetEmail.setVisibility(View.VISIBLE);

        }
    }


}
