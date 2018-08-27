
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ListViewFragment.java

package com.sunny.android.letran_ce01.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunny.android.letran_ce01.Article;
import com.sunny.android.letran_ce01.ListAdapter;
import com.sunny.android.letran_ce01.R;

import java.util.ArrayList;

public class ListViewFragment extends ListFragment {

	// Key to save the ArrayList
	private static final String KEY_ARTICLES = "KEY_TO_ARTICLES";

	public ListViewFragment() {
		// Default empty constructor
	}

	// Code to create new instance of ListViewFragment and take in an ArrayList of Articles to
	// populate the list
	public static ListViewFragment newInstance(ArrayList<Article> articles) {

		Bundle args = new Bundle();

		args.putSerializable(KEY_ARTICLES, articles);

		ListViewFragment fragment = new ListViewFragment();
		fragment.setArguments(args);
		return fragment;
	}

	// Code to return the ListViewFragment view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.listview_fragment, container, false);
	}

	// Code to populate ListView with adapter
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getArguments() != null) {
			ArrayList<Article> articles = (ArrayList<Article>) getArguments().getSerializable(KEY_ARTICLES);

			if (articles != null && getContext() != null) {
				ListAdapter adapter = new ListAdapter(articles, getContext());
				setListAdapter(adapter);
			}
		}
	}
}
