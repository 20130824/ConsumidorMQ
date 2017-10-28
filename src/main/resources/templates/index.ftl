<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <script src="/js/jquery-3.2.1.min.js"></script>

    <script>
        //abriendo el objeto para el websocket
        var webSocket;
        var tiempoReconectar = 5000;
        var datos = '${arreglo}';
        var datosBD = JSON.parse(datos);


        $(document).ready(function(){
            console.info("Iniciando Jquery -  Ejemplo WebServices");
            conectar();
            $("#boton").click(function(){
                webSocket.send($("#mensajeCliente").val());
            });
        });


        window.onload = function () {

            var dps = []; // dataPoints
            var dps2 = []; // dataPoints
            var dps3 = []; // dataPoints
            var dps4 = []; // dataPoints
            var chart = new CanvasJS.Chart("chartContainer", {
                title :{
                    text: "Endpoint 1, temperatura vs tiempo"
                },
                axisX:{
                    title: "tiempo (S)"

                },
                axisY: {
                    title: "temperatura (C)",
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: dps
                }]
            });

            var chart2 = new CanvasJS.Chart("chartContainer2", {
                title :{
                    text: "Endpoint 1, humdead vs tiempo"
                },
                axisX:{
                    title: "tiempo (S)"
                },
                axisY: {
                    title: "humedad (%)",
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: dps2
                }]
            });
            var chart3 = new CanvasJS.Chart("chartContainer3", {
                title :{
                    text: "Endpoint 2, temperatura vs tiempo"
                },
                axisX:{
                    title: "tiempo (S)"
                },
                axisY: {
                    title: "temperatura (C)",
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: dps3
                }]
            });

            var chart4 = new CanvasJS.Chart("chartContainer4", {
                title :{
                    text: "Endpoint 2, humdead vs tiempo"
                },
                axisX:{
                    title: "tiempo (S)"
                },
                axisY: {
                    title: "humedad (%)",
                    includeZero: false
                },
                data: [{
                    type: "line",
                    dataPoints: dps4
                }]
            });

            var xVal = 0;
            var yVal = 0;
            var dataLength = 100;

            var xVal2 = 0;
            var yVal2 = 0;
            var dataLength2 = 100;

            var xVal3 = 0;
            var yVal3 = 0;
            var dataLength3 = 100;

            var xVal4 = 0;
            var yVal4 = 0;
            var dataLength4 = 100;


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

            var updateChart2 = function (x, y) {
                xVal2 = x;
                yVal2 = y;
                dps2.push({
                    x: xVal2,
                    y: yVal2
                });

                if (dps2.length > dataLength2) {
                    dps2.shift();
                }

                chart2.render();
            };

            var updateChart3 = function (x, y) {
                xVal3 = x;
                yVal3 =y;
                dps3.push({
                    x: xVal3,
                    y: yVal3
                });

                if (dps3.length > dataLength3) {
                    dps3.shift();
                }

                chart3.render();
            };

            var updateChart4 = function (x, y) {
                xVal4 = x;
                yVal4 =y;
                dps4.push({
                    x: xVal4,
                    y: yVal4
                });

                if (dps4.length > dataLength4) {
                    dps4.shift();
                }

                chart4.render();
            };

            updateChart(dataLength);
            updateChart2(dataLength2);
            updateChart3(dataLength3);
            updateChart4(dataLength4);



            for(var i = 0; i < datosBD.length; i++){

                if(datosBD[i].IdDispositivo === 1) {
                    updateChart(datosBD[i].fechaGeneracion / 1000, datosBD[i].temperatura);
                    updateChart2(datosBD[i].fechaGeneracion / 1000, datosBD[i].humedad);
                 } else if(datosBD[i].IdDispositivo === 2) {
                    updateChart3(datosBD[i].fechaGeneracion / 1000, datosBD[i].temperatura);
                    updateChart4(datosBD[i].fechaGeneracion / 1000, datosBD[i].humedad);
                }
            }

        /**
         *
         *
         *
         * @param mensaje
         */
        function recibirInformacionServidor(mensaje){

            var obj = JSON.parse(mensaje.data);

if(obj.IdDispositivo ===1) {
    updateChart(obj.fechaGeneracion / 1000, obj.temperatura);
    updateChart2(obj.fechaGeneracion / 1000, obj.humedad);
} else if(obj.IdDispositivo ===2) {
    updateChart3(obj.fechaGeneracion / 1000, obj.temperatura);
    updateChart4(obj.fechaGeneracion / 1000, obj.humedad);
}
          //  datos.push(mensaje.data);
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
<div id="chartContainer2" style="height: 370px; width:100%;"></div>
<div id="chartContainer3" style="height: 370px; width:100%;"></div>
<div id="chartContainer4" style="height: 370px; width:100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="/js/moment.min.js"
<script src="/js/date.js" type="text/javascript"></script>
</body>



</html>