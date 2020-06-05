package com.theta360.pluginapplication.task;

import android.os.AsyncTask;

import com.theta360.pluginapplication.network.HttpConnector;
import com.theta360.pluginapplication.network.HttpEventListener;

import org.json.JSONObject;

import java.util.List;

public class GetOptionsTask extends AsyncTask<Void, Void, HttpConnector.ShootResult> {
    private GetOptionsTask.Callback mCallback;
    private List<String> mOptions;

    public GetOptionsTask(List<String> options, GetOptionsTask.Callback callback) {
        this.mOptions = options;
        this.mCallback = callback;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected HttpConnector.ShootResult doInBackground(Void... params) {
        HttpConnector.ShootResult result = HttpConnector.ShootResult.SUCCESS;
        HttpConnector camera = new HttpConnector("127.0.0.1:8080");
        JSONObject obj = camera.getOptions(mOptions);
        if(obj == null){
            result = HttpConnector.ShootResult.FAIL_CAMERA_DISCONNECTED;
        }else {
            mCallback.onGetOptionsTask(obj);
        }
        return result;
    }

    @Override
    protected void onPostExecute(HttpConnector.ShootResult result) {

    }


    public interface Callback {
        void onGetOptionsTask(JSONObject options);
    }
}
