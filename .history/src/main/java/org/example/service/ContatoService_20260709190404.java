package org.example.service;

import org.example.repository.ContatoRepository;

public class ContatoService {
    
    private static ContatoRepository contatoRepository = new ContatoRepository();

    public Contato criarContato(Contato contato){
        if(contatoRepository.existePorNome())
    }
}
