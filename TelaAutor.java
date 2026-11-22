import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TelaAutor extends JFrame {

    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblNomeMae;
    private JLabel lblCidade;
    private JLabel lblFiltro;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtNomeMae;
    private JTextField txtCidade;
    private JTextField txtFiltro;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaAutor() {
        setTitle("Cadastro de Autores");
        setLayout(null);
        setSize(760, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblId = new JLabel("Id:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 20, 80, 25);
        txtId.setEditable(false);
        add(txtId);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(130, 60, 300, 25);
        add(txtNome);

        lblNomeMae = new JLabel("Nome da Mãe:");
        lblNomeMae.setBounds(20, 100, 100, 25);
        add(lblNomeMae);

        txtNomeMae = new JTextField();
        txtNomeMae.setBounds(130, 100, 300, 25);
        add(txtNomeMae);

        lblCidade = new JLabel("Cidade de Nascimento:");
        lblCidade.setBounds(20, 140, 150, 25);
        add(lblCidade);

        txtCidade = new JTextField();
        txtCidade.setBounds(180, 140, 250, 25);
        add(txtCidade);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(20, 185, 100, 30);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });
        add(btnSalvar);

        btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(130, 185, 100, 30);
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterar();
            }
        });
        add(btnAlterar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 185, 100, 30);
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        add(btnExcluir);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(350, 185, 110, 30);
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabela(txtFiltro.getText());
            }
        });
        add(btnPesquisar);

        lblFiltro = new JLabel("Filtro:");
        lblFiltro.setBounds(20, 230, 60, 25);
        add(lblFiltro);

        txtFiltro = new JTextField();
        txtFiltro.setBounds(85, 230, 300, 25);
        add(txtFiltro);

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nome");
        modelo.addColumn("Nome da Mãe");
        modelo.addColumn("Cidade de Nascimento");

        tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 270, 700, 300);
        add(scroll);

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                preencherCampos();
            }
        });

        carregarTabela("");
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()
                || txtNomeMae.getText().trim().isEmpty()
                || txtCidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }

        Autor autor = new Autor();
        autor.setNome(txtNome.getText().trim());
        autor.setNomeMae(txtNomeMae.getText().trim());
        autor.setCidadeNascimento(txtCidade.getText().trim());

        try {
            autor.salvar();
            JOptionPane.showMessageDialog(this, "Autor salvo com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um autor na tabela para alterar.");
            return;
        }
        if (txtNome.getText().trim().isEmpty()
                || txtNomeMae.getText().trim().isEmpty()
                || txtCidade.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de alterar.");
            return;
        }

        Autor autor = new Autor();
        autor.setId(Integer.parseInt(txtId.getText().trim()));
        autor.setNome(txtNome.getText().trim());
        autor.setNomeMae(txtNomeMae.getText().trim());
        autor.setCidadeNascimento(txtCidade.getText().trim());

        try {
            autor.alterar();
            JOptionPane.showMessageDialog(this, "Autor alterado com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um autor na tabela para excluir.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este autor?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Autor autor = new Autor();
        autor.setId(Integer.parseInt(txtId.getText().trim()));

        try {
            autor.excluir();
            JOptionPane.showMessageDialog(this, "Autor excluído com sucesso!");
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
        txtNome.setText(texto(tabela.getValueAt(linha, 1)));
        txtNomeMae.setText(texto(tabela.getValueAt(linha, 2)));
        txtCidade.setText(texto(tabela.getValueAt(linha, 3)));
    }

    private void carregarTabela(String filtro) {
        modelo.setRowCount(0);
        try {
            List<Autor> lista = Autor.pesquisar(filtro);
            for (Autor autor : lista) {
                modelo.addRow(new Object[]{
                    autor.getId(),
                    autor.getNome(),
                    autor.getNomeMae(),
                    autor.getCidadeNascimento()
                });
            }
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtNomeMae.setText("");
        txtCidade.setText("");
        tabela.clearSelection();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
