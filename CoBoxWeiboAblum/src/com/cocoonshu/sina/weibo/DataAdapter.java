package com.cocoonshu.sina.weibo;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.content.Context;

public class DataAdapter {

	private static final Executor  sDefaultExecutor = Executors.newCachedThreadPool();
	private WeakReference<Context> mRefContext      = null;
	
	public DataAdapter(Context context) {
		mRefContext = new WeakReference<Context>(context);
	}
	
}
