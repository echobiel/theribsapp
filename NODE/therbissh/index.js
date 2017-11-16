
var app = require('express')(),
	http = require('http').createServer(app),
	io = require('socket.io').listen(http),
	lstSalas = [],
	lstPedidos = [],
	lstProdutos = [],
	mysql = require('mysql'),
	con = mysql.createConnection({
	  host: "10.107.144.13",
	  //host: "localhost",
	  //host: "10.107.134.26",
	  //host: "10.107.134.15",
	  user: "root",
	  password: "bcd127",
	  //password: "",
	  database: "dbtheribssh"
	});

con.connect();

app.set('view engine', 'ejs');

app.get('/', function(req, res){

	//res.sendFile( __dirname + "/index.html");
	//res.send(lstAgendas);
	res.render("index", {app_name : lstPedidos});

});

app.get('/selectRestaurante', function(req, res){

  var command = "select r.id_restaurante, r.nome, r.imagem, r.descricao, concat(coalesce(c.nome, ''), ', ' , "+
	"coalesce(e.bairro, '') , ', ' , coalesce(e.rua, '') , ', ' , coalesce(e.numero, '') , ' ' , "+
	"coalesce(e.aptbloco, '')) as 'endereco' from tbl_restaurante as r "+
	"inner join tbl_endereco as e "+
	"on r.id_endereco = e.id_endereco "+
	"inner join tbl_cidade as c "+
	"on c.id_cidade = e.id_cidade "+
	"where r.id_restaurante = 2 "+
	"order by r.id_restaurante asc";

  con.query(command, function (err, result, fields) {
    if (err) throw err;
    res.send(result);
  });


});

app.get('/selectCardapio', function(req, res){

  var command = "select id_produto, nome as 'nome_produto', descricao as 'desc_produto', imagem as 'foto_produto', concat('R$ ', format(preco,2,'de_DE')) as 'preco_produto' from tbl_produto where statusAprovacao = 1 order by nome_produto asc;";

  con.query(command, function (err, result, fields) {
    if (err) throw err;
    res.send(result);
  });


});

app.get('/selectHistoricoPedidos', function(req, res){

  var command = "select p.id_pedido as 'idpedido', r.nome as 'restaurante', p.data as 'data' from tbl_pedido as p " +
                  "inner join tbl_funcionario as f on f.id_funcionario = p.id_funcionario INNER JOIN tbl_restaurante as r on f.id_restaurante = r.id_restaurante";

  con.query(command, function (err, result, fields) {
    if (err) throw err + command;

      var result2;

      for (var i = 0; i < result.length; i++) {

          var id_pedido = result[i].idpedido;

          var command2 = "select count(*) from tbl_pedidoproduto where id_pedido = " + id_pedido;

          con.query(command2, function (err2, result2, fields2) {
              if (err2) throw err2;

              result2 = result2 + result2;
          });
      }

      res.send(result + result2);

  });
});

app.get('/disconnect', function(req, res){

	res.send(disconnect,1);

});

app.get('/inserir', function(req, res){

	var _id = lstPedidos.length,
		_endereco = req.query.endereco,
		_titulo = req.query.titulo,
		_descricao = req.query.descricao,
		_telefone = req.query.telefone,
		_foto = req.query.caminhoFoto,
		p = { id_restaurante : _id, endereco_restaurante : _endereco , nome_restaurante : _titulo , desc_restaurante: _descricao, telefone_restaurante: _telefone, foto_restaurante: _foto};

	lstPedidos.push(p);

	res.send({ mensagem : "Inserido com sucesso"});
	io.sockets.emit("novo_usuario", p);

});

