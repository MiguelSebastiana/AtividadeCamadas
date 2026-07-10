package org.example.service;

import java.sql.SQLException;

import org.example.model.Contato;
import org.example.repository.ContatoRepository;

public class ContatoService {
    
    private static ContatoRepository contatoRepository = new ContatoRepository();

    public Contato criarContato(Contato contato) throws SQLException{

        if(contatoRepository.existePorNome(contato.getNome())){
            throw new RuntimeException("O contato já foi cadastrado");
        }

        contatoRepository.salvar(contato);

        return contato;

    }

    public 
}
