package com.theta360.pluginapplication.task;

import android.os.AsyncTask;
import android.util.Log;

import com.theta360.pluginapplication.network.HttpConnector;
import com.theta360.pluginapplication.network.HttpEventListener;

public class ModeChangeTask extends AsyncTask<Void, Void, HttpConnector.ShootResult> {
    private ModeChangeTask.Callback mCallback;
    private String mCurrentCaptureMode;

    public ModeChangeTask(String mode, ModeChangeTask.Callback callback) {
        this.mCallback = callback;
        mCurrentCaptureMode = mode;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected HttpConnector.ShootResult doInBackground(Void... params) {
        HttpConnector camera = new HttpConnector("127.0.0.1:8080");

        HttpConnector.ShootResult result=HttpConnector.ShootResult.SUCCESS;
        if(mCurrentCaptureMode.isEmpty()) {
            mCurrentCaptureMode = camera.getCaptureMode();
            if (mCurrentCaptureMode.equals("image")) {
                mCurrentCaptureMode = "video";
            } else if (mCurrentCaptureMode.equals("video")) {
                mCurrentCaptureMode = "image";
            } else {
                result = HttpConnector.ShootResult.FAIL_DEVICE_BUSY;
            }
        }
        if(result==HttpConnector.ShootResult.SUCCESS) {
            String errorMessage = camera.setCaptureMode(mCurrentCaptureMode);
            if (errorMessage != null) {
                result = HttpConnector.ShootResult.FAIL_DEVICE_BUSY;
            }else {
                mCallback.onModeChange(mCurrentCaptureMode);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(HttpConnector.ShootResult result) {

    }

    public interface Callback {
        void onModeChange(String mode);
    }
}