app.get('/autenticarUsuario', function(req, res){

	var _email = req.query.email,
		_senha = req.query.senha,
    resultadoFinal = {};

  var contador = 0;
  var command = "select * from tbl_cliente where email = '" + _email + "' or login='" + _email + "' and senha = '" + _senha + "'";

  con.query(command, function(err, result, fields) {
      if (err) throw err + command;

      // Verificação das linhas no select
      if (contador < result.length) {

          var foto = result[contador].foto;
          var id = result[contador].id_cliente;
          var nome = result[contador].nome;

          var permissao = 1;

          resultadoFinal = {id_usuario : id, permissao : permissao, foto : foto, nome : nome, mensagem : 'Login efetuado com sucesso.'};

          contador = contador + 1;

      }else{
          var contador2 = 0;
          var command2 = "select * from tbl_funcionario where email = '" + _email + "' or login = '" + _email + "' and senha = '" + _senha + "'";

          con.query(command2, function(err, result, fields) {
              if (err) throw err + command2;

              // Verificação das linhas no select
              if (contador2 < result.length) {

                  var foto = result[contador2].foto;
                  var id = result[contador2].id_funcionario;
                  var nome = result[contador2].nome_completo;

                  var permissao = 2;

                  resultadoFinal = {id_usuario : id, permissao : permissao, foto : foto, nome : nome, mensagem : 'Login efetuado com sucesso.'};

                  contador2 = contador2 + 1;

              }else{
                  resultadoFinal = {permissao : 0, mensagem : 'Usuário ou senha incorretos. Verifique e tente novamente.'};
              }

              res.send(resultadoFinal);
          });
      }

      if (contador != 0){
          res.send(resultadoFinal);
      }

  });
});

app.get('/autenticarUsuarioSala', function(req, res){

	var _email = req.query.email,
		_senha = req.query.senha,
		_id_pedido = req.query.id_pedido,
    resultadoFinal = {};

  var contador = 0;
  var command = "select * from tbl_cliente where email = '" + _email + "' or login='" + _email + "' and senha = '" + _senha + "'";

  con.query(command, function(err, result, fields) {
      if (err) throw err + command;

      // Verificação das linhas no select
      if (contador < result.length) {

          var id = result[contador].id_cliente;
          var nome = result[contador].nome;

					lstSalas[_id_pedido].id_cliente = id;
					lstSalas[_id_pedido].nome_cliente = nome;

          resultadoFinal = {id_usuario : id, nome : nome, mensagem : 'Login efetuado com sucesso.'};

          contador = contador + 1;

      }

      if (contador != 0){
          res.send(resultadoFinal);
      }else{
				res.send({mensagem : "Usuário ou senha incorretos. Verifique e tente novamente."});
			}

  });
});

app.get('/autenticarUsuarioSalaCadastro', function(req, res){

	var _email = req.query.email,
		_nome = req.query.nome,
		_sobrenome = req.query.sobrenome,
		_id_pedido = req.query.id_pedido,
    resultadoFinal = {};

	if (typeof _nome != 'undefined' && typeof _sobrenome != 'undefined' && typeof _email != 'undefined'){

	  var command = "insert into tbl_cliente(email,nome,sobrenome) values('" + _email + "', '" + _nome + "', '" + _sobrenome + "')";

	  con.query(command, function(err, result, fields) {
      if (err) throw err + command;

			var command2 = "select * from tbl_cliente order by id_cliente desc limit 0,1";

			con.query(command2, function(err2, result2, fields2){

        var id = result2[0].id_cliente;
        var nome = result2[0].nome;

				console.log(lstSalas[_id_pedido]);

				lstSalas[_id_pedido].id_cliente = id;
				lstSalas[_id_pedido].nome_cliente = nome;

        resultadoFinal = {id_usuario : id, nome : nome, mensagem : 'Login efetuado com sucesso.'};

				res.send(resultadoFinal);

			});
	  });
	}else{
		res.send({mensagem : "Dados incorretos. Verifique e tente novamente."});
	}
});

app.get('/infSala', function(req, res){
	var _id_sala = req.query.id_sala;

	res.send(lstSalas[_id_sala]);
});

app.get('/listarDetalhesPedido', function(req, res){
	var _id_sala = req.query.id_sala,
			contador = 0,
			verificador = 0;

	while(contador < lstSalas.length){

		if(lstSalas[contador].id_sala == _id_sala){

			res.send(lstSalas[contador]);

			verificador = 1;
			break;
		}

		contador = contador + 1;
	}

	if(verificador == 0){
		res.send({mensagem : "Este pedido não foi encontrado."});
	}

});

