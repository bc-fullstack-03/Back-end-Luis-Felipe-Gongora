# Projeto BackEnd Rede Social BootCamp SysMap

Este é um projeto de rede social simples que permite aos usuários criar postagens, seguir outros usuários e visualizar as postagens de seus seguidores. Além disso, é possível deixar comentários nas postagens e dar likes tanto nas postagens quanto nos comentários. E se você quiser, também é possível alterar seus dados de usuário, suas postagens e até mesmo os comentários feitos nas postagens.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.0.6
- MongoDB
- JSON Web Token
- Docker

## Como executar
Clone o repositório usando o seguinte comando no terminal:
```bash
git clone https://github.com/bc-fullstack-03/Luis-Felipe-Gongora-Backend.git
```
Navegue até a pasta clonada:
```bash
cd Luis-Felipe-Gongora-Backend
```
Execute o comando para iniciar os containers com Docker:
```bash
docker compose up -d
```
Quando os containers estiverem rodando no Docker, acesse a aplicação no seguinte endereço:
```bash
http://localhost:8082/swagger-ui/index.html
```
No Swagger, você poderá testar e visualizar todos os endpoints da aplicação.
Lembre-se de que, para executar o LocalStack, é necessário seguir alguns comandos no terminal para configurá-lo:

Para o funcionamento de upload de imagens com o localstack:  
Execute o seguinte comando para entrar no container do LocalStack:
```bash
docker exec -it localstack bash
```
Dentro do container, configure o AWS CLI:
```bash
aws configure --profile default
```
Digite as seguintes informações quando solicitado:
```bash
mykey
mykey
us-west-2
json
```
Em seguida, crie um bucket no LocalStack:
```bash
aws s3 mb s3://demo-bucket --endpoint-url http://localhost:4566
```
Depois disso, o upload de imagens nas endpoints específicas deverá funcionar corretamente.

## Autores
- Luis Felipe Gongora Garcia

## Contato
### Email
- Email: lfelipeggarcia@gmail.com

### Redes Sociais
- LinkedIn: https://www.linkedin.com/in/luis-felipe-gongora-garcia/
