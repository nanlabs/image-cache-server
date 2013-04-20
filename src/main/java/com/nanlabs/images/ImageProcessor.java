package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;

public interface ImageProcessor {

	byte[] doResize(InputStream source) throws IOException;

}
