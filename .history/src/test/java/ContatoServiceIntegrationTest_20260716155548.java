import java.beans.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import org.example.db.ConnectionFactory;
import org.example.model.Contato;
import org.example.service.ContatoService;

public class ContatoServiceIntegrationTest {

    private ContatoService contatoService;


    @BeforeAll
    static void setupDatabase() SQLException {
        
        try(Connection conn = ConnectionFactory.getConnection();   
            Statement stmt = conn.preparementSta)
    }

    @Test
    @DisplayName("Deve criar um contato e salvá-lo no banco de dados")
    void testCriarProdutoComSucesso() throws SQLException {

        var contato = new Contato("Eric", "4798804551");

        
    }
    
}
