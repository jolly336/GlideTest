package com.zihuatanejo.glidetest.okhttp;

import android.util.Log;
import com.android.annotations.Nullable;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by Nelson on 2018/1/22.
 */

public class ProgressResponseBody extends ResponseBody {

    private static final String TAG = "ProgressResponseBody";


    private BufferedSource mBufferedSource;

    private final ResponseBody mResponseBody;

    private ProgressListener mListener;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.mResponseBody = responseBody;
        this.mListener = ProgressInterceptor.LISTENER_MAP.get(url);
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(new ProgressSource(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;
        int currentProgress;

        public ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            long fullLength = mResponseBody.contentLength();
            if (bytesRead == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += bytesRead;
            }

            int progress = (int) (100f * totalBytesRead / fullLength);
            Log.e(TAG, "download progress is : " + progress);
            if (mListener != null && progress != currentProgress) {
                mListener.onProgress(progress);
            }

            if (mListener != null && totalBytesRead == fullLength) {
                mListener = null;
            }

            currentProgress = progress;
            return bytesRead;
        }
    }

}
