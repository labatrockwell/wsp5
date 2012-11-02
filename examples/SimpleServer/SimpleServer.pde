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

void onWsOpen(WebSocket connection){
  println("got a new connection");
}

void onWsMessage( WebSocket connection, String message ){
  println("server: got a message "+message);
  connection.send(message);
}

void onWsClose( WebSocket connection){
}
