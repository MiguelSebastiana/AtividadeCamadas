package org.example;

import org.example.model.Contato;
import org.example.repository.ContatoRepository;

public class Main {
    public static void main(String[] args) {

        var contato = new Contato(
            "jorge",
            "47 - 9666-4562"
        );

        ContatoRepository contatoRepository = new ContatoRepository();

        
    
    }
}