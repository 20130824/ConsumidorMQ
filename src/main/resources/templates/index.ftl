<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <script src="/js/jquery-3.2.1.min.js"></script>

    <script>
        //abriendo el objeto para el websocket
        var webSocket;
        var tiempoReconectar = 5000;
        var datos = [];
        $(document).ready(function(){
            console.info("Iniciando Jquery -  Ejemplo WebServices");
            conectar();
            $("#boton").click(function(){
                webSocket.send($("#mensajeCliente").val());
            });
        });


        window.onload = function () {

            var dps = []; // dataPoints
            var chart = new CanvasJS.Chart("chartContainer", {
                title :{
                    text: "Dynamic Data"
                },
                axisX:{
                    title: "timeline",
                    valueFormatString: "YYYY-MM-DDTHH:MM:SS",
                    gridThickness: 2
                },
                axisY: {
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: dps
                }]
            });

            var xVal;
            var yVal = 100;
            var updateInterval = 1000;
            var dataLength = 5; // number of dataPoints visible at any point

            var updateChart = function (x, y) {
                xVal = x;
                yVal =y;
                dps.push({
                        x: xVal,
                        y: yVal
                    });

                if (dps.length > dataLength) {
                    dps.shift();
                }

                chart.render();
            };

            updateChart(dataLength);
            setInterval(function(){updateChart()}, updateInterval);


        /**
         *
         *
         *
         * @param mensaje
         */
        function recibirInformacionServidor(mensaje){

            var obj = JSON.parse(mensaje.data);
            //console.log(obj.fechaGeneracion);


           updateChart(Date.parse(obj.fechaGeneracion), obj.temperatura);
            //datos.push(mensaje.data);
        }

        function conectar() {
            webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor");
            //indicando los eventos:
            webSocket.onmessage = function(data){recibirInformacionServidor(data);};
            webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
            webSocket.onclose = function(e){
                console.log("Desconectado - status "+this.readyState);
            };
        }
        function verificarConexion(){
            if(!webSocket || webSocket.readyState == 3){
                conectar();
            }
        }
        setInterval(verificarConexion, tiempoReconectar); //para reconectar.

        }
    </script>
</head>
    <title>Title</title>
</head>
<body>
<div id="chartContainer" style="height: 370px; width:100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="/js/moment.min.js"
<script src="/js/date.js" type="text/javascript"></script>
</body>



</html>