package org.example;

import java.sql.SQLException;

import org.example.model.Contato;
import org.example.repository.ContatoRepository;
import org.example.service.ContatoService;

public class Main {
    public static void main(String[] args) throws SQLException {

        var contato = new Contato(
            "jorge",
            "47 - 9666-4562"
        );

        ContatoService contatoService = new ContatoService();

        contatoService.criarContato(contato);
    
    }
}