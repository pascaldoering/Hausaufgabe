
var ids = [];
var displayedIds = [];
var counter = 1;
var interval = 0;

function intervalGetData() {
	console.log('intervalGetData()');
 	getData();
}

function getData() {
	console.log('getData()');
	
	
	var bar = new Promise((resolve, reject) => {
		$.post("InternateAbfrage", '', function(data) {
			data.forEach(function(id) {
				if(!ids.includes(id)) ids.push(id);
				if(data.indexOf(id) == data.length-1) {
					resolve();
				}
			});
		});
	});

	bar.then(() => {
		processID();
	});
}

function processID() {
	console.log('processID()');
	
	var wait = new Promise((resolve, reject) => {
		ids.forEach(function(id) {
			$.post("InternateAbfrageBeitrag", 'ID='+id, function(blog) {
				if(blog != null && blog != '' && !displayedIds.includes(id)) {
					
					let blogcard = document.getElementById('blog-card');
					let cloned = blogcard.cloneNode(true);
					cloned.id = cloned.id + counter;
					
					let cheader = cloned.querySelector('#card-header');
					cheader.id = cheader.id + counter;
					cheader.innerHTML += 'Blogeintrag mit der ID : '+id;
					
					let cbody = cloned.querySelector('#card-body');
					cbody.id = cbody.id + counter;
					cbody.innerHTML = blog;
					
					let content = document.getElementById('content');
					
					content.appendChild(cloned);
					
					displayedIds.push(id);
					
					counter++;
				}
				
			});
			if(ids.indexOf(id) == ids.length-1) {
				resolve();
			}
		});
	});

	wait.then(() => {
		if(interval == 0) {
			interval = 5000;													//Interval in welchem die IDs der Blogeintraege abgefragt werden soll
			console.log('setInterval 10000');									//Interval in welchem die IDs der Blogeintraege abgefragt werden soll
			var intervalID = window.setInterval(intervalGetData, interval);		//Interval in welchem die IDs der Blogeintraege abgefragt werden soll
		}
	});
}

$(document).ready(function() {
	getData();
});