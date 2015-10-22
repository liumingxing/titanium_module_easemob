package com.easemob.chatuidemo.ui;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.easemob.chatuidemo.R;
import com.easemob.easeui.utils.EaseUserUtils;
import com.easemob.exceptions.EaseMobException;

import org.appcelerator.titanium.util.TiRHelper;
import com.mamashai.easemob.RHelper;

/**
 * 黑名单列表页面
 * 
 */
public class BlacklistActivity extends Activity {
	private ListView listView;
	private BlacklistAdapater adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(RHelper.get("layout.em_activity_black_list"));

		listView = (ListView) findViewById(RHelper.get("id.list"));

		// 从本地获取黑名单
		 List<String> blacklist = EMContactManager.getInstance().getBlackListUsernames();

		// 显示黑名单列表
		if (blacklist != null) {
			Collections.sort(blacklist);
			adapter = new BlacklistAdapater(this, 1, blacklist);
			listView.setAdapter(adapter);
		}

		// 注册上下文菜单
		registerForContextMenu(listView);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		try{
			getMenuInflater().inflate(TiRHelper.getApplicationResource("menu.em_remove_from_blacklist"), menu);
		}
		catch(Exception e){
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == RHelper.get("id.remove")) {
			final String tobeRemoveUser = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
			// 把目标user移出黑名单
			removeOutBlacklist(tobeRemoveUser);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	/**
	 * 移出黑民单
	 * 
	 * @param tobeRemoveUser
	 */
	void removeOutBlacklist(final String tobeRemoveUser) {
	    final ProgressDialog pd = new ProgressDialog(this);
	    pd.setMessage(getString(RHelper.get("string.be_removing")));
	    pd.setCanceledOnTouchOutside(false);
	    pd.show();
	    new Thread(new Runnable() {
            public void run() {
                try {
                    // 移出黑民单
                    EMContactManager.getInstance().deleteUserFromBlackList(tobeRemoveUser);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            adapter.remove(tobeRemoveUser);
                        }
                    });
                } catch (EaseMobException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), RHelper.get("string.Removed_from_the_failure"), 0).show();
                        }
                    });
                }
            }
        }).start();
	}

	/**
	 * adapter
	 * 
	 */
	private class BlacklistAdapater extends ArrayAdapter<String> {

		public BlacklistAdapater(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				try{
				convertView = View.inflate(getContext(), TiRHelper.getApplicationResource("layout.ease_row_contact"), null);
				}
				catch(Exception e){
				}
			}
			String username = getItem(position);
			TextView name = (TextView) convertView.findViewById(RHelper.get("id.name"));
			ImageView avatar = (ImageView) convertView.findViewById(RHelper.get("id.avatar"));
			
			EaseUserUtils.setUserAvatar(getContext(), username, avatar);
			EaseUserUtils.setUserNick(username, name);
			
			return convertView;
		}

	}

	/**
	 * 返回
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}
}
