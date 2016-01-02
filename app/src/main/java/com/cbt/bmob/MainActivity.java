package com.cbt.bmob;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private static final String APP_ID = "8af0494c3768a952f4dfe5ae6616be63";
    private EditText mEtName;
    private EditText mEtFeedback;
    private EditText mEtQueryOne;
    private Button mBtnSubmit;
    private Button mBtnQueryAll;
    private Button mBtnQueryOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Bmob
        Bmob.initialize(this,APP_ID);

        //消息推送
        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(this, APP_ID);

        mEtName = (EditText) findViewById(R.id.name);
        mEtFeedback = (EditText) findViewById(R.id.feedback);
        mEtQueryOne = (EditText) findViewById(R.id.et_query_name);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnQueryAll = (Button) findViewById(R.id.btn_query);
        mBtnQueryOne = (Button) findViewById(R.id.btn_query_one);

        //保存数据
        mBtnSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = mEtName.getText().toString();
                String feedbackStr = mEtFeedback.getText().toString();
                if ("".equals(nameStr) || "".equals(feedbackStr)) {
                    return;
                }
                Feedback feedback = new Feedback();
                feedback.setName(nameStr);
                feedback.setFeedback(feedbackStr);
                feedback.save(MainActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(MainActivity.this,"FAILURE",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //查询所有
        mBtnQueryAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Feedback> query = new BmobQuery<Feedback>();
                query.findObjects(MainActivity.this, new FindListener<Feedback>() {
                    @Override
                    public void onSuccess(List<Feedback> list) {
                        Builder builder = new Builder(MainActivity.this);
                        builder.setTitle("Query");
                        StringBuffer str = new StringBuffer();
                        for (Feedback feedback : list) {
                            str.append("Name: " + feedback.getName() + ", Feedback: " + feedback.getFeedback() + "\n");
                        }

                        builder.setMessage(str.toString());
                        builder.create().show();
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });

            }
        });

        //条件查询
        mBtnQueryOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEtQueryOne.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                BmobQuery<Feedback> query = new BmobQuery<Feedback>();

                query.addWhereEqualTo("name",str);
                query.findObjects(MainActivity.this, new FindListener<Feedback>() {
                    @Override
                    public void onSuccess(List<Feedback> list) {
                        Builder builder = new Builder(MainActivity.this);
                        builder.setTitle("Query");
                        StringBuffer str = new StringBuffer();
                        for (Feedback feedback : list) {
                            str.append("Name: " + feedback.getName() + ", Feedback: " + feedback.getFeedback() + "\n");
                        }
                        if (TextUtils.isEmpty(str.toString())) {
                            str.append("Sorry，No such a name");
                        }
                        builder.setMessage(str.toString());
                        builder.create().show();
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        });

    }
}
