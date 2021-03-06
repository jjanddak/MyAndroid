package com.gura.step12sharedpref;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener,
                            CompoundButton.OnCheckedChangeListener{
    //필요한 필드
    private EditText editText;
    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //EditText 참조값 얻어와서 필드에 저장하기
        editText=findViewById(R.id.editText);
        //Button 의 참조값 얻어와서 리스너 등록하기
        Button saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        //Switch 의 참조값을 필드에 저장하기
        sw=findViewById(R.id.switch1);
        //스위치의 체크 상태가 바뀌었음을 감시할 리스너등록
        sw.setOnCheckedChangeListener(this);

        SharedPreferences pref=
                getSharedPreferences("info", MODE_PRIVATE);
        // .getString( key 값, default 값)
        String myName=pref.getString("myName", "empty");
        if(myName.equals("empty")){
            return;
        }
        //저장된 이름을 editText 에 출력하기
        editText.setText(myName);
        //스위치 체크 여부 읽어오기
        boolean isChecked=pref.getBoolean("isChecked", false);
        //스위치의 체크 상태 반영하기
        sw.setChecked(isChecked);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴 전개자 객체를 이용해서 res/menu/menu_main.xml 문서를 전개 해서 메뉴를 구성한다.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //선택된 메뉴 아이템의 아이디값을 읽어온다.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else if(id == R.id.finish){
            //액티비티 종료 시키기
            finish();
        }else if(id == R.id.start){

        }
        return true;
    }

    @Override
    public void onClick(View view) {
        //입력한 문자열 읽어오기
        String inputName=editText.getText().toString();
        //Switch 체크 상태 읽어오기
        boolean isChecked=sw.isChecked();

        //SharedPreferences 를 활용해서 저장하기

        SharedPreferences pref=
                getSharedPreferences("info", MODE_PRIVATE);
        //Editor 객체의 참조값을 얻어와서
        SharedPreferences.Editor editor=pref.edit();
        //myName 이라는 키값으로 문자열 저장하기
        editor.putString("myName", inputName);
        editor.putBoolean("isChecked", isChecked);

        editor.commit(); //실제 저장되는 시점
        //알림 띄우기
        new AlertDialog.Builder(this)
                .setMessage("저장 했습니다.")
                .setNeutralButton("확인", null)
                .show();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            sw.setText("On");
        }else{
            sw.setText("Off");
        }
    }
    //Setting 액티비티에서 저장했던 값을 읽어내는 메소드
    public void readSettings(View v){
        //디폴트로 사용하는 SharedPreferences 객체의 참조값 얻어오기
        SharedPreferences pref=
                PreferenceManager.getDefaultSharedPreferences(this);
        // signature 라는 키값으로 저장된 문자열 읽어오기
        String signature=pref.getString("signature", "");
        // reply 라는 키값으로 저장된 문자열 읽어오기
        String reply=pref.getString("reply", "");
        // sync 라는 키값으로 저장된 boolean 값 읽어오기
        boolean sync=pref.getBoolean("sync", false);

        String info="signature:"+signature+" | reply:"+reply+
                " | sync:"+sync;
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();

    }
}









