# ContaRestaurante

  Sistema mobile (Android) usando SQL para registro de pedidos e integrantes em um restaurante

  O objetivo do aplicativo é agilizar o cálculo das contas individuais dos clientes no restaurante.
  
  - No menu principal no inferior da tela existem as seguintes opções: Cardápio, Integrantes, Pedidos e Conta
  - Cardápio: O usuário irá inserir itens a serem consumidos e seu preços (Ex: Chopp, R$5,90)
  - Integrantes: O usuário irá inserir os nomes dos integrantes da mesa
  - Pedido: O usuário insere um pedido informando o item (a partir do menu cardápio - slide menu) e quem irá consumir o item. No inferior da tela é informada a quantidade do item pedido. Se a opção DIVIDIR for selecionada, um item será divido por todos os integrantes selecionados;
  - Ver Pedidos: É possível consultar os pedidos já feitos caso o usuário se esqueça.
  - Conta: O aplicativo apresenta quanto cada integrante deve pagar até aquele instante. Além disso, existe a possibilidade de inserir os 10% de serviço da casa no pagamento;

  A cada pedido feito, o aplicativo divide o valor total do pedido para os integrantes da mesa e salva na CONTA, que é uma tabela com o nome de cada integrante e o valor que cada um tem que pagar.
  
  Modelo de dados:
  
  
  
  
