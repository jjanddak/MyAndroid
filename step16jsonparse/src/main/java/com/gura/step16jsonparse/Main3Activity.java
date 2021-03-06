package com.gura.step16jsonparse;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity
                        implements Util.RequestListener{
    //필드
    private ArrayAdapter<String> adapter;
    private List<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //모델
        names=new ArrayList<>();
        //아답타
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                names
        );
        //ListView
        ListView listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //스프링 웹서버에 요청하기
        String urlAddr="http://192.168.0.2:8888/spring05/android/getmember.do";
        Util.sendGetRequest(0, urlAddr, null, this);
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        /*
            [
                {"NUM":1,"NAME":"김구라","ADDR":"노량진"},
                {"NUM":2,"NAME":"해골","ADDR":"행신동"},
                {"NUM":3,"NAME":"원숭이","ADDR":"동물원"},
                {},
                {},
                .
                .
                .
            ]
         */
        String data=(String)result.get("data");
        // [] 형식은 JSONArray 에 대응된다.
        try {
            JSONArray arr=new JSONArray(data);
            //반복문 돌면서
            for(int i=0; i<arr.length(); i++){

                JSONObject obj=arr.getJSONObject(i);
                int num=obj.getInt("NUM");
                String name=obj.getString("NAME");
                String addr=obj.getString("ADDR");
                String info=num+"|"+name+"|"+addr;
                //모델에 추가
                names.add(info);
            }
            //아답타에 모델의 데이터가 바뀌었다고 알려서 ListView 가 업데이트 되도록한다.
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        String data=(String)result.get("data");
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}








