package app.com.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.button.MaterialButton;

import app.com.firebaseauth.utils.FirebaseOperations;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.ll_caption)
    LinearLayout llCaption;
    @BindView(R.id.caption)
    TextView caption;
    @BindView(R.id.caption1)
    TextView caption1;
    @BindView(R.id.btn_profile)
    MaterialButton btnProfile;
    @BindView(R.id.btn_form)
    MaterialButton btnForm;
    @BindView(R.id.btn_signout)
    MaterialButton btnSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AgentDetailsActivity.class));
            }
        });

        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseOperations.getInstance().signOut(HomeActivity.this);
            }
        });
    }
}
