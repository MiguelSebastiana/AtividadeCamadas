package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Contato;
import org.example.repository.ContatoRepository;
import org.example.service.ContatoService;

public class Main {

    private static ContatoService contatoService = new ContatoService();
    public static void main(String[] args) {

        listarContatos();

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

        Contato contato = null;
        long id = 1;

        try{
            contato = contatoService.buscarPorId(id);
        }catch(SQLException e){
            e.printStackTrace();
        }

        System.out.println("contato: " + contato.getId());
        System.out.println("nome: " + contato.getNome());
    }

    public static void listarContatos(){
    
        List<Contato> contatos = new ArrayList<>();

        try {
            contatos = contatoService.buscarTodosContatos();
        }catch(SQLException e){
            e.printStackTrace();
        }

        for(Contato c : contatos){
            System.out.println("contato: " + c.getId());
            System.out.println("nome: " + c.getNome());
        }
    }

    public static void atualizarContato(){

        String numero = "99 - 9999 - 9999";
        String nome = "Osnivaldo";
    }
}