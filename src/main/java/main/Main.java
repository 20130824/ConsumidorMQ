package main;

import org.json.JSONArray;
import org.json.JSONObject;
import entidades.lecturaSensor;
import freemarker.template.Configuration;
import jms.Consumidor;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.jetty.websocket.api.Session;
import services.BootStrapService;
import services.ServidorMensajesWebSocketHandler;
import services.sensorServices;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.jms.JMSException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;

/**
 * Ejemplo para probar el uso de JMS, utilizando servidor de mensajeria ActiveMQ, ver en
 * http://activemq.apache.org/
 * Created by vacax on 03/10/15.
 */
public class Main {



    public static void main(String[] args) throws IOException, JMSException  {



        staticFiles.location("/");
        port(4567);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        BootStrapService.getInstancia().init();

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        webSocket("/mensajeServidor", ServidorMensajesWebSocketHandler.class);
        init();
        get("/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            List<lecturaSensor> lecturaList = new ArrayList<>();

            lecturaList = sensorServices.getInstancia().findAll();

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date fecha1 = new Date();
                    if(lecturaList.size() > 0) {
                        fecha1 = dateFormat.parse(lecturaList.get(0).getFechaGeneracion());
                    }
           //a System.out.println("fecha " + fecha1);
            JSONArray jsonArray = new JSONArray();

            for(lecturaSensor ls : lecturaList){
                Date fecha2 = dateFormat.parse(ls.getFechaGeneracion());

                long diff = Math.abs(fecha1.getTime() - fecha2.getTime());
                System.out.println("diferencia: " + diff);
                JSONObject jsonObject = new org.json.JSONObject()
                        .put("IdDispositivo", ls.getIdDispositivo())
                        .put("fechaGeneracion", diff*-1)
                        .put("humedad",ls.getHumedad())
                        .put("temperatura", ls.getTemperatura());


                jsonArray.put(jsonObject);
            }

            attributes.put("arreglo", jsonArray);

            return new ModelAndView(attributes, "index.ftl");
        }, freeMarkerEngine);

        System.out.println("Prueba de Mensajeria Asincrona");
        String cola = "pruebajms.cola";



            Consumidor consumidor=new Consumidor(cola);
            consumidor.conectar();

            System.out.println("Presiona Enter para salir del programa:");
            Scanner s = new Scanner(System.in);
            String dato = s.nextLine();
            consumidor.cerrarConexion();
            System.exit(0);




    }


}