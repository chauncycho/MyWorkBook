package com.example.word.mywordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amy.mywordbook.R;
import com.example.word.mywordbook.wordcontract.Words;


public class YouDaoActivity extends AppCompatActivity {

    private TextView word;
    private TextView translation;
    private TextView web;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youdao);

        Intent intent = getIntent();
        final Words.YouDaoWord youDaoWord = (Words.YouDaoWord)intent.getSerializableExtra("youdaoWord");

        word = (TextView)findViewById(R.id.youdaoword);
        translation = (TextView)findViewById(R.id.youdaowordmeaning);
        web = (TextView)findViewById(R.id.youdaowordweb);
        update = (Button)findViewById(R.id.btn_update);

        word.setText(youDaoWord.query);
        translation.setText(youDaoWord.translation);
        web.setText(youDaoWord.web.toString());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordsDB wordsDB=WordsDB.getWordsDB();

                    wordsDB.Insert(youDaoWord.query, youDaoWord.translation, youDaoWord.web.toString());
                    Toast.makeText(YouDaoActivity.this, "单词添加成功", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
