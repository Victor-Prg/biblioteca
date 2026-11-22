import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Autor {

    private int id;
    private String nome;
    private String nomeMae;
    private String cidadeNascimento;

    public Autor() {
    }

    public Autor(int id, String nome, String nomeMae, String cidadeNascimento) {
        this.id = id;
        this.nome = nome;
        this.nomeMae = nomeMae;
        this.cidadeNascimento = cidadeNascimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void salvar() {
        String sql = "INSERT INTO autor (nome, nome_mae, cidade_nascimento) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, nomeMae);
            stmt.setString(3, cidadeNascimento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar autor: " + e.getMessage(), e);
        }
    }

    public void alterar() {
        String sql = "UPDATE autor SET nome = ?, nome_mae = ?, cidade_nascimento = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, nomeMae);
            stmt.setString(3, cidadeNascimento);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar autor: " + e.getMessage(), e);
        }
    }

    public void excluir() {
        String sql = "DELETE FROM autor WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir autor: " + e.getMessage(), e);
        }
    }

    public static List<Autor> pesquisar(String filtro) {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT id, nome, nome_mae, cidade_nascimento FROM autor "
                + "WHERE nome LIKE ? ORDER BY nome";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (filtro == null ? "" : filtro.trim()) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Autor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("nome_mae"),
                            rs.getString("cidade_nascimento")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar autores: " + e.getMessage(), e);
        }
        return lista;
    }
}
