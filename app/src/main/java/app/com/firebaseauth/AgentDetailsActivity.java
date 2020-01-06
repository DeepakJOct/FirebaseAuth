package app.com.firebaseauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;

import app.com.firebaseauth.listeners.FirebaseListener;
import app.com.firebaseauth.utils.FirebaseOperations;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rl_main_left)
    RelativeLayout rlMainLeft;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.ll_details)
    LinearLayout llDetails;
    @BindView(R.id.tv_agent_name)
    TextView tvAgentName;
    @BindView(R.id.tv_agent_email)
    TextView tvAgentEmail;
    @BindView(R.id.tv_agent_id)
    TextView tvAgentId;
    @BindView(R.id.tv_agent_provider)
    TextView tvAgentProvider;
    @BindView(R.id.btn_back)
    MaterialButton btnBack;
    @BindView(R.id.btn_edit)
    MaterialButton btnEdit;
    @BindView(R.id.tv_total_investigations)
    TextView tvTotalInvestigations;
    @BindView(R.id.tv_current_sheet_progress)
    TextView tvCurrentSheetProgress;
    @BindView(R.id.imv_profile)
    ImageView imvProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_details);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        getUserDetails();
    }

    @Override
    public void onClick(View view) {
        if(view == btnBack) {
            onBackPressed();
        } else if(view == btnEdit) {
            startActivity(new Intent(AgentDetailsActivity.this, ProfileActivity.class));
        }
    }

    private void getUserDetails() {
        FirebaseOperations.getInstance().getUserDetails(AgentDetailsActivity.this, new FirebaseListener() {
            @Override
            public void onFirebaseResult(Object result, boolean isSuccess) {
                if(isSuccess) {
                    if(result != null) {
                        FirebaseUser user = (FirebaseUser) result;
                        tvAgentName.setText(user.getDisplayName());
                        tvAgentEmail.setText(user.getEmail());
                        tvAgentId.setText(user.getUid());
                        tvAgentProvider.setText(user.getProviderId());
                        imvProfile.setImageURI(user.getPhotoUrl());
                    }
                } else {
                    Toast.makeText(AgentDetailsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
