# Api-Transfer

📖 API de Transferência Bancária Resiliente

#📌 Visão Geral do Projeto
  Este projeto implementa uma API de Transferência Bancária construída em Spring Boot 3 e Maven, focada em atender a requisitos de alta disponibilidade, baixa latência (< 100ms) e alta vazão (6.000 TPS).
  A aplicação simula operações de consulta de saldo e transferência entre contas, utilizando políticas de Resiliência (Circuit Breaker e Retry) para garantir a estabilidade da integração com serviços externos (BACEN/Cadastro).

#💡 Stack Tecnológica

  Java 17 Linguagem principal do projeto.
  Framework Spring Boot 3 para construção de Microserviço REST.
  Build Maven Gerenciamento de dependências e construção.
  ResiliênciaResilience4J Implementação de Circuit Breaker e Retry.
  Comunicação Feign Client Integração declarativa com serviços externos (BACEN, Cadastro).
  Padrões DTOs, SOLID, Injeção por Construtor.

#▶️ Como Executar o Projeto Localmente
    
  Você pode rodar a API usando o Maven e a porta configurada no seu application.yml.
  Pré-requisitos
  Java 17
  Maven

#Passos de Execução

  Clone o Repositório: git clone https://github.com/FellipeAndre/api-tranfer.git
  cd api-tranfer

  Compile o Projeto: mvn clean install

  Execute a Aplicação Spring Boot: mvn spring-boot:run

#🧪 Endpoints e Testes
  A API possui dois endpoints principais:
   1. Consultar Saldo
   Método: GET
   Endpoint: /api-transfer/consulta-saldo/{cpf}
   2. Realizar Transferência
   Método: POST
   Endpoint: /api-transfer/transferir/{cpf}
   Corpo da Requisição (JSON):
      {
        "saida": 450.50,
        "numeroConta": "98765-4",
        "nome": "João da Silva"
      }
  

#🏗️ Arquitetura de Solução na AWS (Design Cloud)

  A solução é desenhada para ser serverless (usando Fargate e Lambda) e orientada a eventos, garantindo que o requisito de 6.000 transações por segundo com latência abaixo de 100ms seja atingido.

  Amazon ECS (Fargate):	Plataforma serverless para rodar contêineres Docker da API. Fornece escalabilidade horizontal automática (Auto Scaling), lidando com oscilações de carga.	Escalabilidade, Alta Disponibilidade.

  Amazon Aurora PostgreSQL: Banco de Dados relacional ACID para garantir a integridade e segurança das transações. Escolhido por sua alta performance e escalabilidade na AWS.	Consistência, Performance.

  AWS API Gateway & ALB:	Atuam como ponto de entrada e balanceador de carga, distribuindo o tráfego para o cluster ECS.	Balanceamento, Segurança.

#🛡️ Estratégia de Resiliência e Throttling (BACEN)

  Falha de Dependência (Geral)	Circuit Breaker	O serviço de integração usa Resilience4J para Fallback imediato, retornando uma resposta de indisponibilidade simulada (código 429) ao cliente.

  Throttling (429 BACEN)	Retry + Assincronismo	Após as tentativas do @Retry falharem, a notificação é enviada para uma fila Amazon SQS. Uma AWS Lambda consome essa fila, garantindo que a notificação seja reprocessada até o sucesso (Event-Driven).

  Observabilidade	AWS X-Ray + CloudWatch	X-Ray para tracing distribuído (diagnóstico de latência) e CloudWatch para agregação de logs (logs, métricas do Circuit Breaker e alarmes).

  
