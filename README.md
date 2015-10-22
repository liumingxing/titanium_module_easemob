# titanium_module_easemob
* 环信IM SDK的封装，ios和安卓都支持，由于还未商用，接口较少，且稳定性没有接受严酷考验。
* 作者刘明星 lmxbitihero@126.com

github不允许上传大于100M的文件，工程里面iphone/EaseMobSdk/libEaseMObClientSDK.a体积大于100M，故我暂时压缩了，使用的时候需要手动解开。sample中引入的module也有一个文件大于100M，使用时先把module编出来，再导入到sample工程中即可。

调用示例：
```javascript
  var EaseMob = require("com.mamashai.easemob");
  EaseMob.config("mamashai#bbrl", "bbrl_dev");
  
  var win = Ti.UI.createWindow({
  	backgroundColor: "white"
  });
  var from = Ti.UI.createTextField({
  	value: "270",
  	hintText: "登录id",
  	left: 50,
  	width: 100,
  	right: 50, 
  	height: 40,
  	color: "#333",
  	borderStyle: Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
  	top: 50
  });
  var btn_login = Ti.UI.createButton({
  	top: 50,
  	left: 200,
  	title: "登录"
  });
  btn_login.addEventListener("click", function(e){
  	EaseMob.login(from.value, "mamashai", "刘明星");  //刘明星为昵称
  });
  
  var to = Ti.UI.createTextField({
  	value: "271",
  	hintText: "通讯另一方用户id",
  	left: 50,
  	width: 100,
  	height: 40,
  	color: "#333",
  	borderStyle: Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
  	top: 150
  });
  var btn = Ti.UI.createButton({
  	top: 150,
  	left: 200,
  	title: "开始聊天"
  });
  btn.addEventListener("click", function(e){
  	EaseMob.chatWithUser(to.value, "刘明星2");
  });
  win.add(from);
  win.add(btn_login);
  win.add(to);
  win.add(btn);
  win.open();
```

