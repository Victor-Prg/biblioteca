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

public class TelaCapitulo extends JFrame {

    private JLabel lblId;
    private JLabel lblLivro;
    private JLabel lblNumero;
    private JLabel lblTitulo;
    private JLabel lblPaginaInicial;
    private JLabel lblPaginaFinal;
    private JLabel lblResumo;
    private JLabel lblFiltro;
    private JTextField txtId;
    private JTextField txtNumero;
    private JTextField txtTitulo;
    private JTextField txtPaginaInicial;
    private JTextField txtPaginaFinal;
    private JTextField txtFiltro;
    private JTextArea txtResumo;
    private JComboBox<Livro> cboLivro;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private List<Capitulo> capitulosPesquisados;

    public TelaCapitulo() {
        setTitle("Cadastro de Capítulos");
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

        lblLivro = new JLabel("Livro:");
        lblLivro.setBounds(20, 55, 110, 25);
        add(lblLivro);

        cboLivro = new JComboBox<Livro>();
        cboLivro.setBounds(130, 55, 400, 25);
        add(cboLivro);

        lblNumero = new JLabel("Número:");
        lblNumero.setBounds(20, 90, 110, 25);
        add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(130, 90, 100, 25);
        add(txtNumero);

        lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 125, 110, 25);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(130, 125, 400, 25);
        add(txtTitulo);

        lblPaginaInicial = new JLabel("Página Inicial:");
        lblPaginaInicial.setBounds(20, 160, 110, 25);
        add(lblPaginaInicial);

        txtPaginaInicial = new JTextField();
        txtPaginaInicial.setBounds(130, 160, 100, 25);
        add(txtPaginaInicial);

        lblPaginaFinal = new JLabel("Página Final:");
        lblPaginaFinal.setBounds(20, 195, 110, 25);
        add(lblPaginaFinal);

        txtPaginaFinal = new JTextField();
        txtPaginaFinal.setBounds(130, 195, 100, 25);
        add(txtPaginaFinal);

        lblResumo = new JLabel("Resumo:");
        lblResumo.setBounds(20, 230, 110, 25);
        add(lblResumo);

        txtResumo = new JTextArea();
        txtResumo.setLineWrap(true);
        txtResumo.setWrapStyleWord(true);

        JScrollPane scrollResumo = new JScrollPane(txtResumo);
        scrollResumo.setBounds(130, 230, 400, 70);
        add(scrollResumo);

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
        modelo.addColumn("Livro");
        modelo.addColumn("Número");
        modelo.addColumn("Título");
        modelo.addColumn("Pág. Inicial");
        modelo.addColumn("Pág. Final");

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