app.get('/autenticarSala', function(req, res){
	var _qr_code = req.query.qr;
	var _id_cliente = req.query.id_cliente;

	var contador = 0,
			verificador = 0,
			_id_funcionario = 0,
			_id_sala = 0,
			_nome = "";

	while (contador < lstSalas.length){

		if(lstSalas[contador].qr_code == _qr_code){
			verificador = 1;
			_id_funcionario = lstSalas[contador].id_funcionario;
			_id_sala = lstSalas[contador].id_sala;
			lstSalas[contador].id_cliente = _id_cliente;
			lstSalas[contador].nome_cliente = _nome;

			var command = "select nome from tbl_cliente where id_cliente = '" + _id_cliente + "'";

			con.query(command, function(err, result, fields){
				if (err) throw err + command;

				_nome = result[0].nome;

				lstSalas[contador].nome_cliente = _nome;

				res.send({ mensagem : "Autenticado com sucesso.", id_sala : _id_sala });
			});

			break;

		}

		contador = contador + 1;
	}

	if (verificador != 1){
		res.send({ mensagem : "Código QR inválido."});
	}else{

		io.sockets.emit("novo_pedido_autenticado", {id_funcionario : _id_funcionario, id_sala : _id_sala, id_cliente : _id_cliente});
	}
});

app.get('/mesaSala', function(req, res){
	var _id_mesa = req.query.id_mesa;

	var contador = 0, verificador = 0;

	while (contador < lstSalas.length){

		if(lstSalas[contador].qr_code == _qr_code){
			verificador = 1;
			res.send({ mensagem : "Autenticado com sucesso.", id_sala : lstSalas[contador].id_sala });
		}

		contador = contador + 1;
	}

	if (verificador != 1){
		res.send({ mensagem : "Código QR inválido."});
	}
});

app.get('/criacaoSala', function(req, res){

	var _id_sala = lstSalas.length,
      _id_funcionario = req.query.id_funcionario,
      _qr_code = "",
			_tamanho = 0;

	var command = "select e.uf as 'uf', r.id_restaurante as 'id_rest' from tbl_funcionario as f "+
									"inner join tbl_restaurante as r "+
									"on f.id_restaurante = r.id_restaurante "+
									"inner join tbl_endereco as en "+
									"on r.id_endereco = en.id_endereco "+
									"inner join tbl_cidade as c "+
									"on c.id_cidade = en.id_cidade "+
									"inner join tbl_estado as e "+
									"on e.id_estado = c.id_estado "+
									"where f.id_funcionario = '" + _id_funcionario + "'";

	con.query(command, function(err, result, fields){
		if (err) throw err + command;

		if (result.length > 0){

			var contador = 0;

			//Número utilizado para o qrcode
			var numero = 0;
			//Verificador de local para o armazenamento no qrcode
			while (contador < _id_sala){
				if (typeof lstSalas[contador].qr_code != 'undefined'){
					var qr = lstSalas[contador].qr_code.slice(0,2);

					if (qr == result[0].uf){
						numero = numero + 1;
					}
				}
				contador = contador + 1;
			};
			//Formata o número de acordo com um padrão
			var formattedNumber = ("00000" + numero).slice(-5);

			_qr_code = result[0].uf;
			_qr_code = _qr_code + formattedNumber;

			var _id_restaurante = result[0].id_rest;

			var s = {id_sala : _id_sala, id_restaurante : _id_restaurante, id_cliente : 0, id_mesa : 0, produtos : [], nome_cliente : "", status_nome : "Em espera", id_funcionario : _id_funcionario, mesa : "", qr_code : _qr_code, status : 0, mensagem : "Sala criada com sucesso."};

			lstSalas.push(s);

			//res.send({id_sala : _id_sala, id_funcionario : _id_funcionario, produtos : lstProdutos[_id_sala], tamanho : lstProdutos[_id_sala].length});
			res.send(lstSalas[_id_sala]);
			io.sockets.emit("novo_pedido", lstSalas[_id_sala]);
		}else{
			res.send({mensagem : "Ocorreu um erro durante a conexão. Tente novamente mais tarde."});
		}

	});

});

app.get('/finalizarPedidoFisico', function(req, res){
	var _id_sala = req.query.id_sala;
	var _id_cliente = lstSalas[_id_sala].id_cliente;

	if (lstSalas[_id_sala].id_cliente == "" || lstSalas[_id_sala].id_mesa == 0){
		lstSalas[_id_sala] = {};
		res.send({mensagem : "O pedido não pode ser enviado por informações insuficientes. Tente novamente."});
	}else{
		var d = new Date();
		var day = d.getDate();
		var month = d.getMonth() + 1;
		var year = d.getFullYear();
		var dataAtual = year + "-" + month + "-" + day;

		var command = "insert into tbl_pedido (id_funcionario, id_cliente, data) "+
									"values('" + lstSalas[_id_sala].id_funcionario + "','" + lstSalas[_id_sala].id_cliente + "', '" + dataAtual + "')";

		con.query(command, function(err){
			if (err) throw err + command;

		});
		var id_pedido;
		var command2 = "select * from tbl_pedido order by id_pedido limit 0,1";

		con.query(command2, function(err2,result2,fields2){
			if (err2) throw err2 + command2;
			id_pedido = result2[0].id_pedido;

			var produtos = lstSalas[_id_sala].produtos;
			var contador = 0;

			while (contador < produtos.lenght){

				var command3 = "insert into tbl_pedidoproduto(id_pedido, id_produto) "+
				"values('" + id_pedido + "', '" + produtos[contador].id_produto + "')";

				con.query(command3, function(err3){
					if (err) throw err + command;
				});

				contador = contador + 1;
			}


			io.sockets.emit("pedido_finalizado",  _id_cliente);
			lstSalas[_id_sala] = {};

			res.send({mensagem : "Finalizado com sucesso."});
		});
	}

});

