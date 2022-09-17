package com.seantone.xsdk.plugin.webchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.seantone.xsdk.core.XSDK;
import com.seantone.xsdk.core.annotation.ForLogin;
import com.seantone.xsdk.core.annotation.ForPay;
import com.seantone.xsdk.core.annotation.ForShare;
import com.seantone.xsdk.core.define.LoginParams;
import com.seantone.xsdk.core.define.PayParams;
import com.seantone.xsdk.core.define.SDKParams;
import com.seantone.xsdk.core.define.ShareParams;
import com.seantone.xsdk.core.error.ErrCode;
import com.seantone.xsdk.core.impl.ILogin;
import com.seantone.xsdk.core.impl.IPay;
import com.seantone.xsdk.core.impl.ISDK;
import com.seantone.xsdk.core.impl.IShare;
import com.seantone.xsdk.core.impl.IXSDKCallback;
import com.seantone.xsdk.utils.ShareUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ForPay(provider = "webchat")
@ForLogin(provider = "webchat")
@ForShare(provider = "webchat")
public class SDK implements ISDK, IPay, ILogin, IShare {
    static String TAG = "XSDK";

    private IWXAPI iwxapi = null;
    private static SDK mInstace = null;

    private Map<String, IXSDKCallback> mCallBackMap = new HashMap<String, IXSDKCallback>();

    public IWXAPI getIWXApi() {
        return iwxapi;
    }

    public static SDK getInstance() {
        return mInstace;
    }

    private boolean checkInstallWebChat(IXSDKCallback callback)
    {
        if(iwxapi.isWXAppInstalled()){
            return true;
        }

        JSONObject ret = new JSONObject();
        try {
            ret.put("errCode", ErrCode.ERR_NO_INSTALL_TARGET_APP);
            ret.put("errMsg", "您的手机没有安装微信，请先安装微信");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //发送取消
        callback.onFaild(ret.toString());
        return false;
    }


    @Override
    public void pay(PayParams params, IXSDKCallback callback) {
        if (iwxapi == null) {
            Log.e(TAG, "webchat sdk no init");
            return;
        }
        if(!checkInstallWebChat(callback)) return;
        try {
            PayReq payReq = new PayReq();
            JSONObject data = new JSONObject(params.orderInfo);
            payReq.appId = data.getString("appid");
            payReq.partnerId = data.getString("partnerid");
            payReq.prepayId = data.getString("prepayid");
            payReq.packageValue = data.getString("package");
            payReq.nonceStr = data.getString("noncestr");
            payReq.timeStamp = data.getString("timestamp");
            payReq.sign = data.getString("sign");
            payReq.transaction = UUID.randomUUID().toString();
            mCallBackMap.put(payReq.transaction, callback);
            iwxapi.sendReq(payReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(LoginParams params, IXSDKCallback callback) {
        if (iwxapi == null) {
            Log.e(TAG, "webchat sdk no init");
            return;
        }
        if(!checkInstallWebChat(callback)) return;
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        req.transaction = UUID.randomUUID().toString();
        mCallBackMap.put(req.transaction, callback);
        iwxapi.sendReq(req);
    }

    @Override
    public void isAuthorized(LoginParams params, IXSDKCallback callback) {
        // 微信不支持
        JSONObject ret = new JSONObject();
        try {
            ret.put("ret", false);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        callback.onSuccess(ret.toString());
    }

    @Override
    public void logout(LoginParams params, IXSDKCallback callback) {
        // 微信不支持
        JSONObject ret = new JSONObject();
        try {
            ret.put("ret", true);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        callback.onSuccess(ret.toString());
    }

    @Override
    public void share(ShareParams params, IXSDKCallback callback) {
        if (iwxapi == null) {
            Log.e(TAG, "webchat sdk no init");
            return;
        }
        if(!checkInstallWebChat(callback)) return;
        BaseReq req = null;
        // 分享文本
        if (params.type == 1) {
            req = ShareUtils.shareText(params);
        } else if (params.type == 2) {
            req = ShareUtils.shareImage(params);
        }else if (params.type == 3) {
            req = ShareUtils.shareMusic(params);
        }  else if (params.type == 4){
            req = ShareUtils.shareVideo(params);
        }
        else if (params.type == 5) {
            req = ShareUtils.shareMP(params);
        } else if (params.type == 6) {
            req = ShareUtils.shareWebPage(params);
        }

        if (req != null) {
            mCallBackMap.put(req.transaction, callback);
            iwxapi.sendReq(req);
        }
    }

    // 支付的事件响应
    public void onPayResp(BaseResp resp) {
        // 不存在callBack
        if (!mCallBackMap.containsKey(resp.transaction)) {
            return;
        }

        IXSDKCallback callBack = mCallBackMap.get(resp.transaction);
        mCallBackMap.remove(resp.transaction);
        JSONObject ret = new JSONObject();
        if (resp.errCode == 0)//支付成功
        {
            // 支付成功
            callBack.onSuccess(ret.toString());
        } else if (resp.errCode == -2) {
            // "取消了支付";
            try {
                ret.put("errCode", ErrCode.ERR_USER_CANCEL);
                ret.put("errMsg", "用户取消");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //发送取消
            callBack.onFaild(ret.toString());
        } else {
            try {
                ret.put("errCode", ErrCode.ERR_COMM);
                ret.put("errMsg", resp.errStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //发送取消
            callBack.onFaild(ret.toString());

            // "微信支付失败";
            Log.w("XSDK", resp.errStr);
        }
    }

    // 除支付的事件响应
    public void onResp(BaseResp resp) {
        // 不存在callBack
        if (!mCallBackMap.containsKey(resp.transaction)) {
            return;
        }

        IXSDKCallback callBack = mCallBackMap.get(resp.transaction);
        mCallBackMap.remove(resp.transaction);

        JSONObject ret = new JSONObject();
        // 分享的响应
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            callBack.onSuccess(ret.toString());
            return;
        }

        // 登陆相关的
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                SendAuth.Resp newResp = (SendAuth.Resp) resp;
                //获取微信传回的code
                String code = newResp.code;

                try {
                    ret.put("code", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess(ret.toString());
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                try {
                    ret.put("errCode", ErrCode.ERR_USER_CANCEL);
                    ret.put("errMsg", "用户取消");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //发送取消
                callBack.onFaild(ret.toString());
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                try {
                    ret.put("errCode", ErrCode.ERR_USER_DENIED);
                    ret.put("errMsg", "用户拒绝");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //发送被拒绝
                callBack.onFaild(ret.toString());
                break;
            default:
                //发送返回
                break;
        }
    }

    @Override
    public void initSDK(SDKParams params, IXSDKCallback callback) {
        try {
            iwxapi = WXAPIFactory.createWXAPI(XSDK.getInstance().getTopActivity(),  params.appid, true);
            // 将应用的 appId 注册到微信
            iwxapi.registerApp(params.appid);
            //建议动态监听微信启动广播进行注册到微信
            XSDK.getInstance().getTopActivity().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    iwxapi.registerApp(params.appid);
                }
            }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));


            mInstace = this;
            callback.onSuccess("{}");
        }catch (Exception e)
        {
            callback.onFaild("{}");
            e.printStackTrace();
        }
    }
}
