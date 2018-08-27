
	// Name: Tran Le
	// JAV2 - 1809
	// File name: Article.java

package com.sunny.android.letran_ce01;

import java.io.Serializable;

public class Article implements Serializable {

	// Member variables
	private String title;
	private String author;
	private int numOfComments;

	// Constructor
	public Article(String title, String author, int numOfComments) {
		this.title = title;
		this.author = author;
		this.numOfComments = numOfComments;
	}

	// Getters for retrieve info from member variables
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public Integer getNumOfComments() {
		return numOfComments;
	}
}
