package com.example.genius_plaza_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.genius_plaza_sample.Config.Config;
import com.example.genius_plaza_sample.View.CommonClass;
import com.example.genius_plaza_sample.View.Validate;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Exam_PracAddEditActivity extends AppCompatActivity {

    Button btn_AppliancesAddEdit_Submit;
    TextView tvTitle;
    ImageView ivBack;
    EditText edt_User_Name,edt_User_Job;
    private static AsyncHttpClient client = new AsyncHttpClient(true,80,443);
    String str_Name,str_job;
    String name="";
    String job="";
    CommonClass CC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam__prac_add);

        CC=new CommonClass(this);
        btn_AppliancesAddEdit_Submit=findViewById(R.id.btn_UserAddEdit_Submit);
        edt_User_Name=findViewById(R.id.edt_UserAddEdit_Name);
        edt_User_Job=findViewById(R.id.edt_UserAddEdit_Job);

        ivBack=findViewById(R.id.ivback);
        tvTitle=findViewById(R.id.tvTitle);

        btn_AppliancesAddEdit_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    //Check Internet connection also
                    try {
                        UsersAdd_Submit(postJsonFormateData(str_Name, str_job));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void UsersAdd_Submit(JSONObject jsonObject) throws UnsupportedEncodingException {
        RequestParams params = new RequestParams();
        params.put("",jsonObject);

        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        final String url= Config.userURL+"/api/users";
        Log.d("url put",url);

        client.post(Exam_PracAddEditActivity.this,url,entity,"application/json",new JsonHttpResponseHandler(){


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("resonse",""+response);
                        if(response!=null)
                        {
                            try {

                                /*int responseCode=response.getInt("responseCode");
                                Log.d("responseCode",""+responseCode);*/
                                Toast.makeText(Exam_PracAddEditActivity.this,"User Added Successfully",Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                Log.d("Post","PostServerError");
                            }
                        }
                    }
                }
        );
    }
    
    private JSONObject postJsonFormateData(String Name, String Job) {

        JSONObject mainobject = null;

        try {
            mainobject = new JSONObject();
            mainobject.put("name", Name);
            mainobject.put("job", Job);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainobject;
    }

    private boolean checkValidation()
    {
        str_Name = edt_User_Name.getText().toString().trim();
        str_job = edt_User_Job.getText().toString().trim();

        if (Validate.isNull(str_Name)) {
            CC.showToast("Please Enter Name");
            edt_User_Name.requestFocus();
            return false;

        }
        else if (Validate.isNull(str_job)) {
            CC.showToast("Please Enter Job Description");
            edt_User_Job.requestFocus();
            return false;

        }

        else
        {
            return true;
        }
    }
}
