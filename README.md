# Agendador de Pagamentos
#### por Bruno Merez

### Instruções de build

1. Garanta que você tenha instalado o Java 11 na sua máquina, ou mais especificamente `openjdk 11.0.22 2024-01-16`
2. Garanta que você tem instalado o NodeJs e o NPM
3. Clone este repositório para uma pasta, que deve então conter duas pastas dentro dele: `frontend` e `backend`
4. Entre na pasta `frontend` e rode o comando `npm run build` (Isso vai gerar a build do frontend e colocar na pasta `/resources/static/` do backend, de qual será servida a aplicação.
5. Feito isso, entre na pasta `backend` e gere a build pelo utilitário `mvnw` (ou `mvn` se tiver Maven instalado na sua máquina) rodando o comando `mvnw clean package`
6. Isso irá criar um arquivo `paymentschedule-0.0.1-SNAPSHOT.jar` na pasta `target` do `backend`, e a partir disso você pode rodar o comando `java -jar paymentschedule-0.0.1-SNAPSHOT.jar` para rodar a aplicação na porta `8080`
7. Acesse em seu navegador a url `http://localhost:8080` e a aplicação deve estar disponível para interação.

### Outras Dicas

1. Você pode testar a criação de agendamentos de transferência entrando no mesmo link por uma janela anônima do navegador, aí pode se cadastrar como normal e sua outra conta deve aparecer nos contatos na hora do agendamento (se não, tente recarregar a página com F5)

---

3. #### A arquitetura do Backend

    Busquei me fazer valer de princípios do _Domain Driven Design_ (DDD) para este projeto, baseado no excelente livro _Domain Driven Design: Tackling complexity at the heart of software_, de Eric Evans.
    
    A ideia é que cada entidade de domínio (Payment, Account, ScheduledPayment, TaxBracket) seja capaz de validar a si mesma quanto aos seus dados.
	
	Mais do que isso, busquei ao máximo colocar a lógica de negócio para dentro dessas entidades, pensando em camadas:
    - Camada de domínio:
      - Entidades, regras de negócio, validacão para que cada entidade tenha seu estado válido.
    - Camada de servico:
      - Para fazer o "meio de campo" entre as entidades de domínio e a camada de persistência em banco de dados
    - Camada de persistência:
        - Lida diretamente com a persistência em banco.
    - Camada de adapter:
      - Para fazer interface entre o que é recebido (por API REST, por exemplo) com a camada de servico.

    O importante é que a lógica de negócio em si esteja circunscrita o máximo possível nas entidades de domínio e nos servicos de domínio

    Então, por exemplo, a classe ScheduledPayment recebe outra entidade Payment, junto com as datas de criacao e pagamento e um servico de taxa

    Neste sentido, a entidade ScheduledPayment deve apenas se preocupar em operar o que deve (ter certeza de que a data agendada é válida)

    A validacão para o pagamento em si (Payment) reside na própria entidade, que vai verificar se o valor é válido, se existe saldo, se não estamos tentando transferir de uma conta pra ela mesma, etc.

    A camada de servico recebe como injecao (aplicando o princípio de inversão de dependência) os repositórios que em si vão lidar com a persistência no banco, criando desacoplamento.

    Aqui devo notar que numa concepcão mais estrita de DDD não se utilizaria as anotacões do Hibernate nas classes de entidade de domínio, uma vez que isso é confundir modelagem de banco (Hibernate) com o domínio puro, e não necessariamente uma entidade de domínio deve ser uma tabela no banco de dados.

    Mesmo assim, optei por usar o Hibernate e o @Entity nas classes por questão de simplicidade como meio-termo, e é assim que geralmente se usa, na minha experiência.

    Sobre as regras de taxa, optei por não implementar direto no código, mas sim fazer isso ser representado no banco de dados.
	
	Isso possibilitou que o frontend busque no backend as regras de taxa, que serão pegas do banco de dados, assim facilitando para que haja alteração sem ter que subir uma build nova do backend.

	Acabei não implementando o pagamento em si (no débito da conta no horário marcado) por falta de tempo hábil, mas facilmente poderia ser feito adicionando uma task regular pra rodar de 1 em 1 hora, ou 10 em 10 minutos, no serviço de PaymentScheduler. Isso iria necessitar alterar como se criam os agendamentos para apenas permitir no máximo intervalos de 10 minutos, não tem como uma task ficar rodando de 1 em 1 minuto pra saber se algum agendamento foi feito naquele minuto específico, mas sim fazer isso a cada 1h ou 10min.

Em retrospecto, um problema de ordem de negócio seria que a taxa e o valor só é debitado na hora da transferência (teoricamente, pois isso não está implementado), e fico pensando se a taxa não deveria ser cobrada no ato da transferência. Isso já é do domínio de negócio e há um argumento válido para ambas.

De modo geral, fiquei feliz com o resultado.

2. #### A Arquitetura do Frontend

	O frontend foi feito em Vue3 (conforme as especificações), usando tailwindcss para estilização e algumas outras libs para DatePicker e Ícones

	O sistema de "autenticação" foi feito em forma de mock, e todos os dados da aplicação são salvos em localstorage ao invés de gerenciamento de estado de forma mais complexa. Resolvi focar em entregar a funcionalidade ao invés de tentar deixar tudo funcionando da melhor maneira como gostaria.

	Escolhi usar poucas cores, mas que sejam complementares (roxo e amarelo) e uma estética limpa.
	
	Inicialmente gostaria de fazer também uma tela de "admin" para gerenciar as taxas do back ou para ter um resumo de quanto se rendeu em taxas entre todas as transações, mas acabou não acontecendo por conta do tempo.



