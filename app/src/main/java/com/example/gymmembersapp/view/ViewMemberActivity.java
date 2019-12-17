package com.example.gymmembersapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gymmembersapp.R;
import com.example.gymmembersapp.util.Constants;
import com.example.gymmembersapp.util.Member;
import com.example.gymmembersapp.util.Plan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMemberActivity extends AppCompatActivity {

    @BindView(R.id.returnButton3)
    Button buttonReturn;
    @BindView(R.id.memberIdTextView3)
    TextView textViewID;
    @BindView(R.id.memberNameTextView3)
    TextView textViewName;
    @BindView(R.id.memberIcon3)
    ImageView memberIcon;
    @BindView(R.id.planTextView3)
    TextView planTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Member member = intent.getParcelableExtra(Constants.SEND_GYM);
        textViewID.setText(member.getId());
        textViewName.setText(member.getName());
        Plan plan = new Plan(member.getPlan());
        planTextView.setText(plan.toString());
        Glide.with(this).load(R.drawable.ic_face_black).into(memberIcon);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