        capitulosPesquisados = new ArrayList<>();
        carregarCombo();
        carregarTabela("");
    }

    private void carregarCombo() {
        cboLivro.removeAllItems();
        for (Livro livro : Livro.pesquisar("")) {
            cboLivro.addItem(livro);
        }
    }

    private void salvar() {
        if (txtNumero.getText().trim().isEmpty()
                || txtTitulo.getText().trim().isEmpty()
                || txtPaginaInicial.getText().trim().isEmpty()
                || txtPaginaFinal.getText().trim().isEmpty()
                || txtResumo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }
        if (cboLivro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Cadastre e selecione o livro do capítulo.");
            return;
        }

        int numero;
        int paginaInicial;
        int paginaFinal;
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
            paginaInicial = Integer.parseInt(txtPaginaInicial.getText().trim());
            paginaFinal = Integer.parseInt(txtPaginaFinal.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this,
                    "O número, a página inicial e a página final devem ser números inteiros.");
            return;
        }

        Capitulo capitulo = new Capitulo();
        capitulo.setNumero(numero);
        capitulo.setTitulo(txtTitulo.getText().trim());
        capitulo.setPaginaInicial(paginaInicial);
        capitulo.setPaginaFinal(paginaFinal);
        capitulo.setResumo(txtResumo.getText().trim());
        capitulo.setLivro((Livro) cboLivro.getSelectedItem());

        try {
            capitulo.salvar();
            JOptionPane.showMessageDialog(this, "Capítulo salvo com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um capítulo na tabela para alterar.");
            return;
        }
        if (txtNumero.getText().trim().isEmpty()
                || txtTitulo.getText().trim().isEmpty()
                || txtPaginaInicial.getText().trim().isEmpty()
                || txtPaginaFinal.getText().trim().isEmpty()
                || txtResumo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de alterar.");
            return;
        }
        if (cboLivro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o livro do capítulo.");
            return;
        }

        int numero;
        int paginaInicial;
        int paginaFinal;
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
            paginaInicial = Integer.parseInt(txtPaginaInicial.getText().trim());
            paginaFinal = Integer.parseInt(txtPaginaFinal.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this,
                    "O número, a página inicial e a página final devem ser números inteiros.");
            return;
        }

        Capitulo capitulo = new Capitulo();
        capitulo.setId(Integer.parseInt(txtId.getText().trim()));
        capitulo.setNumero(numero);
        capitulo.setTitulo(txtTitulo.getText().trim());
        capitulo.setPaginaInicial(paginaInicial);
        capitulo.setPaginaFinal(paginaFinal);
        capitulo.setResumo(txtResumo.getText().trim());
        capitulo.setLivro((Livro) cboLivro.getSelectedItem());

        try {
            capitulo.alterar();
            JOptionPane.showMessageDialog(this, "Capítulo alterado com sucesso!");
            limparCampos();
            carregarTabela(txtFiltro.getText());
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um capítulo na tabela para excluir.");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este capítulo?",
                "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        Capitulo capitulo = new Capitulo();
        capitulo.setId(Integer.parseInt(txtId.getText().trim()));

        try {
            capitulo.excluir();
            JOptionPane.showMessageDialog(this, "Capítulo excluído com sucesso!");
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
        txtNumero.setText(texto(tabela.getValueAt(linha, 2)));
        txtTitulo.setText(texto(tabela.getValueAt(linha, 3)));
        txtPaginaInicial.setText(texto(tabela.getValueAt(linha, 4)));
        txtPaginaFinal.setText(texto(tabela.getValueAt(linha, 5)));

        selecionarLivro((Livro) tabela.getValueAt(linha, 1));

        // O resumo não é exibido na tabela, por isso é recuperado do capítulo pesquisado
        int id = Integer.parseInt(txtId.getText());
        for (Capitulo capitulo : capitulosPesquisados) {
            if (capitulo.getId() == id) {
                txtResumo.setText(capitulo.getResumo());
                break;
            }
        }
    }

    private void selecionarLivro(Livro livro) {
        if (livro == null) {
            return;
        }
        for (int i = 0; i < cboLivro.getItemCount(); i++) {
            if (cboLivro.getItemAt(i).getId() == livro.getId()) {
                cboLivro.setSelectedIndex(i);
                return;
            }
        }
    }

    private void carregarTabela(String filtro) {
        modelo.setRowCount(0);
        capitulosPesquisados.clear();
        try {
            capitulosPesquisados = Capitulo.pesquisar(filtro);
            for (Capitulo capitulo : capitulosPesquisados) {
                modelo.addRow(new Object[]{
                    capitulo.getId(),
                    capitulo.getLivro(),
                    capitulo.getNumero(),
                    capitulo.getTitulo(),
                    capitulo.getPaginaInicial(),
                    capitulo.getPaginaFinal()
                });
            }
        } catch (RuntimeException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNumero.setText("");
        txtTitulo.setText("");
        txtPaginaInicial.setText("");
        txtPaginaFinal.setText("");
        txtResumo.setText("");
        if (cboLivro.getItemCount() > 0) {
            cboLivro.setSelectedIndex(0);
        }
        tabela.clearSelection();
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
