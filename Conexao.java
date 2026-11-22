import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe responsável por abrir a conexão com o banco de dados MySQL.
 */
public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver do MySQL não encontrado no projeto:\n" + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Driver do MySQL não encontrado.", e);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível conectar ao banco de dados biblioteca:\n" + e.getMessage(),
                    "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Falha na conexão com o banco de dados.", e);
        }
    }
}
