let stompClient = null;

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/response', (message) => showClient(JSON.parse(message.body)));
    });
}

const sendMsg = () => stompClient.send("/app/message", {}, JSON.stringify({
    'name': $("#name").val(),
    'address': $("#address").val(),
    'phone': $("#phone").val()
}))


const showClient = (message) => {
    document.querySelector('.content').innerHTML = `<table class="client" border = 2></table>`
    let row = document.createElement('tr')
    row.innerHTML = `<td>Id</td><td>Имя</td><td>Адрес</td><td>Телефоны</td>`
    document.querySelector('.client').appendChild(row)
    const clientList = JSON.parse(message.messageStr.replace(/&quot;/g, '"'))
    for (key in clientList) {
        let row = document.createElement('tr')
        row.innerHTML = `<td>${clientList[key]['id']}</td>
    <td>${clientList[key]['name']}</td>
    <td>${clientList[key]['address']['street']}</td>
    <td>${clientList[key]['phones'][0]['number']}</td>`
        document.querySelector('.client').appendChild(row)
    }
}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#send").click(sendMsg);
});
