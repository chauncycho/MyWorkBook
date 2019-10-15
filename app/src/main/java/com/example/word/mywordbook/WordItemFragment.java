package com.example.word.mywordbook;

import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import com.example.amy.mywordbook.R;
import com.example.word.mywordbook.wordcontract.Words;

public class WordItemFragment extends ListFragment {
    private static final String TAG = "myTag";

    private OnFragmentInteractionListener mListener;

    public WordItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);


        //为列表注册上下文菜单
        ListView mListView = (ListView) view.findViewById(android.R.id.list);
        registerForContextMenu(mListView);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "WordItemFragment::onAttach");
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) getActivity();

    }

    //刷新单词列表
    public void refreshWordsList() {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null) {
            ArrayList<Map<String, String>> items = wordsDB.getAllWords();   //从数据库类获取数据

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,  //使用adapter更新数据到ListView
                    new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                    new int[]{R.id.textId, R.id.textViewWord});

            setListAdapter(adapter);
        }
    }

    //模糊查询
    public void refreshWordsList(String strWord) {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null) {
            ArrayList<Map<String, String>> items = wordsDB.SearchUseSql(strWord); //获取查询结果
            if(items.size()>0){

                SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,  //使用adapter更新数据到ListView
                        new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                        new int[]{R.id.textId, R.id.textViewWord});

                setListAdapter(adapter);
            }else{
                Toast.makeText(getActivity(),"Not found",Toast.LENGTH_LONG).show(); //如果查询不到单词，则弹出提示
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //刷新单词列表
        refreshWordsList();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {   //单词长按操作
        TextView textId = null;
        TextView textWord = null;
        TextView textMeaning = null;
        TextView textSample = null;

        AdapterView.AdapterContextMenuInfo info = null;
        View itemView = null;

        switch (item.getItemId()) {
            case R.id.action_delete:
                //删除单词
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); //先获取选择的单词
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId); //获取单词的Id
                if (textId != null) {
                    String strId = textId.getText().toString();
                    mListener.onDeleteDialog(strId);
                }
                break;
            case R.id.action_update:
                //修改单词
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); //先获取选择的单词
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId); //获取单词的Id

                if (textId != null) {
                    String strId = textId.getText().toString();

                    mListener.onUpdateDialog(strId);
                }
                break;
        }
        return true;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {   //为单词绑定长按菜单
        Log.v(TAG, "WordItemFragment::onCreateContextMenu()");
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.contextmenu_wordslistview, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {    //点击单词进入详情
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            TextView textView = (TextView) v.findViewById(R.id.textId); //获取单词Id
            if (textView != null) {
                mListener.onWordItemClick(textView.getText().toString()); //将单词id传入
            }
        }
    }

    /**
     * Fragment所在的Activity必须实现该接口，通过该接口Fragment和Activity可以进行通信
     */
    public interface OnFragmentInteractionListener {
        public void onWordItemClick(String id);

        public void onDeleteDialog(String strId);

        public void onUpdateDialog(String strId);
    }

}