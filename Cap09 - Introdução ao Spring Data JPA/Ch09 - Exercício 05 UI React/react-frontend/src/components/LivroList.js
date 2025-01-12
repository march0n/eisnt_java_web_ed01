import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './LivroList.css';

const LivroList = () => {
    const [livros, setLivros] = useState([]);
    const [titulo, setTitulo] = useState('');
    const [autor, setAutor] = useState('');
    const [loading, setLoading] = useState(false);
    const [erro, setErro] = useState('');

    useEffect(() => {
        fetchLivros();
    }, []);

    const fetchLivros = async () => {
        setLoading(true);
        setErro('');
        try {
            const response = await axios.get('http://localhost:8080/api/livros');
            setLivros(response.data);
        } catch (error) {
            setErro('Erro ao buscar livros. Tente novamente mais tarde.');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const adicionarLivro = async () => {
        setErro('');
        if (!titulo || !autor) {
            setErro('Por favor, preencha todos os campos!');
            return;
        }
        try {
            const novoLivro = { titulo, autor };
            await axios.post('http://localhost:8080/api/livros', novoLivro);
            fetchLivros();
            setTitulo('');
            setAutor('');
        } catch (error) {
            setErro('Erro ao adicionar livro. Tente novamente.');
            console.error(error);
        }
    };

    const removerLivro = async (id) => {
        setErro('');
        try {
            await axios.delete(`http://localhost:8080/api/livros/${id}`);
            fetchLivros();
        } catch (error) {
            setErro('Erro ao remover livro. Tente novamente.');
            console.error(error);
        }
    };

    return (
        <div className="livro-list">
            <h1>Gestão de Livros</h1>
            {erro && <p className="erro">{erro}</p>}

            <div className="livro-form">
                <input
                    type="text"
                    placeholder="Título"
                    value={titulo}
                    onChange={(e) => setTitulo(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Autor"
                    value={autor}
                    onChange={(e) => setAutor(e.target.value)}
                />
                <button onClick={adicionarLivro}>Adicionar Livro</button>
            </div>

            {loading ? (
                <p>Carregando...</p>
            ) : (
                <ul>
                    {livros.map((livro) => (
                        <li key={livro.id}>
                            <strong>{livro.titulo}</strong> - {livro.autor}
                            <button
                                className="remover-btn"
                                onClick={() => removerLivro(livro.id)}
                            >
                                Remover
                            </button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default LivroList;
