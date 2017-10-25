package services;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import static j2html.TagCreator.*;
import j2html.TagCreator;
import j2html.tags.ContainerTag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebSocket
public class ServidorMensajesWebSocketHandler {
    public static List<Session> usuariosConectados = new ArrayList<>();
    public static HashMap <Session, String> usuarios = new HashMap<>();
    public static HashMap <String, Session> sesiones = new HashMap<>();
    public static Session sesionAdmin;

    /**
     * Una vez conectado el cliente se activa este metodo.
     * @param usuario
     */
    @OnWebSocketConnect
    public void conectando(Session usuario){
        System.out.println("Conectando Usuario: "+usuario.getLocalAddress().getAddress().toString());
        usuariosConectados.add(usuario);
        sesionAdmin = usuario;
    }

    /**
     * Una vez cerrado la conexión, es ejecutado el metodo anotado
     * @param usuario
     * @param statusCode
     * @param reason
     */
    @OnWebSocketClose
    public void cerrandoConexion(Session usuario, int statusCode, String reason) {
        System.out.println("Desconectando el usuario: "+usuario.getLocalAddress().getAddress().toString());
        usuariosConectados.remove(usuario);
    }

    /**
     * Una vez recibido un mensaje es llamado el metodo anotado.
     * @param usuario
     * @param message
     */
    @OnWebSocketMessage
    public void recibiendoMensaje(Session usuario, String message) {



    }



    public static void enviarMensajeAClientesConectados(String mensaje, String color){
        for(Session sesionConectada : usuariosConectados){
            try {
                sesionConectada.getRemote().sendString(p(mensaje).withClass(color).render());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}