/**
 * author:fulin.wang
 * date:2017-03-20
 * remark:web-socket通信
 */
import socketutil from "./socketurl"
import SockJS from "SockJS" 
import Stomp from "Stomp" 
export default  {
    connect: function(arr,callBack){
        try
            {
                var socket= new SockJS('/endpointWisely');
                socketutil.stompClient = Stomp.over(socket);
                socketutil.stompClient.connect({}, function(frame) {
                    for(var i=0;i<=arr.length-1;i++){
                        socketutil.stompClient.subscribe('/topic/'+arr[i].measureInfo["ECACashPoolMHT.ECACashMeasure"].key, callBack);
                    }
                });
            }
        catch(err){
            console.log("错误："+err)
        }
    },
    disconnect: function() {
        if (socketutil.stompClient != null) {
            socketutil.stompClient.disconnect();
        }
    },
    subscribe: function (socket_con) {
        socketutil.stompClient = Stomp.over(socket_con);
        socketutil.stompClient.connect({}, function(frame) {
            socketutil.stompClient.subscribe('/ECACashPoolMHT/1/ECACashMeasure', function(respnose){
                console.log(JSON.parse(respnose.body).responseMessage);
            });
        });
    },
    sendName: function(sendMsg) {
        socketutil.stompClient.send("/security", {}, JSON.stringify(sendMsg));
    }
}
