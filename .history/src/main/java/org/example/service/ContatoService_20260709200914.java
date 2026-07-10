package org.example.service;

import java.sql.SQLException;
import java.util.List;

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

    public Contato buscarPorId(long id) throws SQLException{

        if(id <= 0){
            throw new IllegalArgumentException("O id não pode ser igual ou menor a 0");
        } 

        return contatoRepository.findById(id);
    }

    public List<Contato> buscarTodosContatos() throws SQLException {

        return contatoRepository.findAllContatos();
    }

    public coonta 
}
