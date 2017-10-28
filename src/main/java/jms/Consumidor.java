package jms;

import com.google.gson.Gson;
import entidades.lecturaSensor;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.json.JSONObject;
import services.ServidorMensajesWebSocketHandler;
import services.sensorServices;

import javax.jms.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static services.ServidorMensajesWebSocketHandler.sesionAdmin;

/**
 * Created by vacax on 04/10/15.
 */
public class Consumidor {

    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Queue queue;
    Topic topic;
    MessageConsumer consumer;
    String cola;


    /**
     *
     */
    public Consumidor(String cola) {
        this.cola = cola;
    }

    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {

        //Creando el connection factory indicando el host y puerto, en la trama el failover indica que reconecta de manera
        // automatica
        factory = new ActiveMQConnectionFactory("admin", "admin", "failover:tcp://localhost:61616");


        //Crea un nuevo hilo cuando hacemos a conexión, que no se detiene cuando
        // aplicamos el metodo stop(), para eso tenemos que cerrar la JVM o
        // realizar un close().
        connection = factory.createConnection();


        // Arrancamos la conexión
        //Puede verlo en direccion por defecto de tu activemq local:
        //http://127.0.0.1:8161/admin/connections.jsp
        connection.start();

        // Creando una sesión no transaccional y automatica.
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.

        //queue = session.createQueue(cola);
        topic = session.createTopic(cola);


        consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage messageTexto = (TextMessage) message;
                    System.out.println("El mensaje de texto recibido: " + messageTexto.getText());
                    String s = messageTexto.getText();

                    Gson gson = new Gson();
                    lecturaSensor ls = gson.fromJson( s , lecturaSensor.class );

                    List<lecturaSensor> lecturaList = new ArrayList<>();

                    lecturaList = sensorServices.getInstancia().findAll();
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date fecha1 = new Date();
                    if(lecturaList.size() > 0) {
                        fecha1 = dateFormat.parse(lecturaList.get(0).getFechaGeneracion());
                    }

                   sensorServices.getInstancia().crear(ls);

                    JSONObject entrada = new JSONObject(s);


                    Date fecha2 = dateFormat.parse(entrada.getString("fechaGeneracion"));

                    long diff = (fecha1.getTime() - fecha2.getTime());

                    String salida = new JSONObject()

                            .put("IdDispositivo", entrada.getBigInteger("IdDispositivo"))
                            .put("fechaGeneracion", diff*-1)
                            .put("humedad", entrada.getFloat("humedad"))
                            .put("temperatura", entrada.getFloat("temperatura"))

                            .toString();
                    sesionAdmin.getRemote().sendString(salida);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }


    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }
}