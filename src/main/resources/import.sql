INSERT INTO professor (nome, email, re, senha, coordenador, primeiro_acesso, ativo) VALUES ('Alecio Aparecido', 'teste@teste.com', '123456', '$2a$10$PGbBlRRc2YfnzSOXvh6PKe5GHzlJkY6UP2.LrPrv8suAmFfYykxdq', true, true, true);
INSERT INTO professor (nome, email, re, senha, coordenador, primeiro_acesso, ativo) VALUES ('Teste Usuário Inativo', 'usuarioinativo@teste.com', '123456', '$2a$10$PGbBlRRc2YfnzSOXvh6PKe5GHzlJkY6UP2.LrPrv8suAmFfYykxdq', true, false, true);


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

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Sequência de passos para resolver um problema', true, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável que armazena apenas números', false, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um comando de repetição', false, 1);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador matemático', false, 1);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar valores temporários durante a execução do programa', true, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar instruções repetidamente', false, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Comparar dados lógicos', false, 2);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Retornar um valor ao final do programa', false, 2);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Estrutura que repete instruções enquanto uma condição for verdadeira', true, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executa uma única instrução', false, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazena valores', false, 3);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Realiza operações matemáticas', false, 3);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Permitir comparações lógicas entre valores', true, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar números', false, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Repetir comandos', false, 4);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Definir funções', false, 4);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Conjunto de instruções que realizam uma tarefa específica', true, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um valor fixo', false, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável temporária', false, 5);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma condição lógica', false, 5);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma estrutura que armazena múltiplos valores do mesmo tipo', true, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável que armazena apenas um valor', false, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador lógico', false, 6);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma função que retorna valores', false, 6);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar blocos de código apenas se determinada condição for verdadeira', true, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Repetir blocos de código várias vezes', false, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Definir variáveis temporárias', false, 7);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criar funções matemáticas', false, 7);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Modelo que define atributos e comportamentos de objetos', true, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma variável global', false, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Um operador lógico', false, 8);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Uma estrutura de repetição', false, 8);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Retornar um valor do interior de uma função para quem a chamou', true, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Iniciar um loop', false, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Armazenar valores em variáveis', false, 9);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Comparar valores lógicos', false, 9);

INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Processo de identificar e corrigir erros no código', true, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Criar funções matemáticas', false, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Declarar variáveis', false, 10);
INSERT INTO alternativa (texto, correto, questao_id) VALUES ('Executar loops infinitos', false, 10);
