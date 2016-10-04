package com.baekcedar.android.permission_runtime;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 유효성체크 - 권한을 획득하기 전
        // checkSelfPermission()

        // 2. 권한에 대한 부가적인 설명이 필요할 경우 호출
        // shouldShowRequestPermissionRationale();

        // 3. 권한을 획득하기 위해 호출
        // requestPermissions();

        // 완료 후 최종 결과값을 처리하는 callback 함수가 자동으로 호출된다.
        // onRequestPermissionsResult();

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            createFile();
        }
        else {
            checkPermissions();
        }




    } //onCreate

    // 파일생성
    private void createFile(){
        String filePath = Environment.getExternalStorageDirectory().getPath();
        Log.i("rootPath",filePath);
        try {
            File file = new File(filePath + File.separator + "temp.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 권한 허용  시스템 팝업창
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions(){
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // 쓰기 권한이 없으면 로직 처리
            // 중간에 권한 내용에 대한 알림을 처리하는 함수
            // shouldShowRequestPermissionRationale();
            String permissionArray[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissionArray, REQUEST_CODE);
        }else{
            // 쓰기권한이 있으면 파일생성
            createFile();
        }
    }

    // Generate -> override Methods -> onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case  REQUEST_CODE :
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    createFile();
                }
                break;
        }
    }
}//class

