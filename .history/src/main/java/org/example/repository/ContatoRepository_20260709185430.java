package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS)){

            }
        return null;
    }
}
