package org.example;

import java.sql.SQLException;

import org.example.model.Contato;
import org.example.repository.ContatoRepository;
import org.example.service.ContatoService;

public class Main {

    private static ContatoService contatoService = new ContatoService();
    public static void main(String[] args) {

        criarPrimeiroContato();

    }

    public static void criarPrimeiroContato(){
            var contato = new Contato(
                "jorge",
                "47 - 9666-4562"
            );

            try{
                contatoService.criarContato(contato);
            }catch(SQLException e){
                e.printStackTrace();
            } 
        }
        

    public static void testarMetodoNomeRepetido(){
        var contato = new Contato(
            "jorge",
            "47 - 98804-4551"
        );

        try{
            contatoService.criarContato(contato);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void procurarPorId(){
        long id = 1;

        contatoService
    }
}