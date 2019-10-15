package com.example.word.mywordbook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.word.*;

/**
 * 内容跟提供器，供外界查询dict表
 */

public class WordsProvider extends ContentProvider {

    //dict表相对应的数字
    public static final int DICT_NUM = 0;

    //包路径authority
    public static final String AUTHORITY = "com.example.amy.wordsprovider";

    private static UriMatcher uriMatcher;
    private WordsDBHelper dbhelper;

    //静态代码块做初始化
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"words",DICT_NUM);

    }


    public WordsProvider() {


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int delectRows = 0;
        switch (uriMatcher.match(uri)){
            case DICT_NUM:
                delectRows = db.delete("words", selection, selectionArgs);
                break;
        }

        return delectRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case DICT_NUM:
                return "vnd.android.cursor.dir/vnd.com.example.amy.wordsprovider";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case DICT_NUM:
                db.insert("words",null,values);
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbhelper = new WordsDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = null;
        //判断是查询哪个表
        switch (uriMatcher.match(uri)){
            case DICT_NUM:
                cursor = db.query("words",projection,selection,selectionArgs,null,null,sortOrder);

                break;
        }

        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int updateraws = 0;
        switch (uriMatcher.match(uri)){
            case DICT_NUM:
                updateraws = db.update("words", values, selection, selectionArgs);
                break;
        }
        return updateraws;
    }
}