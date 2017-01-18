package tv.danmaku.ijk.media.example.utils;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.vbyte.p2p.OnLoadedListener;
import com.vbyte.p2p.SecurityUrl;
import com.vbyte.p2p.UrlGenerator;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

import cn.vbyte.p2p.LiveController;
import cn.vbyte.p2p.VodController;
import tv.danmaku.ijk.media.player.AbstractMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

/**
 * Created by passion on 17-1-11.
 */

public class VbyteP2PMediaPlayer implements IMediaPlayer {
    public static final int VIDEO_LIVE = 0;
    public static final int VIDEO_VOD = 1;

    private IMediaPlayer mediaPlayer;
    private int videoType;
    private double shiftTime;

    public VbyteP2PMediaPlayer(IMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.videoType = VIDEO_LIVE;
        this.shiftTime = 0;
    }

    public IMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public VbyteP2PMediaPlayer setMediaPlayer(IMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        return this;
    }

    public int getVideoType() {
        return videoType;
    }

    public VbyteP2PMediaPlayer setVideoType(int videoType) {
        this.videoType = videoType;
        return this;
    }

    public double getShiftTime() {
        return shiftTime;
    }

    public VbyteP2PMediaPlayer setShiftTime(double shiftTime) {
        this.shiftTime = shiftTime;
        return this;
    }

    // proxy mediaPlayer
    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        mediaPlayer.setDataSource(iMediaDataSource);
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        final Context mContext = context;
        OnLoadedListener listener = new OnLoadedListener() {
            @Override
            public void onLoaded(Uri uri) {
                try {
                    mediaPlayer.setDataSource(mContext, uri);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            if (videoType == VIDEO_LIVE) {
                LiveController.getInstance().load(uri.toString(), "UHD", shiftTime, listener);
            } else {
                VodController.getInstance().setUrlGenerator(new UrlGenerator() {
                    @Override
                    public SecurityUrl createSecurityUrl(String sourceId) {
                        return new SecurityUrl(sourceId);
                    }
                });
                VodController.getInstance().load(uri.toString(), "UHD", listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        final Context mContext = context;
        final Map<String, String> mMap = map;
        OnLoadedListener listener = new OnLoadedListener() {
            @Override
            public void onLoaded(Uri uri) {
                try {
                    mediaPlayer.setDataSource(mContext, uri, mMap);
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            if (videoType == VIDEO_LIVE) {
                LiveController.getInstance().load(uri.toString(), "UHD", shiftTime, listener);
            } else {
                VodController.getInstance().setUrlGenerator(new UrlGenerator() {
                    @Override
                    public SecurityUrl createSecurityUrl(String sourceId) {
                        return new SecurityUrl(sourceId);
                    }
                });
                VodController.getInstance().load(uri.toString(), "UHD", listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        mediaPlayer.setDataSource(fileDescriptor);
    }

    @Override
    public void setDataSource(String s) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        OnLoadedListener listener = new OnLoadedListener() {
            @Override
            public void onLoaded(Uri uri) {
                try {
                    mediaPlayer.setDataSource(uri.toString());
                    mediaPlayer.prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            if (videoType == VIDEO_LIVE) {
                LiveController.getInstance().load(s, "UHD", shiftTime, listener);
            } else {
                VodController.getInstance().setUrlGenerator(new UrlGenerator() {
                    @Override
                    public SecurityUrl createSecurityUrl(String sourceId) {
                        return new SecurityUrl(sourceId);
                    }
                });
                VodController.getInstance().load(s, "UHD", listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDataSource() {
        return mediaPlayer.getDataSource();
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mediaPlayer.prepareAsync();
    }

    @Override
    public void start() throws IllegalStateException {
        mediaPlayer.start();
        if (videoType == VIDEO_VOD) {
            // VodController.getInstance().resume();
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        mediaPlayer.stop();
        if (videoType == VIDEO_LIVE) {
            LiveController.getInstance().unload();
        } else {
            VodController.getInstance().unload();
        }
    }

    @Override
    public void pause() throws IllegalStateException {
        mediaPlayer.pause();
        if (videoType == VIDEO_VOD) {
            // VodController.getInstance().pause();
        }
    }

    @Override
    public void setScreenOnWhilePlaying(boolean b) {
        mediaPlayer.setScreenOnWhilePlaying(b);
    }

    @Override
    public int getVideoWidth() {
        return mediaPlayer.getVideoWidth();
    }

    @Override
    public int getVideoHeight() {
        return mediaPlayer.getVideoHeight();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(long l) throws IllegalStateException {
        mediaPlayer.seekTo(l);
    }

    @Override
    public long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void release() {
        mediaPlayer.release();
    }

    @Override
    public void reset() {
        mediaPlayer.reset();
    }

    @Override
    public void setVolume(float v, float v1) {
        mediaPlayer.setVolume(v, v1);
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    @Override
    public MediaInfo getMediaInfo() {
        return mediaPlayer.getMediaInfo();
    }

    @Override
    public void setLogEnabled(boolean b) {
        mediaPlayer.setLogEnabled(b);
    }

    @Override
    public boolean isPlayable() {
        return mediaPlayer.isPlayable();
    }

    @Override
    public void setAudioStreamType(int i) {
        mediaPlayer.setAudioStreamType(i);
    }

    @Override
    public void setKeepInBackground(boolean b) {
        mediaPlayer.setKeepInBackground(b);
    }

    @Override
    public int getVideoSarNum() {
        return mediaPlayer.getVideoSarNum();
    }

    @Override
    public int getVideoSarDen() {
        return mediaPlayer.getVideoSarDen();
    }

    @Override
    public void setWakeMode(Context context, int i) {
        mediaPlayer.setWakeMode(context, i);
    }

    @Override
    public void setLooping(boolean b) {
        mediaPlayer.setLooping(b);
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        mediaPlayer.setOnPreparedListener(onPreparedListener);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        mediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        mediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
    }

    @Override
    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        mediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
    }

    @Override
    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        mediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
    }

    @Override
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        mediaPlayer.setOnErrorListener(onErrorListener);
    }

    @Override
    public void setOnInfoListener(OnInfoListener onInfoListener) {
        mediaPlayer.setOnInfoListener(onInfoListener);
    }

    @Override
    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        mediaPlayer.setOnTimedTextListener(onTimedTextListener);
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        return mediaPlayer.getTrackInfo();
    }

    @Override
    public void setSurface(Surface surface) {
        mediaPlayer.setSurface(surface);
    }
}
