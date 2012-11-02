import wsp5.*;

WsServer server;

void setup(){
  // you need to wrap this in a try/catch for now...
  try {
    server = new WsServer( this, 8080);
  } catch ( Exception e ){
  }
}

void draw(){
}

void mousePressed(){
  server.sendToAll("hey");
}

void onWsOpen(org.java_websocket.WebSocket connection){
  println("got a new connection");
}

void onWsMessage( org.java_websocket.WebSocket connection, String message ){
  println("got a message "+message);
}

void onWsClose( org.java_websocket.WebSocket connection){
}
