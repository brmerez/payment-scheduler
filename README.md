
# Agendador de Pagamentos
#### por Bruno Merez


1. A arquitetura

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

    Sobre as regras de taxa, optei por não implementar direto no código, mas sim fazer isso ser representado no banco de dados. Isso torna mais fácil que haja alteracão depois, mediante criar um servico ou endpoint para criar, alterar ou remover a politica de cobranca.
    