package com.gnoemes.shikimoriapp.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.santalu.respinner.ReSpinner;

import java.util.ArrayList;
import java.util.List;

//TODO fix fragments concat and add subs support with fragments
public class PlayerManager implements Player.EventListener, PlayerControlView.VisibilityListener {

    private static final String TAG = "EmbeddedPlayer";

    private Context context;
    private ConcatenatingMediaSource mediaSource;
    private PlayerView playerView;
    private SimpleExoPlayer player;

    private ImageView backBtn;
    private TextView titleView;
    private TextView subtitleView;
    private ReSpinner resolutionSpinner;

    private ImageButton nextBtn;
    private ImageButton prevBtn;

    private LinearLayout fastForward;
    private LinearLayout rewind;

    private int controlsVisibility;

    private GestureDetector detector;
    private ExoPlayerGestureListener gestureListener;

    private PlayerControllerEventListener eventListener;

    public PlayerManager(Context context,
                         PlayerView playerView) {
        this.context = context;
        this.playerView = playerView;
        this.mediaSource = new ConcatenatingMediaSource();
        initPlayer();
        initControls();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initControls() {
        backBtn = playerView.findViewById(R.id.back);
        titleView = playerView.findViewById(R.id.controls_title);
        subtitleView = playerView.findViewById(R.id.controls_subtitle);
        resolutionSpinner = playerView.findViewById(R.id.spinner_resolution);
        fastForward = playerView.findViewById(R.id.fastForward);
        rewind = playerView.findViewById(R.id.rewind);
        nextBtn = playerView.findViewById(R.id.next);
        prevBtn = playerView.findViewById(R.id.prev);

        nextBtn.setOnClickListener(v -> {
            if (eventListener != null) {
                eventListener.loadNextEpisode();
            }
        });

        prevBtn.setOnClickListener(v -> {
            if (eventListener != null) {
                eventListener.loadPrevEpisode();
            }
        });

        backBtn.setOnClickListener(v -> {
            if (eventListener != null) {
                eventListener.onBackPressed();
            }
        });

        Drawable rateBackground = DrawableHelper
                .withContext(context)
                .withDrawable(resolutionSpinner.getBackground())
                .withColor(R.color.white)
                .tint()
                .get();
        resolutionSpinner.setBackground(rateBackground);

        gestureListener = new ExoPlayerGestureListener();

        detector = new GestureDetector(context, gestureListener);
        playerView.setOnTouchListener(gestureListener);
    }

    private void initPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        player.addListener(this);
        playerView.setPlayer(player);
        playerView.setControllerVisibilityListener(this);
    }

    public boolean isPlaying() {
        return player.getPlaybackState() != Player.STATE_ENDED
                && player.getPlaybackState() != Player.STATE_IDLE
                && player.getPlayWhenReady();
    }

    private boolean isControlsVisible() {
        return controlsVisibility == 0;
    }

    private void onFastForward() {
        seek(10000);
        fastForward.setVisibility(View.VISIBLE);
        fastForward.performClick();
        fastForward.postDelayed(() -> fastForward.setVisibility(View.INVISIBLE), 750);
    }

    private void onRewind() {
        seek(-10000);
        rewind.setVisibility(View.VISIBLE);
        rewind.performClick();
        rewind.postDelayed(() -> rewind.setVisibility(View.INVISIBLE), 750);
    }

    private void seek(long pos) {
        if (player != null) {
            if (pos >= 0 || player.getCurrentPosition() != 0) {
                pos = pos + player.getCurrentPosition();

                if (pos < 0) {
                    pos = 0;
                }

                player.seekTo(pos);
            }
        }
    }

    public void disableNextButton() {
        nextBtn.setAlpha(0.3f);
        nextBtn.setEnabled(false);
    }

    public void enableNextButton() {
        nextBtn.setAlpha(1f);
        nextBtn.setEnabled(true);
    }

    public void disablePrevButton() {
        prevBtn.setAlpha(0.3f);
        prevBtn.setEnabled(false);
    }

    public void enablePrevButton() {
        prevBtn.setAlpha(1f);
        prevBtn.setEnabled(true);
    }

    private void showControls() {
        playerView.showController();
    }

    private void hideControls() {
        playerView.hideController();
    }

    private void toggleControlsVisibility() {
        if (!isControlsVisible()) {
            hideControls();
        } else {
            showControls();
        }
    }

    public void addMediaSource(MediaSource source) {
        mediaSource.clear();
        mediaSource.addMediaSource(source);
        player.setPlayWhenReady(true);
        player.prepare(mediaSource);
    }

    public void setTitle(@Nullable String title) {
        titleView.setText(title);
    }

    public void setSubtitle(@Nullable String subtitle) {
        subtitleView.setText(subtitle);
    }

