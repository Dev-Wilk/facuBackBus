# # facuBackBus - Sistema de Gerenciamento de Transporte

## 1. Informações Básicas do Projeto

- **Nome do Projeto:** facuBackBus
- **Descrição Curta:** Sistema de gerenciamento para agendamento de viagens de ônibus e controle de frotas e motoristas, desenvolvido para fins didáticos.
- **Tipo de Projeto:** Aplicação Web Full Stack (Backend API RESTful + Frontend).
- **Qual o problema que ele resolve?** Simplifica a gestão de viagens, motoristas e ônibus para uma instituição, automatizando agendamentos e evitando conflitos de alocação de veículos e pessoal.

---

## 2. Tecnologias Utilizadas

O projeto é um monorepo que utiliza Docker para orquestrar os serviços de backend, frontend e banco de dados.

### Backend
- **Linguagem:** Java 17
- **Frameworks/Bibliotecas:**
  - Spring Boot 3.2.6
  - Spring Data JPA (com Hibernate)
  - Lombok
  - MapStruct
  - Springdoc OpenAPI (para documentação da API com Swagger)
- **Banco de Dados:** PostgreSQL
- **Ferramentas:** Maven, Docker

### Frontend
- **Linguagem:** JavaScript (ES6+)
- **Frameworks/Bibliotecas:**
  - React 19
  - React Router Dom (para roteamento)
  - Axios (para chamadas à API)
  - React Toastify (para notificações)
- **Ferramentas:** Vite, Nginx, Docker, ESLint

---

## 3. Funcionalidades Principais

- **Gestão de Ônibus:** Cadastro, edição, exclusão e visualização de ônibus, com controle de status (`DISPONÍVEL`, `EM MANUTENÇÃO`, `EM VIAGEM`).
- **Gestão de Motoristas:** Cadastro e controle de motoristas.
- **Gestão de Eventos/Viagens:** Agendamento de viagens, associando ônibus e motoristas disponíveis.
- **Gestão de Usuários:** Cadastro de usuários para acesso ao sistema.
- **Validação de Conflitos:** O sistema impede o agendamento de um mesmo ônibus ou motorista para eventos conflitantes.
- **API Documentada:** A API do backend é autodocumentada e pode ser explorada via Swagger UI.

---

## 4. Como Instalar e Rodar o Projeto Localmente

A maneira mais simples de executar o projeto é utilizando Docker e Docker Compose, que cuidam de toda a configuração e inicialização dos serviços.

**Pré-requisitos:**
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/) (geralmente já vem com o Docker Desktop)

**Passos para Execução:**

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/Dev-Wilk/facuBackBus.git
   ```

2. **Navegar até o diretório raiz do projeto:**
   ```bash
   cd facuBackBus
   ```

3. **Subir os contêineres com Docker Compose:**
   ```bash
   docker-compose up --build
   ```
   Este comando irá construir as imagens do backend e do frontend e iniciar os contêineres do banco de dados, da API e da aplicação web.

4. **Acessar os serviços:**
   - **Frontend (Aplicação Web):** [http://localhost:5173](http://localhost:5173)
   - **Backend (API):** [http://localhost:8082](http://localhost:8082)
   - **Documentação da API (Swagger UI):** [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

---

## 5. Como Usar (Exemplos de API)

A API está disponível em `http://localhost:8082`. Você pode usar o Swagger UI para explorar e testar todos os endpoints de forma interativa.

**Exemplo 1: Listar todos os ônibus cadastrados**
- **Endpoint:** `GET /buses`
- **Resposta (Exemplo):**
  ```json
  [
    {
      "id": 1,
      "patente": "BUS-101",
      "capacidade": 45,
      "status": "AVAILABLE"
    },
    {
      "id": 2,
      "patente": "BUS-102",
      "capacidade": 40,
      "status": "IN_MAINTENANCE"
    }
  ]
  ```

**Exemplo 2: Criar um novo ônibus**
- **Endpoint:** `POST /buses`
- **Corpo da Requisição (JSON):**
  ```json
  {
    "patente": "BUS-103",
    "capacidade": 50,
    "status": "AVAILABLE"
  }
  ```

---

## 6. Contribuição

Este é um projeto acadêmico desenvolvido para fins didáticos. No entanto, sinta-se à vontade para abrir *Issues* para relatar problemas ou sugerir melhorias.

---

## 7. Licença

Este projeto está sob a licença **MIT License**.

---