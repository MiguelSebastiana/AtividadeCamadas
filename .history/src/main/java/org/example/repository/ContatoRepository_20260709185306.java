package org.example.repository;

import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Contato;

public class ContatoRepository {
    
    public Contato salvar(Contato contato) throws SQLException {
        String command = """
                    INSERT INTO contatos
                    (nome, numero)
                    VALUES
                    (?,?)
                """;

        try(Connection conn = ConnectionFactory.getConnection())
        return null;
    }
}
