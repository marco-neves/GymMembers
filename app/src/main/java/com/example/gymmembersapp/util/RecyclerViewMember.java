package com.example.gymmembersapp.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymmembersapp.R;

import java.util.ArrayList;

public class RecyclerViewMember extends RecyclerView.Adapter<RecyclerViewMember.ViewHolder> {

    private ArrayList<Member> members;

    private GymMemberAdapterDelegate gymMemberAdapterDelegate;

    public interface GymMemberAdapterDelegate{
        void memberSelected(Member selectedMember);
    }

    //constructors
    public RecyclerViewMember(ArrayList<Member> members, GymMemberAdapterDelegate gymMemberAdapterDelegate) {
        this.gymMemberAdapterDelegate = gymMemberAdapterDelegate;
        this.members = members;
    }
    public RecyclerViewMember(){
        this.members = new ArrayList<>();
    }



    @NonNull
    @Override
    public RecyclerViewMember.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMember.ViewHolder holder, final int position) {
        final Member member = members.get(position);
        holder.textViewMemberId.setText(member.getId());
        holder.textViewMemberName.setText(member.getName());
        Glide.with(holder.imageViewMemberPic.getContext())
                .load(R.drawable.ic_face_black)
                .into(holder.imageViewMemberPic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gymMemberAdapterDelegate.memberSelected(members.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMemberId;
        TextView textViewMemberName;
        ImageView imageViewMemberPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMemberId = itemView.findViewById(R.id.memberIDTextView);
            textViewMemberName = itemView.findViewById(R.id.memberNameTextView);
            imageViewMemberPic = itemView.findViewById(R.id.memberPhotoImageView);
        }
    }
}
