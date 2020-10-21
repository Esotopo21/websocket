var webSocket;

function openConnection(){
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
        console.info("Connection with server has already started");
        return;
    }
    webSocket = new WebSocket("ws:localhost:8080/wsecho");

    webSocket.onopen = ($event) => {
        console.log($event);
        appendMessage("Connection started");
    }
    
    webSocket.onmessage = ($event) => {
        console.log($event);
        appendMessage($event.data);
    }
    
    webSocket.onclose = ($event) => {
        console.log($event);
        appendMessage("COnnection closed");
    }
}

function closeConnection(){
    if(!webSocket){
        console.info("No connection opened");
    }
    webSocket.close();
}

function appendMessage(message){
    var messagesBox = document.getElementById("messagesBox"); 
    messagesBox.innerHTML += "<br/>" + message + "<br/>" + "----------------------";
}

function sendMessage(){
    let message = document.getElementById("textMessage").value;
    webSocket.send(message);
}
