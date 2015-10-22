package com.easemob.chatuidemo.ui;

import android.content.Intent;
import android.os.Bundle;

import com.easemob.chatuidemo.R;
import com.easemob.easeui.ui.EaseChatFragment;
import org.appcelerator.titanium.util.TiRHelper;

import android.util.Log;


/**
 * 聊天页面，需要fragment的使用{@link #EaseChatFragment}
 *
 */
public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        try{
        	setContentView(TiRHelper.getApplicationResource("layout.em_activity_chat"));
        }
        catch(Exception e){
        	Log.d("map", "-------fuck-------");
        }
        activityInstance = this;
        //聊天人或群id
        toChatUsername = getIntent().getExtras().getString("userId");
        //可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        try{
        	getSupportFragmentManager().beginTransaction().add(TiRHelper.getApplicationResource("id.container"), chatFragment).commit();
        }
        catch(Exception e){
        }
        
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
