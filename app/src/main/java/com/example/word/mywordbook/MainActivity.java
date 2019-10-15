package com.example.word.mywordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amy.mywordbook.R;
import com.example.word.mywordbook.wordcontract.Words;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //工具栏
        setSupportActionBar(toolbar);


        Button book = (Button)findViewById(R.id.my);    //点击进入单词本
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,BookActivity.class);
                startActivity(intent);
            }
        });

        Button news = (Button)findViewById(R.id.news);  //点击进入新闻
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,News.class);
                startActivity(intent);
            }
        });

        Button search = (Button)findViewById(R.id.search);
        final EditText in = (EditText)findViewById(R.id.in);    //点击查询有道单词
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadWordByYouDao wordByYouDao = new ReadWordByYouDao(in.getText().toString());
                wordByYouDao.start();
                try {
                    wordByYouDao.join();
                    Words.YouDaoWord youdaoWord = wordByYouDao.getYouDaoWord(wordByYouDao.getResultJson()); //查到单词跳转到单词页面
                    Intent i = new Intent(MainActivity.this, YouDaoActivity.class);
                    i.putExtra("youdaoWord", youdaoWord);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
