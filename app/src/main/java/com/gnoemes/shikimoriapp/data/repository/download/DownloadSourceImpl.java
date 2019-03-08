package com.gnoemes.shikimoriapp.data.repository.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.data.local.preferences.UserSettingsSource;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import java.io.File;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import io.reactivex.Completable;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadSourceImpl implements DownloadSource {

    private Context context;
    private UserSettingsSource settingsSource;

    @Inject
    public DownloadSourceImpl(Context context, UserSettingsSource settingsSource) {
        this.context = context;
        this.settingsSource = settingsSource;
    }

    @Override
    public Completable downloadVideo(DownloadVideo video) {
        if (video.getFileName() == null || video.getAnimeName() == null || video.getDownloadLink() == null) {
            return Completable.error(new NoSuchElementException());
        }

        int location = settingsSource.getDownloadLocation();
        return Completable.fromAction(() -> {
            String title = video.getFileName()
                    .concat(" ")
                    .concat(video.getAnimeName());

            Uri link = Uri.parse(video.getDownloadLink());
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(link)
                    .setTitle(title)
                    .setDescription(context.getString(R.string.app_name))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.allowScanningByMediaScanner();

            if (location == 1) {
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, "ShikimoriApp/" + video.getAnimeName() + "/" + title + ".mp4");
            } else {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator);
                Uri path = Uri.withAppendedPath(Uri.fromFile(file), "ShikimoriApp/" + video.getAnimeName() + "/" + title + ".mp4");
                request.setDestinationUri(path);
            }

            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }

            Log.i("DOWNLOAD", "downloadVideo: " + video.getDownloadLink());
        });
    }
    }

