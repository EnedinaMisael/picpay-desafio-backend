## Sistema de Pagamento Simplificado : PicPay

Sistema de transferências financeiras desenvolvido em Spring Boot que permite transações entre usuários comuns 
e lojistas, com validação externa e sistema de notificações.

## Funcionalidades

- Cadastro de Usuários: Usuários comuns e lojistas
- Transferências Seguras: Validação de saldo e autorização externa
- Sistema Transacional: Rollback automático em caso de falhas
- Notificações: Envio de notificações via serviços externos
- Validações de Negócio: CPF/CNPJ únicos, regras de transferência

## Tecnologias Utilizadas

Java
Spring Boot
Spring Data JPA
Spring Web
H2 Database 
RestTemplate (integração com APIs externas)
Maven

## Regras de Negócio
Validações de Usuário

- CPF/CNPJ devem ser únicos
- Email deve ser único
- Todos os campos são obrigatórios

## Validações de Transação

- Usuários comuns podem enviar e receber
- Lojistas apenas recebem transferências
- Saldo deve ser suficiente para a transferência
- Não é possível transferir para si mesmo
- Autorização externa obrigatória
- Sistema transacional com rollback

## Integrações Externas
Serviço de Autorização

URL: https://util.devi.tools/api/v2/authorize
Método: GET
Função: Valida se a transação pode ser realizada

Serviço de Notificação

URL: https://util.devi.tools/api/v1/notify
Método: POST
Função: Envia notificações aos usuários
Comportamento: Pode estar indisponível (simulação de instabilidade)