package com.example.targon.killant;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Game extends Activity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new NormalPanel(this, this));

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    public void saveBest(int value){
        sharedPreferences=getSharedPreferences("com.example.targon.killant", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("best", value);
        editor.commit();
    }
    public int getBest(){
        sharedPreferences=getSharedPreferences("com.example.targon.killant", MODE_PRIVATE);
        int a=0;
        a=sharedPreferences.getInt("best", 0);
        return a;
    }
}
