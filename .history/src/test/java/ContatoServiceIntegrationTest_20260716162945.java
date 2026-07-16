import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Contato;
import org.example.service.ContatoService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.Result;


public class ContatoServiceIntegrationTest {

    private ContatoService contatoService;

    private static final String SQL_CREATE_TABLE = 
            " CREATE TABLE contato (" +
                "   id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "   nome VARCHAR(100) NOT NULL," +
                "   numero VARCHAR(100) UNIQUE NOT NULL, " +
                " );";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS contato";

    private static final String SQL_TRUNCATE_TABLE = "TRUNCATE TABLE contato";

    @BeforeAll
    static void setupDatabase() throws SQLException {
        
        try(Connection conn = ConnectionFactory.getConnection();   
            Statement stmt = conn.createStatement()){


            stmt.execute(SQL_DROP_TABLE);
            stmt.execute(SQL_CREATE_TABLE);

            System.out.println("Tabela 'contato' criada no banco de teste.");


        }catch(Exception e){
            System.err.println("Erro ao conectar no banco!");
            e.printStackTrace();
            throw e;
        }
    }

    @AfterAll
    static void tearDownDataBase() throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()){

                stmt.execute(SQL_DROP_TABLE);
                System.out.println("Tabela 'contato' destruida com sucesso!");

        }catch(Exception e){
            System.err.println("Erro ao destuir tabela 'contato'!");
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setupTest() throws SQLException {
        
        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement()){

                stmt.execute(SQL_TRUNCATE_TABLE);

        }catch(Exception e){
            System.err.println("Erro ao limpar tabela");
            e.printStackTrace();
        }


        contatoService = new ContatoService();
    }

    @Test
    @DisplayName("Deve criar um contato e salvá-lo no banco de dados")
    void testCriarContatoComSucesso() throws SQLException {

        var contato = new Contato("Eric", "4798804551");

        var contatoSalvo = contatoService.criarContato(contato);

        assertNotNull(contatoSalvo);
        assertTrue(contatoSalvo.getId() > 0, "O id nao foi gerado pelo banco");
        assertEquals("Eric", contatoSalvo.getNome());

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contato WHERE id = " + contatoSalvo.getId())){
                
                assertTrue(rs.next(), "Produto nao foi salvo no banco");
                assertEquals("Eric", rs.getString("nome"));
                assertEquals("")
            }
        
        
    }
    
}
