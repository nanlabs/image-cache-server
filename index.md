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

lead-text: A Java server to save images in a MongoDB database with specific dimensions to improve image loading.
---

<p>This application works as a Java Server providing an API to save, load and remove images.</p>

<p class="lead">Libraries used:</p>
<ul>
	<li>This Java application uses the Java2D API to reduce the images dimensions (and therefore its size).</li>
	<li>In order to become a Java server it uses <a href="http://netty.io/">Netty</a>.</li>
	<li>To include the different components to save, load and remove images the <a href="http://picocontainer.codehaus.org">PicoContainer</a> is used, a lightweight IoC library.</li>
</ul>