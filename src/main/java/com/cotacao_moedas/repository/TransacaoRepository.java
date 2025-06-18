package com.cotacao_moedas.repository;

import com.cotacao_moedas.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um componente de persistência (repositório)
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária>
    // Esta interface já fornece métodos CRUD básicos (save, findById, findAll, delete, etc.)
    // Sem precisar escrever NENHUMA implementação!
}