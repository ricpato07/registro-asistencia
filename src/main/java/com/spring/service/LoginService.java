package com.spring.service;

import com.adeadms.core.security.pojos.UsuarioWebmx;
import com.spring.pojos.AdeAAsistencias;
import java.util.Date;
import java.util.List;

public interface LoginService {

    public String validarAcceso(String user, String pass, String authority) throws Exception;

    public List<UsuarioWebmx> getUsers();

    public UsuarioWebmx getUser(String user) throws Exception;

    public void saveAdeAAsistencias(AdeAAsistencias asistencia);

    public Date getFechaActual();

    public AdeAAsistencias getMovimientoHoy(String usuario, String movimiento);

    public boolean isIpAutorizada(String usuario, String ip);

}
