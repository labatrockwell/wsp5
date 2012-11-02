package wsp5;

import java.util.*;

// import general java stuff for server
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.lang.reflect.Method;

import java.lang.Integer;
import java.lang.Boolean;
import java.lang.String;

// import java-websocket stuff
//import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WsServer extends WebSocketServer {

	private Object parent;
	private Method onOpenMethod;
	private Method onCloseMethod;
	private boolean bUseAdvancedMessage = false;
	private Method onMessageMethod;
	private Method onErrorMethod;

	public WsServer(Object _parent, int port) {
		super( new InetSocketAddress( port ) );
		org.java_websocket.WebSocket.DEBUG = false;
		parent = _parent;
        
        registerEvents();
        start();
	}

	@Override
	public void onOpen( org.java_websocket.WebSocket conn, ClientHandshake handshake ) {
		if ( onOpenMethod != null ){
			try {
				onOpenMethod.invoke( parent, new WebSocket(conn) );
			} catch( Exception e ){
				System.err.println("onWsOpened invoke failed, disabling :(");
				onOpenMethod = null;
			}
		}
	}

	@Override
	public void onClose( org.java_websocket.WebSocket conn, int code, String reason, boolean remote ) {
		if ( onCloseMethod != null ){
			try {
				onCloseMethod.invoke( parent, new WebSocket(conn), code, reason, remote);
			} catch( Exception e ){
				System.err.println("onWsClosed invoke failed, disabling :(");
				onCloseMethod = null;
			}
		}
	}

	@Override
	public void onMessage( org.java_websocket.WebSocket conn, String message ) {
		if ( onMessageMethod != null ){
			try {
				if ( bUseAdvancedMessage ){
					onMessageMethod.invoke( parent, new WebSocket(conn), message);
				} else {
					onMessageMethod.invoke( parent, message);	
				}
			} catch( Exception e ){
				System.err.println("onMessage invoke failed, disabling :(");
				onMessageMethod = null;
			}
		}
	}

	@Override
	public void onError( org.java_websocket.WebSocket conn, Exception ex ) {
		ex.printStackTrace();
	}

	/**
	 * Sends <var>text</var> to all currently connected WebSocket clients.
	 * 
	 * @param text
	 *            The String to send across the network.
	 * @throws InterruptedException
	 *             When socket related I/O errors occur.
	 */
	public void sendToAll( String text ) {
		Set<org.java_websocket.WebSocket> con = connections();
		synchronized ( con ) {
			for( org.java_websocket.WebSocket c : con ) {
				c.send( text );
			}
		}
	}

	/** 
	 * check to see if the host applet implements methods:
	 * public void onWsMessage(String message)
	 * or [ADVANCED] public void onWsMessage(WebSocket conn, String message) 
	 * Optional:
	 * public void onWsOpened(WebSocket p)
	 * public void onWSClosed(WebSocket p, int code, String reason, boolean remote)
	*/
	private void registerEvents() {
		try {
			onOpenMethod = parent.getClass().getMethod("onWsOpened", new Class[]{WebSocket.class});
			onCloseMethod = parent.getClass().getMethod("onWSClosed", new Class[]{WebSocket.class, Integer.class, String.class, Boolean.class});
        } catch (Exception optionalE){
        	// should be a warning here
        }

		try {
			onMessageMethod = parent.getClass().getMethod("onWsMessage", new Class[]{String.class});
        } catch (Exception advE){
        	try {
				onMessageMethod = parent.getClass().getMethod("onWsMessage", new Class[]{WebSocket.class, String.class});
				bUseAdvancedMessage = true;
        	}
        	catch (Exception e){
	        	System.err.println("No onMessage method implemented. You won't be able to catch methods from WebSockets.");
        	}
        }

	}
};