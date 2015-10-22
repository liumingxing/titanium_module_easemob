/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.chatuidemo.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chatuidemo.DemoHelper;
import com.easemob.chatuidemo.R;
import com.easemob.easeui.widget.EaseAlertDialog;
import org.appcelerator.titanium.util.TiRHelper;
import com.mamashai.easemob.RHelper;

public class AddContactActivity extends BaseActivity{
	private EditText editText;
	private LinearLayout searchedUserLayout;
	private TextView nameText,mTextView;
	private Button searchBtn;
	private ImageView avatar;
	private InputMethodManager inputMethodManager;
	private String toAddUsername;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(RHelper.get("layout.em_activity_add_contact"));
		mTextView = (TextView) findViewById(RHelper.get("id.add_list_friends"));
		
		editText = (EditText) findViewById(RHelper.get("id.edit_note"));
		String strAdd = getResources().getString(RHelper.get("string.add_friend"));
		mTextView.setText(strAdd);
		String strUserName = getResources().getString(RHelper.get("string.user_name"));
		editText.setHint(strUserName);
		searchedUserLayout = (LinearLayout) findViewById(RHelper.get("id.ll_user"));
		nameText = (TextView) findViewById(RHelper.get("id.name"));
		searchBtn = (Button) findViewById(RHelper.get("id.search"));
		avatar = (ImageView) findViewById(RHelper.get("id.avatar"));
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	
	/**
	 * 查找contact
	 * @param v
	 */
	public void searchContact(View v) {
		final String name = editText.getText().toString();
		String saveText = searchBtn.getText().toString();
		
		if (getString(RHelper.get("string.button_search")).equals(saveText)) {
			toAddUsername = name;
			if(TextUtils.isEmpty(name)) {
				new EaseAlertDialog(this, RHelper.get("string.Please_enter_a_username")).show();
				return;
			}
			
			// TODO 从服务器获取此contact,如果不存在提示不存在此用户
			
			//服务器存在此用户，显示此用户和添加按钮
			searchedUserLayout.setVisibility(View.VISIBLE);
			nameText.setText(toAddUsername);
			
		} 
	}	
	
	/**
	 *  添加contact
	 * @param view
	 */
	public void addContact(View view){
		if(EMChatManager.getInstance().getCurrentUser().equals(nameText.getText().toString())){
			new EaseAlertDialog(this, RHelper.get("string.not_add_myself")).show();
			return;
		}
		
		if(DemoHelper.getInstance().getContactList().containsKey(nameText.getText().toString())){
		    //提示已在好友列表中(在黑名单列表里)，无需添加
		    if(EMContactManager.getInstance().getBlackListUsernames().contains(nameText.getText().toString())){
		        new EaseAlertDialog(this, RHelper.get("string.user_already_in_contactlist")).show();
		        return;
		    }
			new EaseAlertDialog(this, RHelper.get("string.This_user_is_already_your_friend")).show();
			return;
		}
		
		progressDialog = new ProgressDialog(this);
		String stri = getResources().getString(RHelper.get("string.Is_sending_a_request"));
		progressDialog.setMessage(stri);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				
				try {
					//demo写死了个reason，实际应该让用户手动填入
					String s = getResources().getString(RHelper.get("string.Add_a_friend"));
					EMContactManager.getInstance().addContact(toAddUsername, s);
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							String s1 = getResources().getString(RHelper.get("string.send_successful"));
							Toast.makeText(getApplicationContext(), s1, 1).show();
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							String s2 = getResources().getString(RHelper.get("string.Request_add_buddy_failure"));
							Toast.makeText(getApplicationContext(), s2 + e.getMessage(), 1).show();
						}
					});
				}
			}
		}).start();
	}
	
	public void back(View v) {
		finish();
	}
}