    public void setEventListener(PlayerControllerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void addResolutions(List<Integer> resolutions) {
        List<String> items = new ArrayList<>();
        for (int resolution : resolutions) {
            items.add(String.valueOf(resolution));
        }

        resolutionSpinner.setAdapter(new ArrayAdapter<>(context, R.layout.item_spinner_player, items));
        resolutionSpinner.setOnItemClickListener((parent, view, position, id) -> {
            if (eventListener != null) {
                eventListener.onResolutionChanged(resolutions.get(position));
            }
        });
    }

    public void destroy() {
        player.stop();
        player.release();
        titleView.setText(null);
        subtitleView.setText(null);
    }

    public void onStop() {
        player.setPlayWhenReady(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
        Log.i(TAG, "onTimelineChanged: " + timeline);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case com.google.android.exoplayer2.Player.STATE_IDLE:
                Log.d(TAG, "IDLE state");
                if (eventListener != null) {
                    eventListener.onEnded();
                }
                break;
            case com.google.android.exoplayer2.Player.STATE_BUFFERING:
                Log.d(TAG, "BUFFERING state");
                if (eventListener != null) {
                    eventListener.onLoading();
                }
                break;
            case com.google.android.exoplayer2.Player.STATE_READY:
                Log.d(TAG, "READY state");
                if (eventListener != null) {
                    eventListener.onReady();
                }
                break;
            case com.google.android.exoplayer2.Player.STATE_ENDED:
                Log.d(TAG, "ENDED state");
                if (eventListener != null) {
                    eventListener.onEnded();
                }
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        //not implemented
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        //not implemented
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        error.printStackTrace();
        switch (error.type) {
            case ExoPlaybackException.TYPE_SOURCE:
                if (error.getSourceException() instanceof HttpDataSource.InvalidResponseCodeException) {
//                    HttpDataSource.InvalidResponseCodeException exception = (HttpDataSource.InvalidResponseCodeException) error.getSourceException();
////                    if (exception.responseCode == HttpStatusCode.FORBIDDED) {
////                        if (eventListener != null) {
////                            eventListener.onAlternativeSource();
////                        }
////                    } else {
////                        if (eventListener != null) {
////                            eventListener.onNetworkError();
////                        }
////                    }
                    if (eventListener != null) {
                        eventListener.onNetworkError();
                    }
                }
                break;
        }
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        //not implemented
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        //not implemented
    }

    @Override
    public void onSeekProcessed() {
        //not implemented
    }

    @Override
    public void onVisibilityChange(int visibility) {
        if (eventListener != null) {
            switch (visibility) {
                case View.VISIBLE:
                    eventListener.onControlsVisible();
                    break;
                default:
                    eventListener.onControlsHidden();
                    break;
            }
        }
        controlsVisibility = visibility;
    }

    public interface PlayerControllerEventListener {


        void onControlsVisible();

        void onControlsHidden();

        void onBackPressed();

        void onResolutionChanged(int newResolution);

        void onLoading();

        void onReady();

        void onEnded();

        void onNetworkError();

        void onAlternativeSource();

        void loadPrevEpisode();

        void loadNextEpisode();
    }

    public static class MediaSourceHelper {

        private DataSource.Factory factory;
        private VideoFormat format;
        private MediaSource videoSource;
        private List<MediaSource> videoSources;

        public MediaSourceHelper(DataSource.Factory factory) {
            this.factory = factory;
            videoSources = new ArrayList<>();
        }

        public static MediaSourceHelper withFactory(DataSource.Factory factory) {
            return new MediaSourceHelper(factory);
        }

        public MediaSourceHelper withFormat(VideoFormat format) {
            this.format = format;
            return this;
        }

        public MediaSourceHelper withVideoUrls(@NonNull String... urls) {

            for (String url : urls) {
                if (!TextUtils.isEmpty(url)) {
                    videoSources.add(getMediaSourceFactory()
                            .createMediaSource(Uri.parse(url)));
                }
            }

            videoSource = new ConcatenatingMediaSource(videoSources.toArray(new MediaSource[videoSources.size()]));
            return this;
        }

        public MediaSource get() {
            return videoSource;
        }

        private AdsMediaSource.MediaSourceFactory getMediaSourceFactory() {
            switch (format) {
                case MP4:
                    return new ExtractorMediaSource.Factory(factory);
                case HLS:
                    return new HlsMediaSource.Factory(factory);
                case DASH:
                    return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(factory), factory);
                default:
                    throw new IllegalArgumentException(format.getType() + " format not implemented");
            }
        }
    }

    private class ExoPlayerGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {

//            if (!isPlaying()) {
//                return false;
//            }

            if (e.getX() > ((float) (playerView.getWidth() / 2))) {
                onFastForward();
            } else {
                onRewind();
            }

            return true;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return detector.onTouchEvent(event);
        }

    }
}

