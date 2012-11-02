import wsp5.*;

WsClient client;

void setup(){
  // you need to wrap this in a try/catch for now...
  try {
    client = new WsClient( this, "ws://localhost:8080");
    client.connect();
  } catch ( Exception e ){
  }
}

void draw(){
}

void mousePressed(){
  client.send("hey?");
}

void onWsOpen(){
}

void onWsMessage( String msg ){
  println("client: got a message "+msg);
}

void onWsClose(){
}
