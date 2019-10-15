package com.example.word.mywordbook;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.word.mywordbook.wordcontract.Words;

public class WordsDBHelper extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "wordsdb";  //数据库名称
    private final static int DATABASE_VERSION = 1;          //数据库版本

    /**
     *建表SQL
     * 表名： Words.Word.TABLE_NAME
     * 该表中共4个字段：_ID, COLUMN_NAME_WORD, COLUMN_NAME_MEANING, COLUMN_NAME_SAMPLE
     */

    private final static String SQL_CREATE_DATABASE = "CREATE TABLE " +
            Words.Word.TABLE_NAME + " (" +
            Words.Word._ID + " VARCHAR(32) PRIMARY KEY NOT NULL," +
            Words.Word.COLUMN_NAME_WORD + " TEXT UNIQUE NOT NULL,"+
            Words.Word.COLUMN_NAME_MEANING + " TEXT," +
            Words.Word.COLUMN_NAME_SAMPLE + " TEXT)";

    //删表SQL
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " +
            Words.Word.TABLE_NAME;


    //构造方法（继承父类）
    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库（调用父类的execSQL()方法）
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //当数据库升级时被调用，先删除旧表，然后调用OnCreate()创建新表
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }

}

