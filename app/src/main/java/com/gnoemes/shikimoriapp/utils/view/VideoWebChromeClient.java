package com.gnoemes.shikimoriapp.utils.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;

public class VideoWebChromeClient extends WebChromeClient {
    private boolean isVideoFullscreen = false;
    private ViewGroup activityVideoView;
    private View videoViewContainer;
    private CustomViewCallback videoCallback;
    private Window videoWindow;

    public VideoWebChromeClient(ViewGroup activityVideoView, Window window) {
        this.activityVideoView = activityVideoView;
        this.videoWindow = window;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        Log.i("DEVE", "ENTER FULLSCREEN");
        videoCallback = callback;
        videoViewContainer = view;
        videoWindow.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activityVideoView.addView(videoViewContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        activityVideoView.setVisibility(View.VISIBLE);
        isVideoFullscreen = true;
    }

    @Override
    public void onHideCustomView() {
        if (!isVideoFullscreen) {
            return;
        }
        Log.i("DEVE", "EXIT FULLSCREEN");
        activityVideoView.setVisibility(View.VISIBLE);
        activityVideoView.removeView(videoViewContainer);
        videoWindow.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        videoViewContainer = null;
        videoCallback.onCustomViewHidden();
        isVideoFullscreen = false;
    }

    public boolean onBackPressed() {
        onHideCustomView();
        return isVideoFullscreen;
    }
}