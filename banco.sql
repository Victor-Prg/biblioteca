CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nome_mae VARCHAR(100) NOT NULL,
    cidade_nascimento VARCHAR(100) NOT NULL
);

CREATE TABLE editora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    editor_chefe VARCHAR(100) NOT NULL,
    ano_fundacao INT NOT NULL
);

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    issn INT NOT NULL,
    nome VARCHAR(150) NOT NULL,
    resenha TEXT,
    ano_publicacao INT NOT NULL,
    id_autor INT NOT NULL,
    id_editora INT NOT NULL,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_autor) REFERENCES autor (id),
    FOREIGN KEY (id_editora) REFERENCES editora (id),
    FOREIGN KEY (id_categoria) REFERENCES categoria (id)
);

CREATE TABLE capitulo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    pagina_inicial INT NOT NULL,
    pagina_final INT NOT NULL,
    resumo TEXT,
    id_livro INT NOT NULL,
    FOREIGN KEY (id_livro) REFERENCES livro (id) ON DELETE CASCADE
);
