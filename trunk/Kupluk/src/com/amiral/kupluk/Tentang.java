package com.amiral.kupluk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.webkit.WebView;

public class Tentang extends Activity{
	
	WebView webview;
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.tentang);
		
		webview = (WebView) findViewById(R.id.tentang_kupluk);
		
		 String content = ambilResource(getResources(),R.raw.info);

	        String mimeType = "text/html";
	        String encoding = "UTF-8";

	        //webview.loadUrl("file:///android_asset/info.html");
	        //webview.loadData(content, mimeType, encoding);
	        webview.loadDataWithBaseURL("file:///android_asset", content, mimeType, encoding, null);
	}

	public static String ambilResource(Resources resources, int resId) {
		InputStream rawResource = resources.openRawResource(resId);
	    String content = ubahKeString(rawResource);
	    try {rawResource.close();} catch (IOException e) {}
		return content;
	}
	
	private static String ubahKeString(InputStream in) {
	    String l;
	    BufferedReader r = new BufferedReader(new InputStreamReader(in));
	    StringBuilder s = new StringBuilder();
	    try {
	        while ((l = r.readLine()) != null) {
	            s.append(l + "\n");
	        }
	    } catch (IOException e) {} 
	    return s.toString();
	}
	

}
