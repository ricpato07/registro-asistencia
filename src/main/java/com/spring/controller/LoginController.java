package com.spring.controller;

import com.adeadms.core.security.pojos.UsuarioWebmx;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.pojos.AdeAAsistencias;
import com.spring.pojos.Constantes;
import com.spring.pojos.Usuarios;
import com.spring.service.LoginService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    private ResponseEntity<?> list() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<UsuarioWebmx> result = loginService.getUsers();

            if (result != null && result.size() > 0) {
                return new ResponseEntity<String>(mapper.writeValueAsString(result), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error method:" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    private ResponseEntity<?> getUser(@RequestParam(value = "user") String user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {

            UsuarioWebmx usuario = loginService.getUser(user);
            if (usuario != null) {
                return new ResponseEntity<String>(mapper.writeValueAsString(usuario), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error method:" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<?> login(@RequestBody Usuarios usuarios) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> model = new HashMap();
        try {
            String res = loginService.validarAcceso(usuarios.getUsuario(), usuarios.getPassword(), Constantes.AUTHORITY);
            if (res != null && !res.equals("principal")) {
                model.put("men", res);
                return new ResponseEntity(mapper.writeValueAsString(model), HttpStatus.NOT_FOUND);
            }
            if (!loginService.isIpAutorizada(usuarios.getUsuario(), usuarios.getIp())) {
                model.put("men", "La IP no esta autorizada para realizar el registro de asistencia.");
                return new ResponseEntity(mapper.writeValueAsString(model), HttpStatus.NOT_FOUND);
            }
            UsuarioWebmx usuario = loginService.getUser(usuarios.getUsuario());
            if (usuario != null) {
                return new ResponseEntity<String>(mapper.writeValueAsString(usuario), HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error method:" + e.getMessage());
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<?> guardar(@RequestBody Usuarios usuarios) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> model = new HashMap();
        try {
            AdeAAsistencias asistenciaprevia = loginService.getMovimientoHoy(usuarios.getUsuario(), usuarios.getMovimiento());
            if (asistenciaprevia != null) {
                model.put("men", "Ya se registró un movimiento de " + (usuarios.getMovimiento().equals("E") ? "ENTRADA" : "SALIDA") + " el día de hoy");
                return new ResponseEntity(mapper.writeValueAsString(model), HttpStatus.NOT_FOUND);
            }
           
            AdeAAsistencias asistencia = new AdeAAsistencias();
            asistencia.setUsuario(usuarios.getUsuario());
            asistencia.setIp(usuarios.getIp());
            asistencia.setMovimiento(usuarios.getMovimiento());
            loginService.saveAdeAAsistencias(asistencia);
            return new ResponseEntity<String>(mapper.writeValueAsString(asistencia), HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error method:" + e.getMessage());
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    private ResponseEntity<?> heartbeat() throws Exception {
        Map<String, Object> model = new HashMap();
        try {
            model.put("men", "Respuesta del WebService");
            return new ResponseEntity(model, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error method:" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

//
//    @RequestMapping(value = "/trasvase", method = RequestMethod.PUT, headers = "Accept=application/xml,application/json")
//    @ResponseBody
//    private String updateTrasvase(@RequestBody SbTrasvase sbTrasvase) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println("PUT method");
//        try {
//            loginService.updateTrasvase(sbTrasvase);
//            return mapper.writeValueAsString(new ResponseEntity<Object>(sbTrasvase, HttpStatus.OK));
//        } catch (Exception e) {
//            System.out.println("Error method:" + e.getMessage());
//            return mapper.writeValueAsString(new ResponseEntity<Object>(HttpStatus.SERVICE_UNAVAILABLE));
//        }
//    }
}
