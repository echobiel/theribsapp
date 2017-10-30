
var app = require('express')(),
	http = require('http').createServer(app),
	io = require('socket.io').listen(http),
	lstPedidos = [],
	mysql = require('mysql'),
	con = mysql.createConnection({
	  host: "localhost",
	  user: "root",
	  password: "",
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

	  var command = "select id_produto, nome as 'nome_produto', descricao as 'desc_produto', imagem as 'foto_produto', concat('R$ ',preco) as 'preco_produto' from tbl_produto where statusAprovacao = 1;";

	  con.query(command, function (err, result, fields) {
	    if (err) throw err;
	    res.send(result);
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

app.get('/delete', function(req, res){

	var _nome = req.query.nome;

	for( i = 0 ; i < lstPedidos.length ; i++){

		if (_nome == lstPedidos[i].nome){

			lstPedidos.splice(i, 1);

		}

	}

	res.send("Deletado com sucesso");

});



http.listen(8888, function(){

	console.log("Servidor rodando na porta 8888");

});
