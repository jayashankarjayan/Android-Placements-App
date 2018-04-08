package com.example.jayashankarjayan.placements.functions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jayashankar Jayan on 12/28/2017.
 */


public class ScheduledTimerTask extends TimerTask {

    public static void main(String[] args) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new ScheduledTimerTask(),0,9000);

    }

    @Override
    public void run() {
        System.out.print("Test Timer Activity");
    }
}