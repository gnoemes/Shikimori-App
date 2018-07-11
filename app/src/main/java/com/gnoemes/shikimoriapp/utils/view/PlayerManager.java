package com.gnoemes.shikimoriapp.utils.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.series.domain.VideoFormat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
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
import com.google.android.exoplayer2.ui.DefaultTimeBar;
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
    private DefaultTimeBar videoProgress;

    private PlayerControllerEventListener eventListener;

    public PlayerManager(Context context,
                         PlayerView playerView) {
        this.context = context;
        this.playerView = playerView;
        this.mediaSource = new ConcatenatingMediaSource();
        initPlayer();
        initControls();
    }

    private void initControls() {
        backBtn = playerView.findViewById(R.id.back);
        titleView = playerView.findViewById(R.id.controls_title);
        subtitleView = playerView.findViewById(R.id.controls_subtitle);
        resolutionSpinner = playerView.findViewById(R.id.spinner_resolution);
        videoProgress = playerView.findViewById(R.id.exo_progress);

        playerView.setShowMultiWindowTimeBar(true);

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

    public void addMediaSource(MediaSource source) {
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
    }

    public static class MediaSourceHelper {

        private DataSource.Factory factory;
        private VideoFormat format;
        private MediaSource videoSource;
        private List<MediaSource> videoSources;
        @Nullable
        private MediaSource subtitlesSource;

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

        public MediaSourceHelper withSubtitles(@Nullable String url, Format format) {
            if (url == null) {
                return this;
            } else {
                subtitlesSource = new SingleSampleMediaSource.Factory(factory)
                        .createMediaSource(Uri.parse(url), format, C.TIME_UNSET);
                return this;
            }
        }

        public MediaSource get() {
            //TODO REMOVE
            return videoSource;
//            return new MergingMediaSource(videoSource, subtitlesSource);
        }

        public AdsMediaSource.MediaSourceFactory getMediaSourceFactory() {
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
}
