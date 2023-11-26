package com.zypher.laba5;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class DataLoader {
    private DataLoadListener listener;

    public DataLoader(DataLoadListener listener) {
        this.listener = listener;
        new DownloadDataTask().execute();
    }

    public interface DataLoadListener {
        void onDataLoaded(ArrayList<String> data);
    }

    private class DownloadDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> result = new ArrayList<>();

            String apiUrl = "https://www.floatrates.com/daily/usd.xml";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    result = Parser.parseData(responseData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> data) {
            super.onPostExecute(data);
            listener.onDataLoaded(data);
        }
    }
}
