import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/Api';
import { statusMotorista } from '../utils/statusLabels';
import '../styles/CadastroEvento.css';

export default function EditarEvento() {
    const { id } = useParams();
    const navigate = useNavigate();

    const [form, setForm] = useState({
        nomeResponsavel: '',
        telefoneResponsavel: '',
        localEvento: '',
        dataEvento: '',
        dataRetorno: '',
        horarioIda: '',
        horarioVolta: '',
        quantidadePassageiros: '',
        funcionarioId: '',
        valorEvento: '',
        motoristaId: '',
        onibusIds: '',
    });

    const [erroTelefone, setErroTelefone] = useState('');
    const [funcionariosDisponiveis, setFuncionariosDisponiveis] = useState([]);
    const [motoristasDisponiveis, setMotoristasDisponiveis] = useState([]);
    const [onibusDisponiveis, setOnibusDisponiveis] = useState([]);

    useEffect(() => {
        async function fetchDados() {
            try {
                const [eventoRes, funcionariosRes, motoristasRes, onibusRes] = await Promise.all([
                    api.get(`/events/${id}`),
                    api.get('/users'),
                    api.get('/drivers'),
                    api.get('/buses'),
                ]);

                const evento = eventoRes.data;

                const formataData = (data) => {
                    if (Array.isArray(data) && data.length === 3) {
                        const [ano, mes, dia] = data;
                        return `${ano}-${String(mes).padStart(2, '0')}-${String(dia).padStart(2, '0')}`;
                    }
                    return data || '';
                };

                const formataHorario = (horario) => {
                    if (Array.isArray(horario) && horario.length === 2) {
                        const [hora, minuto] = horario;
                        return `${String(hora).padStart(2, '0')}:${String(minuto).padStart(2, '0')}`;
                    }
                    return horario || '';
                };


                const motoristaEvento = motoristasRes.data.find(m => m.id === evento.driverId);
                const onibusEvento = onibusRes.data.find(o => o.id === evento.busId);


                let motoristas = motoristasRes.data;
                if (motoristaEvento && !motoristas.find(m => m.id === motoristaEvento.id)) {
                    motoristas = [motoristaEvento, ...motoristas];
                }


                let onibus = onibusRes.data;
                if (onibusEvento && !onibus.find(o => o.id === onibusEvento.id)) {
                    onibus = [onibusEvento, ...onibus];
                }

                setForm({
                    nomeResponsavel: evento.responsibleName || '',
                    telefoneResponsavel: evento.contactPhone || '',
                    localEvento: evento.eventLocation || '',
                    dataEvento: formataData(evento.eventDepartureDate),
                    dataRetorno: formataData(evento.eventReturnDate),
                    horarioIda: formataHorario(evento.departureTime),
                    horarioVolta: formataHorario(evento.returnTime),
                    quantidadePassageiros: evento.numberOfPassengers?.toString() || '',
                    funcionarioId: evento.employee?.id?.toString() || '',
                    valorEvento: evento.eventValue?.toString() || '',
                    motoristaId: motoristaEvento?.id?.toString() || '',
                    onibusIds: onibusEvento?.id?.toString() || '',
                });

                setFuncionariosDisponiveis(funcionariosRes.data);
                setMotoristasDisponiveis(motoristas);
                setOnibusDisponiveis(onibus);
            } catch (err) {
                console.error('Erro ao buscar dados:', err);
                alert('Erro ao carregar dados do evento');
                navigate('/dashboard-admin');
            }
        }
        fetchDados();
    }, [id, navigate]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'telefoneResponsavel') {
            const somenteNumeros = value.replace(/\D/g, '').slice(0, 12);
            setForm(prev => ({ ...prev, [name]: somenteNumeros }));
        } else {
            setForm(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setErroTelefone('');

        const hoje = new Date();
        const dataIda = new Date(form.dataEvento + 'T00:00');
        const dataVolta = new Date(form.dataRetorno + 'T00:00');

        if (dataIda < hoje.setHours(0, 0, 0, 0)) {
            alert('A data do evento não pode ser anterior à data atual.');
            return;
        }

        if (dataVolta < dataIda) {
            alert('A data de retorno não pode ser anterior à data do evento.');
            return;
        }

        if (form.telefoneResponsavel.length !== 12) {
            setErroTelefone('O telefone deve conter exatamente 12 dígitos.');
            return;
        }

        const onibusSelecionado = onibusDisponiveis.find(o => o.id.toString() === form.onibusIds);
        if (onibusSelecionado && Number(form.quantidadePassageiros) > Number(onibusSelecionado.maxCapacity)) {
            alert(`Passageiros excedem a capacidade máxima do ônibus (${onibusSelecionado.maxCapacity})`);
            return;
        }

        try {
            const payload = {
                responsibleName: form.nomeResponsavel,
                contactPhone: form.telefoneResponsavel,
                eventLocation: form.localEvento,
                eventDepartureDate: form.dataEvento,
                eventReturnDate: form.dataRetorno,
                departureTime: form.horarioIda,
                returnTime: form.horarioVolta,
                numberOfPassengers: Number(form.quantidadePassageiros),
                employeeId: Number(form.funcionarioId),
                statusPayment: 'Confirmado',
                eventValue: Number(form.valorEvento),
                driverId: Number(form.motoristaId),
                busId: Number(form.onibusIds),
            };

            await api.put(`/events/${id}`, payload);
            alert('Evento atualizado com sucesso!');
            navigate('/dashboard-admin');
        } catch (error) {
            console.error('Erro ao atualizar evento:', error.response?.data || error.message);
            alert('Erro ao atualizar evento');
        }
    };


    const motoristasDisponiveisFiltrados = motoristasDisponiveis.filter(m => m.status === 'AVAILABLE');
    const motoristaSelecionado = motoristasDisponiveis.find(m => m.id.toString() === form.motoristaId);
    const motoristasParaExibir = motoristaSelecionado && motoristaSelecionado.status !== 'AVAILABLE'
        ? [motoristaSelecionado, ...motoristasDisponiveisFiltrados.filter(m => m.id !== motoristaSelecionado.id)]
        : motoristasDisponiveisFiltrados;


    const onibusDisponiveisFiltrados = onibusDisponiveis.filter(o => o.status === 'AVAILABLE');
    const onibusSelecionado = onibusDisponiveis.find(o => o.id.toString() === form.onibusIds);
    const onibusParaExibir = onibusSelecionado && onibusSelecionado.status !== 'AVAILABLE'
        ? [onibusSelecionado, ...onibusDisponiveisFiltrados.filter(o => o.id !== onibusSelecionado.id)]
        : onibusDisponiveisFiltrados;

    return (
        <div className="ContainerCadastro">
            <h2>Editar Evento</h2>
            <form className="FormCadastro" id="form-evento" onSubmit={handleSubmit}>
                <div className="GridFormulario">
                    <div className="ColunaFormulario">
                        <div className="LinhaFormulario">
                            <label>Nome:</label>
                            <input
                                name="nomeResponsavel"
                                value={form.nomeResponsavel}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Telefone (12 dígitos):</label>
                            <input
                                name="telefoneResponsavel"
                                type="text"
                                maxLength="12"
                                value={form.telefoneResponsavel}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Local:</label>
                            <input
                                name="localEvento"
                                value={form.localEvento}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Data do Evento:</label>
                            <input
                                name="dataEvento"
                                type="date"
                                value={form.dataEvento}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Data de Retorno:</label>
                            <input
                                name="dataRetorno"
                                type="date"
                                value={form.dataRetorno}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Horário Ida:</label>
                            <input
                                name="horarioIda"
                                type="time"
                                value={form.horarioIda}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Horário Volta:</label>
                            <input
                                name="horarioVolta"
                                type="time"
                                value={form.horarioVolta}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </div>

                    <div className="ColunaFormulario">
                        <div className="LinhaFormulario">
                            <label>Passageiros:</label>
                            <input
                                name="quantidadePassageiros"
                                type="number"
                                min="1"
                                value={form.quantidadePassageiros}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="LinhaFormulario">
                            <label>Funcionário:</label>
                            <select
                                name="funcionarioId"
                                value={form.funcionarioId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Selecione</option>
                                {funcionariosDisponiveis.map((f) => (
                                    <option key={f.id} value={f.id}>
                                        {f.fullName}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="LinhaFormulario">
                            <label>Motorista:</label>
                            <select
                                name="motoristaId"
                                value={form.motoristaId}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Selecione</option>
                                {motoristasParaExibir.map((m) => (
                                    <option key={m.id} value={m.id}>
                                        {m.fullName} ({statusMotorista[m.status]})
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="LinhaFormulario">
                            <label>Ônibus:</label>
                            <select
                                name="onibusIds"
                                value={form.onibusIds}
                                onChange={handleChange}
                                required
                            >
                                <option value="">Selecione</option>
                                {onibusParaExibir.map((o) => (
                                    <option key={o.id} value={o.id}>
                                        {o.plate || o.placa} (Cap.: {o.maxCapacity || o.capacidade})
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="LinhaFormulario">
                            <label>Valor (R$):</label>
                            <input
                                name="valorEvento"
                                type="number"
                                min="0"
                                value={form.valorEvento}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </div>
                </div>

                {erroTelefone && (
                    <span style={{ color: '#CCC', fontSize: '0.9rem', marginTop: '2rem' }}>
                        {erroTelefone}
                    </span>
                )}

                <button className="ButtonCadastro" type="submit">
                    Salvar
                </button>
            </form>
        </div>
    );
}
