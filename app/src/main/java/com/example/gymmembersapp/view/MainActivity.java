package com.example.gymmembersapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymmembersapp.R;
import com.example.gymmembersapp.util.Constants;
import com.example.gymmembersapp.util.Member;
import com.example.gymmembersapp.util.RecyclerViewMember;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerViewMember.GymMemberAdapterDelegate{

    File file;
    @BindView(R.id.addMembers)
    Button addMembersButton;
    @BindView(R.id.membersRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.gymTitle)
    TextView textView;
    ArrayList<Member> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        members = new ArrayList<Member>();

        file = new File(Environment.getRootDirectory(),Constants.FILE_NAME);
        drawRecyclerView();

        addMembersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMembersActivity.class);
                intent.putExtra(Constants.SEND_COUNT,members.size());
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });
    }

    @Override
    public void memberSelected(Member selectedMember) {
        Intent intent = new Intent(this,ViewMemberActivity.class);
        intent.putExtra(Constants.SEND_GYM, selectedMember);
        startActivity(intent);
    }

    private void drawRecyclerView(){
        readFromFile();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerViewMember adapter = new RecyclerViewMember(members, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.REQUEST_CODE){
            if(resultCode==RESULT_OK){
                drawRecyclerView();
            }else if(resultCode==RESULT_CANCELED){
                Toast.makeText(this,"Could not add member",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //read the file and populate arraylist
    private void readFromFile(){
        try{
            FileInputStream fileInputStream = openFileInput(Constants.FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String text = bufferedReader.readLine();
            String[] membersText = text.split("\\*\\*\\*");
            for(int i = 0; i<membersText.length; i++){
                members.add(new Member(membersText[i]));
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
