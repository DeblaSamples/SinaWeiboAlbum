package com.cocoonshu.network;

import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import com.cocoonshu.sina.weibo.util.ThreadUtil;

public class HttpRequestor {

    private BlockingDeque<Task> mTaskQueue       = null;
    private Executor            mExecutor        = null;
    private Executor            mDefaultExecutor = null;

    /**
     * Task finish listener
     * @author Cocoonshu
     * @date   2016-01-06
     */
    private static interface OnTaskFinishedListener {
        void onTaskFinished(Task task);
    }
    
    /**
     * Http request task package
     * @author Cocoonshu
     * @date   2016-01-06
     */
    private static class Task implements Runnable {
        private boolean                mIsRequestCancel        = false;
        private boolean                mIsCanceled             = false;
        private boolean                mIsFinished             = false;
        private HttpRequest            mHttpRequest            = null;
        private HttpListener           mHttpListener           = null;
        private OnTaskFinishedListener mOnTaskFinishedListener = null;
        
        public Task(HttpRequest request, HttpListener callback) {
            mHttpRequest  = request;
            mHttpListener = callback;
        }
        
        @Override
        public void run() {
            // Cancel policy
            if (mIsRequestCancel) {
                mIsCanceled = true;
                return;
            }

            // Executing
            try {
                if (mHttpRequest != null) {
                    mHttpRequest.execute();
                    if (mHttpListener != null) {
                        if (mHttpRequest.isError()) {
                            mHttpListener.onError(mHttpRequest.getStatusCode());
                        } else {
                            mHttpListener.onResponed(mHttpRequest.getResponse());
                        }
                    }
                }
            } catch(Throwable thr) {
                if (mHttpListener != null) {
                    mHttpListener.onError(HttpCode.Unknown);
                }
            }
            
            // Callback
            mIsFinished = true;
            if (mOnTaskFinishedListener != null) {
                mOnTaskFinishedListener.onTaskFinished(Task.this);
            }
        }
        
        public final void setOnTaskFinishedListener(OnTaskFinishedListener listener) {
            mOnTaskFinishedListener = listener;
        }
        
        public final boolean isDone() {
            return mIsFinished;
        }
        
        public final boolean isCanceled() {
            return mIsCanceled;
        }
        
        public final boolean isRequestedCancel() {
            return mIsRequestCancel;
        }
        
        public final void requestCancel() {
            mIsRequestCancel = true;
        }
    }
    
    public HttpRequestor() {
        mTaskQueue = new LinkedBlockingDeque<Task>();
    }
    
    public final void setExecutor(Executor executor) {
        mExecutor = executor;
    }
    
    /**
     * Request an HTTP request package,
     * and the HttpListener will be callback
     * after the HTTP server responding.
     * @param request
     * @param callback
     * @see #HttpRequest
     * @see #HttpListener
     */
    public void request(HttpRequest request) {
        request(request, null);
    }
    
    /**
     * Request an HTTP request package,
     * and the HttpListener will be callback
     * after the HTTP server responding.
     * @param request
     * @param callback
     * @see #HttpRequest
     * @see #HttpListener
     */
    public void request(HttpRequest request, HttpListener callback) {
        // Package task
        Task task = new Task(request, callback);
        task.setOnTaskFinishedListener(new OnTaskFinishedListener() {
            
            @Override
            public void onTaskFinished(Task task) {
                mTaskQueue.remove(task);
            }
            
        });
        
        // Submit to execute
        mTaskQueue.offer(task);
        if (mExecutor != null) {
            mExecutor.execute(task);
        } else {
            if (mDefaultExecutor == null && ThreadUtil.isCurrentInMainThread()) {
                mDefaultExecutor = Executors.newSingleThreadExecutor();
            }
            if (mDefaultExecutor != null) {
                mDefaultExecutor.execute(task);
            } else {
                task.run();
            }
        }
    }

    /**
     * Cancel all task and remove them from task queue
     */
    public void cancelAll() {
        Iterator<Task> itr = mTaskQueue.iterator();
        while (itr.hasNext()) {
            itr.next().requestCancel();
        }
    }
}
