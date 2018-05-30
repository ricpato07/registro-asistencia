package com.spring.pojos;

import java.io.Serializable;

/**
 *
 * @author rgarciau
 */

public class Usuarios implements Serializable {

    private static final long serialVersionUID = 25022026L;

    private Long idusuario;
    private String usuario;
    private String password;
    private String movimiento;
    private String ip;
    

    /**
     * @return the idusuario
     */
    public Long getIdusuario() {
        return idusuario;
    }

    /**
     * @param idusuario the idusuario to set
     */
    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
