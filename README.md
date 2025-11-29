# AutoBots - Gestão das Lojas Especializadas em Manutenção Veicular

## Estrutura e funcionalidades

O AutoBots é um sistema voltado para gerenciamento de clientes, documentos, telefones e endereços de lojas de manutenção veicular e vendas de autopeças.

Funcionalidades principais:

* CRUD completo de clientes, documentos, telefones e endereços.
* Implementação dos níveis de maturidade RMM.
* Arquitetura seguindo princípios SOLID.

---

### 📝 Rotas disponíveis

#### **USUÁRIO**

* **GET** `http://localhost:8080/usuario/{id}` - Busca por ID
* **GET** `http://localhost:8080/usuario` - Lista todos
* **POST** `http://localhost:8080/usuario/cadastro` - Criar
* **PUT** `http://localhost:8080/usuario/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/usuario/{id}` - Excluir

#### **DOCUMENTO**

* **GET** `http://localhost:8080/documento/{id}` - Busca por ID
* **GET** `http://localhost:8080/documento` - Lista todos
* **POST** `http://localhost:8080/documento/cadastro` - Criar
* **PUT** `http://localhost:8080/documento/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/documento/{id}` - Excluir

#### **TELEFONE**

* **GET** `http://localhost:8080/telefone/{id}` - Busca por ID
* **GET** `http://localhost:8080/telefone` - Lista todos
* **POST** `http://localhost:8080/telefone/cadastro` - Criar
* **PUT** `http://localhost:8080/telefone/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/telefone/{id}` - Excluir

#### **ENDEREÇO**

* **GET** `http://localhost:8080/endereco/{id}` - Busca por ID
* **GET** `http://localhost:8080/endereco` - Lista todos
* **POST** `http://localhost:8080/endereco/cadastro` - Criar
* **PUT** `http://localhost:8080/endereco/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/endereco/{id}` - Excluir

#### **EMPRESA**

* **GET** `http://localhost:8080/empresa/{id}` - Busca por ID
* **GET** `http://localhost:8080/empresa` - Lista todos
* **POST** `http://localhost:8080/empresa/cadastro` - Criar
* **PUT** `http://localhost:8080/empresa/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/empresa/{id}` - Excluir

#### **VEÍCULO**

* **GET** `http://localhost:8080/veiculo/{id}` - Busca por ID
* **GET** `http://localhost:8080/veiculo` - Lista todos
* **POST** `http://localhost:8080/veiculo/cadastro` - Criar
* **PUT** `http://localhost:8080/veiculo/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/veiculo/{id}` - Excluir

#### **MERCADORIA (PEÇA)**

* **GET** `http://localhost:8080/mercadoria/{id}` - Busca por ID
* **GET** `http://localhost:8080/mercadoria` - Lista todos
* **POST** `http://localhost:8080/mercadoria/cadastro` - Criar
* **PUT** `http://localhost:8080/mercadoria/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/mercadoria/{id}` - Excluir

#### **SERVIÇO**

* **GET** `http://localhost:8080/servico/{id}` - Busca por ID
* **GET** `http://localhost:8080/servico` - Lista todos
* **POST** `http://localhost:8080/servico/cadastro` - Criar
* **PUT** `http://localhost:8080/servico/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/servico/{id}` - Excluir

#### **VENDA**

* **GET** `http://localhost:8080/venda/{id}` - Busca por ID
* **GET** `http://localhost:8080/venda` - Lista todos
* **POST** `http://localhost:8080/venda/cadastro` - Criar
* **PUT** `http://localhost:8080/venda/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/venda/{id}` - Excluir

#### **CREDENCIAL — Código de barra**

* **GET** `http://localhost:8080/credencial/codigobarra/{id}` - Busca por ID
* **GET** `http://localhost:8080/credencial/codigobarra` - Lista todos
* **POST** `http://localhost:8080/credencial/codigobarra/cadastro` - Criar
* **PUT** `http://localhost:8080/credencial/codigobarra/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/credencial/codigobarra/{id}` - Excluir

#### **CREDENCIAL — Usuário/Senha**

* **GET** `http://localhost:8080/credencial/usuariosenha/{id}` - Busca por ID
* **GET** `http://localhost:8080/credencial/usuariosenha` - Lista todos
* **POST** `http://localhost:8080/credencial/usuariosenha/cadastro` - Criar
* **PUT** `http://localhost:8080/credencial/usuariosenha/atualizar` - Atualizar
* **DELETE** `http://localhost:8080/credencial/usuariosenha/{id}` - Excluir

---

## Exemplo de JSON para teste rápido (usuário)

```json
{
  "nome": "Teste Usuario",
  "nomeSocial": "Teste",
  "perfis": ["CLIENTE"],
  "telefones": [
    {
      "ddd": "11",
      "numero": "999999999"
    }
  ],
  "endereco": {
    "estado": "São Paulo",
    "cidade": "São Paulo",
    "bairro": "Centro",
    "rua": "Rua Teste",
    "numero": "123",
    "codigoPostal": "01310-100",
    "informacoesAdicionais": "Teste"
  },
  "documentos": [
    {
      "tipo": "CPF",
      "numero": "999999131",
      "dataEmissao": "2025-11-29T20:15:27.224+00:00"
    }
  ],
  "emails": [
    {
      "endereco": "teste@teste.com"
    }
  ],
  "credenciais": []
}
```

---

## Ambiente de Teste

* **Java:** 17
* **Spring Boot:** 2.6.7
* **MySQL Connector:** 8.0.33
* **H2 Database:** 2.2.224
* **Lombok:** 1.18.28
* **Maven:** 3.9.3
* **IDE sugerida:** VS Code

---

**⚠️ Observação:** Não esqueça de configurar seu banco de dados no `application.properties` antes de rodar a aplicação!
