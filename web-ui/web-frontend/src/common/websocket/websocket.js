/**
 * author:fulin.wang
 * date:2017-03-20
 * remark:web-socket通信
 */
import SockJS from "SockJS"
import Stomp from "Stomp"
let stompClient = null;
export default  {
    /**
    * url:web socket连接地址
    * topicKeyArr：订阅地址
    * callBack：有消息来时的回调函数
    */
    connect: function(url,topicKeyArr, callBack){
        try
            {
                var socket= new SockJS(url);
                stompClient = Stomp.over(socket);
                stompClient.connect({}, function(frame) {
                    for(var i=0;i<=topicKeyArr.length-1;i++){
                        stompClient.subscribe(topicKeyArr[i].topicKey, callBack);
                    }
                });
            }
        catch(err){
            console.log("错误："+err)
        }
    },
    disconnect: function() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }
}
