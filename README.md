🚗 Estacionamento Sebrae

Sistema web simples para controle de entradas e saídas de veículos em um estacionamento com 30 vagas. Desenvolvido em Java com Spring Boot para o projeto SEBRAE.

🌐 Acesse online: https://estacionamentosebrae.onrender.com

A aplicação está hospedada no Render. Se ficar um tempo sem acessos, a primeira abertura pode levar alguns segundos enquanto o servidor "acorda".

Funcionalidades
Login e cadastro de usuários (e-mail e senha).
Painel: botões de Registrar Entrada e Registrar Saída, tabela com os veículos atualmente estacionados e contadores de vagas (ocupadas, disponíveis e total).
Registrar Entrada: formulário com placa, modelo, cor e observações (opcional).
Registrar Saída: lista dos veículos estacionados, cada um com o botão Confirmar Saída (com confirmação antes de concluir). Ao finalizar, exibe um resumo com horário de saída e tempo de permanência.
Movimentações: histórico de todas as entradas e saídas, com tempo de permanência, total de movimentações e tempo médio.
Relatórios: contadores de vagas, resumo das movimentações e barra de ocupação.
Regras de negócio
O estacionamento possui 30 vagas (VeiculoService.TOTAL_VAGAS).
Não é possível registrar entrada quando as vagas estão esgotadas.
Não é possível registrar entrada de uma placa que já está estacionada. A placa é salva em letras maiúsculas.
A saída não apaga o veículo: ele recebe estacionado = false e a horaSaida, mantendo o histórico.
Registros antigos com estacionado nulo são tratados como "ainda estacionado".
O tempo médio de permanência considera apenas veículos que já saíram.
Tecnologias
Camada	Tecnologia
Linguagem	Java 25
Framework	Spring Boot 4.1.0 (Web MVC, Data JPA)
Visual	Thymeleaf + CSS próprio (static/css/style.css)
Banco de dados	H2 em arquivo (./database/appdb)
Build	Maven (Maven Wrapper incluso)
Usando a versão online
Abra https://estacionamentosebrae.onrender.com.
Clique em Cadastre-se e crie um usuário.
Faça login e use o Painel para registrar entradas e saídas.
Como executar localmente

Pré-requisito: JDK 25 instalado.

bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run

Depois acesse: http://localhost:8080

Clique em Cadastre-se e crie um usuário.
Faça login com o e-mail e a senha cadastrados.
Você será levado ao Painel.
Console do banco (H2)

Disponível em http://localhost:8080/h2-console com:

JDBC URL: jdbc:h2:file:./database/appdb
Usuário: sa
Senha: (vazia)
Rotas
Método	Rota	Descrição
GET	/	Tela de login
POST	/login	Efetua o login
GET	/cadastrar	Tela de cadastro de usuário
POST	/efetuar-cadastro	Salva o novo usuário
GET	/painel	Painel com veículos estacionados e vagas
GET	/movimentacoes	Histórico de movimentações
GET	/relatorios	Relatório de ocupação
GET	/veiculo/registarEntrada	Formulário de entrada de veículo
POST	/veiculo/cadastro	Salva a entrada do veículo
GET	/veiculo/registrarSaida	Lista de veículos para registrar saída
POST	/veiculo/registrarSaida	Confirma a saída (parâmetro id)
Estrutura do projeto
src/main/java/br/gov/sp/etec/Estacionamento
├── controller/   LoginController, VeiculoController
├── entity/       UsuarioEntity, VeiculoEntity (tabelas tb_usuario e tb_veiculo)
├── model/        Usuario, Veiculo (objetos recebidos dos formulários)
├── repository/   UsuarioRepository, VeiculoRepository
└── service/      UsuarioService(IMPL), VeiculoService(IMPL)

src/main/resources
├── static/css/style.css
├── templates/    login, cadastro, painel, movimentacoes, relatorios,
│                 registrarEntrada, registrarSaida, saidaRegistrada, erro, ...
└── application.properties
Modelo de dados

tb_veiculo

Campo	Tipo	Observação
id	Long	Chave primária, gerada automaticamente
placa	String	
modelo	String	
cor	String	
observacoes	String	Opcional
horaEntrada	LocalDateTime	Definida ao registrar a entrada
horaSaida	LocalDateTime	Preenchida ao registrar a saída
estacionado	Boolean	true enquanto o veículo está no estacionamento

tb_usuario: id, nome, email, senha, telefone, cpf, dataNascimento.

Limitações conhecidas
As rotas não são protegidas por sessão: após o login o usuário é redirecionado, mas as páginas podem ser acessadas diretamente pela URL.
As senhas são salvas em texto puro no banco.
Os horários usam o fuso do servidor onde a aplicação está rodando (no Render, normalmente UTC, o que pode diferir do horário de Brasília).
O banco H2 grava em arquivo local (./database). Em hospedagens sem disco persistente, os dados podem ser apagados a cada novo deploy ou reinício do servidor.
O console do H2 (/h2-console) está habilitado em application.properties. Em produção, o ideal é desativar com spring.h2.console.enabled=false.

Evoluções naturais: Spring Security (sessão e senha com hash), um banco externo (MySQL ou PostgreSQL) para a versão online e ajuste do fuso horário (America/Sao_Paulo).
