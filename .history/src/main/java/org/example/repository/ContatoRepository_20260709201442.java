package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

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

                stmt.setString(1, contato.getNome());
                stmt.setString(2, contato.getNumero());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();

                if(rs.next()){
                    contato.setId(rs.getLong(1));
                    return contato;
                }
            }
        throw new RuntimeException("Erro ao salvar no banco de dados!");
    }

    public boolean existePorNome(String nome) throws SQLException {

        String query = """
                    SELECT nome 
                    FROM contatos
                    WHERE nome = ?
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        }
            
    }

    public Contato findById(long id) throws SQLException {
        String query = """
                    SELECT 
                        nome, 
                        numero
                    FROM contatos
                    WHERE id = ?
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

                stmt.setLong(1, id);

                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    return new Contato(
                        rs.getInt(),
                        rs.getString(),
                        rs.getString(3)
                    );
                }
            }

        return null;

    }

    public List<Contato> findAllContatos() throws SQLException {

        List<Contato> contatos = new ArrayList<>();

        String query = """
                    SELECT FROM contatos
                        id, nome, numero
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Contato contato = new Contato(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                    );
                    contatos.add(contato);
                }
            }

        return contatos;
    }

    public boolean upadteContato(Contato contato) throws SQLException {
        String command = """
                    UPDATE contatos SET nome = ?, SET numero = ?
                    WHERE id = ?
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(command)){

                stmt.setString(1, contato.getNome());
                stmt.setString(2, contato.getNumero());
                stmt.setLong(3, contato.getId());
                return stmt.executeUpdate() > 0;
            }
    }

    public boolean deleteContato(long id) throws SQLException {
        String command = """
                    DELETE FROM contatos
                    WHERE id = ?    
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(command)){

                stmt.setLong(1, id);

                int linhasAfetadas = stmt.executeUpdate();

                if(linhasAfetadas > 0){
                    return true;
                }
            }
        return false;
    }

}
