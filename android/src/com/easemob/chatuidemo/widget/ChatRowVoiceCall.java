package com.easemob.chatuidemo.widget;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.chatuidemo.Constant;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.R;
import com.easemob.easeui.widget.chatrow.EaseChatRow;

import org.appcelerator.titanium.util.TiRHelper;
import com.mamashai.easemob.RHelper;

public class ChatRowVoiceCall extends EaseChatRow{

    private TextView contentvView;
    private ImageView iconView;

    public ChatRowVoiceCall(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        try{
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                    TiRHelper.getApplicationResource("layout.ease_row_received_voice_call") : TiRHelper.getApplicationResource("layout.ease_row_sent_voice_call"), this);
        // 视频通话
        }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                    TiRHelper.getApplicationResource("layout.ease_row_received_video_call") : TiRHelper.getApplicationResource("layout.ease_row_sent_video_call"), this);
        }
        }
        catch(Exception e){
        }
    }

    @Override
    protected void onFindViewById() {
        contentvView = (TextView) findViewById(RHelper.get("id.tv_chatcontent"));
        iconView = (ImageView) findViewById(RHelper.get("id.iv_call_icon"));
    }

    @Override
    protected void onSetUpView() {
        TextMessageBody txtBody = (TextMessageBody) message.getBody();
        contentvView.setText(txtBody.getMessage());
    }
    
    @Override
    protected void onUpdateView() {
        
    }

    @Override
    protected void onBubbleClick() {
        
    }

  

}
