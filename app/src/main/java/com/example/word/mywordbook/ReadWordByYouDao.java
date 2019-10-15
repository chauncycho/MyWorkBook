package com.example.word.mywordbook;

import android.util.Log;

import com.example.word.mywordbook.wordcontract.Words;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送请求给有道，获取查询的单词
 */
public class ReadWordByYouDao extends Thread{

    private String word;    //查询的单词
    private String resultJson;  //返回的Json
    private static final String TAG = "myTag";

    public ReadWordByYouDao(String word){
        this.word = word;
    }

    public void run(){
        try {
            URL url = new URL("http://fanyi.youdao.com/openapi.do");  //api地址
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //创建请求
            connection.addRequestProperty("encoding", "utf-8");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStream out = connection.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(out);
            BufferedWriter bufferW = new BufferedWriter(outWriter);

            String require = "keyfrom=haobaoshui&key=1650542691&type=data&doctype=json&version=1.1&q="; //请求参数

            bufferW.write(require + word);
            bufferW.flush();

            InputStream in = connection.getInputStream();  //接收请求返回内容
            InputStreamReader inReader = new InputStreamReader(in);
            BufferedReader bufferR = new BufferedReader(inReader);

            String line;
            StringBuilder strBuilder = new StringBuilder();
            while((line = bufferR.readLine()) != null){
                strBuilder.append(line);
            }

            out.close();
            outWriter.close();
            bufferW.close();

            in.close();
            inReader.close();
            bufferR.close();

            resultJson = strBuilder.toString();  //结果保存到resultJson
            Log.i(TAG, "run: " + resultJson);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Words.YouDaoWord getYouDaoWord(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("web");
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<jsonArray.length(); i++){
            map.put(jsonArray.getJSONObject(i).getString("key"), jsonArray.getJSONObject(i).getString("value"));
        }
        return new Words.YouDaoWord(jsonObject.getString("query"), jsonObject.getString("translation"), map);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}

