//
//  BackupViewController.m
//  ChatDemo-UI2.0
//
//  Created by dhc on 15/5/11.
//  Copyright (c) 2015年 dhc. All rights reserved.
//

#import "BackupViewController.h"

#import "MBProgressHUD.h"

@interface BackupViewController ()<IEMChatProgressDelegate>
{
    UIProgressView *_progressView;
    UIButton *_pauseBackupButton;
    UIButton *_cancelBackupButton;
    UIButton *_continueBackupButton;
}

@property (strong, nonatomic) UIView *backupView;

@property (strong, nonatomic) UIView *restoreView;

@property (strong, nonatomic) __block MBProgressHUD *hud;

@end

@implementation BackupViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"聊天记录备份和恢复";
    self.view.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(20, 50, self.view.frame.size.width - 40, 150)];
    titleLabel.numberOfLines = 0;
    titleLabel.font = [UIFont systemFontOfSize:15.0];
    titleLabel.textColor = [UIColor grayColor];
    titleLabel.text = @"你可以将聊天记录备份到云端，当你在另一台手机登录时，可以登录并下载。聊天记录将在云端存储7天，过期清除。";
    [self.view addSubview:titleLabel];
    
    UIButton *backupButton = [[UIButton alloc] initWithFrame:CGRectMake(20, CGRectGetMaxY(titleLabel.frame) + 50, (self.view.frame.size.width - 60) / 2, 50)];
    [backupButton setTitle:@"开始备份" forState:UIControlStateNormal];
    [backupButton setBackgroundColor:[UIColor blackColor]];
    [backupButton addTarget:self action:@selector(backupAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:backupButton];
    
    UIButton *restoreButton = [[UIButton alloc] initWithFrame:CGRectMake(CGRectGetMaxX(backupButton.frame) + 20, CGRectGetMinY(backupButton.frame), backupButton.frame.size.width, 50)];
    [restoreButton setTitle:@"开始恢复" forState:UIControlStateNormal];
    [restoreButton setBackgroundColor:[UIColor blackColor]];
    [restoreButton addTarget:self action:@selector(restoreAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:restoreButton];
    
    _backupView = [[UIView alloc] initWithFrame:CGRectMake(20, 20, self.view.frame.size.width - 40, self.view.frame.size.height - 40)];
    _backupView.backgroundColor = [UIColor colorWithWhite:0.5 alpha:0.5];
    _backupView.clipsToBounds = YES;
    _backupView.layer.cornerRadius = 3;
    
    _pauseBackupButton = [[UIButton alloc] initWithFrame:CGRectMake(20, CGRectGetMaxY(titleLabel.frame) + 50, (self.view.frame.size.width - 60) / 3, 50)];
    [_pauseBackupButton setTitle:@"开始备份" forState:UIControlStateNormal];
    [_pauseBackupButton setBackgroundColor:[UIColor greenColor]];
    [_pauseBackupButton addTarget:self action:@selector(pauseBackupAction) forControlEvents:UIControlEventTouchUpInside];
    [_backupView addSubview:_pauseBackupButton];
    
    _continueBackupButton = [[UIButton alloc] initWithFrame:CGRectMake(20, CGRectGetMaxY(titleLabel.frame) + 50, (self.view.frame.size.width - 60) / 3, 50)];
    [_continueBackupButton setTitle:@"继续" forState:UIControlStateNormal];
    [_continueBackupButton setBackgroundColor:[UIColor greenColor]];
    [_continueBackupButton addTarget:self action:@selector(continueBackupAction) forControlEvents:UIControlEventTouchUpInside];
    _continueBackupButton.hidden = YES;
    [_backupView addSubview:_continueBackupButton];
    
    _cancelBackupButton = [[UIButton alloc] initWithFrame:CGRectMake(CGRectGetMaxX(titleLabel.frame) + 20, CGRectGetMinY(backupButton.frame), backupButton.frame.size.width, 50)];
    [_cancelBackupButton setTitle:@"开始恢复" forState:UIControlStateNormal];
    [_cancelBackupButton setBackgroundColor:[UIColor greenColor]];
    [_cancelBackupButton addTarget:self action:@selector(cancelBackupAction) forControlEvents:UIControlEventTouchUpInside];
    [_backupView addSubview:_cancelBackupButton];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - IEMChatProgressDelegate

- (void)setProgress:(float)progress
{
    self.hud.progress = progress;
}

#pragma mark - action

- (void)backupAction
{
    [_restoreView removeFromSuperview];
    
    _hud = [[MBProgressHUD alloc] initWithView:self.view];
    _hud.labelText = @"加载数据";
    [self.view addSubview:_hud];
    [_hud show:YES];
    
}

- (void)restoreAction
{
    _hud = [[MBProgressHUD alloc] initWithView:self.view];
    _hud.labelText = @"恢复备份";
    _hud.mode = MBProgressHUDModeDeterminate;
    [self.view addSubview:_hud];
    [_hud show:YES];
    
    
}

- (void)pauseBackupAction
{
    
}

- (void)cancelBackupAction
{
    
}

- (void)continueBackupAction
{
    
}


@end
