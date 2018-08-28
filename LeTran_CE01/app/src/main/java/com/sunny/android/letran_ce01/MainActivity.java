
	// Name: Tran Le
	// JAV2 - 1809
	// File name: MainActivity.java

package com.sunny.android.letran_ce01;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sunny.android.letran_ce01.fragments.ListViewFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ArticleTask.GetArticles {

	// Member variables
	private HashMap<String, ArrayList<Article>> articleDict = new HashMap<>();
	private String keyToSave = null;
	private static final String TAG = "MainActivity";
	private static final String FILENAME = "subreddit_articles.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Find spinner and set OnItemSelectedListener
		Spinner spinner = (Spinner)findViewById(R.id.spn_Spinner);
		spinner.setOnItemSelectedListener(spinnerTapped);
	}

	// Function to check if there is internet connection
	private boolean isConnected() {
		ConnectivityManager mgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		if (mgr != null) {
			NetworkInfo info = mgr.getActiveNetworkInfo();

			if (info != null) {
				return info.isConnected();
			}
		}

		return false;
	}

	// Implement interface methods to update main thread with data from task
	@Override
	public void getArticles(ArrayList<Article> articles) {
		if (keyToSave != null) {
			articleDict.put(keyToSave, articles);
		}

		saveSerializable();
		getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, ListViewFragment.newInstance(articles)).commit();
	}

	// Function to handle actions when user taps on different selection in spinner
	private final AdapterView.OnItemSelectedListener spinnerTapped = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String subreddit = parent.getItemAtPosition(position).toString();
			if (isConnected()) {
				keyToSave = subreddit;
				String url = "https://www.reddit.com/r/" + subreddit + "/hot.json";

				ArticleTask articleTask = new ArticleTask(MainActivity.this);
				articleTask.execute(url);
			} else {
				Toast.makeText(MainActivity.this, R.string.toast_no_connection, Toast.LENGTH_SHORT).show();

				loadSerializable();
				ArrayList<Article> temp_Articles = articleDict.get(subreddit);
				getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, ListViewFragment.newInstance(articleDict.get(subreddit))).commit();
				if (temp_Articles == null) {
					Toast.makeText(MainActivity.this, R.string.toast_no_data, Toast.LENGTH_SHORT).show();
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			Log.i(TAG, "onNothingSelected: NOTHING IS SELECTED!");
		}
	};

	// Function to save the dictionary which contains all the information to file
	private void saveSerializable() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(articleDict);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Function to retrieve data
	private void loadSerializable() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			articleDict = (HashMap<String, ArrayList<Article>>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
