package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DefaultSourceRepository implements SourceRepository {

	@Override
	public InputStream fetchImageData(String sourceURL) throws IOException {
	    URL sourceUrl = new URL(sourceURL);
	    URLConnection connection = sourceUrl.openConnection();
	    return connection.getInputStream();
	}

}
