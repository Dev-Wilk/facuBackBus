import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/Api';
import '../styles/DashBoard.css';
import { statusMotorista } from '../utils/statusLabels';

export default function DashBoardMotorista() {
  const [motoristas, setMotoristas] = useState([]);
  const [tipoUsuario, setTipoUsuario] = useState(null);

  useEffect(() => {
    setTipoUsuario(localStorage.getItem('tipoUsuario'));
    fetchMotoristas();
  }, []);

  const fetchMotoristas = async () => {
    try {
      const response = await api.get('/drivers');
      setMotoristas(response.data);
    } catch (error) {
      console.error('Erro ao buscar motoristas:', error);
      alert('Erro ao buscar motoristas');
    }
  };

  const deletar = async (id) => {
    const confirm = window.confirm('Tem certeza que deseja excluir este motorista?');
    if (!confirm) return;

    try {
      await api.delete(`/drivers/${id}`);
      setMotoristas((prev) => prev.filter((m) => m.id !== id));
      alert('Motorista excluído com sucesso!');
    } catch (error) {
      console.error('Erro ao excluir motorista:', error);
      alert('Erro ao excluir motorista');
    }
  };

  return (
    <div className='ContainerCadastro'>
      <h2 className='H2Lista'>Lista de Motoristas</h2>
      <div className='DivTable'>
        <table>
          <thead>
            <tr>
              <th className='ThTable'>Nome</th>
              <th className='ThTable'>Contato</th>
              <th className='ThTable'>CNH</th>
              <th className='ThTable'>Status</th>
              {tipoUsuario === 'GERENTE' && <th className='ThTable'>Ações</th>}
            </tr>
          </thead>
          <tbody>
            {motoristas.map((m) => (
              <tr key={m.id}>
                <td>{m.fullName}</td>
                <td>{m.contact}</td>
                <td>{m.identificationNumber}</td>
                <td>{statusMotorista[m.status]}</td>
                {tipoUsuario === 'GERENTE' && (
                  <td>
                    <Link to={`/editar/motorista/${m.id}`}>
                      <button className='ButtonAcao'>Editar</button>
                    </Link>
                    <button className='ButtonAcao' onClick={() => deletar(m.id)}>
                      Excluir
                    </button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
