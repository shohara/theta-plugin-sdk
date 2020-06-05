package com.theta360.pluginapplication.task;

import android.os.AsyncTask;
import android.util.Log;

import com.theta360.pluginapplication.network.HttpConnector;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieCaptureTask extends AsyncTask<Void, Void, HttpConnector.ShootResult> {
    private MovieCaptureTask.Callback mCallback;

    public MovieCaptureTask(MovieCaptureTask.Callback callback) {
        this.mCallback = callback;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected HttpConnector.ShootResult doInBackground(Void... params) {
        HttpConnector.ShootResult result = HttpConnector.ShootResult.SUCCESS;
        try{
            HttpConnector camera = new HttpConnector("127.0.0.1:8080");

            JSONObject status = camera.getStatus();
            String _captureStatus = status.getString("_captureStatus");
            Log.d("debug", "MovieCaptureTask::doInBackground(): _captureStatus="+_captureStatus);
            if(_captureStatus.equals("shooting")) {
                result = camera.stopCapture();
            }else if(_captureStatus.equals("idle")) {
                result = camera.startCapture();
            }
            mCallback.onMovieCapture(camera.getStatus().getString("captureStatus"));

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    protected void onPostExecute(HttpConnector.ShootResult result) {

    }

    public interface Callback {
        void onMovieCapture(String status);
    }
}
