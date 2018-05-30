package com.spring.service.impl;

import com.adeadms.core.security.pojos.UsuarioWebmx;
import com.spring.pojos.AdeAAsistencias;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import com.spring.service.LoginService;
import java.util.Date;
import mx.com.adea.security.services.AuthenticationService;
import mx.com.adea.security.services.UserService;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public String validarAcceso(String user, String pass, String authority)throws Exception {
        
        return authenticationService.validarAcceso(user, pass, authority);
        
//        try {
//            UsuarioWebmx usuario = getUser(user);
//            if (usuario != null) {
//                if (usuario.getStatus() == 'A') {
//                    String sha1Password = SHA1.encriptarBase64(pass);
//                    if (usuario.getPassword().equals(sha1Password)) {
//                        usuario.setIntentos(new Integer(0).shortValue());
//
//                        if (usuario.getFechaVigencia() == null || usuario.getFechaVigencia().before(new Date())) {
//                            if (usuario.getNoAcceso() == null) {
//                                usuario.setNoAcceso(1l);
//                            } else {
//                                usuario.setNoAcceso(usuario.getNoAcceso() + 1);
//                            }
//                            
//                        } else {
//                            throw new Exception("El usuario a expirado, favor de verificar vigencia.");
//                        }
//                    } else {
//                        usuario.setIntentos(new Integer(0 + 1).shortValue());
//                        if (usuario.getIntentos() >= 3) {
//                            usuario.setStatus('R');
//                            usuario.setFecharevocado(new Date());
//                            throw new Exception("Usuario revocado");
//                        } else {
//                            throw new Exception("Contraseña incorrecta");
//                        }
//                    }
//                } else if (usuario.getStatus() == 'R') {
//                    throw new Exception("Usuario revocado");
//                } else {
//                    throw new Exception("Usuario incorrecto");
//                }
//            } else {
//                //return null;
//                throw new Exception("Usuario incorrecto o privilegios insuficientes");
//            }
//
//            return usuario;
//
//        } catch (Exception ex) {
//            throw new RuntimeException(ex.getMessage(), ex);
//        }
    }

    public UsuarioWebmx getUser(String user)throws Exception {
//          return userService.getUser(user);
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" select * from mexweb.USUARIO_WEBMX");
            sql.append(" where LOGIN = ? ");
            SQLQuery SQLquery = getSession().createSQLQuery(sql.toString());
            SQLquery.addEntity(UsuarioWebmx.class);
            SQLquery.setParameter(0, user);
            List<UsuarioWebmx> result = SQLquery.list();

            if (result != null && result.size() > 0) {
                return result.get(0);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public List<UsuarioWebmx> getUsers() {
        try {
            String query = "SELECT * FROM MEXWEB.USUARIO_WEBMX WHERE LOGIN LIKE 'rgarciau'";
            SQLQuery SQLquery = getSession().createSQLQuery(query);
            SQLquery.addEntity(UsuarioWebmx.class);
            List<UsuarioWebmx> result = SQLquery.list();

            if (result != null && result.size() > 0) {
                return result;
            }

        } catch (Exception e) {
            System.out.println("Error en el DAO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public void saveAdeAAsistencias(AdeAAsistencias asistencia) {
        asistencia.setFecha(getFechaActual());
        getSession().persist(asistencia);
    }
    
    @Override
    @Transactional
    public AdeAAsistencias getMovimientoHoy(String usuario, String movimiento) {
        try {
            String query = "SELECT * FROM PROD.ADEA_ASISTENCIAS WHERE USUARIO = ? AND MOVIMIENTO = ? AND TRUNC(FECHA )= TRUNC(SYSDATE)";
            SQLQuery SQLquery = getSession().createSQLQuery(query);
            SQLquery.addEntity(AdeAAsistencias.class);
            SQLquery.setParameter(0, usuario);
            SQLquery.setParameter(1, movimiento);
            List<AdeAAsistencias> result = SQLquery.list();

            if (result != null && result.size() > 0) {
                return result.get(0);
            }

        } catch (Exception e) {
            System.out.println("Error en el DAO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Date getFechaActual() {
        String query = "SELECT SYSDATE NOW FROM DUAL";
        SQLQuery SQLquery = getSession().createSQLQuery(query);
        List<?> result = SQLquery.list();

        if (result != null && result.size() > 0) {
            return (Date) result.get(0);
        }
        return null;
    }
    
    @Override
    @Transactional
    public boolean isIpAutorizada(String usuario, String ip) {
        int ires = userService.isValidaIpRemota(usuario,ip);
        return ires != 0;
    }

}
