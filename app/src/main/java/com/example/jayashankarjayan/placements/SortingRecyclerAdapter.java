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
 * Created by Jayashankar Jayan on 11/27/2017.
 */

public class SortingRecyclerAdapter extends RecyclerView.Adapter<SortingRecyclerAdapter.MyViewHolder> {
    private List<SortingRecyclerViewContent> mainList;

    private int lastPosition = -1;
    Context context;
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView activity_sorting_row_text_view_title;;

        public MyViewHolder(View view) {
            super(view);
            view.setClickable(true);
            activity_sorting_row_text_view_title = (TextView) view.findViewById(R.id.activity_sorting_row_text_view_title);
        }


        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public SortingRecyclerAdapter(List<SortingRecyclerViewContent> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sorting_row, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.activity_sorting_row_text_view_title,"bold");

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SortingRecyclerAdapter.MyViewHolder holder, int position) {
        SortingRecyclerViewContent data = mainList.get(position);

        holder.activity_sorting_row_text_view_title.setText(data.getSortingItemTitle());
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