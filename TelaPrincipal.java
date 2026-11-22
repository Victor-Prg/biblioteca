import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaPrincipal extends JFrame {

    private JLabel lblTitulo;
    private JButton btnAutores;
    private JButton btnEditoras;
    private JButton btnCategorias;
    private JButton btnLivros;
    private JButton btnCapitulos;

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }

    public TelaPrincipal() {
        setTitle("Sistema de Biblioteca");
        setLayout(null);
        setSize(420, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitulo = new JLabel("Sistema de Biblioteca");
        lblTitulo.setBounds(20, 20, 380, 30);
        add(lblTitulo);

        btnAutores = new JButton("Autores");
        btnAutores.setBounds(60, 70, 280, 35);
        btnAutores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaAutor().setVisible(true);
            }
        });
        add(btnAutores);

        btnEditoras = new JButton("Editoras");
        btnEditoras.setBounds(60, 120, 280, 35);
        btnEditoras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaEditora().setVisible(true);
            }
        });
        add(btnEditoras);

        btnCategorias = new JButton("Categorias");
        btnCategorias.setBounds(60, 170, 280, 35);
        btnCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCategoria().setVisible(true);
            }
        });
        add(btnCategorias);

        btnLivros = new JButton("Livros");
        btnLivros.setBounds(60, 220, 280, 35);
        btnLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLivro().setVisible(true);
            }
        });
        add(btnLivros);

        btnCapitulos = new JButton("Capítulos");
        btnCapitulos.setBounds(60, 270, 280, 35);
        btnCapitulos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaCapitulo().setVisible(true);
            }
        });
        add(btnCapitulos);
    }
}
