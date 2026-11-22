import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Editora {

    private int id;
    private String nome;
    private String editorChefe;
    private int anoFundacao;

    public Editora() {
    }

    public Editora(int id, String nome, String editorChefe, int anoFundacao) {
        this.id = id;
        this.nome = nome;
        this.editorChefe = editorChefe;
        this.anoFundacao = anoFundacao;
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

    public String getEditorChefe() {
        return editorChefe;
    }

    public void setEditorChefe(String editorChefe) {
        this.editorChefe = editorChefe;
    }

    public int getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(int anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void salvar() {
        String sql = "INSERT INTO editora (nome, editor_chefe, ano_fundacao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, editorChefe);
            stmt.setInt(3, anoFundacao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar editora: " + e.getMessage(), e);
        }
    }

    public void alterar() {
        String sql = "UPDATE editora SET nome = ?, editor_chefe = ?, ano_fundacao = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, editorChefe);
            stmt.setInt(3, anoFundacao);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar editora: " + e.getMessage(), e);
        }
    }

    public void excluir() {
        String sql = "DELETE FROM editora WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir editora: " + e.getMessage(), e);
        }
    }

    public static List<Editora> pesquisar(String filtro) {
        List<Editora> lista = new ArrayList<>();
        String sql = "SELECT id, nome, editor_chefe, ano_fundacao FROM editora "
                + "WHERE nome LIKE ? ORDER BY nome";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (filtro == null ? "" : filtro.trim()) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Editora(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("editor_chefe"),
                            rs.getInt("ano_fundacao")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar editoras: " + e.getMessage(), e);
        }
        return lista;
    }
}