app.get('/adicionarProduto', function(req, res){
	var _id_produto = req.query.id_produto,
			_qtd = req.query.qtd,
			id_pedido = req.query.id_pedido;

	var _produtos = lstSalas[id_pedido].produtos;
	_produtos[0]
	var command = "select nome, preco, descricao, imagem from tbl_produto where id_produto = '" + _id_produto + "'";
	con.query(command, function(err, result, fields){
		if (err) throw err + command;

		_produtos.push({id_produto : _id_produto, qtd : _qtd, nome : result[0].nome, obs : result[0].descricao, preco : result[0].preco, imagem : result[0].imagem,status : 0, nome_status : "Em espera"});

		res.send({mensagem : "Sucesso."});
		io.sockets.emit("novo_produto", {id_funcionario : lstSalas[id_pedido].id_funcionario, id_cliente : lstSalas[id_pedido].id_cliente});
	});
});

app.get('/adicionarMesa', function(req,res){
	var _id_mesa = req.query.id_mesa,
			_nome = req.query.nome,
			_id_pedido = req.query.id_pedido;

	var contador = 0,
			verificador = 0;

	while (contador < lstSalas.length){

		if (_id_pedido == lstSalas[contador].id_sala){

				lstSalas[contador].id_mesa = _id_mesa;
				lstSalas[contador].mesa = _nome;

				verificador = 1;

				res.send({id_mesa : _id_mesa, nome : "Selecionado com sucesso."});
				io.sockets.emit("novo_pedido", lstSalas[contador]);
				break;
		}

		contador = contador + 1;
	}

	if (verificador == 0){
		res.send({id_mesa : _id_mesa,nome : "Ocorreu um erro. Tente novamente mais tarde."});
	}
});

app.get('/mesas', function(req, res){

	var _id_funcionario = req.query.id_funcionario;

	var command = "select r.id_restaurante as 'id_rest' from tbl_funcionario as f "+
									"inner join tbl_restaurante as r "+
									"on f.id_restaurante = r.id_restaurante "+
									"where f.id_funcionario = '" + _id_funcionario + "'";

	con.query(command, function(err, result, fields){
		if (err) throw err + command;

		if (result.length > 0){

			var _id_restaurante = result[0].id_rest;

			var command2 = "select m.id_mesa as 'id_mesa', m.nome as 'nome' from tbl_mesa as m "+
										 "where id_restaurante = '" + _id_restaurante +"'";

			con.query(command2, function(err2, result2, fields2){

				var contador = 0,
						lstMesas = [];

				while (contador < result2.length){

					var m = {id_mesa : result2[contador].id_mesa, nome : result2[contador].nome};

					lstMesas.push(m);

					contador = contador + 1;
				}

				res.send(lstMesas);

			});
		}else{
			res.send({mensagem : "Ocorreu um erro durante a conexão. Tente novamente mais tarde."});
		}

	});

});

app.get('/salas', function(req, res){
	var _id_funcionario = req.query.id_funcionario,
			lstTempSalas = [],
			contador = 0,
			contador2 = 0;

	if(typeof _id_funcionario != "undefined"){
		while (contador < lstSalas.length){

			var id_funcionario = lstSalas[contador].id_funcionario;

			if(id_funcionario == _id_funcionario){

				lstTempSalas[contador2] = lstSalas[contador];

				contador2 = contador2 + 1;

			}

			contador = contador + 1;
		}
		res.send(lstTempSalas);
	}else{
		res.send(lstSalas);
	}

});



http.listen(8100, function(){

	console.log("Servidor rodando na porta 8100");

});
