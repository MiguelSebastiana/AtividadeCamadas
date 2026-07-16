import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Contato;
import org.example.service.ContatoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;



@DisplayName("Teste de Integração - Service Contato")
class ContatoServiceIntegrationTest {

    private ContatoService contatoService;

    private static final String SQL_CREATE_TABLE = 
            " CREATE TABLE contatos (" +
                "   id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "   nome VARCHAR(100) NOT NULL," +
                "   numero VARCHAR(100) UNIQUE NOT NULL " +
                " );";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS contatos";

    private static final String SQL_TRUNCATE_TABLE = "TRUNCATE TABLE contatos";

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

        var contato = new Contato("Flavio", "31231231");

        var contatoSalvo = contatoService.criarContato(contato);

        assertNotNull(contatoSalvo);
        assertTrue(contatoSalvo.getId() > 0, "O id nao foi gerado pelo banco");
        assertEquals("Flavio", contatoSalvo.getNome());

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contatos WHERE id = " + contatoSalvo.getId())){
                
                assertTrue(rs.next(), "Produto nao foi salvo no banco");
                assertEquals("Flavio", rs.getString("nome"));
                assertEquals("31231231", rs.getString("numero"));
        }
    }

    @Test
    @DisplayName("Deve listar todos os contatos")
    void testListarTodosContatos() throws SQLException{

        List<Contato> contatos = contatoService.buscarTodosContatos();

        assertNotNull(contatos);
    }

    @Test
    @DisplayName("Deve listar o contato do id desejado")
    void testFindById() throws SQLException{

        Contato contatoResponse = contatoService.criarContato(new Contato("Mathias", "1234567654"));

        assertTrue(contatoResponse.getId() > 0, "O contato nao foi criado");

        Contato contato = contatoService.buscarPorId(contatoResponse.getId());

        assertTrue(contato.getId() > 0, "O contato nao foi achado");
        assertEquals(contatoResponse.getId(), contato.getId());
    }

    @Test
    @DisplayName("Deve verificar se o nome existe")
    void testExisteByName() throws SQLException{

        Contato contato = contatoService.criarContato(new Contato("Andrey", "23456654231"));
        String nomeTeste = contato.getNome();

        assertTrue(contato.getId() > 0, "O contato nao foi criado");
        
        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contatos WHERE nome = " + nomeTeste)){

            assertTrue(rs.next(), "Contato não encontrado com esse nome.");
            assertEquals("Andrey", rs.getString("nome"));
        }
    }

    @Test
    @DisplayName("Deve verificar se o numero existe")
    void testExisteByNumero() throws SQLException{

        Contato contato = contatoService.criarContato(new Contato("Murilo", "12345678910"));
        String numeroTeste = contato.getNumero();

        assertTrue(contato.getId() > 0, "O contato nao foi criado");

        try(Connection conn = ConnectionFactory.getConnection());
    }

    @Test 
    @DisplayName("Deve atualizar o contato")
    void testAtualizarContato() throws SQLException{


        Contato contatoOriginal = contatoService.criarContato(new Contato("Neymar", "1242142141"));
        long idOriginal = contatoOriginal.getId();

        String nomeAtualizado = "Pedrinho";
        String numeroAtualizado = "23125412512421";

        contatoService.atualizarContatoo(nomeAtualizado, numeroAtualizado, idOriginal);

        try(Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contatos WHERE id = " + idOriginal)){

                assertTrue(rs.next());
                assertEquals("Pedrinho", rs.getString("nome"));
                assertEquals("23125412512421", rs.getString("numero"));
            }
    }

    @Test
    @DisplayName("Deve deletar um contato e remove-lo do banco")
    void testDeletarContato() throws SQLException{

        Contato contato = contatoService.criarContato(new Contato("Eduardo", "8754378901"));
        long idParaExcluir = contato.getId();

        boolean resultado = contatoService.deletarContatoo(idParaExcluir);

        assertTrue(resultado, "Metodo de deletar deveria retornar true");

        Contato contatoAposExclusao = contatoService.buscarPorId(idParaExcluir);
        assertNull(contatoAposExclusao, "Contato não foi removido do banco");
    }
}
