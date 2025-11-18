INSERT INTO professor (nome, email, re, senha, coordenador, primeiro_acesso, ativo) VALUES ('Alecio Aparecido', 'teste@teste.com', '123456', '$2a$10$PGbBlRRc2YfnzSOXvh6PKe5GHzlJkY6UP2.LrPrv8suAmFfYykxdq', true, true, true);
INSERT INTO professor (nome, email, re, senha, coordenador, primeiro_acesso, ativo) VALUES ('Teste Usuário Inativo', 'usuarioinativo@teste.com', '123456', '$2a$10$PGbBlRRc2YfnzSOXvh6PKe5GHzlJkY6UP2.LrPrv8suAmFfYykxdq', false, true, false);

INSERT INTO curso (nome) VALUES ('Análise e Desenvolvimento de Sistemas');
INSERT INTO curso (nome) VALUES ('Logística');
INSERT INTO curso (nome) VALUES ('Gestão da Produção Industrial');
INSERT INTO curso (nome) VALUES ('Comércio Exterior');

INSERT INTO disciplina (nome, curso_id) VALUES ('Algoritmos e Lógica de Programação', 1);
INSERT INTO disciplina (nome, curso_id) VALUES ('Banco de Dados', 1);
INSERT INTO disciplina (nome, curso_id) VALUES ('Engenharia de Software', 1);

INSERT INTO disciplina (nome, curso_id) VALUES ('Gestão de Estoques', 2);
INSERT INTO disciplina (nome, curso_id) VALUES ('Transporte e Distribuição', 2);
INSERT INTO disciplina (nome, curso_id) VALUES ('Logística Reversa', 2);

INSERT INTO professor_curso (curso_id, professor_id) VALUES (1, 1);
INSERT INTO professor_disciplina (disciplina_id, professor_id) VALUES (1, 1);

INSERT INTO professor_curso (curso_id, professor_id) VALUES (1, 2);
INSERT INTO professor_disciplina (disciplina_id, professor_id) VALUES (1, 2);
INSERT INTO professor_curso (curso_id, professor_id) VALUES (2, 2);
INSERT INTO professor_disciplina (disciplina_id, professor_id) VALUES (4, 2);



INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é um algoritmo?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Qual a função de uma variável?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é um laço de repetição?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Qual a função de um operador lógico?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é uma função em programação?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é um array?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Para que serve uma estrutura condicional if?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é uma classe em programação orientada a objetos?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Qual a função do comando return em uma função?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que significa depuração (debugging)?', 1, 1, 1, false);
INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Questao de logistica', 2, 2, 4, true);


INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Sequência de passos para resolver um problema', true, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável que armazena apenas números', false, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um comando de repetição', false, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador matemático', false, 1);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar instruções repetidamente', false, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Comparar dados lógicos', false, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar valores temporários durante a execução do programa', true, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Retornar um valor ao final do programa', false, 2);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executa uma única instrução', false, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Estrutura que repete instruções enquanto uma condição for verdadeira', true, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazena valores', false, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Realiza operações matemáticas', false, 3);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar números', false, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Repetir comandos', false, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Definir funções', false, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Permitir comparações lógicas entre valores', true, 4);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um valor fixo', false, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável temporária', false, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma condição lógica', false, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Conjunto de instruções que realizam uma tarefa específica', true, 5);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável que armazena apenas um valor', false, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma estrutura que armazena múltiplos valores do mesmo tipo', true, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador lógico', false, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma função que retorna valores', false, 6);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar blocos de código apenas se determinada condição for verdadeira', true, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Repetir blocos de código várias vezes', false, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Definir variáveis temporárias', false, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criar funções matemáticas', false, 7);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável global', false, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador lógico', false, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Modelo que define atributos e comportamentos de objetos', true, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma estrutura de repetição', false, 8);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Iniciar um loop', false, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar valores em variáveis', false, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Comparar valores lógicos', false, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Retornar um valor do interior de uma função para quem a chamou', true, 9);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de identificar e corrigir erros no código', true, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criar funções matemáticas', false, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Declarar variáveis', false, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar loops infinitos', false, 10);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('logistica', true, 11);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('logistica a', false, 11);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('logistica b', false, 11);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('logistica c', false, 11);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é um SGBD?', 1, 1, 2, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Modelo conceitual de dados', false, 12);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Sistema usado para gerenciar bancos de dados', true, 12);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Tipo de linguagem de programação', false, 12);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Ferramenta para versionamento de código', false, 12);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é uma chave primária?', 1, 1, 2, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Atributo que permite valores repetidos', false, 13);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Campo usado apenas para ordenação', false, 13);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Atributo que identifica unicamente um registro', true, 13);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um tipo de tabela auxiliar', false, 13);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é normalização de dados?', 1, 1, 2, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de organizar dados para reduzir redundância', true, 14);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de criptografar dados sensíveis', false, 14);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Backup periódico de tabelas', false, 14);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Método de indexação', false, 14);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é Engenharia de Software?', 1, 1, 3, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Ato de programar em linguagens de baixo nível', false, 15);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Disciplina que estuda métodos para desenvolvimento de software', true, 15);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de otimizar hardware', false, 15);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criação de redes e servidores', false, 15);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é um requisito funcional?', 1, 1, 3, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Restrição de desempenho', false, 16);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Característica estética da interface', false, 16);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Regra de arquitetura do servidor', false, 16);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Função ou comportamento que o sistema deve realizar', true, 16);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('No contexto de UML, o que é um diagrama de casos de uso?', 1, 1, 3, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Representação das interações do usuário com o sistema', true, 17);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Representação do fluxo de dados entre sistemas', false, 17);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Descrição do banco de dados lógico', false, 17);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Desenho da infraestrutura física', false, 17);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é estoque de segurança?', 1, 2, 4, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Estoque guardado para descarte', false, 18);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Produtos que já foram vendidos', false, 18);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Itens devolvidos pelo cliente', false, 18);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Quantidade extra para evitar rupturas', true, 18);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é giro de estoque?', 1, 2, 4, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Velocidade com que o estoque é renovado', true, 19);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Número de fornecedores ativos', false, 19);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Quantidade máxima armazenada', false, 19);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Tempo de transporte entre centros', false, 19);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é inventário físico?', 1, 2, 4, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Registro automático feito pelo sistema', false, 20);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de devolução de produtos', false, 20);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Contagem real dos itens armazenados', true, 20);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Auditoria financeira anual', false, 20);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é lead time de transporte?', 1, 2, 5, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Capacidade de carga máxima', false, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Tempo total entre pedido e entrega', true, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Custo total do combustível', false, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Velocidade média do motorista', false, 21);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é lead time de transporte?', 1, 2, 5, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Capacidade de carga máxima', false, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Custo total do combustível', false, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Tempo total entre pedido e entrega', true, 21);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Velocidade média do motorista', false, 21);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é roteirização?', 1, 2, 5, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Planejamento do melhor caminho para entregas', true, 22);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Organização de estoque no armazém', false, 22);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Seleção de fornecedores estratégicos', false, 22);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Cálculo de impostos de importação', false, 22);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Qual a função do modal ferroviário?', 1, 2, 5, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Entregas rápidas de última milha', false, 23);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Transporte exclusivo de perecíveis', false, 23);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Serviço de transporte urbano', false, 23);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Transporte eficiente de grandes volumes a longas distâncias', true, 23);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que é logística reversa?', 1, 2, 6, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Distribuição de produtos para varejistas', false, 24);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Otimização de rotas de entrega', false, 24);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de retorno de produtos após o consumo', true, 24);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Controle de estoque de produtos novos', false, 24);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('Qual é o principal objetivo da logística reversa?', 1, 2, 6, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Aumentar a velocidade de entregas', false, 25);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Reaproveitar, reparar ou reciclar produtos retornados', true, 25);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Reduzir custos de transporte externo', false, 25);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criar novos pontos de venda', false, 25);

INSERT INTO questao (enunciado, autor_id, curso_id, disciplina_id, simulado) VALUES ('O que caracteriza um produto retornável?', 1, 2, 6, false);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Produto descartado após o uso', false, 26);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Produto proibido por lei', false, 26);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Produto que pode voltar ao fabricante para novo uso', true, 26);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Produto sem possibilidade de reciclagem', false, 26);