package com.gnoemes.shikimoriapp.presentation.view.player.embedded;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.domain.Constants;
import com.gnoemes.shikimoriapp.entity.series.domain.PlayVideo;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoTrack;
import com.gnoemes.shikimoriapp.entity.series.presentation.PlayVideoNavigationData;
import com.gnoemes.shikimoriapp.presentation.presenter.player.EmbeddedPlayerPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.utils.SystemServiceKt;
import com.gnoemes.shikimoriapp.utils.view.PlayerManager;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class EmbeddedPlayerActivity extends BaseActivity<EmbeddedPlayerPresenter, EmbeddedPlayerView>
        implements EmbeddedPlayerView, PlayerManager.PlayerControllerEventListener {

    @BindView(R.id.player)
    PlayerView playerView;
    @BindView(R.id.controls_video_loading)
    ProgressBar progressBar;
    @BindView(R.id.volumeView)
    TextView volumeView;
    @BindView(R.id.brightnessView)
    TextView brightnessView;
    @BindView(R.id.unLocker)
    ImageView unLockerView;
    @BindView(R.id.constraint)
    ConstraintLayout container;

    @InjectPresenter
    EmbeddedPlayerPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;
    private PlayerManager playerManager;

    private int currentVolume;
    private int currentBrightness;

    private Runnable hideLockerRunnable = new Runnable() {
        @Override
        public void run() {
            unLockerView.setVisibility(View.GONE);
        }
    };

    private Runnable hideVolumeRunnable = new Runnable() {
        @Override
        public void run() {
            volumeView.setVisibility(View.GONE);
        }
    };

    private Runnable hideBrighnessRunnable = new Runnable() {
        @Override
        public void run() {
            brightnessView.setVisibility(View.GONE);
        }
    };

    public static Intent newIntent(Context context, PlayVideoNavigationData data) {
        Intent intent = new Intent(context, EmbeddedPlayerActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_PLAY_VIDEO_DATA, data);
        return intent;
    }

    @ProvidePresenter
    EmbeddedPlayerPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getIntent() != null) {
            presenter.setPlayData((PlayVideoNavigationData) getIntent().getSerializableExtra(AppExtras.ARGUMENT_PLAY_VIDEO_DATA));
        }

        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initUiFlags();
        playerManager = new PlayerManager(EmbeddedPlayerActivity.this, playerView, getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.MULTIPLY);
        currentVolume = SystemServiceKt.audioManager(EmbeddedPlayerActivity.this).getStreamVolume(AudioManager.STREAM_MUSIC);
        currentBrightness = (int) ((getWindow().getAttributes().screenBrightness / 255f) * 100);
        unLockerView.setOnClickListener(view -> {
            playerManager.unlock();
            unLockerView.setVisibility(View.GONE);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerManager.onStop();
    }

    @Override
    protected void onDestroy() {
        playerManager.destroy();
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    protected int getLayoutActivity() {
        return R.layout.activity_embedded_player;
    }

    @Override
    protected Navigator getNavigator() {
        return null;
    }

    @Override
    protected NavigatorHolder getNavigatorHolder() {
        return navigatorHolder;
    }

    @Override
    protected EmbeddedPlayerPresenter getPresenter() {
        return presenter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onResolutionChanged(int newResolution) {
        getPresenter().onResolutionChanged(newResolution);
    }

    @Override
    public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReady() {
        progressBar.setVisibility(View.GONE);
        enterFullscreen();
    }

    @Override
    public void onEnded() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkError() {
        getPresenter().onNetworkError();
    }

    @Override
    public void showSystemMessage(String s) {
        Toast.makeText(EmbeddedPlayerActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void exitWithSystemMessage(String error) {
        Toast.makeText(EmbeddedPlayerActivity.this, error, Toast.LENGTH_LONG).show();
        super.finish();
    }

    @Override
    public void onControlsVisible() {

    }

    @Override
    public void onLocked() {
        unLockerView.setVisibility(View.VISIBLE);
        unLockerView.postDelayed(hideLockerRunnable, 3500);
        container.setOnClickListener(view -> {
            if (playerManager.isLocked()) {
                unLockerView.removeCallbacks(hideLockerRunnable);
                unLockerView.setVisibility(View.VISIBLE);
                unLockerView.postDelayed(hideLockerRunnable, 3500);
            }
        });
    }

    @Override
    public void onScrollEnd() {
        volumeView.removeCallbacks(hideVolumeRunnable);
        volumeView.postDelayed(hideVolumeRunnable, 2500);
        brightnessView.removeCallbacks(hideBrighnessRunnable);
        brightnessView.postDelayed(hideBrighnessRunnable, 2500);
    }

    @Override
    public void onChangeBrightness(int brightness) {

        currentBrightness += brightness;
        if (currentBrightness > 100) {
            currentBrightness = 100;
        } else if (currentBrightness < 1) {
            currentBrightness = 1;
        }

        brightnessView.setVisibility(View.VISIBLE);
        String text = String.format(getString(R.string.brightness_format), currentBrightness);
        brightnessView.setText(text);

        WindowManager.LayoutParams layout = getWindow()
                .getAttributes();
        layout.screenBrightness = 255f / 100 * currentBrightness / 255f;
        getWindow().setAttributes(layout);
        Log.i("EmbeddedPlayer", "onChangeBrightness: " + 255f / 100 * currentBrightness / 255f);
    }

    @Override
    public void onChangeVolume(int volume) {
        AudioManager manager = SystemServiceKt.audioManager(EmbeddedPlayerActivity.this);

        int max = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume += volume;
        if (currentVolume > max) {
            currentVolume = max;
        } else if (currentVolume < 0) {
            currentVolume = 0;
        }

        manager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        volumeView.setVisibility(View.VISIBLE);
        String text = String.format(getString(R.string.volume_format), currentVolume == 0 ? 0 : Math.round(currentVolume / (max * 0.01f)));
        volumeView.setText(text);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        togglePlayer();
        if (android.provider.Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private void togglePlayer() {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            onPortrait();
        } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            onLandscape();
        }
    }

    private void onPortrait() {
        playerManager.onPortrait();
    }

    private void onLandscape() {
        playerManager.onLandscape();
    }

    @Override
    public void toggleOrientation() {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            onLandscape();
        } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            onPortrait();
        }
    }

    @Override
    public void onAlternativeSource() {
        getPresenter().onAlternativeSource();
    }

    @Override
    public void onControlsHidden() {
        enterFullscreen();
    }

    @Override
    public void loadPrevEpisode() {
        getPresenter().loadPrevEpisode();
    }

    @Override
    public void loadNextEpisode() {
        getPresenter().loadNextEpisode();
    }

    private void exitFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void enterFullscreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initUiFlags() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hideSystemUi();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUi() {
        getWindow()
                .getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_IMMERSIVE
                                // Set the content to appear under the system bars so that the
                                // content doesn't resize when the system bars hide and show.
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                // Hide the nav bar and status bar
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        super.finish();
        exitFullScreen();
    }

    ///////////////////////////////////////////////////////////////////////////
    // MVP
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setTitle(String title) {
        //not implemented
    }

    @Override
    public void initToolbar() {
        //not implemented
    }

    @Override
    public void disableNextButton() {
        playerManager.disableNextButton();
    }

    @Override
    public void disablePrevButton() {
        playerManager.disablePrevButton();
    }

    @Override
    public void enableNextButton() {
        playerManager.enableNextButton();
    }

    @Override
    public void enablePrevButton() {
        playerManager.enablePrevButton();
    }

    @Override
    public void updateInformation(PlayVideo playVideo, int currentTrack) {
        playerManager.setTitle(playVideo.getTitle());
        playerManager.setSubtitle(String.format(getResources().getString(R.string.episode_list_format), playVideo.getEpisodeId()));

        List<Integer> resolutions = new ArrayList<>();

        if (playVideo.getTracks() != null) {
            for (VideoTrack track : playVideo.getTracks()) {
                if (track.getResolution() != Constants.NO_ID) {
                    resolutions.add(track.getResolution());
                }
            }
        }

        playerManager.setResolutions(resolutions, currentTrack);
    }

    @Override
    public void playVideo(VideoTrack videoTrack, boolean isNeedReset) {
        Log.i("DEVE", "playVideo: " + videoTrack.getUrl());
        MediaSource source = PlayerManager.MediaSourceHelper
                .withFactory(new DefaultHttpDataSourceFactory("sap", new DefaultBandwidthMeter(), 30000, 30000, true))
                .withFormat(videoTrack.getFormat())
                .withVideoUrl(videoTrack.getUrl())
                .get();

        playerManager.setEventListener(this);

        if (isNeedReset) {
            playerManager.addMediaSource(source);
        } else {
            playerManager.updateTrack(source);
        }
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
