var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

$(function () {

    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#connect" ).click(function() {
        // SockJS와 stomp.js를 사용해서 /chat 에 대한 커넥션을 생성한다
        // 이때 SockJS 서버는 연결을 대기하고 성공 시 클라이언트는 서버가 greeting message를
        // publish할 path인 /topic/greetings 를 subscribe(구독) 한다
        // 해당 topic에서 greeting 메시지를 수신하면 이를 표시하기 위해 DOM에 paragraph 요소를 추가한다
        let socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            /**
             * Connected: CONNECTED
             * heart-beat:0,0
             * version:1.1
             */

            // 연결이 되면 여기에 멈춰있는 상태
            // stompClient.subscribe('/topic/greetings', function (greeting) {
            //     console.log("===== subscribe ======")
            //     let message = greeting.body;
            //     // let message = JSON.parse(greeting.body).content;
            //
            //     $("#greetings").append("<tr><td>" + message + "</td></tr>");
            // });

            stompClient.subscribe('/topic/greetings', function (greeting) {
                console.log("===== subscribe ======")
                // let message = greeting.body;
                let message = greeting;
                // let message = JSON.parse(greeting.body).content;

                $("#greetings").append("<tr><td>" + message + "</td></tr>");
            });
        });
    });

    $( "#disconnect" ).click(function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    });

    $( "#send" ).click(function() {
        // 사용자가 입력한 이름을 STOMP 클라이언트를 이용해서 /app/hello로 전송한다
        // 이 메시지는 GreetingController의 greeting() 메서드로 전달된다
        // stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));

        stompClient.send("/pub/chat/message/1", {}, JSON.stringify({'name': $("#name").val()}));
    });
});