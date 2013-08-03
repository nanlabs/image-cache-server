---
layout: project
page: project
title: NaN Labs - Image Cache Server
description: A Java server to save images in a MongoDB database with specific dimensions to improve image loading.
keywords: java,mongodb,cache,picocontainer
home-text: Home
footer-title: Get in touch
logoImg: logo-195x120.png

section: project
header: Image Cache Server

lead-text: A Java server to save, load and remove images in a MongoDB database with specific dimensions to improve image loading.
github-url: https://github.com/nanlabs/image-cache-server
---

<p>Mode of use:</p>

<ul>
	<li>
		To upload a new image invoke, through HTTP, the following URL <code>http://&lt;image-cache-server-url&gt;/upload?source=&lt;image-source-url&gt;</code> where <strong>image-cache-server-url</strong> is the URL of the server, eg: <strong>localhost:8181</strong>, and <strong>image-source-url</strong> is the URL where the image to be cached is found, eg: <strong>http://localhost/assets/image.jpeg</strong>
	</li>

	<li>
		To retrieve a cached image invoke the following URL <code>http://&lt;image-cache-server-url&gt;/static/&lt;imageId&gt;</code> where <strong>imageId</strong> matches the relative path of the uploaded image, eg: if the image is uploaded from <strong>http://localhost/assets/image.jpeg</strong>, then the ID will be <strong>/assets/image.jpeg</strong> and the URL to get the image will be <strong>http://localhost:8181/static/assets/image.jpeg</strong>
	</li>

	<li>
		To remove a cached image invoke the following URL <code>http://&lt;image-cache-server-url&gt;/remove/&lt;imageId&gt;</code> where <strong>imageId</strong> matches the relative path of the uploaded image.
	</li>
</ul>

<p class="lead">Libraries used:</p>
<ul>
	<li>This Java application uses the Java2D API to reduce the images dimensions (and therefore its size).</li>
	<li>The application uses the <a href="http://maven.apache.org/">Maven</a> to ease up the building process.</li>
	<li>In order to become a Java server it uses <a href="http://netty.io/">Netty</a>.</li>
	<li>To include the different components to save, load and remove images the <a href="http://picocontainer.codehaus.org">PicoContainer</a> is used, a lightweight IoC library.</li>
</ul>