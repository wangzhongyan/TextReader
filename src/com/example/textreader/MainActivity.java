package com.example.textreader;

import java.io.IOException;
import java.io.InputStream;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends Activity{
	private Book book = null;
	private GestureDetector gestureDetector;
	private PageManager pageManager;
	private WebView webView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// TextView content = (TextView) findViewById(R.id.textContent);
		// content.setMovementMethod(new ScrollingMovementMethod());
		// content.setText(readFileFromAssets("Session.txt"));
		init();
		
		// Set the touch listener for the main view to be our custom gesture listener
        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		disableHomeActionItem();
		return true;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void disableHomeActionItem() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
		}
	}

	public void init() {
		webView = (WebView)findViewById(R.id.webView1);
		
		AssetManager assetManager = getAssets();
		try {
			// find InputStream for book
			InputStream epubInputStream = assetManager.open("Abay.epub");

			// Load Book from inputStream
			book = (new EpubReader()).readEpub(epubInputStream);
		} catch (IOException ex) {

		}
		
		pageManager = new PageManager(webView, book);
		pageManager.showPage(1);
		gestureDetector = new GestureDetector(new MyGestureDetector(pageManager, webView));
		
	}

}
