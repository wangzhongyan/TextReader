package com.example.textreader;

import java.io.IOException;

import nl.siegmann.epublib.domain.Book;
import android.view.View;
import android.webkit.WebView;

public class PageManager {
	private int currentPage = 1;
	private View view;
	private Book book;
	
	public PageManager(View view, Book book) {
		this.view = view;
		this.book = book;
	}
	
	public void showPage(int pageNumber) {
		String data = "";
		try {
			data = new String(book.getContents().get(pageNumber).getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebView webView = (WebView) view;
		webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
	}
	
	public void showPrevPage() {
		if (currentPage > 1) {
			currentPage = currentPage - 1;
			showPage(currentPage);
		}
		android.util.Log.i("epublib", "prev button");
	}

	public void showNextPage() {
		int size = book.getContents().size();
		if (currentPage < size) {
			currentPage = currentPage + 1;
			showPage(currentPage);
		}

		android.util.Log.i("epublib", "next button");

	}
}
