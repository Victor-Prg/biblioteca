import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Livro {

    private int id;
    private int issn;
    private String nome;
    private String resenha;
    private int anoPublicacao;
    private Autor autor;
    private Editora editora;
    private Categoria categoria;

    public Livro() {
    }

    public Livro(int id, int issn, String nome, String resenha, int anoPublicacao,
                 Autor autor, Editora editora, Categoria categoria) {
        this.id = id;
        this.issn = issn;
        this.nome = nome;
        this.resenha = resenha;
        this.anoPublicacao = anoPublicacao;
        this.autor = autor;
        this.editora = editora;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssn() {
        return issn;
    }

    public void setIssn(int issn) {
        this.issn = issn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResenha() {
        return resenha;
    }

    public void setResenha(String resenha) {
        this.resenha = resenha;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void salvar() {
        String sql = "INSERT INTO livro (issn, nome, resenha, ano_publicacao, id_autor, id_editora, id_categoria) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, issn);
            stmt.setString(2, nome);
            stmt.setString(3, resenha);
            stmt.setInt(4, anoPublicacao);
            stmt.setInt(5, autor.getId());
            stmt.setInt(6, editora.getId());
            stmt.setInt(7, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar livro: " + e.getMessage(), e);
        }
    }

    public void alterar() {
        String sql = "UPDATE livro SET issn = ?, nome = ?, resenha = ?, ano_publicacao = ?, "
                + "id_autor = ?, id_editora = ?, id_categoria = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, issn);
            stmt.setString(2, nome);
            stmt.setString(3, resenha);
            stmt.setInt(4, anoPublicacao);
            stmt.setInt(5, autor.getId());
            stmt.setInt(6, editora.getId());
            stmt.setInt(7, categoria.getId());
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar livro: " + e.getMessage(), e);
        }
    }

    public void excluir() {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir livro: " + e.getMessage(), e);
        }
    }

    public static List<Livro> pesquisar(String filtro) {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT l.id, l.issn, l.nome, l.resenha, l.ano_publicacao, "
                + "a.id AS autor_id, a.nome AS autor_nome, a.nome_mae, a.cidade_nascimento, "
                + "e.id AS editora_id, e.nome AS editora_nome, e.editor_chefe, e.ano_fundacao, "
                + "c.id AS categoria_id, c.nome AS categoria_nome "
                + "FROM livro l "
                + "INNER JOIN autor a ON l.id_autor = a.id "
                + "INNER JOIN editora e ON l.id_editora = e.id "
                + "INNER JOIN categoria c ON l.id_categoria = c.id "
                + "WHERE l.nome LIKE ? ORDER BY l.nome";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (filtro == null ? "" : filtro.trim()) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Reconstrói os objetos agregados a partir das colunas do INNER JOIN
                    Autor autor = new Autor(
                            rs.getInt("autor_id"),
                            rs.getString("autor_nome"),
                            rs.getString("nome_mae"),
                            rs.getString("cidade_nascimento"));

                    Editora editora = new Editora(
                            rs.getInt("editora_id"),
                            rs.getString("editora_nome"),
                            rs.getString("editor_chefe"),
                            rs.getInt("ano_fundacao"));

                    Categoria categoria = new Categoria(
                            rs.getInt("categoria_id"),
                            rs.getString("categoria_nome"));

                    lista.add(new Livro(
                            rs.getInt("id"),
                            rs.getInt("issn"),
                            rs.getString("nome"),
                            rs.getString("resenha"),
                            rs.getInt("ano_publicacao"),
                            autor,
                            editora,
                            categoria));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar livros: " + e.getMessage(), e);
        }
        return lista;
    }
}
