import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Capitulo {

    private int id;
    private int numero;
    private String titulo;
    private int paginaInicial;
    private int paginaFinal;
    private String resumo;
    private Livro livro;

    public Capitulo() {
    }

    public Capitulo(int id, int numero, String titulo, int paginaInicial,
                    int paginaFinal, String resumo, Livro livro) {
        this.id = id;
        this.numero = numero;
        this.titulo = titulo;
        this.paginaInicial = paginaInicial;
        this.paginaFinal = paginaFinal;
        this.resumo = resumo;
        this.livro = livro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPaginaInicial() {
        return paginaInicial;
    }

    public void setPaginaInicial(int paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    public int getPaginaFinal() {
        return paginaFinal;
    }

    public void setPaginaFinal(int paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public void salvar() {
        String sql = "INSERT INTO capitulo (numero, titulo, pagina_inicial, pagina_final, resumo, id_livro) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.setString(2, titulo);
            stmt.setInt(3, paginaInicial);
            stmt.setInt(4, paginaFinal);
            stmt.setString(5, resumo);
            stmt.setInt(6, livro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar capítulo: " + e.getMessage(), e);
        }
    }

    public void alterar() {
        String sql = "UPDATE capitulo SET numero = ?, titulo = ?, pagina_inicial = ?, "
                + "pagina_final = ?, resumo = ?, id_livro = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.setString(2, titulo);
            stmt.setInt(3, paginaInicial);
            stmt.setInt(4, paginaFinal);
            stmt.setString(5, resumo);
            stmt.setInt(6, livro.getId());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar capítulo: " + e.getMessage(), e);
        }
    }

    public void excluir() {
        String sql = "DELETE FROM capitulo WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir capítulo: " + e.getMessage(), e);
        }
    }

    public static List<Capitulo> pesquisar(String filtro) {
        List<Capitulo> lista = new ArrayList<>();
        String sql = "SELECT cap.id, cap.numero, cap.titulo, cap.pagina_inicial, cap.pagina_final, cap.resumo, "
                + "l.id AS livro_id, l.issn, l.nome AS livro_nome, l.resenha, l.ano_publicacao, "
                + "a.id AS autor_id, a.nome AS autor_nome, a.nome_mae, a.cidade_nascimento, "
                + "e.id AS editora_id, e.nome AS editora_nome, e.editor_chefe, e.ano_fundacao, "
                + "c.id AS categoria_id, c.nome AS categoria_nome "
                + "FROM capitulo cap "
                + "INNER JOIN livro l ON cap.id_livro = l.id "
                + "INNER JOIN autor a ON l.id_autor = a.id "
                + "INNER JOIN editora e ON l.id_editora = e.id "
                + "INNER JOIN categoria c ON l.id_categoria = c.id "
                + "WHERE cap.titulo LIKE ? ORDER BY cap.titulo";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + (filtro == null ? "" : filtro.trim()) + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Reconstrói o livro (composição) a partir das colunas do INNER JOIN
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

                    Livro livro = new Livro(
                            rs.getInt("livro_id"),
                            rs.getInt("issn"),
                            rs.getString("livro_nome"),
                            rs.getString("resenha"),
                            rs.getInt("ano_publicacao"),
                            autor,
                            editora,
                            categoria);

                    lista.add(new Capitulo(
                            rs.getInt("id"),
                            rs.getInt("numero"),
                            rs.getString("titulo"),
                            rs.getInt("pagina_inicial"),
                            rs.getInt("pagina_final"),
                            rs.getString("resumo"),
                            livro));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao pesquisar capítulos: " + e.getMessage(), e);
        }
        return lista;
    }
}
