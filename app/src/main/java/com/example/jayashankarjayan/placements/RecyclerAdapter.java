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
 * Created by Jayashankar Jayan on 11/19/2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Recycler_View_Content> mainList;

    MyViewHolder holder;
    Context context;
    private int lastPosition = -1;
    int mExpandedPosition = -1;
    View itemView;
    ViewGroup parent;
//    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, jobName;
        RelativeLayout relativeLayout;
        Button list_row_apply_unapply_button;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            jobName = (TextView) view.findViewById(R.id.jobName);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.list_row_accept_ignore_layout);
            list_row_apply_unapply_button = (Button)view.findViewById(R.id.list_row_apply_unapply_button);
        }


    public void clearAnimation()
    {
        itemView.clearAnimation();
    }
    }


    public void clearAnimation()
    {}
    public RecyclerAdapter(List<Recycler_View_Content> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        this.parent = parent;
        holder = new MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.title,"bold");
        functions.applyFont(parent.getContext(),holder.jobName,"bold");
        functions.applyFont(parent.getContext(),holder.list_row_apply_unapply_button,"bold");


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Recycler_View_Content data = mainList.get(position);
        holder.title.setText(data.getTitle());
        holder.jobName.setText(data.getJob());

        String buttonType = data.getButtonType();

        final String table_name = data.getTableName();
        switch (buttonType)
        {
            case "apply":
                holder.list_row_apply_unapply_button.setText(parent.getContext().getResources().getText(R.string.apply_button_text));
                holder.list_row_apply_unapply_button.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_positive));
                holder.list_row_apply_unapply_button.setTextColor(parent.getContext().getResources().getColor(R.color.green));


                holder.list_row_apply_unapply_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainFragmentApplyToCompanyAsyncTask mainFragmentApplyToCompanyAsyncTask = new
                                MainFragmentApplyToCompanyAsyncTask(parent.getContext(),"applyToCompany",table_name);
                        mainFragmentApplyToCompanyAsyncTask.execute();
                    }
                });

                break;
            case "unapply":
                holder.list_row_apply_unapply_button.setText(parent.getContext().getResources().getText(R.string.unapply_button_text));
                holder.list_row_apply_unapply_button.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_negative));
                holder.list_row_apply_unapply_button.setTextColor(parent.getContext().getResources().getColor(R.color.red));

                holder.list_row_apply_unapply_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainFragmentApplyToCompanyAsyncTask mainFragmentApplyToCompanyAsyncTask = new
                                MainFragmentApplyToCompanyAsyncTask(parent.getContext(),"unapplyFromCompany",table_name);
                        mainFragmentApplyToCompanyAsyncTask.execute();
                    }
                });
                break;
            case "not applicable":
                holder.list_row_apply_unapply_button.setClickable(false);
                holder.list_row_apply_unapply_button.setText(parent.getContext().getResources().getText(R.string.not_applicable_button_text));
                holder.list_row_apply_unapply_button.setBackground(parent.getContext().getResources().getDrawable(R.drawable.recycler_view_button_not_applicable));
                holder.list_row_apply_unapply_button.setTextColor(parent.getContext().getResources().getColor(R.color.orange));
                break;
            case "closed":
                holder.list_row_apply_unapply_button.setClickable(false);
                holder.list_row_apply_unapply_button.setText(parent.getContext().getResources().getText(R.string.closed_button_text));
                holder.list_row_apply_unapply_button.setBackground(parent.getContext().getResources().getDrawable(android.R.color.transparent));
                holder.list_row_apply_unapply_button.setTextColor(parent.getContext().getResources().getColor(android.R.color.holo_red_dark));
                break;
        }
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