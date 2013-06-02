package com.nanlabs.images;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionFactory {
	
	public URLConnection open(String url) throws MalformedURLException, IOException{
		return new URL(url).openConnection();
	}

}
