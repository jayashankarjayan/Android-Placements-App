package com.example.jayashankarjayan.placements;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayashankarjayan.placements.functions.Functions;

import java.util.List;

/**
 * Created by Jayashankar Jayan on 11/29/2017.
 */

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder> {
    private List<ChatMessage> mainList;

    private int lastPosition = -1;
    Context context;
    //    public TextView title, jobName;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message_user, message_time,
                category;
        public ImageView image_of_user;

        public MyViewHolder(View view) {
            super(view);
            message_user = (TextView) view.findViewById(R.id.message_user);
            category = (TextView) view.findViewById(R.id.posted_by);
            message_time = (TextView)view.findViewById(R.id.message_time);
            image_of_user = (ImageView)view.findViewById(R.id.message_row_image_of_user);
        }


        public void clearAnimation()
        {
            itemView.clearAnimation();
        }
    }


    public ChatRecyclerAdapter(List<ChatMessage> mainList, Context context) {
        this.mainList = mainList;
        this.context = context;
    }

    @Override
    public ChatRecyclerAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        final ChatRecyclerAdapter.MyViewHolder holder = new ChatRecyclerAdapter.MyViewHolder(itemView);

        final Functions functions = new Functions(parent.getContext());
        functions.applyFont(parent.getContext(),holder.message_user,"bold");
        functions.applyFont(parent.getContext(),holder.message_time,"bold");

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        return new ChatRecyclerAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ChatRecyclerAdapter.MyViewHolder holder, final int position) {
        final ChatMessage data = mainList.get(position);

        Functions function = new Functions(context);
        holder.message_user.setText(Html.fromHtml(data.getMessage()));
        holder.category.setText(data.getCategory());
        holder.message_time.setText(data.getMessageTime());
        function.loadImage(function.getLink()+"/student_profile_pictures/testimage.png",holder.image_of_user,"rounded",150,0);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToIndividualDiscussionThread = new Intent(context.getApplicationContext(),IndividualDiscussionThreads.class);
                goToIndividualDiscussionThread.putExtra("thread_text",""+data.getMessage());
                goToIndividualDiscussionThread.putExtra("thread_time",""+data.getMessageTime());
                goToIndividualDiscussionThread.putExtra("thread_posted_by",""+data.getCategory());
                goToIndividualDiscussionThread.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(goToIndividualDiscussionThread);
            }
        });
        setAnimation(holder.itemView, position);
    }


    @Override
    public void onViewDetachedFromWindow(ChatRecyclerAdapter.MyViewHolder holder) {
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
