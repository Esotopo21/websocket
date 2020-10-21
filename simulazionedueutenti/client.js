var webSocket;

function openConnection(){
    if (webSocket){
        console.log("Connection already started");
        return;
    }
    webSocket = new WebSocket("ws://localhost:8080/wsdeliverer");

    webSocket.onopen = ($event) => {
        console.log("Connection started");
    }

    webSocket.onmessage = ($event) => {
        appendMessage($event.data);
    }

    webSocket.onclose = ($event) => {
        console.log("Connection closed");
    }
}

function closeConnection(){
    webSocket.close();
}

function sendMessage(){
    console.log(document.getElementById("receiverId").value);
    let message = {
        receiverId: document.getElementById("receiverId").value,
        message: document.getElementById("message").value
    };
    webSocket.send(JSON.stringify(message));
}

function appendMessage(message){
    document.getElementById("messagebox").innerHTML += "<br>" + message + "<br>" + "--------------";
}