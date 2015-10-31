/**
 * EaseMob
 *
 * Created by Your Name
 * Copyright (c) 2015 Your Company. All rights reserved.
 */

#import "ComMamashaiEasemobModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import "TiApp.h"

#import "EaseMob.h"
#import "chatViewController.h"
#import "mainViewController.h"
#import "userProfileManager.h"
#import <Parse/Parse.h>

@implementation ComMamashaiEasemobModule

#pragma mark Internal

// this is generated for your module, please do not change it
-(id)moduleGUID
{
	return @"c1216e40-8b9c-4ef9-b20f-399353f2b806";
}

// this is generated for your module, please do not change it
-(NSString*)moduleId
{
	return @"com.mamashai.easemob";
}

#pragma mark Lifecycle

-(void)startup
{
	// this method is called when the module is first loaded
	// you *must* call the superclass
	[super startup];

	NSLog(@"[INFO] %@ loaded",self);
}

-(void)shutdown:(id)sender
{
	// this method is called when the module is being unloaded
	// typically this is during shutdown. make sure you don't do too
	// much processing here or the app will be quit forceably

	// you *must* call the superclass
	[super shutdown:sender];
}

#pragma mark Cleanup

-(void)dealloc
{
	// release any resources that have been retained by the module
	[super dealloc];
}

#pragma mark Internal Memory Management

-(void)didReceiveMemoryWarning:(NSNotification*)notification
{
	// optionally release any resources that can be dynamically
	// reloaded once memory is available - such as caches
	[super didReceiveMemoryWarning:notification];
}

#pragma mark Listener Notifications

-(void)_listenerAdded:(NSString *)type count:(int)count
{
	if (count == 1 && [type isEqualToString:@"my_event"])
	{
		// the first (of potentially many) listener is being added
		// for event named 'my_event'
	}
}

-(void)_listenerRemoved:(NSString *)type count:(int)count
{
	if (count == 0 && [type isEqualToString:@"my_event"])
	{
		// the last listener called for event named 'my_event' has
		// been removed, we can optionally clean up any resources
		// since no body is listening at this point for that event
	}
}

#pragma Public APIs

//传入两个参数：mamashai#bbrl, bbrl
-(id)config:(id)args
{
	NSLog([args objectAtIndex:0]);
	NSLog([args objectAtIndex:1]);
	
    [[EaseMob sharedInstance] registerSDKWithAppKey:[args objectAtIndex:0] apnsCertName:[args objectAtIndex:1]];
    [[EaseMob sharedInstance] application:[TiApp app] didFinishLaunchingWithOptions:[[TiApp app] launchOptions]];
    
    //[[TiApp app] parseApplication:[TiApp app] didFinishLaunchingWithOptions:[[TiApp app] launchOptions]];
    
    [Parse enableLocalDatastore];
    
    // Initialize Parse.
    [Parse setApplicationId:@"UUL8TxlHwKj7ZXEUr2brF3ydOxirCXdIj9LscvJs"
                  clientKey:@"B1jH9bmxuYyTcpoFfpeVslhmLYsytWTxqYqKQhBJ"];
    
    // [Optional] Track statistics around application opens.
    [PFAnalytics trackAppOpenedWithLaunchOptions: [[TiApp app] launchOptions]];
    
    
    // setup ACL
    PFACL *defaultACL = [PFACL ACL];
    
    [defaultACL setPublicReadAccess:YES];
    [defaultACL setPublicWriteAccess:YES];
    
    [PFACL setDefaultACL:defaultACL withAccessForCurrentUser:YES];

    
    
    return @"ok";
}

-(id)enterBackground
{
    [[EaseMob sharedInstance] applicationDidEnterBackground:[TiApp app]];
}

-(id)enterForeground
{
    [[EaseMob sharedInstance] applicationWillEnterForeground:[TiApp app]];
}

-(void)login:(id)args
{
    [[EaseMob sharedInstance].chatManager asyncLoginWithUsername:[args objectAtIndex:0] password:[args objectAtIndex:1] completion:^(NSDictionary *loginInfo, EMError *error) {
        if (!error && loginInfo) {
            TiModule* _app = self;
            [_app fireEvent:@"login_success" withObject:nil];
            NSLog(@"登陆成功");
        }
        else{
        	TiModule* _app = self;
            [_app fireEvent:@"login_fail" withObject:nil];
        	NSLog(@"登录失败");
        }
    } onQueue:nil];
}

-(void)doneButtonPressed:(id)sender{
	//[self dismissModalViewControllerAnimated:YES];
    dispatch_async(dispatch_get_main_queue(), ^{
    	[[TiApp app] hideModalController:h_nav animated: YES];
    });
}


-(void)chatWithUser:(id)args
{
	dispatch_async(dispatch_get_main_queue(), ^{
        MainViewController *main = [[MainViewController alloc] init];
	    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:main];
	    h_nav = nav;
        
        
	    
	    UIBarButtonItem * doneButton = [[UIBarButtonItem alloc]
                                        initWithBarButtonSystemItem:UIBarButtonSystemItemDone
                                        target:self
                                        action:@selector(doneButtonPressed:) ];
		main.navigationItem.leftBarButtonItem = doneButton;
		
		                                        
	    [[TiApp app] showModalController:nav animated: YES];
	    
	    ChatViewController *chatVC = [[ChatViewController alloc] initWithChatter:[args objectAtIndex: 0] isGroup:NO];
	    chatVC.title = [args objectAtIndex: 0];
	    [nav pushViewController:chatVC animated:YES];
    });
}

-(void)mainScreen:(id)args
{
	dispatch_async(dispatch_get_main_queue(), ^{
        MainViewController *main = [[MainViewController alloc] init];
	    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:main];
	    h_nav = nav;
	    
	    UIBarButtonItem * doneButton = [[UIBarButtonItem alloc]
                                        initWithBarButtonSystemItem:UIBarButtonSystemItemDone
                                        target:self
                                        action:@selector(doneButtonPressed:) ];
		main.navigationItem.leftBarButtonItem = doneButton;
		
		                                        
	    [[TiApp app] showModalController:nav animated: YES];
    });
}

@end
