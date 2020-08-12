/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.controller.rest;

/**
 * @author angelogl
 */


import local.locadora.dao.CartaoDAO;
import local.locadora.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import local.locadora.entities.Cartao;

@RestController
@RequestMapping("/api")
public class CartaoControllerAPI {


    @Autowired
    CartaoDAO cartaoDAO;

    // -------------------Retrieve All Usuarios---------------------------------------------

    @RequestMapping(value = "/cartao", method = RequestMethod.GET)
    public ResponseEntity<List<Cartao>> listAllUsuarios() {
        List<Cartao> clientes = cartaoDAO.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // -------------------Retrieve Single Usuario------------------------------------------

    @RequestMapping(value = "/cartao/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario(@PathVariable("id") long id) {

        Cartao cliente = cartaoDAO.findById(id).get();
        if (cliente == null) {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/cartaoPorCpf/{cpf,numero}", method = RequestMethod.GET)
    public ResponseEntity<?> getUsuario(@PathVariable("cpf") String cpf,@PathVariable("numero") String numero) {

        Cartao cliente = cartaoDAO.encontrarCartaoCpf(cpf, numero);
        
        if (cliente == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    // -------------------Create a Usuario-------------------------------------------

    @RequestMapping(value = "/cartao", method = RequestMethod.POST)
    public ResponseEntity<?> createUsuario(@RequestBody Cartao cliente, UriComponentsBuilder ucBuilder) {
        if (cartaoDAO.existsById(cliente)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        cartaoDAO.save(cliente);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/cliente/{id}").buildAndExpand(cliente.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a Usuario ------------------------------------------------

    @RequestMapping(value = "/cartao/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUsuario(@PathVariable("id") long id, @RequestBody Cartao cliente) {

        Cartao currentCliente = cartaoDAO.findById(id).get();

        if (currentCliente == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        currentCliente.setCpfCliente(cliente.getCpfCliente());

        cartaoDAO.save(currentCliente);
        return new ResponseEntity<>(currentCliente, HttpStatus.OK);
    }

    // ------------------- Delete a Cliente-----------------------------------------

    @RequestMapping(value = "/cartao/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCliente(@PathVariable("id") long id) {
        Cartao cliente = cartaoDAO.findById(id).get();
        if (cliente == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        cartaoDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Clientes-----------------------------

    @RequestMapping(value = "/cartao", method = RequestMethod.DELETE)
    public ResponseEntity<Cliente> deleteAllClientes() {
        cartaoDAO.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
