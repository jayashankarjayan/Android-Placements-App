package com.example.jayashankarjayan.placements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;

import java.util.List;

/**
 * Created by Jayashankar Jayan on 3/9/2018.
 */
class AdminDisplayLevelDetailsRecyclerAdapter extends RecyclerView.Adapter<AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder> {
    private List<AdminDisplayLevelDetailsRecyclerViewContent> mainList;

    private AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder holder;
    private Context context;
    private String tableName;
    private int lastPosition = -1;
    View itemView;
    ViewGroup parent;
    int mExpandedPosition = -1;
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView admin_level_display_details_textview_info;
        public MyViewHolder(View view) {
            super(view);
            admin_level_display_details_textview_info = (TextView) view.findViewById(R.id.admin_level_display_details_textview_info);
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public AdminDisplayLevelDetailsRecyclerAdapter(List<AdminDisplayLevelDetailsRecyclerViewContent> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_level_display_details_row, parent, false);

        holder = new AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.admin_level_display_details_textview_info,"bold");

        this.parent = parent;

        return new AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder holder, final int position) {
        AdminDisplayLevelDetailsRecyclerViewContent data = mainList.get(position);
        holder.admin_level_display_details_textview_info.setText(data.getInfo());
        setAnimation(holder.itemView, position);

    }


    @Override
    public void onViewDetachedFromWindow(AdminDisplayLevelDetailsRecyclerAdapter.MyViewHolder holder) {
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
