import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Contato;
import org.example.service.ContatoService;

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

    @afterAll
    static void tearDownDataBase() throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = )
    }

    @Test
    @DisplayName("Deve criar um contato e salvá-lo no banco de dados")
    void testCriarProdutoComSucesso() throws SQLException {

        var contato = new Contato("Eric", "4798804551");

        
    }
    
}
