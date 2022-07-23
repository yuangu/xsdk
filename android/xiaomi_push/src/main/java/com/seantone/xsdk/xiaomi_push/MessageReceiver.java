package com.seantone.xsdk.xiaomi_push;

import android.content.Context;
import java.util.List;
import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.plugin.xiaomi_push.SDK;
import com.xiaomi.mipush.sdk.*;

import org.json.JSONObject;

public class MessageReceiver  extends PushMessageReceiver {

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null)
        {
            return;
        }

        JSONObject ret = new JSONObject();
        try {
            ret.put("provider",  SDK.provider);
            ret.put("title", message.getTitle());
            ret.put("payload", message.getContent());
            ret.put("description",message.getDescription());
            ret.put("isPassThrough", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSDK.getInstance().getPushCallBack().onNotificationMessageArrived(ret.toString());
    }
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null)
        {
            return;
        }

        JSONObject ret = new JSONObject();
        try {
            ret.put("provider", SDK.provider);
            ret.put("title", message.getTitle());
            ret.put("payload", message.getContent());
            ret.put("description",message.getDescription());
            ret.put("isPassThrough", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSDK.getInstance().getPushCallBack().onNotificationMessageClicked(ret.toString());
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null)
        {
            return;
        }
//        mMessage = message.getContent();
//        if(!TextUtils.isEmpty(message.getTopic())) {
//            mTopic=message.getTopic();
//        } else if(!TextUtils.isEmpty(message.getAlias())) {
//            mAlias=message.getAlias();
//        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount=message.getUserAccount();
//        }
        JSONObject ret = new JSONObject();
        try {
            ret.put("provider",  SDK.provider);
            ret.put("title", message.getTitle());
            ret.put("payload", message.getContent());
            ret.put("description",message.getDescription());
            ret.put("isPassThrough", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XSDK.getInstance().getPushCallBack().onNotificationMessageArrived(ret.toString());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
//        String command = message.getCommand();
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mStartTime = cmdArg1;
//                mEndTime = cmdArg2;
//            }
//        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        if(XSDK.getInstance().getPushCallBack() == null)
        {
            return;
        }

        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                JSONObject ret = new JSONObject();
                try {
                    ret.put("regId",  cmdArg1);
                    ret.put("provider", SDK.provider);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                XSDK.getInstance().getPushCallBack().onRegisterSucceed(ret.toString());
            }
        }
    }
}