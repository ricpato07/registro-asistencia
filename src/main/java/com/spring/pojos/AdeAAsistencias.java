package com.spring.pojos;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rgarciau
 */
@Entity
@Table(name = "ADEA_ASISTENCIAS", schema = "PROD")
public class AdeAAsistencias {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADEA_ASISTENCIAS")
    @SequenceGenerator(name = "SEQ_ADEA_ASISTENCIAS", sequenceName = "PROD.SEQ_ADEA_ASISTENCIAS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "MOVIMIENTO")
    private String movimiento;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA")
    private Date fecha;
    @Column(name = "IP")
    private String ip;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the movimiento
     */
    public String getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
