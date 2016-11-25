package com.jredthree.ffmpegasdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cn.dennishucd.FFmpegNative;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)this.findViewById(R.id.textview_hello);
        final EditText urlEdittext_input= (EditText) this.findViewById(R.id.inputedit);
        final EditText urlEdittext_output= (EditText) this.findViewById(R.id.outedit);


        final FFmpegNative ffmpeg = new FFmpegNative();
        int codecID = 28; //28 is the H264 Codec ID

        int res = ffmpeg.avcodec_find_decoder(codecID);

        if(res ==0) {
            tv.setText("Success!");
        }
        else{
            tv.setText("Failed!");
        }

        File file = new File("/storage/emulated/0/shumei.txt");
        // 2.定义存储空间
        byte[] buffer = new byte[1024];
        // 3.开始读文件
        int len = -1;
        
        String s = "";
        try {
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(file));

            if (inputStream != null) {
                while ((len = inputStream.read(buffer)) != -1) {
                   String a = new String(buffer,"UTF-8");
                    s = s+ a;
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.text)).setText(s);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderurl= Environment.getExternalStorageDirectory().getPath();

                String urltext_input=urlEdittext_input.getText().toString();
                final String inputurl=folderurl+"/"+urltext_input;

                String urltext_output=urlEdittext_output.getText().toString();
                final String outputurl=folderurl+"/"+urltext_output;

                Log.i("inputurl",inputurl);
                Log.i("outputurl",outputurl);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ffmpeg.decode(inputurl,outputurl);
                    }
                }).start();

            }
        });
    }


}
