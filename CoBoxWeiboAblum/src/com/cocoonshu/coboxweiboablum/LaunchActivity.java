package com.cocoonshu.coboxweiboablum;

import com.cocoonshu.sina.weibo.AccountManager;
import com.cocoonshu.sina.weibo.Weibo;
import com.cocoonshu.sina.weibo.util.Config;
import com.cocoonshu.sina.weibo.util.Debugger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class LaunchActivity extends Activity {

	private static final String TAG = "LaunchActivity";
	
	private Handler   mHandler        = new Handler();
	private Animation mAnimPageFadeIn = null;
	private View      mRootView       = null;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		setupWeiboAPI();
		setupViews();
		setupAnimations();
		setupListeners();
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        beginPageAction();
    }
    
    private void setupWeiboAPI() {
        Weibo.createInstance(getApplicationContext());
    }
    
    private void setupViews() {
        mRootView = findViewById(R.id.LayoutLaunchPage);
    }

    private void setupAnimations() {
        mAnimPageFadeIn = AnimationUtils.loadAnimation(LaunchActivity.this, R.anim.launch_page_fade_in);
    }

    private void setupListeners() {
        mAnimPageFadeIn.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Weibo.getInstance().authorize(getApplicationContext(), new Runnable() {

                            private Context mContext = getApplicationContext();
                            
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }
                            
                        });
                        finish();
                    }
                    
                }, Config.LaunchPage.DISPLAY_STILL_DURATION);
            }
            
        });
    }
    
    private void beginPageAction() {
        mRootView.startAnimation(mAnimPageFadeIn);
    }
}
