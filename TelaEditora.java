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

public class TelaEditora extends JFrame {

    private JLabel lblId;
    private JLabel lblNome;
    private JLabel lblEditorChefe;
    private JLabel lblAnoFundacao;
    private JLabel lblFiltro;
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEditorChefe;
    private JTextField txtAnoFundacao;
    private JTextField txtFiltro;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaEditora() {
        setTitle("Cadastro de Editoras");
        setLayout(null);
        setSize(760, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblId = new JLabel("Id:");
        lblId.setBounds(20, 20, 120, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 20, 80, 25);
        txtId.setEditable(false);
        add(txtId);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 60, 300, 25);
        add(txtNome);

        lblEditorChefe = new JLabel("Editor Chefe:");
        lblEditorChefe.setBounds(20, 100, 120, 25);
        add(lblEditorChefe);

        txtEditorChefe = new JTextField();
        txtEditorChefe.setBounds(150, 100, 300, 25);
        add(txtEditorChefe);

        lblAnoFundacao = new JLabel("Ano de Fundação:");
        lblAnoFundacao.setBounds(20, 140, 130, 25);
        add(lblAnoFundacao);

        txtAnoFundacao = new JTextField();
        txtAnoFundacao.setBounds(150, 140, 100, 25);
        add(txtAnoFundacao);

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
        modelo.addColumn("Editor Chefe");
        modelo.addColumn("Ano de Fundação");

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
                || txtEditorChefe.getText().trim().isEmpty()
                || txtAnoFundacao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }

        int anoFundacao;
        try {
            anoFundacao = Integer.parseInt(txtAnoFundacao.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "O ano de fundação deve ser um número inteiro.");
            return;
        }

        Editora editora = new Editora();
        editora.setNome(txtNome.getText().trim());
        editora.setEditorChefe(txtEditorChefe.getText().trim());
        editora.setAnoFundacao(anoFundacao);

        try {
            editora.salvar();
            JOptionPane.showMessageDialog(this, "Editora salva com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma editora na tabela para alterar.");
            return;
        }
        if (txtNome.getText().trim().isEmpty()
                || txtEditorChefe.getText().trim().isEmpty()
                || txtAnoFundacao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de alterar.");
            return;
        }

        int anoFundacao;
        try {
            anoFundacao = Integer.parseInt(txtAnoFundacao.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "O ano de fundação deve ser um número inteiro.");
            return;
        }

        Editora editora = new Editora();
        editora.setId(Integer.parseInt(txtId.getText().trim()));
        editora.setNome(txtNome.getText().trim());
        editora.setEditorChefe(txtEditorChefe.getText().trim());
        editora.setAnoFundacao(anoFundacao);

        try {
            editora.alterar();
            JOptionPane.showMessageDialog(this, "Editora alterada com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma editora na tabela para excluir.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta editora?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Editora editora = new Editora();
        editora.setId(Integer.parseInt(txtId.getText().trim()));

        try {
            editora.excluir();
            JOptionPane.showMessageDialog(this, "Editora excluída com sucesso!");
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
        txtEditorChefe.setText(texto(tabela.getValueAt(linha, 2)));
        txtAnoFundacao.setText(texto(tabela.getValueAt(linha, 3)));
    }

    private void carregarTabela(String filtro) {
        modelo.setRowCount(0);
        try {
            List<Editora> lista = Editora.pesquisar(filtro);
            for (Editora editora : lista) {
                modelo.addRow(new Object[]{
                    editora.getId(),
                    editora.getNome(),
                    editora.getEditorChefe(),
                    editora.getAnoFundacao()
                });
            }
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEditorChefe.setText("");
        txtAnoFundacao.setText("");
        tabela.clearSelection();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
