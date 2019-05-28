package com.example.genius_plaza_sample;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.genius_plaza_sample.Adapter.User_data_adapter;
import com.example.genius_plaza_sample.Model.UserPojo;
import com.example.genius_plaza_sample.View.Httpclientclass;
import com.example.genius_plaza_sample.View.connectionDector;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    RecyclerView user_recycler_view;
    LinearLayoutManager linearLayoutManager;
    TextView txt_appliances_norecord;
    SwipeRefreshLayout refreshLayout;
    private FloatingActionButton mFAB;
    User_data_adapter userDataAdapter;
    private List<UserPojo> User_data_List=new ArrayList<>();
    //private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFAB = (FloatingActionButton) findViewById(R.id.userAdd_fab);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);
        user_recycler_view=(RecyclerView)findViewById(R.id.user_recycler_view);
        txt_appliances_norecord=(TextView)findViewById(R.id.txt_user_norecord);


        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.loginbtncolor));

        user_recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(new connectionDector(MainActivity.this).isConnectingToInternet()) {

                    getUserList();
                }
                else
                {
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exam_PracAddEditActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener swipeRefreshListner = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            if(new connectionDector(MainActivity.this).isConnectingToInternet()) {


                getUserList();
            }
            else
            {
                refreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                swipeRefreshListner.onRefresh();
            }
        });
    }


    private void getUserList()
    {
        Httpclientclass.get(MainActivity.this,"api/users",null,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response",response.toString());

                if(response!=null)
                {
                    try {

                        JSONObject jsonObject=new JSONObject(""+response);
                        JSONArray array = jsonObject.getJSONArray("data");

                        Log.d("array_Appliances",array.toString());
                        User_data_List.clear();

                        if(array.length()>0)
                        {
                            for(int i=0;i<array.length();i++)
                            {

                                JSONObject object=array.getJSONObject(i);

                                UserPojo data=new UserPojo();
                                data.User_FirstName=object.getString("first_name");
                                data.User_LastName=object.getString("last_name");
                                data.User_avatar=object.getString("avatar");

                                User_data_List.add(data);
                            }
                            txt_appliances_norecord.setVisibility(View.GONE);
                            refreshLayout.setRefreshing(false);
                            userDataAdapter=new User_data_adapter(MainActivity.this,User_data_List);
                            user_recycler_view.setAdapter(userDataAdapter);

                        }
                        else {
                            refreshLayout.setRefreshing(false);
                            txt_appliances_norecord.setVisibility(View.VISIBLE);
                            User_data_List.clear();
                            userDataAdapter=new User_data_adapter(MainActivity.this,User_data_List);
                            user_recycler_view.setAdapter(userDataAdapter);
                        }

                        /*}
                        else
                        {
                            refreshLayout.setRefreshing(false);
                            txt_appliances_norecord.setVisibility(View.VISIBLE);
                            txt_appliances_norecord.setText(response.getString("responseMsg"));
                            User_data_List.clear();
                            userDataAdapter=new User_data_adapter(MainActivity.this,User_data_List);
                            user_recycler_view.setAdapter(userDataAdapter);
                        }*/
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

}

