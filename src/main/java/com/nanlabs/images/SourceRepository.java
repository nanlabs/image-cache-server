package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;

public interface SourceRepository {

	InputStream fetchImageData(String sourceURL) throws IOException;


}
