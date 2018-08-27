
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ArticleTask.java

package com.sunny.android.letran_ce01;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

	public class ArticleTask extends AsyncTask<String, Void, ArrayList<Article>> {

	// Member variables
	private final Context tContext;
	private final GetArticles tInterface;
		private static final String TAG = "ArticleTask";

	// Create interface to communicate with the MainThread
	public interface GetArticles {
		void getArticles(ArrayList<Article> articles);
	}

	// Constructor
	public ArticleTask(Context tContext, GetArticles tInterface) {
		this.tContext = tContext;
		this.tInterface = tInterface;
	}

	// Code to parse JSON and create an ArrayList of Articles
	@Override
	protected ArrayList<Article> doInBackground(String... strings) {
		URL url = null;
		HttpURLConnection connection = null;
		InputStream stream = null;

		ArrayList<Article> articles = new ArrayList<>();

		// Open connection
		try {
			Log.i(TAG, "doInBackground: GET CONNECTION");
			url = new URL(strings[0]);
			connection = (HttpsURLConnection)url.openConnection();
			connection.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Open stream and close after done getting data
		try {
			Log.i(TAG, "doInBackground: GET INPUT STREAM");
			stream = connection.getInputStream();
			String result = IOUtils.toString(stream, "UTF-8");

			JSONObject outerObj = new JSONObject(result);
			JSONObject innerObj = outerObj.getJSONObject("data");
			JSONArray array = innerObj.getJSONArray("children");

			Log.i(TAG, "doInBackground: "+outerObj+" "+innerObj+" "+array.length());

			for (int i = 0; i < array.length(); i++) {
				Log.i(TAG, "doInBackground: INSIDE LOOP");
				JSONObject obj = array.getJSONObject(i);
				JSONObject data = obj.getJSONObject("data");
				articles.add(new Article(data.getString("title"), data.getString("author"), data.getInt("num_comments")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				connection.disconnect();
				IOUtils.close(connection);
			}
		}

		return articles;
	}

	// Code to update the ArrayList to MainThread
	@Override
	protected void onPostExecute(ArrayList<Article> articles) {
		super.onPostExecute(articles);

		if (tInterface != null) {
			tInterface.getArticles(articles);
		}
	}
}
