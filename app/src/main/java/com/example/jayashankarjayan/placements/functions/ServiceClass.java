package com.example.jayashankarjayan.placements.functions;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jayashankar Jayan on 12/26/2017.
 */

public class ServiceClass extends Service{

    LocalDatabaseHandler localDatabaseHandler;
    GetSkillsAsyncTask getSkillsAsyncTask;
    StudentDataAsyncTask studentDataAsyncTask;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*@Override
    protected void onHandleIntent(@Nullable Intent intent) {
        localDatabaseHandler = new LocalDatabaseHandler(getApplicationContext());
        studentDataAsyncTask = new StudentDataAsyncTask(getApplicationContext());
        Functions functions = new Functions(getApplicationContext());


        getSkillsAsyncTask = new GetSkillsAsyncTask(getApplicationContext());
        if((localDatabaseHandler.getPersonalSkillsCount() == 0) || (localDatabaseHandler.getTechnicalSkillsCount() == 0))
        {
            getSkillsAsyncTask.execute();
        }
        if(localDatabaseHandler.getStudentDataCount() == 0)
        {
            studentDataAsyncTask.execute();
        }
        if(getSkillsAsyncTask.getStatus() == AsyncTask.Status.FINISHED && studentDataAsyncTask.getStatus() == AsyncTask.Status.FINISHED)
        {
            stopSelf();
        }
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.print("Timer task in service");
            }
        },0,9000);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localDatabaseHandler = new LocalDatabaseHandler(getApplicationContext());
        studentDataAsyncTask = new StudentDataAsyncTask(getApplicationContext());
        Functions functions = new Functions(getApplicationContext());


        getSkillsAsyncTask = new GetSkillsAsyncTask(getApplicationContext());
        if((localDatabaseHandler.getPersonalSkillsCount() == 0) || (localDatabaseHandler.getTechnicalSkillsCount() == 0))
        {
            getSkillsAsyncTask.execute();
        }
        if(localDatabaseHandler.getStudentDataCount() == 0)
        {
            studentDataAsyncTask.execute();
        }
        if(getSkillsAsyncTask.getStatus() == AsyncTask.Status.FINISHED && studentDataAsyncTask.getStatus() == AsyncTask.Status.FINISHED)
        {
            stopSelf();
        }
    }
}