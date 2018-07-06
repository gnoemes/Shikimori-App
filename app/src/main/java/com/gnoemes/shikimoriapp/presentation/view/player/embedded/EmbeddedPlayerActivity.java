package com.gnoemes.shikimoriapp.presentation.view.player.embedded;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.PlayEpisode;
import com.gnoemes.shikimoriapp.entity.anime.series.domain.TranslationWithSources;
import com.gnoemes.shikimoriapp.entity.app.presentation.AppExtras;
import com.gnoemes.shikimoriapp.entity.main.presentation.Constants;
import com.gnoemes.shikimoriapp.presentation.presenter.player.EmbeddedPlayerPresenter;
import com.gnoemes.shikimoriapp.presentation.view.common.activity.BaseActivity;
import com.gnoemes.shikimoriapp.utils.view.PlayerManager;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;

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
    @InjectPresenter
    EmbeddedPlayerPresenter presenter;
    @Inject
    NavigatorHolder navigatorHolder;
    private PlayerManager playerManager;
    private boolean hasSubtitles;

    public static Intent newIntent(Context context, long translationId) {
        Intent intent = new Intent(context, EmbeddedPlayerActivity.class);
        intent.putExtra(AppExtras.ARGUMENT_TRANSLATION_ID, translationId);
        return intent;
    }

    @ProvidePresenter
    EmbeddedPlayerPresenter providePresenter() {
        presenter = presenterProvider.get();

        if (getIntent() != null) {
            presenter.setTranslationId(getIntent().getLongExtra(AppExtras.ARGUMENT_TRANSLATION_ID, Constants.NO_ID));
        }

        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initUiFlags();
        playerManager = new PlayerManager(EmbeddedPlayerActivity.this, playerView);
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
    public void onShitHappens() {
        if (hasSubtitles)
            Toast.makeText(getApplicationContext(), "Внимание, субтитры текущего перевода временно не поддерживаются в данном плеере", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNetworkError() {
        Toast.makeText(getApplicationContext(), "Превышено количество запросов на сервер. \n Попробуйте выбрать другой перевод.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSystemMessage(String s) {
        Toast.makeText(EmbeddedPlayerActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onControlsVisible() {

    }

    @Override
    public void onControlsHidden() {
        enterFullscreen();
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
    public void setPlayerData(TranslationWithSources translation, int position) {
        playerManager.setTitle(translation.getTranslation().getSerialTitle());
        playerManager.setSubtitle(translation.getTranslation().getEpisode().getEpisodeFull());

        Format subtitlesFormat = Format.createTextSampleFormat(
                null,
                MimeTypes.TEXT_VTT,
                Format.NO_VALUE,
                null,
                null
        );

        PlayEpisode episode = translation.getSources().get(position);

        hasSubtitles = episode.HasSubtitles();

        MediaSource source = PlayerManager.MediaSourceHelper
                .withFactory(new DefaultHttpDataSourceFactory("sap", new DefaultBandwidthMeter(), 30000, 30000, true))
                .withSubtitles(episode.HasSubtitles() ? episode.getSubtitles() : null, subtitlesFormat)
                .withVideoUrls(episode.getVideoUrls().toArray(new String[episode.getVideoUrls().size()]))
                .get();

        List<Integer> res = new ArrayList<>();
        for (PlayEpisode playEpisode : translation.getSources()) {
            res.add(playEpisode.getResolution());
        }
        playerManager.addResolutions(res);
        playerManager.setEventListener(this);

        playerManager.addMediaSource(source);
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
