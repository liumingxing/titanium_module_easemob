package com.easemob.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.chat.VoiceMessageBody;
import com.easemob.easeui.R;
import com.easemob.util.EMLog;

import org.appcelerator.titanium.util.TiRHelper;
import com.mamashai.easemob.RHelper;

public class EaseChatRowVoice extends EaseChatRowFile{

    private ImageView voiceImageView;
    private TextView voiceLengthView;
    private ImageView readStutausView;

    public EaseChatRowVoice(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
    	try{
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                TiRHelper.getApplicationResource("layout.ease_row_received_voice") : TiRHelper.getApplicationResource("layout.ease_row_sent_voice"), this);
        }
        catch(Exception e){
        }
    }

    @Override
    protected void onFindViewById() {
    	try{
        voiceImageView = ((ImageView) findViewById(TiRHelper.getApplicationResource("id.iv_voice")));
        voiceLengthView = (TextView) findViewById(TiRHelper.getApplicationResource("id.tv_length"));
        readStutausView = (ImageView) findViewById(TiRHelper.getApplicationResource("id.iv_unread_voice"));
        }
        catch(Exception e){
        }
    }

    @Override
    protected void onSetUpView() {
        VoiceMessageBody voiceBody = (VoiceMessageBody) message.getBody();
        int len = voiceBody.getLength();
        if(len>0){
            voiceLengthView.setText(voiceBody.getLength() + "\"");
            voiceLengthView.setVisibility(View.VISIBLE);
        }else{
            voiceLengthView.setVisibility(View.INVISIBLE);
        }
        if (EaseChatRowVoicePlayClickListener.playMsgId != null
                && EaseChatRowVoicePlayClickListener.playMsgId.equals(message.getMsgId()) && EaseChatRowVoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(RHelper.get("anim.voice_from_icon"));
            } else {
                voiceImageView.setImageResource(RHelper.get("anim.voice_to_icon"));
            }
            voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(RHelper.get("drawable.ease_chatfrom_voice_playing"));
            } else {
                voiceImageView.setImageResource(RHelper.get("drawable.ease_chatto_voice_playing"));
            }
        }
        
        if (message.direct == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // 隐藏语音未听标志
                readStutausView.setVisibility(View.INVISIBLE);
            } else {
                readStutausView.setVisibility(View.VISIBLE);
            }
            EMLog.d(TAG, "it is receive msg");
            if (message.status == EMMessage.Status.INPROGRESS) {
                progressBar.setVisibility(View.VISIBLE);
                setMessageReceiveCallback();
            } else {
                progressBar.setVisibility(View.INVISIBLE);

            }
            return;
        }

        // until here, deal with send voice msg
        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        new EaseChatRowVoicePlayClickListener(message, voiceImageView, readStutausView, adapter, activity).onClick(bubbleLayout);
    }
    
}
