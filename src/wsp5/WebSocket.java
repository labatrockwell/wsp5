package wsp5;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshakeBuilder;

public class WebSocket{

	public enum Role {
		CLIENT, SERVER
	}

	public static int RCVBUF = org.java_websocket.WebSocket.RCVBUF;

	//public static/*final*/boolean DEBUG = org.java_websocket.WebSocket.DEBUG; // must be final in the future in order to take advantage of VM optimization

	public static final int READY_STATE_CONNECTING = org.java_websocket.WebSocket.READY_STATE_CONNECTING;
	public static final int READY_STATE_OPEN = org.java_websocket.WebSocket.READY_STATE_OPEN;
	public static final int READY_STATE_CLOSING = org.java_websocket.WebSocket.READY_STATE_CLOSING;
	public static final int READY_STATE_CLOSED = org.java_websocket.WebSocket.READY_STATE_CLOSED;
	/**
	 * The default port of WebSockets, as defined in the spec. If the nullary
	 * constructor is used, DEFAULT_PORT will be the port the WebSocketServer
	 * is binded to. Note that ports under 1024 usually require root permissions.
	 */
	public static final int DEFAULT_PORT = org.java_websocket.WebSocket.DEFAULT_PORT;

	public static final int DEFAULT_WSS_PORT = org.java_websocket.WebSocket.DEFAULT_WSS_PORT;

	org.java_websocket.WebSocket m_Socket;
	public WebSocket(org.java_websocket.WebSocket a_Socket){
		m_Socket = a_Socket;
	}

	public org.java_websocket.WebSocket getWebSocket(){
		return m_Socket;
	}

	public void close(int code, String message){
		m_Socket.close(code, message);
	}

	public void close(int code){
		m_Socket.close(code);
	}

	public void send( String text ) throws NotYetConnectedException{
		m_Socket.send(text);
	}

	public void send( ByteBuffer bytes ) throws IllegalArgumentException , NotYetConnectedException{
		m_Socket.send(bytes);
	}

	public void send( byte[] bytes ) throws IllegalArgumentException , NotYetConnectedException{
		m_Socket.send(bytes);
	}

	public void sendFrame( Framedata framedata ){
		m_Socket.sendFrame(framedata);
	}

	public boolean hasBufferedData(){
		return m_Socket.hasBufferedData();
	}

	public void startHandshake( ClientHandshakeBuilder handshakedata ) throws InvalidHandshakeException{
		m_Socket.startHandshake(handshakedata );
	}

	public InetSocketAddress getRemoteSocketAddress(){
		return m_Socket.getRemoteSocketAddress();
	}

	public InetSocketAddress getLocalSocketAddress(){
		return m_Socket.getLocalSocketAddress();
	}

	public boolean isConnecting(){
		return m_Socket.isConnecting();
	}

	public boolean isOpen(){
		return m_Socket.isOpen();
	}

	public boolean isClosing(){
		return m_Socket.isClosing();
	}

	public boolean isClosed(){
		return m_Socket.isClosed();
	}

	public Draft getDraft(){
		return m_Socket.getDraft();
	}

	public int getReadyState(){
		return m_Socket.getReadyState();
	}
};