package com.nanlabs.images;

import org.apache.commons.pool.ObjectPool;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import com.mongodb.MongoClient;

public class WebServer {
	
	
	
	private final Config config;
	private MutablePicoContainer pico;

	public WebServer(Config config) throws Exception {
		this.config = config;
		initializeBeans();
		Server server = new Server(new QueuedThreadPool(config.getPoolSize()));
		ServerConnector connector=new ServerConnector(server);
        connector.setPort(config.getServerPort());
        server.setConnectors(new Connector[]{connector});
		
        ContextHandler uploaderContext = new ContextHandler("/upload");
		uploaderContext.setHandler(pico.getComponent(ImageResizerHandler.class));
		
		ContextHandler imageServerContext = new ContextHandler("/static");
		imageServerContext.setHandler(pico.getComponent(ImageServerHandler.class));
		
		ContextHandler imageRemoveContext = new ContextHandler("/remove");
		imageRemoveContext.setHandler(pico.getComponent(ImageRemoveHandler.class));
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[]{uploaderContext, imageServerContext, imageRemoveContext});
	    server.setHandler(contexts);
	    server.start();
	    server.join();
	}
	

	private void initializeBeans(){
		pico = new DefaultPicoContainer();
		pico.addComponent(ImageResizerHandler.class)
		.addComponent(ImageServerHandler.class)
		.addComponent(ImageRemoveHandler.class)
		.addComponent(Java2DImageProcessor.class)
		.addComponent(ImageImporter.class)
		.addComponent(URLConnectionPoolableObjectFactory.class)
		.addComponent(ObjectPool.class, URLConnectionFactoryPool.class,new ConstantParameter(config.getClientPoolSize()), new ComponentParameter())
		.addComponent(MongoClient.class, MongoClient.class, new ConstantParameter(config.getMongoURL()))
		.addComponent(MongoStorageRepository.class, MongoStorageRepository.class, new ComponentParameter(), new ConstantParameter(config.getDbName()));		
	}

	/**java -jar image-resizer.jar http.port=8080 source.url=localhost:8080
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
			Config config = new Config(args);
			new WebServer(config);
	}
	
	 private static class Config{
		 
		 private  int serverPort = 8181;
		 private  String dbname = "imageStorage";
		 private  String mongoURL = "localhost";
		 private int poolSize = 10;
		 private int clientPoolSize = 10;

		public Config(String[] args) {
			
			for(String arg:args){
				String[] tokens = arg.split("=");
				String key = tokens[0];
				String value = tokens[1];
				switch(key){
					case "server.port":
						this.serverPort = Integer.parseInt(value);
						break;
					case "mongo.dbname":
						this.dbname = value;
						break;
					case "mongo.url":
						this.mongoURL = value;
						break;
					case "server.poolSize":
						this.poolSize = Integer.parseInt(value);
						break;
					case "client.poolSize":
						this.clientPoolSize = Integer.parseInt(value);
						break;
				}
			}
		}

		public int getServerPort() {
			return this.serverPort;
		}

		public String getDbName() {
			return this.dbname;
		}

		public String getMongoURL() {
			return this.mongoURL;
		}
		
		public int getPoolSize(){
			return this.poolSize;
		}
		
		public int getClientPoolSize(){
			return this.clientPoolSize;
		}
		
	}
	
}
