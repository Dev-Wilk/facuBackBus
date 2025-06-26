import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/Api'; 
import '../styles/CadastroUsuario.css'; 

export default function CadastroOnibus() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    plate: '',           
    maxCapacity: '',     
    status: 'AVAILABLE',    
  });

  const [errors, setErrors] = useState({
    plate: '',
    maxCapacity: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    let hasError = false;
    let newErrors = { plate: '', maxCapacity: '' };

    if (!form.plate.trim()) {
      newErrors.plate = 'Placa é obrigatória.';
      hasError = true;
    }

    if (!form.maxCapacity || isNaN(form.maxCapacity) || Number(form.maxCapacity) <= 0) {
      newErrors.maxCapacity = 'Capacidade deve ser um número maior que zero.';
      hasError = true;
    }

    setErrors(newErrors);
    return !hasError;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    try {
      await api.post('/buses', {
        plate: form.plate,
        maxCapacity: Number(form.maxCapacity),
        status: form.status,
      });
      alert('Ônibus cadastrado com sucesso!');
      navigate('/dashboard-admin'); 
    } catch (err) {
      alert('Erro ao cadastrar ônibus');
      console.error('Erro na requisição:', err.response?.data || err.message);
    }
  };

  return (
    <div className="ContainerCadastro">
      <h2>Cadastro de Ônibus</h2>
      <form className="FormCadastro" onSubmit={handleSubmit}>
        <input
          className="InputCadastro"
          name="plate"
          placeholder="Placa do Ônibus"
          value={form.plate}
          onChange={handleChange}
          required
        />
        {errors.plate && <p style={{ color: '#CCC', fontSize: '12px' }}>{errors.plate}</p>}

        <input
          className="InputCadastro"
          name="maxCapacity"
          type="number"
          placeholder="Capacidade Máxima"
          value={form.maxCapacity}
          onChange={handleChange}
          required
        />
        {errors.maxCapacity && <p style={{ color: '#CCC', fontSize: '12px' }}>{errors.maxCapacity}</p>}

        <select
          className="SelectCadastro"
          name="status"
          value={form.status}
          onChange={handleChange}
          required
        >
          <option value="AVAILABLE">Ativo</option>
          <option value="IN_USE">Inativo</option>
          <option value="MAINTENANCE">Manutenção</option>
          <option value="IN_TRANSIT">Em Trânsito</option>
          <option value="IN_SERVICE">Em Serviço</option>
        </select>

        <button className="ButtonCadastro" type="submit">Cadastrar</button>
      </form>
    </div>
  );
}
