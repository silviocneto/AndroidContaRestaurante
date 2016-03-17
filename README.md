# AndroidContaRestaurante

  Sistema cloud , mobile (Android) usando SQL para registro de pedidos e integrantes em um restaurante

  O objetivo do aplicativo é agilizar o cálculo das contas individuais dos clientes no restaurante.
  
  - No menu principal no inferior da tela existem as seguintes opções: Cardápio, Adicionar integrante, Adicionar Pedido e Conta
  - Cardápio: O usuário irá inserir itens a serem consumidos e seu preços (Ex: Chopp, R$ 5,90) ou deletar todos os itens já listados.
  - Integrantes: O usuário adiciona integrantes da mesa inserindo apenas o nome ou deleta todos os integrantes já listados;
  - Pedidos: O usuário insere um pedido informando o item (a partir do menu cardápio - slide menu), a quantidade e quem irá consumir o item. Além disso, é possível consultar os pedidos já feitos através do "Ver Pedidos";
  - Conta: O aplicativo apresenta quanto cada integrante deve pagar até aquele instante;

  A cada pedido feito, o aplicativo divide o valor total do pedido para os integrantes que irão consumir e salva na base de dados, que é consultada toda vez que se clica em "CONTA".
  
  MODELO DE DADOS:
  
  [!DATA] (https://cloud.githubusercontent.com/assets/16818936/13861528/96e7ce40-ec6c-11e5-9b3b-cac8987545b7.png)
  
  
