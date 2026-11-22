import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TelaLivro extends JFrame {

    private JLabel lblId;
    private JLabel lblIssn;
    private JLabel lblNome;
    private JLabel lblAutor;
    private JLabel lblEditora;
    private JLabel lblCategoria;
    private JLabel lblAnoPublicacao;
    private JLabel lblResenha;
    private JLabel lblFiltro;
    private JTextField txtId;
    private JTextField txtIssn;
    private JTextField txtNome;
    private JTextField txtAnoPublicacao;
    private JTextField txtFiltro;
    private JTextArea txtResenha;
    private JComboBox<Autor> cboAutor;
    private JComboBox<Editora> cboEditora;
    private JComboBox<Categoria> cboCategoria;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private List<Livro> livrosPesquisados;

    public TelaLivro() {
        setTitle("Cadastro de Livros");
        setLayout(null);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblId = new JLabel("Id:");
        lblId.setBounds(20, 20, 110, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 80, 25);
        txtId.setEditable(false);
        add(txtId);

        lblIssn = new JLabel("ISSN:");
        lblIssn.setBounds(250, 20, 60, 25);
        add(lblIssn);

        txtIssn = new JTextField();
        txtIssn.setBounds(310, 20, 120, 25);
        add(txtIssn);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 55, 110, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(130, 55, 400, 25);
        add(txtNome);

        lblAnoPublicacao = new JLabel("Ano de Publicação:");
        lblAnoPublicacao.setBounds(20, 90, 110, 25);
        add(lblAnoPublicacao);

        txtAnoPublicacao = new JTextField();
        txtAnoPublicacao.setBounds(130, 90, 100, 25);
        add(txtAnoPublicacao);

        lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 125, 110, 25);
        add(lblAutor);

        cboAutor = new JComboBox<Autor>();
        cboAutor.setBounds(130, 125, 400, 25);
        add(cboAutor);

        lblEditora = new JLabel("Editora:");
        lblEditora.setBounds(20, 160, 110, 25);
        add(lblEditora);

        cboEditora = new JComboBox<Editora>();
        cboEditora.setBounds(130, 160, 400, 25);
        add(cboEditora);

        lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 195, 110, 25);
        add(lblCategoria);

        cboCategoria = new JComboBox<Categoria>();
        cboCategoria.setBounds(130, 195, 400, 25);
        add(cboCategoria);

        lblResenha = new JLabel("Resenha:");
        lblResenha.setBounds(20, 230, 110, 25);
        add(lblResenha);

        txtResenha = new JTextArea();
        txtResenha.setLineWrap(true);
        txtResenha.setWrapStyleWord(true);

        JScrollPane scrollResenha = new JScrollPane(txtResenha);
        scrollResenha.setBounds(130, 230, 400, 70);
        add(scrollResenha);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(20, 320, 100, 30);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });
        add(btnSalvar);

        btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(130, 320, 100, 30);
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterar();
            }
        });
        add(btnAlterar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 320, 100, 30);
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        add(btnExcluir);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(350, 320, 110, 30);
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabela(txtFiltro.getText());
            }
        });
        add(btnPesquisar);

        lblFiltro = new JLabel("Filtro:");
        lblFiltro.setBounds(20, 365, 60, 25);
        add(lblFiltro);

        txtFiltro = new JTextField();
        txtFiltro.setBounds(85, 365, 300, 25);
        add(txtFiltro);

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("ISSN");
        modelo.addColumn("Nome");
        modelo.addColumn("Autor");
        modelo.addColumn("Editora");
        modelo.addColumn("Categoria");
        modelo.addColumn("Ano");

        tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBounds(20, 405, 850, 230);
        add(scrollTabela);

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                preencherCampos();
            }
        });

        livrosPesquisados = new ArrayList<>();
        carregarCombos();
        carregarTabela("");
    }

    private void carregarCombos() {
        cboAutor.removeAllItems();
        for (Autor autor : Autor.pesquisar("")) {
            cboAutor.addItem(autor);
        }

        cboEditora.removeAllItems();
        for (Editora editora : Editora.pesquisar("")) {
            cboEditora.addItem(editora);
        }

        cboCategoria.removeAllItems();
        for (Categoria categoria : Categoria.pesquisar("")) {
            cboCategoria.addItem(categoria);
        }
    }

    private void salvar() {
        if (txtIssn.getText().trim().isEmpty()
                || txtNome.getText().trim().isEmpty()
                || txtAnoPublicacao.getText().trim().isEmpty()
                || txtResenha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }
        if (cboAutor.getSelectedItem() == null
                || cboEditora.getSelectedItem() == null
                || cboCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Cadastre e selecione o autor, a editora e a categoria do livro.");
            return;
        }

        int issn;
        int anoPublicacao;
        try {
            issn = Integer.parseInt(txtIssn.getText().trim());
            anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "O ISSN e o ano de publicação devem ser números inteiros.");
            return;
        }

        Livro livro = new Livro();
        livro.setIssn(issn);
        livro.setNome(txtNome.getText().trim());
        livro.setResenha(txtResenha.getText().trim());
        livro.setAnoPublicacao(anoPublicacao);
        livro.setAutor((Autor) cboAutor.getSelectedItem());
        livro.setEditora((Editora) cboEditora.getSelectedItem());
        livro.setCategoria((Categoria) cboCategoria.getSelectedItem());

        try {
            livro.salvar();
            JOptionPane.showMessageDialog(this, "Livro salvo com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela para alterar.");
            return;
        }
        if (txtIssn.getText().trim().isEmpty()
                || txtNome.getText().trim().isEmpty()
                || txtAnoPublicacao.getText().trim().isEmpty()
                || txtResenha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de alterar.");
            return;
        }
        if (cboAutor.getSelectedItem() == null
                || cboEditora.getSelectedItem() == null
                || cboCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o autor, a editora e a categoria do livro.");
            return;
        }

        int issn;
        int anoPublicacao;
        try {
            issn = Integer.parseInt(txtIssn.getText().trim());
            anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "O ISSN e o ano de publicação devem ser números inteiros.");
            return;
        }

        Livro livro = new Livro();
        livro.setId(Integer.parseInt(txtId.getText().trim()));
        livro.setIssn(issn);
        livro.setNome(txtNome.getText().trim());
        livro.setResenha(txtResenha.getText().trim());
        livro.setAnoPublicacao(anoPublicacao);
        livro.setAutor((Autor) cboAutor.getSelectedItem());
        livro.setEditora((Editora) cboEditora.getSelectedItem());
        livro.setCategoria((Categoria) cboCategoria.getSelectedItem());

        try {
            livro.alterar();
            JOptionPane.showMessageDialog(this, "Livro alterado com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela para excluir.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este livro?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Livro livro = new Livro();
        livro.setId(Integer.parseInt(txtId.getText().trim()));

        try {
            livro.excluir();
            JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return;
        }

        txtId.setText(texto(tabela.getValueAt(linha, 0)));
        txtIssn.setText(texto(tabela.getValueAt(linha, 1)));
        txtNome.setText(texto(tabela.getValueAt(linha, 2)));
        txtAnoPublicacao.setText(texto(tabela.getValueAt(linha, 6)));

        selecionarAutor((Autor) tabela.getValueAt(linha, 3));
        selecionarEditora((Editora) tabela.getValueAt(linha, 4));
        selecionarCategoria((Categoria) tabela.getValueAt(linha, 5));

        // A resenha não é exibida na tabela, por isso é recuperada do livro pesquisado
        int id = Integer.parseInt(txtId.getText());
        for (Livro livro : livrosPesquisados) {
            if (livro.getId() == id) {
                txtResenha.setText(livro.getResenha());
                break;
            }
        }
    }

    private void selecionarAutor(Autor autor) {
        if (autor == null) {
            return;
        }
        for (int i = 0; i < cboAutor.getItemCount(); i++) {
            if (cboAutor.getItemAt(i).getId() == autor.getId()) {
                cboAutor.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarEditora(Editora editora) {
        if (editora == null) {
            return;
        }
        for (int i = 0; i < cboEditora.getItemCount(); i++) {
            if (cboEditora.getItemAt(i).getId() == editora.getId()) {
                cboEditora.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarCategoria(Categoria categoria) {
        if (categoria == null) {
            return;
        }
        for (int i = 0; i < cboCategoria.getItemCount(); i++) {
            if (cboCategoria.getItemAt(i).getId() == categoria.getId()) {
                cboCategoria.setSelectedIndex(i);
                return;
            }
        }
    }

    private void carregarTabela(String filtro) {
        modelo.setRowCount(0);
        livrosPesquisados.clear();
        try {
            livrosPesquisados = Livro.pesquisar(filtro);
            for (Livro livro : livrosPesquisados) {
                modelo.addRow(new Object[]{
                    livro.getId(),
                    livro.getIssn(),
                    livro.getNome(),
                    livro.getAutor(),
                    livro.getEditora(),
                    livro.getCategoria(),
                    livro.getAnoPublicacao()
                });
            }
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtIssn.setText("");
        txtNome.setText("");
        txtAnoPublicacao.setText("");
        txtResenha.setText("");
        if (cboAutor.getItemCount() > 0) {
            cboAutor.setSelectedIndex(0);
        }
        if (cboEditora.getItemCount() > 0) {
            cboEditora.setSelectedIndex(0);
        }
        if (cboCategoria.getItemCount() > 0) {
            cboCategoria.setSelectedIndex(0);
        }
        tabela.clearSelection();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
