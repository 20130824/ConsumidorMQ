package entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class lecturaSensor {

    private String fechaGeneracion;
    private int IdDispositivo;

    private Float temperatura;

    private Float humedad;
    @Id
    @GeneratedValue
    private long id;

    public lecturaSensor() {
    }

    public lecturaSensor(String fechaGeneracion, int idDispositivo, Float temperatura, Float humedad) {
        this.fechaGeneracion = fechaGeneracion;
        IdDispositivo = idDispositivo;
        this.temperatura = temperatura;
        this.humedad = humedad;
    }


    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public int getIdDispositivo() {
        return IdDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getHumedad() {
        return humedad;
    }

    public void setHumedad(Float humedad) {
        this.humedad = humedad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
