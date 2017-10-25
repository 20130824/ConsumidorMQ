package entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class lecturaSensor {

    private String fechaGeneracion;
    private int IdDispositivo;
    private Number temperatura;
    private Number humedad;
    @Id
    @GeneratedValue
    private long id;

    public lecturaSensor(String fechaGeneracion, int idDispositivo, Number temperatura, Number humedad) {
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

    public Number getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Number temperatura) {
        this.temperatura = temperatura;
    }

    public Number getHumedad() {
        return humedad;
    }

    public void setHumedad(Number humedad) {
        this.humedad = humedad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
