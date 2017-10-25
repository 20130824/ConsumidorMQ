package services;


import entidades.lecturaSensor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by darle on 6/14/2017.
 */
public class sensorServices extends GestionDB<lecturaSensor> {

    private static sensorServices instancia;

    private sensorServices(){
        super(lecturaSensor.class);
    }

    public static sensorServices getInstancia(){
        if(instancia==null){
            instancia = new sensorServices();
        }
        return instancia;
    }

    /**
     *
     * @param username
     * @return
     */



}