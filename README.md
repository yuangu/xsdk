## 说明
       在应用开发过程中，需要接入各种开放平台的帐号、支付、广告等SDK。某种程度上来说，这个过程是重复的，但存在众多的“坑”。如果能够以一个统一框架接入这些SDK，然后再适配到不同的平台上，比如cocos creator，unity等应用，可以提升不少工作效率。
       本项目是参考了uniapp的api设计的，统一SDK接入框架。


## 数语统一
* 原生层

   Android指java; IOS指OC
* 脚本层

  如果使用一些以脚本为开发的混合开发平台，比如cocos creator的js。那么这里的js，就是脚本层


## Android接入的SDK版本

### push类
| provider | 版本 | sdk发布时间 | 接入方式 | 说明  |
|:-|:-|:-|:-|:-|
|vivo|版本号：484、版本名：3.0.0.4|2022-02-11|aar|https://dev.vivo.com.cn/documentCenter/doc/365|
|xiaomi|5.0.5|2022.04.28|aar|https://admin.xmpush.xiaomi.com/zh_CN/mipush/downpage|
|oppo|3.3.0|2021.09.18|aar|https://open.oppomobile.com/new/developmentDoc/info?id=11219|

### 支付类
| provider | 版本 | sdk发布时间 | 接入方式 | 说明  |
| --- | --- |  --------- | --- |--- |


### 分享类
| provider | 版本 | sdk发布时间 | 接入方式 | 说明  |
| --- | --- |  --------- | --- |--- |


### 帐号类
| provider | 版本 | sdk发布时间 | 接入方式 | 说明  |
| --- | --- |  --------- | --- |--- |


### Android的混淆配置

### webchat
https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Access_Guide/Android.html
```
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
```

### vivo
https://dev.vivo.com.cn/documentCenter/doc/365

```
-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
-keep class com.seantone.xsdk.vivo_push.PushMessageReceiverImpl{*;}
```

### oppo
```
-keep public class * extends android.app.Service
-keep class com.heytap.msp.** { *;}
```


## API设计说明(必读)

1、为了方便原生层与脚本层的通信,在原生层所有的参数及回调的返回参数使用json格式化（也就是字符串）。

2、所有功能接口格式，类似于：

```js
XSDK.dosamething(jsonParams, {
    onSuccess(String ret),
    onFaild(String ret)
})
```
注： 第一个参数有一个provider字段，用于触发相应的SDK的调用；
以下接口文档，可能第二个参数会使用 IXSDKCallback 等替代，下文不做另外说明。

3. 为了使用方便，脚本层二次封装，不直接操作json格式化。

## API文档


### 一、SDK初使化
``` js
XSDK.initSDK(String jsonParams, IXSDKCallback callback)
```

#### 1、已支持的SDK

|微信|支付宝|小米|谷歌|
|:-|:-|:-|:-|
|√|x|x|x| 


#### 2、支持的平台
|cocos creator|uni-app|  
|:-|:-|
|x|x| 


#### 3、参数说明

| 参数名 | 类型 | 必填 | 说明 | 平台差异 |
| --- | --- | --- | --------- | --- |
| provider | String | 是 | 这个参数用于判断请求哪个SDK |
| appid | String | 是 | 开放平台提供的appid |
|service | String | 否 | 如果某个开放平台，不同功能不同的appid,则需要使用这个参数。oauth:帐号体系 share:分享 payment:支付 |


#### 4、 success 返回参数说明
无



### 二、APP登录

``` js
XSDK.login(jsonParams, IXSDKCallback)
```

#### 1、已支持的SDK

|微信|支付宝|小米|谷歌|
|:-|:-|:-|:-|
|√|x|x|x| 


#### 2、支持的平台
|cocos creator|uni-app|  
|:-|:-|
|x|x| 


#### 3、参数说明

| 参数名 | 类型 | 必填 | 说明 | 平台差异 |
|:-|:-|:-|:-|:-|
| provider | String | 是 | 这个参数用于判断请求哪个SDK |


#### 4、 success 返回参数说明
| 参数名 | 类型 | 说明 | 平台差异 |
|:-|:-|:-|:-|
| code | String | 用户登录凭证。开发者需要在开发者服务器后台，使用 code 换取 openid 和 session_key 等信息 |

### 三、分享

#### 1、已支持的SDK

|微信|支付宝|小米|谷歌|
|:-|:-|:-|:-|
|√|x|x|x| 


#### 2、支持的平台
|cocos creator|uni-app|  
|:-|:-|
|x|x| 


#### 3、参数说明

**OBJECT 参数说明**

|参数名|类型|必填|说明|
|:-|:-|:-|:-|
|provider|String|是|分享服务提供商|
|type|Number|否|分享形式，如图文、纯文字、纯图片、音乐、视频、小程序等。默认1。不同分享服务商支持的形式不同，具体参考下面type值说明。|
|title|String|否|分享内容的标题|
|scene|String|provider 为 webchat 时必选|场景，可取值参考下面说明。|
|summary|String|type 为 1 时必选|分享内容的摘要|
|href|String|type 为 0 时必选|跳转链接|
|imageUrl|String|type 为 0、2、5 时必选|图片地址或者base64格式图片。type为0时，推荐使用小于20Kb的图片|
|mediaUrl|String|type 为 3、4 时必选|音视频地址|
|miniProgram|Object|type 为 5 时必选|分享小程序必要参数|
|openCustomerServiceChat|Boolean|否|是否启用拉起客服功能（目前仅支持微信。）|
|corpid|String|`openCustomerServiceChat = true` 时必填|客服ID（目前仅支持微信。）|
|customerUrl|String|`openCustomerServiceChat = true` 时必填|客服的页面路径（目前仅支持微信。）|


**type 值说明**

|值|说明|provider 支持度|
|:-|:-|:-|
|1|纯文字|webchat、qq|
|2|纯图片|webchat、qq|
|3|音乐|webchat、qq|
|4|视频|webchat、sinaweibo|
|5|小程序|webchat|
|6|网页|webchat|

**scene 值说明**

|值|说明|
|:-|:-|
|WXSceneSession|分享到聊天界面|
|WXSceneTimeline|分享到朋友圈|
|WXSceneFavorite|分享到微信收藏|

**miniProgram 值说明**

|值|类型|说明|
|:-|:-|:-|
|appId|String|微信小程序原始id|
|path|String|点击链接进入的页面|
|type|Number|微信小程序版本类型，可取值： 0-正式版； 1-测试版； 2-体验版。 默认值为0。|
|webUrl|String|兼容低版本的网页链接|


#### 4、 success 返回参数说明
无