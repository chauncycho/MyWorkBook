package com.example.word.mywordbook;

import android.app.Application;
import android.content.Context;

public class WordsApplication extends Application {

    private static Context context;

    //获取上下文
    public static Context getContext(){
        return WordsApplication.context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        WordsApplication.context=getApplicationContext();
    }

}