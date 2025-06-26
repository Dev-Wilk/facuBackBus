import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/Api';
import '../styles/CadastroUsuario.css';

export default function CadastroUsuario() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        fullName: '',
        login: '',
        password: '',
        userType: 'ATENDENTE',
    });

    const [errors, setErrors] = useState({
        password: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const validatePassword = (password) => {
        const hasNumber = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
        if (!hasNumber || !hasSpecialChar) {
            return 'A senha deve conter pelo menos um número e um caractere especial.';
        }
        return '';
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const passwordError = validatePassword(form.password);
        if (passwordError) {
            setErrors({ password: passwordError });
            return;
        }

        try {
            await api.post('/users', form);
            alert('Usuário cadastrado com sucesso!');

            if (form.userType === 'GERENTE') {
                navigate('/dashboard-admin');
            } else {
                navigate('/dashboard');
            }
        } catch (err) {
            alert('Erro ao cadastrar usuário');
            console.error('Erro na requisição:', err);
        }
    };

    return (
        <div className="ContainerCadastro">
            <h2>Cadastro de Usuário</h2>
            <form className="FormCadastro" onSubmit={handleSubmit}>
                <input
                    className="InputCadastro"
                    name="fullName"
                    placeholder="Nome Completo"
                    value={form.fullName}
                    onChange={handleChange}
                    required
                />
                <input
                    className="InputCadastro"
                    name="login"
                    type="text"
                    placeholder="Login"
                    value={form.login}
                    onChange={handleChange}
                    required
                />
                <input
                    className="InputCadastro"
                    name="password"
                    type="password"
                    placeholder="Senha"
                    value={form.password}
                    onChange={handleChange}
                    required
                />
                <select
                    className="SelectCadastro"
                    name="userType"
                    value={form.userType}
                    onChange={handleChange}
                    required
                >
                    <option value="ATENDENTE">Atendente</option>
                    <option value="GERENTE">Gerente</option>
                </select>
                <button className="ButtonCadastro" type="submit">
                    Cadastrar
                </button>
                {errors.password && (
                    <p style={{ marginTop: '1rem', color: '#CCC', fontSize: '12px' }}>
                        {errors.password}
                    </p>
                )}
            </form>
        </div>
    );
}
