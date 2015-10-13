# titanium_module_easemob
* 环信IM SDK的封装，暂时只做了ios，安卓还在开发中
* 作者刘明星 lmxbitihero@126.com

github不允许上传大于100M的文件，工程里面iphone/EaseMobSdk/libEaseMObClientSDK.a体积大于100M，故我暂时压缩了，使用的时候需要手动解开。

调用示例：
```ruby
  var win = Ti.UI.createWindow({
  	backgroundColor: "white"
  });
  var from = Ti.UI.createTextField({
  	value: "270",
  	hintText: "登录id",
  	left: 50,
  	right: 50, 
  	borderStyle: Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
  	top: 50
  });
  var to = Ti.UI.createTextField({
  	value: "271",
  	hintText: "通讯另一方用户id",
  	left: 50,
  	right: 50, 
  	borderStyle: Titanium.UI.INPUT_BORDERSTYLE_ROUNDED,
  	top: 100
  });
  var btn = Ti.UI.createButton({
  	top: 150,
  	title: "打开"
  });
  btn.addEventListener("click", function(e){
  	var EaseMob = require("com.mamashai.easemob");
  	
  	EaseMob.config("mamashai#bbrl", "bbrl_dev");
  	EaseMob.login(from.value, "mamashai");
  	EaseMob.chatWithUser(to.value);
  });
  win.add(from);
  win.add(to);
  win.add(btn);
  win.open();
```

