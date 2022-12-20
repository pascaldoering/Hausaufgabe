
var webSocket;
var ids = [];
var displayedIds = [];
var counter = 1;

function openSocket() {
    if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
       writeResponse("WebSocket is already opened.");
        return;
    }

    webSocket = new WebSocket("ws://localhost:8080/Hausaufgabe/getID");
     

    webSocket.onopen = function(event) {
        if(event.data === undefined)
            return;
    };

    webSocket.onmessage = function(messageEvent) {
    	if(messageEvent != null && messageEvent.data != null && messageEvent.data.split('&')[0] != 'null') {
    		console.log(messageEvent);
    		
    		let data = messageEvent.data;
    		
    		let text = data.split('&')[0];
    		
    		let id = data.split('&')[1];
    		
			let blogcard = document.getElementById('blog-card');
			let cloned = blogcard.cloneNode(true);
			cloned.id = cloned.id + counter;
			
			let cheader = cloned.querySelector('#card-header');
			cheader.id = cheader.id + counter;
			cheader.innerHTML += 'Blogeintrag mit der ID : '+id;
			
			let cbody = cloned.querySelector('#card-body');
			cbody.id = cbody.id + counter;
			cbody.innerHTML = text;
			
			let content = document.getElementById('content');
			
			content.appendChild(cloned);
			
			displayedIds.push(id);
			
			counter++;
		}
    };
}

function send() {
    var text = document.getElementById("messageinput").value;
    webSocket.send(text);
}

function closeSocket() {
    webSocket.close();
}

$(document).ready(function() {
	openSocket();

});