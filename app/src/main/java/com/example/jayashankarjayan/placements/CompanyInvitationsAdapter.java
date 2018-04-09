package com.example.jayashankarjayan.placements;

import android.content.Context;
import android.content.Intent;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;

import java.util.List;

/**
 * Created by Jayashankar Jayan on 12/3/2017.
 */

public class CompanyInvitationsAdapter extends RecyclerView.Adapter<CompanyInvitationsAdapter.MyViewHolder> {
    private List<CompanyInvitationsContent> mainList;

    Context context;
    private int lastPosition = -1;
    private int mExpandedPosition = -1,position;
    private View itemView;
    private ViewGroup parent;
    private String inviteId;
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView companyName, dateOfSelection;
        RelativeLayout relativeLayout;
        Button invitations_row_accept,invitations_row_decline,invitations_row_more_details;
        public MyViewHolder(View view) {
            super(view);
            companyName = (TextView) view.findViewById(R.id.title);
            dateOfSelection = (TextView) view.findViewById(R.id.jobName);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.invitations_row_accept_ignore_layout);
            invitations_row_accept = (Button)view.findViewById(R.id.invitations_row_accept);
            invitations_row_decline = (Button)view.findViewById(R.id.invitations_row_decline);
            invitations_row_more_details = (Button)view.findViewById(R.id.invitations_row_more_details);

        }


        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public void clearAnimation()
    {}
    public CompanyInvitationsAdapter(List<CompanyInvitationsContent> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invitations_row, parent, false);

        this.parent = parent;
        MyViewHolder holder = new MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(), holder.companyName,"bold");
        functions.applyFont(parent.getContext(), holder.dateOfSelection,"bold");
        functions.applyFont(parent.getContext(), holder.invitations_row_accept,"bold");
        functions.applyFont(parent.getContext(), holder.invitations_row_decline,"bold");
        functions.applyFont(parent.getContext(), holder.invitations_row_more_details,"bold");


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CompanyInvitationsContent data = mainList.get(position);
        holder.companyName.setText(data.getCompanyName());
        holder.dateOfSelection.setText("Date of interview: "+data.getDateOfSelection());
        String accepted = data.getAccepted();

        final boolean isExpanded = position==mExpandedPosition;
        holder.relativeLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:holder.getAdapterPosition();
                TransitionManager.beginDelayedTransition(parent);
                notifyDataSetChanged();
            }
        });
        inviteId = data.getId();
        final String companyName = data.getCompanyName();
        switch (accepted)
        {
            case "1":
                holder.invitations_row_accept.setBackground(context.getResources().getDrawable(R.drawable.recycler_view_button_positive_selected));
                holder.invitations_row_accept.setTextColor(context.getResources().getColor(R.color.white));
                holder.invitations_row_accept.setText("Accepted");
                holder.invitations_row_accept.setClickable(false);

                holder.invitations_row_decline.setVisibility(View.GONE);
                holder.invitations_row_more_details.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.invitations_row_decline.setBackground(context.getResources().getDrawable(R.drawable.recycler_view_button_negative_selected));
                holder.invitations_row_decline.setTextColor(context.getResources().getColor(R.color.white));
                holder.invitations_row_decline.setText("Declined");
                holder.invitations_row_decline.setClickable(false);

                holder.invitations_row_accept.setVisibility(View.GONE);
                break;
        }
        if(holder.invitations_row_accept.isClickable())
        {
            holder.invitations_row_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.invitations_row_decline.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_negative));
                    holder.invitations_row_decline.setTextColor(parent.getContext().getResources().getColor(R.color.red));
                    holder.invitations_row_decline.setText("Decline");

                    holder.invitations_row_accept.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_positive_selected));
                    holder.invitations_row_accept.setTextColor(parent.getContext().getResources().getColor(R.color.white));
                    holder.invitations_row_accept.setText("Accepted");
                    new CompanyInvitationsUpdateInviteAsyncTask(parent.getContext(),"acceptInvitations",inviteId).execute();

                }
            });
        }
        if(holder.invitations_row_decline.isClickable())
        {
            holder.invitations_row_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.invitations_row_accept.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_positive));
                    holder.invitations_row_accept.setTextColor(parent.getContext().getResources().getColor(R.color.green));
                    holder.invitations_row_accept.setText("Accept");

                    holder.invitations_row_decline.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_negative_selected));
                    holder.invitations_row_decline.setTextColor(parent.getContext().getResources().getColor(R.color.white));
                    holder.invitations_row_decline.setText("Declined");
                    new CompanyInvitationsUpdateInviteAsyncTask(parent.getContext(),"declineInvitations",inviteId).execute();
                }
            });
        }
        holder.invitations_row_more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCompanyInvitations = new Intent(parent.getContext(),CompanyInvitationsMoreDetails.class);
                goToCompanyInvitations.putExtra("inviteId",inviteId);
                goToCompanyInvitations.putExtra("name_of_company",companyName);
                parent.getContext().startActivity(goToCompanyInvitations);
            }
        });

        setAnimation(holder.itemView, position);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context , android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
