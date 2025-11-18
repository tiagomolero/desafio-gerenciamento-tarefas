# 📘 Gerenciamento de Tarefas — API (Java + Spring Boot)

Este projeto é uma **API REST** para gerenciamento de tarefas, construída com **Java 21**, **Spring Boot 3.5**, **PostgreSQL**, **Autenticação JWT**, validações, boas práticas e testes unitários.  
Foi desenvolvido como parte de um desafio técnico, com foco em organização, segurança e arquitetura limpa.

---

## 🚀 Tecnologias Utilizadas

### **Back-end**
- Java **21**
- Spring Boot **3.5.x**
- Spring Web
- Spring Data JPA
- Spring Security (JWT)
- Hibernate Validator
- Lombok
- PostgreSQL
- H2 (para testes)
- Docker e Docker Compose
- JUnit 5 + Mockito

### **Ferramentas extras**
- pgAdmin 4
- Maven
- IntelliJ IDEA

---

# 📂 Estrutura do Repositório

# 📘 Gerenciamento de Tarefas — API (Java + Spring Boot)

Este projeto é uma **API REST** para gerenciamento de tarefas, construída com **Java 21**, **Spring Boot 3.5**, **PostgreSQL**, **Autenticação JWT**, validações, boas práticas e testes unitários.  
Foi desenvolvido como parte de um desafio técnico, com foco em organização, segurança e arquitetura limpa.

---

## 🚀 Tecnologias Utilizadas

### **Back-end**
- Java **21**
- Spring Boot **3.5.x**
- Spring Web
- Spring Data JPA
- Spring Security (JWT)
- Hibernate Validator
- Lombok
- PostgreSQL
- H2 (para testes)
- Docker e Docker Compose
- JUnit 5 + Mockito

### **Ferramentas extras**
- pgAdmin 4
- Maven
- IntelliJ IDEA

---

# 📂 Estrutura do Repositório

```
desafio-gerenciamento-tarefas/
│
├── api-gerenciamento-tarefas/ # Back-end completo
├── app-gerenciamento-tarefas/ # Front-end Angular (não iniciado)
└── README.md
```

---

# 🔐 Autenticação e Segurança

A API utiliza **JWT (JSON Web Token)** para autenticação e autorização.

### Endpoints públicos:
- `POST /api/auth/login`
- `POST /api/auth/register`

### Endpoints protegidos:
Toda rota exceto as acima.

### Geração do Token
O token contém:
- subject (email)
- id do usuário
- email do usuário
- expiração de 7 dias

---

# 🗂️ Funcionalidades da API

## 👤 **Usuários**
- Registro de usuário
- Login com geração de token
- Autenticação via JWT
- Senhas armazenadas com BCrypt

---

## 📝 **Tarefas**
Cada tarefa possui:
- ID
- Título
- Descrição
- Prioridade (`BAIXA`, `MEDIA`, `ALTA`)
- Status (`PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`, `CANCELADA`)
- Datas de criação e atualização
- Usuário responsável

### Funcionalidades implementadas:
- Criar tarefa
- Buscar tarefas por usuário
- Editar tarefa
- Editar status (com regras validadas)
- Excluir tarefa
- Validações com Bean Validation
- Tratativas de erro personalizadas

### Valores padrões:
- Prioridade padrão → **MÉDIA**
- Status padrão → **PENDENTE**

---

# 🧪 Testes

## Tipos implementados:
✔️ Testes de Controller (MockMvc)  
✔️ Testes de Repository (DataJpaTest)  
✔️ Testes com banco H2  
✔️ Uso correto de mocks sem `@MockBean` (deprecated)  

## Como executar:
Via IntelliJ:

```
Ctrl + Shift + F10 (Windows)
⌘ + Shift + R (Mac)
```

Via Maven:

```
mvn test
```

---

# 🐳 Docker

## 📦 `docker-compose.yml` inclui:
- **PostgreSQL**
- **pgAdmin**
- Aplicação Java rodando na porta **8081**

### Subir os containers:

```
docker-compose up -d
```

### Acessar pgAdmin:
- URL: `http://localhost:8082`
- Email: `user`
- Senha: `Desafio@123`

---

# ⚙️ Configuração da aplicação

Arquivo `application.properties`:

```properties
spring.application.name=GerenciamentoTarefas

# DATASOURCE
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# SERVER PORT
server.port=8081

# JWT SECRET
api.security.token.secret=${JWT_SECRET}
```
---

# ▶️ Executando o Back-end
Via IntelliJ:

- Abrir o projeto api-gerenciamento-tarefas
- Rodar a classe principal:

```
GerenciamentoTarefasApplication.java
```

Via Maven
```
mvn spring-boot:run
```

# 📌 Endpoints Principais

## 📌 Endpoints Principais

Login
```
POST /api/auth/login
```
Exemplo de JSON
```
{
  "email": "user@email.com",
  "senha": "123"
}
```
# 📝 Tarefas
### Criar tarefa
```
POST /api/tarefas
Authorization: Bearer <TOKEN>
```
### Buscar tarefas do usuário autenticado
```
GET /api/tarefas
```
### Atualizar tarefa
```
PUT /api/tarefas/{id}
```
### Atualizar status
```
PATCH /api/tarefas/{id}/status
```
### Deletar
```
DELETE /api/tarefas/{id}
```


# 🧱 Padrões e Boas Práticas Utilizadas

- ✔️ DTOs para entrada e saída
- ✔️ Tratamento global de exceções
- ✔️ Validações com Bean Validation
- ✔️ Arquitetura limpa e separação de responsabilidades
- ✔️ JWT robusto com claims personalizados
- ✔️ Senhas criptografadas
- ✔️ Repository + Service + Controller bem definidos
- ✔️ Testes automatizados
- ✔️ Docker para ambiente padronizado

# 🚧 Front-end (status)

O projeto Angular está criado, porém não iniciado:
```
app-gerenciamento-tarefas/
```

A API já está totalmente pronta para integração.

# 📄 Licença

Este projeto foi desenvolvido como parte de um desafio técnico.
Sinta-se livre para estudar, reutilizar e evoluir o código.