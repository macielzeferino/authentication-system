# Authentication System API
Uma API REST com sistema de autenticação desenvolvida com Spring Boot e JWT.

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- PostgreSQL
- Docker
- Lombok

## Pré-requisitos
- Java 21 ou superior
- Docker e Docker Compose
- Maven

## Configuração do Ambiente

### Variáveis de Ambiente
1. Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:
```properties
POSTGRES_USER=nomeusuario
POSTGRES_PASSWORD=senha
POSTGRES_DB=products_database
```

2. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/authentication-system.git
cd authentication-system
```

3. Configure o banco de dados PostgreSQL usando Docker:
```bash
docker compose up -d
```

### Configuração do Banco de Dados
O projeto utiliza PostgreSQL como banco de dados, configurado através do Docker Compose. As configurações do banco estão definidas nos seguintes arquivos:

- `.env`: Contém as variáveis de ambiente para configuração do banco
- `docker-compose.yml`: Configuração do container Docker do PostgreSQL
- `application.properties`: Configurações de conexão do Spring Boot com o banco

## Arquivos de Configuração

### application.properties
```properties
spring.application.name=Authentication System
spring.datasource.url=jdbc:postgresql://localhost:5432/${POSTGRES_DB}
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

### docker-compose.yml
```yaml
services:
  postgres:
    image: bitnami/postgresql:latest
    container_name: auth_system_db
    ports:
      - '5432:5432'
    env_file:
      - .env
    environment:
      - POSTGRESQL_USERNAME=${POSTGRES_USER}
      - POSTGRESQL_PASSWORD=${POSTGRES_PASSWORD}
      - POSTGRESQL_DATABASE=${POSTGRES_DB}
    volumes:
      - auth_system_database:/bitnami/postgresql
volumes:
  auth_system_database:
```

## Como Executar
1. Compile o projeto:
```bash
mvn clean install
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## Endpoints da API

### Autenticação

#### Registro de usuário
```http
POST /auth/register
```
Body:
```json
{
    "userLogin": "usuario@email.com",
    "userPassword": "senha123",
    "userRole": "USER"
}
```

#### Login
```http
POST /auth/login
```
Body:
```json
{
    "userLogin": "usuario@email.com",
    "userPassword": "senha123"
}
```
Resposta:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Produtos

#### Criar um novo produto (requer permissão ADMIN)
```http
POST /products
```
Header:
```
Authorization: Bearer {token}
```
Body:
```json
{
    "productName": "Produto Teste",
    "productPrice": 99.90
}
```

#### Listar todos os produtos (requer autenticação)
```http
GET /products
```
Header:
```
Authorization: Bearer {token}
```

## Estrutura do Projeto
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── macielzeferino/
│   │           └── authenticationsystem/
│   │               ├── controller/
│   │               │   ├── AuthenticationController.java
│   │               │   └── ProductController.java
│   │               ├── entity/
│   │               │   ├── AuthenticationDto.java
│   │               │   ├── LoginResponseDTO.java
│   │               │   ├── Product.java
│   │               │   ├── RegisterDto.java
│   │               │   ├── User.java
│   │               │   └── UserRole.java
│   │               ├── infra/
│   │               │   └── security/
│   │               │       ├── SecurityConfiguration.java
│   │               │       ├── SecurityFilter.java
│   │               │       └── TokenService.java
│   │               ├── repository/
│   │               │   ├── ProductRepository.java
│   │               │   └── UserRepository.java
│   │               ├── service/
│   │               │   ├── ProductService.java
│   │               │   └── UserService.java
│   │               └── AuthenticationSystemApplication.java
│   └── resources/
│       └── application.properties
├── .env                    # Arquivo de variáveis de ambiente
└── docker-compose.yml      # Configuração do Docker
```

## Modelo de Dados

### User
| Campo           | Tipo      | Descrição                        |
|-----------------|-----------|----------------------------------|
| productId       | UUID      | Identificador único do usuário   |
| userLogin       | String    | Login do usuário                 |
| userPassword    | String    | Senha do usuário (encriptada)    |
| userRole        | UserRole  | Papel do usuário (ADMIN ou USER) |

### Product
| Campo           | Tipo      | Descrição                        |
|-----------------|-----------|----------------------------------|
| productId       | UUID      | Identificador único do produto   |
| productName     | String    | Nome do produto                  |
| productPrice    | Double    | Preço do produto                 |

### AuthenticationDto
| Campo           | Tipo      | Descrição                        |
|-----------------|-----------|----------------------------------|
| userLogin       | String    | Login do usuário                 |
| userPassword    | String    | Senha do usuário                 |

### RegisterDto
| Campo           | Tipo      | Descrição                        |
|-----------------|-----------|----------------------------------|
| userLogin       | String    | Login do usuário                 |
| userPassword    | String    | Senha do usuário                 |
| userRole        | UserRole  | Papel do usuário (ADMIN ou USER) |

### LoginResponseDTO
| Campo           | Tipo      | Descrição                        |
|-----------------|-----------|----------------------------------|
| token           | String    | Token JWT de autenticação        |

### UserRole
Enum com os valores:
- USER
- ADMIN

## Segurança

O sistema utiliza autenticação baseada em JWT (JSON Web Token):
- Tokens são gerados ao fazer login com credenciais válidas
- Tokens expiram após 2 horas
- Rotas protegidas requerem o token no header Authorization
- Controle de acesso baseado em papéis (ADMIN e USER)

## Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Autor
**Maciel Zeferino** - [Linkedin](https://www.linkedin.com/in/macielzeferino/)