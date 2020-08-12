/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.locadora.dao;

import local.locadora.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


/**
 * @author angelogl
 */
@Repository
public interface CartaoDAO extends JpaRepository<Cartao, Long> {
    
    boolean existsById(Cartao cartao);
    
    @Query("SELECT p FROM Cartao p WHERE cpfCliente = :cpf and numeroCartao = :numero")
    public Cartao encontrarCartaoCpf(@Param("cpf") String cpf , @Param("numero") String numero);
    
}
