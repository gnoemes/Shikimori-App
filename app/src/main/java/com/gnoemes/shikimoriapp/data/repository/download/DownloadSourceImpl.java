package com.gnoemes.shikimoriapp.data.repository.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.gnoemes.shikimoriapp.R;
import com.gnoemes.shikimoriapp.entity.video.domain.DownloadVideo;

import java.util.NoSuchElementException;

import javax.inject.Inject;

import io.reactivex.Completable;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadSourceImpl implements DownloadSource {

    private Context context;

    @Inject
    public DownloadSourceImpl(Context context) {
        this.context = context;
    }

    @Override
    public Completable downloadVideo(DownloadVideo video) {
        if (video.getFileName() == null || video.getAnimeName() == null || video.getDownloadLink() == null) {
            return Completable.error(new NoSuchElementException());
        }

        return Completable.fromAction(() -> {
            String title = video.getFileName()
                    .concat(" ")
                    .concat(video.getAnimeName());

            Uri link = Uri.parse(video.getDownloadLink());
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(link);
            request.setTitle(title);
            request.setDescription(context.getString(R.string.app_name));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, "ShikimoriApp/" + video.getAnimeName() + "/" + title);

            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }

            Log.i("DOWNLOAD", "downloadVideo: " + video.getDownloadLink());
        });
    }
}
