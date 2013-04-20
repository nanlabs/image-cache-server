package com.nanlabs.images;


public interface StorageRepository {

	void store(Image image);
	
	Image findBestFitting(String imageId, int preferredSize);

}
