package com.example.gymmembersapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymmembersapp.R;
import com.example.gymmembersapp.util.Constants;
import com.example.gymmembersapp.util.Member;
import com.example.gymmembersapp.util.Plan;
import com.example.gymmembersapp.util.RecyclerViewPlan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMembersActivity extends AppCompatActivity implements RecyclerViewPlan.GymPlanDelegate {

    File file;
    @BindView(R.id.confirmAddMemberButton)
    Button addMemberButton;
    @BindView(R.id.cancelAddMemberButton)
    Button cancelAddMemberButton;
    @BindView(R.id.editTextName)
    EditText editText;
    @BindView(R.id.recylerViewPlan)
    RecyclerView recyclerView;
    @BindView(R.id.planSelected)
    TextView planSelectedTextView;

    int memberCount;
    ArrayList<Plan> plans;
    int plan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        ButterKnife.bind(this);
        file = new File(Environment.getRootDirectory(), Constants.FILE_NAME);
        Intent intent = getIntent();
        memberCount = intent.getIntExtra(Constants.SEND_COUNT,-1);

        setUpRecyclerView();
        //cancel add members
        cancelAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    public void setUpRecyclerView(){
        plans = new ArrayList<>();
        for(int i = 1; i<4;i++){
            plans.add(new Plan(i));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final RecyclerView.Adapter adapter = new RecyclerViewPlan(plans,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }

    public Member createMember(){
        String id = Constants.GYM_MEMBER_ID +memberCount;
        String name = editText.getText().toString().trim();
        String planName = planSelectedTextView.getText().toString().trim();
        if(name.equals("")||planName.equals("")){
            return null;
        }
        return new Member(id,name,plan);
    }

    //add member
    public void onClick(View v) {
        Member member = createMember();
        if(member ==null){
            Toast.makeText(this,"Must input a name",Toast.LENGTH_LONG).show();
        }else {
            try {
                FileOutputStream fileOutputStream = openFileOutput(Constants.FILE_NAME, MODE_APPEND);
                String text = member.toSaveString()+"***";
                fileOutputStream.write(text.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish(); }
    }

    @Override
    public void choosePlan(Plan plan) {
        this.plan = plan.getId();
        planSelectedTextView.setText((new Plan(this.plan)).getName());
    }
}
