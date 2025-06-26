import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/Api';
import '../styles/CadastroUsuario.css';

export default function CadastroMotorista() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    fullName: '',
    contact: '',
    identificationNumber: '',
    status: 'AVAILABLE',
  });

  const [telefoneErro, setTelefoneErro] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'contact') {

      const somenteNumeros = value.replace(/\D/g, '');

      if (somenteNumeros.length > 12) return;
      setForm(prev => ({ ...prev, [name]: somenteNumeros }));

    } else {
      setForm(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    setTelefoneErro('');

    if (form.contact.length !== 12) {
      setTelefoneErro('O telefone deve conter exatamente 12 dígitos.');
      return;
    }

    try {
      await api.post('/drivers', form);
      alert('Motorista cadastrado com sucesso!');
      navigate('/dashboard-admin');
    } catch (err) {
      alert('Erro ao cadastrar motorista');
      if (err.response) {
        console.error('Status:', err.response.status);
        console.error('Data:', err.response.data);
      } else {
        console.error('Erro:', err.message);
      }
    }
  };

  return (
    <div className="ContainerCadastro">
      <h2>Cadastro de Motorista</h2>
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
          name="contact"
          placeholder="Telefone (somente números)"
          value={form.contact}
          onChange={handleChange}
          required
          maxLength={12}
          inputMode="numeric"
        />


        <input
          className="InputCadastro"
          name="identificationNumber"
          placeholder="Número da CNH"
          value={form.identificationNumber}
          onChange={handleChange}
          required
        />

        <select
          className="SelectCadastro"
          name="status"
          value={form.status}
          onChange={handleChange}
          required
        >
          <option value="AVAILABLE">Ativo</option>
          <option value="ON_VACATION">De férias</option>
          <option value="ON_LEAVE">Licença</option>
          <option value="ASSIGNED_TO_EVENT">Atribuído a Evento</option>
        </select>

        <button className="ButtonCadastro" type="submit">
          Cadastrar
        </button>
        {telefoneErro && (
          <span style={{ color: '#CCC', fontSize: '0.9rem', marginTop: '2rem' }}>
            {telefoneErro}
          </span>
        )}
      </form>
    </div>
  );
}
