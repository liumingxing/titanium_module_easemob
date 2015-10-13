/*
 * Single Window Application Template:
 * A basic starting point for your application.  Mostly a blank canvas.
 *
 * In app.js, we generally take care of a few things:
 * - Bootstrap the application with any data we need
 * - Check for dependencies like device type, platform version or network connection
 * - Require and open our top-level UI component
 *
 */

//bootstrap and check dependencies
if (Ti.version < 1.8) {
  alert('Sorry - this application template requires Titanium Mobile SDK 1.8 or later');
}

// This is a single context application with multiple windows in a stack
(function() {
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
  /*
  var osname = Ti.Platform.osname,
    version = Ti.Platform.version,
    height = Ti.Platform.displayCaps.platformHeight,
    width = Ti.Platform.displayCaps.platformWidth;
    var factor = Ti.Platform.displayCaps.logicalDensityFactor;
    
    var win = Ti.UI.createWindow({
    	title: "Emoji test",
    	layout: "vertical"
    });
    var Emoji = require("com.mamashai.emoji"); 
	var label = Emoji.createLabel({
		top: 10*factor,
		text: "😥a"
	});
	label.addEventListener("click", function(e){
		alert(e.source.text);
	});
	win.add(label);

    win.add(Emoji.createLabel({
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: Ti.UI.SIZE,
		text: "😥font 10",
		color: "blue",
		font: {fontSize: 10*factor},
		backgroundColor: "red"
	}));
	win.add(Emoji.createLabel({
		text: "😥font 12",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 12*factor}
	}));
	win.add(Emoji.createLabel({
		text: "😥font 14",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 14*factor}
	}));
	win.add(Emoji.createLabel({
		text: "😥font 16",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 16*factor}
	}));
	win.add(Emoji.createLabel({
		text: "😥font 18",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 18*factor}
	}));
	win.add(Emoji.createLabel({
		html: "😥from web html, font 20 <a href='http://twitter.com'>twitter</a>",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 20*factor}
	}));
	win.add(Emoji.createLabel({
		text: "😥font 22",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 30*factor,
		font: {fontSize: 22*factor}
	}));
	win.add(Emoji.createTextField({
		value: "😥text field font 12",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: Ti.UI.SIZE,
		font: {fontSize: 12*factor}
	}));
	
	win.add(Emoji.createTextField({
		value: "😥text field font 14",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: Ti.UI.SIZE,
		font: {fontSize: 14*factor}
	}));
	
	win.add(Emoji.createTextField({
		value: "😥text field font 20",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: Ti.UI.SIZE,
		font: {fontSize: 20*factor}
	}));
	
	win.add(Emoji.createTextArea({
		value: "😥text area font 14",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 60*factor,
		font: {fontSize: 14*factor}
	}));
	var t = Emoji.createTextArea({
		value: "😥text area font 20",
		top: 2*factor,
		left: 10*factor,
		right: 10*factor,
		height: 60*factor,
		font: {fontSize: 20*factor}
	});
	Ti.API.log(t.value);
	t.value = "😥😥😥😥";
	Ti.API.log(t.value);
	win.add(t);
	
    win.open();
    */
})();
