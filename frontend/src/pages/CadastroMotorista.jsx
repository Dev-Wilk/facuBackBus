import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/Api';
import '../styles/CadastroUsuario.css';

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function CadastroMotorista() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    fullName: '',
    contact: '',
    identificationNumber: '',
    status: 'AVAILABLE',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === 'contact') {
      let telefoneFormatado = value.replace(/\D/g, '');

      if (telefoneFormatado.length > 0) {
        telefoneFormatado = telefoneFormatado.replace(/^(\d{2})(\d)/g, '($1) $2');
        telefoneFormatado = telefoneFormatado.replace(/(\d{5})(\d)/, '$1-$2');
        if (telefoneFormatado.length > 15) {
          telefoneFormatado = telefoneFormatado.substring(0, 15);
        }
      }

      setForm(prev => ({ ...prev, [name]: telefoneFormatado }));
    } else {
      setForm(prev => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const telefoneSomenteNumeros = form.contact.replace(/\D/g, '');
    if (telefoneSomenteNumeros.length !== 10 && telefoneSomenteNumeros.length !== 11) {
      toast.error('O telefone deve conter 10 ou 11 dígitos (incluindo DDD).', { autoClose: 3000, position: 'top-center' });
      return;
    }

    try {
      const payload = {
        ...form,
        contact: telefoneSomenteNumeros,
      };

      await api.post('/drivers', payload);
      toast.success('Motorista cadastrado com sucesso!', { autoClose: 3000, position: 'top-center' });
      navigate('/dashboard-admin');
    } catch (err) {
      toast.error('Erro ao cadastrar motorista', { autoClose: 3000, position: 'top-center' });
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
          placeholder="Telefone com DDD"
          value={form.contact}
          onChange={handleChange}
          required
          maxLength={15}
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
      </form>
      <ToastContainer position="top-center" autoClose={3000} hideProgressBar />
    </div>
  );
}
