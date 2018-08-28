
	// Name: Tran Le
	// JAV2 - 1809
	// File name: ListAdapter.java

package com.sunny.android.letran_ce01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

	// Member variables
	private static final int BASE_ID = 0x00001;
	private final ArrayList<Article> articles;
	private final Context aContext;

	// Constructor
	public ListAdapter(ArrayList<Article> articles, Context aContext) {
		this.articles = articles;
		this.aContext = aContext;
	}

	// Code to return the size of the ArrayList
	@Override
	public int getCount() {
		if (articles != null) {
			return articles.size();
		}

		return 0;
	}

	// Code to get object at the respective index in the ArrayList
	@Override
	public Object getItem(int position) {
		if (articles != null && articles.size() > position || 0 <= position) {
			return articles.get(position);
		}

		return null;
	}

	// Code to get unique item id
	@Override
	public long getItemId(int position) {
		return BASE_ID + position;
	}

	// Code to get view using recycle pattern
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;

		if (convertView == null) {
			convertView = LayoutInflater.from(aContext).inflate(R.layout.listview_row, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder)convertView.getTag();
		}

		Article article = (Article)getItem(position);

		if (article != null) {
			vh.txt_Title.setText(article.getTitle());
			vh.txt_Author.setText(article.getAuthor());
			vh.txt_Comments.setText(article.getNumOfComments().toString());
		}

		return convertView;
	}

	// Create a class for recycle view pattern
	static class ViewHolder {
		final TextView txt_Title;
		final TextView txt_Comments;
		final TextView txt_Author;

		ViewHolder(View _layout) {
			txt_Title = (TextView)_layout.findViewById(R.id.txt_ArticleTitle);
			txt_Author = (TextView)_layout.findViewById(R.id.txt_Author);
			txt_Comments = (TextView)_layout.findViewById(R.id.txt_NumOfComments);
		}
	}
}
