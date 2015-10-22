/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.mamashai.easemob;


import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import com.easemob.chatuidemo.DemoHelper;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.ui.MainActivity;
import com.easemob.chatuidemo.ui.ChatActivity;
import com.easemob.chatuidemo.ui.LoginActivity;
import com.easemob.chatuidemo.R;
import com.easemob.easeui.utils.EaseCommonUtils;

import android.text.TextUtils;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;

@Kroll.module(name="EasemobAndroid", id="com.mamashai.easemob")
public class EasemobAndroidModule extends KrollModule
{

	// Standard Debugging variables
	private static final String LCAT = "EasemobAndroidModule";
	private static final boolean DBG = TiConfig.LOGD;
	
	private String currentUsername;
	private String currentPassword;
	private String currentNick;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public EasemobAndroidModule()
	{
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(LCAT, "inside onAppCreate");
		// put module init code that needs to run when the application is created
	}
	
	@Kroll.method
	public String config(String key, String cert)
	{
		DemoHelper.getInstance().init(TiApplication.getInstance().getApplicationContext());
		return "ok";
	}
	
	@Kroll.method
	public String login(String username, String password, String nick)
	{
		if (TextUtils.isEmpty(username)) {
			return "用户名不能为空";
		}
		if (TextUtils.isEmpty(password)) {
			return "密码不能为空";
		}
		
		currentUsername = username;
		currentPassword = password;
		currentNick = nick;

		final long start = System.currentTimeMillis();
		
		EMChatManager.getInstance().login(username, password, new EMCallBack() {
			@Override
			public void onSuccess() {
				DemoHelper.getInstance().setCurrentUserName(currentUsername);
				DemoHelper.getInstance().registerGroupAndContactListener();

			    EMGroupManager.getInstance().loadAllGroups();
				EMChatManager.getInstance().loadAllConversations();
				
				boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(currentNick);
				if (!updatenick) {
					Log.e("LoginActivity", "update current user nick fail");
				}
				DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
				
				Log.d(LCAT, "登录成功");
				/*
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				*/
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {
				Log.d(LCAT, "登录失败");
			}
		});
		return "ok";
	}
	
	@Kroll.method
	public String chatWithUser(String username, String nickname){
		//startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
		
		/*
		Intent intent = new Intent(TiApplication.getInstance().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TiApplication.getInstance().getApplicationContext().startActivity(intent);
        */
        
        
        Intent intent = new Intent(TiApplication.getInstance().getApplicationContext(), ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", username);
        Log.d(LCAT, username);
        TiApplication.getInstance().getApplicationContext().startActivity(intent);
        
        /*
        Intent intent = new Intent(TiApplication.getInstance().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userId", username);
        Log.d(LCAT, username);
        TiApplication.getInstance().getApplicationContext().startActivity(intent);
        */
  		return "ok";
	}
}

