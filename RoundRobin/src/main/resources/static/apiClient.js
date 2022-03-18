var apiclient = (function () {
    var url=window.location.href+'results';
    function addMessage(){
        var mensaje=document.getElementById("Message").value;
        console.log(mensaje)
        axios.post(url,mensaje)
            .then(res => {
                getMessages();
            })
    }
    function getMessages(){
        var num=1;
        $("#Table > tbody").empty();
        axios.get(url).then(res=>{
            console.log(res.data)
            res.data.map(message=>{
                console.log(message)
                $("#Table > tbody").append(
                    "<tr>" +
                    "<td>" + num + "</td>" +
                    "<td>" + message.info + "</td>" +
                    "<td>" + message.date + "</td> " +
                    "</tr>"
                );
                num = num +1;
            })
        })
    }
    return {
        addMessage:addMessage,
        getMessages:getMessages
    };
})();
