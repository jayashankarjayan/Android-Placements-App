package com.example.jayashankarjayan.placements;

import android.content.Context;
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
 * Created by Jayashankar Jayan on 11/28/2017.
 */

public class AppliedCompaniesRecyclerViewAdapter extends RecyclerView.Adapter<AppliedCompaniesRecyclerViewAdapter.MyViewHolder> {

    private List<AppliedCompaniesRecyclerViewContent> mainList;

    private MyViewHolder holder;
    private Context context;
    private String tableName;
    private int lastPosition = -1;
    View itemView;
    ViewGroup parent;
    int mExpandedPosition = -1;
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, jobName;
        public RelativeLayout relativeLayout;
        public Button applied_companies_backout;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.applied_companies_title);
            jobName = (TextView) view.findViewById(R.id.applied_companies_jobName);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.applied_companies_row_unapply_layout);
            applied_companies_backout = (Button)view.findViewById(R.id.applied_companies_backout);
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public AppliedCompaniesRecyclerViewAdapter(List<AppliedCompaniesRecyclerViewContent> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.applied_companies_row, parent, false);

        holder = new MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.title,"bold");
        functions.applyFont(parent.getContext(),holder.jobName,"bold");
        functions.applyFont(parent.getContext(),holder.applied_companies_backout,"bold");
        this.parent = parent;

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        AppliedCompaniesRecyclerViewContent data = mainList.get(position);
        holder.title.setText(data.getTitle());
        holder.jobName.setText(data.getJob());

        final boolean isExpanded = position==mExpandedPosition;
        holder.relativeLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(parent);
                notifyDataSetChanged();
            }
        });

        tableName = data.getTableName();
        holder.applied_companies_backout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppliedCompaniesUnapplyAsyncTask(parent.getContext(),tableName).execute();
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