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

public class TelaCategoria extends JFrame {

    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblFiltro;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtFiltro;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaCategoria() {
        setTitle("Cadastro de Categorias");
        setLayout(null);
        setSize(760, 520);
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

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(20, 105, 100, 30);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvar();
            }
        });
        add(btnSalvar);

        btnAlterar = new JButton("Alterar");
        btnAlterar.setBounds(130, 105, 100, 30);
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterar();
            }
        });
        add(btnAlterar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 105, 100, 30);
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });
        add(btnExcluir);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(350, 105, 110, 30);
        btnPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabela(txtFiltro.getText());
            }
        });
        add(btnPesquisar);

        lblFiltro = new JLabel("Filtro:");
        lblFiltro.setBounds(20, 150, 60, 25);
        add(lblFiltro);

        txtFiltro = new JTextField();
        txtFiltro.setBounds(85, 150, 300, 25);
        add(txtFiltro);

        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nome");

        tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 190, 700, 270);
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
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da categoria antes de salvar.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setNome(txtNome.getText().trim());

        try {
            categoria.salvar();
            JOptionPane.showMessageDialog(this, "Categoria salva com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela para alterar.");
            return;
        }
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome da categoria antes de alterar.");
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(txtId.getText().trim()));
        categoria.setNome(txtNome.getText().trim());

        try {
            categoria.alterar();
            JOptionPane.showMessageDialog(this, "Categoria alterada com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela para excluir.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta categoria?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(txtId.getText().trim()));

        try {
            categoria.excluir();
            JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!");
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
    }

    private void carregarTabela(String filtro) {
        modelo.setRowCount(0);
        try {
            List<Categoria> lista = Categoria.pesquisar(filtro);
            for (Categoria categoria : lista) {
                modelo.addRow(new Object[]{
                    categoria.getId(),
                    categoria.getNome()
                });
            }
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        tabela.clearSelection();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
