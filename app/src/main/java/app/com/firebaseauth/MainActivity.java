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
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_pass)
    TextView tvPass;
    @BindView(R.id.tv_extra)
    TextView tvExtra;
    @BindView(R.id.old_email)
    EditText oldEmail;
    @BindView(R.id.new_email)
    EditText newEmail;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.change_email_button)
    Button btnChangeEmail;
    @BindView(R.id.change_password_button)
    Button btnChangePassword;
    @BindView(R.id.sending_pass_reset_button)
    Button btnSendResetEmail;
    @BindView(R.id.remove_user_button)
    Button btnRemoveUser;
    @BindView(R.id.changeEmail)
    Button changeEmail;
    @BindView(R.id.changePass)
    Button changePassword;
    @BindView(R.id.send)
    Button sendEmail;
    @BindView(R.id.remove)
    Button remove;
    @BindView(R.id.sign_out)
    Button signOut;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        btnChangeEmail.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnSendResetEmail.setOnClickListener(this);
        btnRemoveUser.setOnClickListener(this);

        changeEmail.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        remove.setOnClickListener(this);
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

        } else if (view.getId() == R.id.sending_pass_reset_button) {

        } else if (view.getId() == R.id.remove_user_button) {

        } else if (view.getId() == R.id.changeEmail) {
            submitChangedEmail();
        } else if (view.getId() == R.id.changePass) {

        } else if (view.getId() == R.id.send) {

        } else if (view.getId() == R.id.remove) {

        }

    }

    private void changeEmail() {
        newEmail.setVisibility(View.VISIBLE);
        changeEmail.setVisibility(View.VISIBLE);
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


}
