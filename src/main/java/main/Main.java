package main;

import freemarker.template.Configuration;
import jms.Consumidor;
import org.apache.activemq.broker.BrokerService;
import org.eclipse.jetty.websocket.api.Session;
import services.ServidorMensajesWebSocketHandler;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.jms.JMSException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

        exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });

        webSocket("/mensajeServidor", ServidorMensajesWebSocketHandler.class);
        init();
        get("/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();


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