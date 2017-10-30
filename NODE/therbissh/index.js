
var app = require('express')(),
	http = require('http').createServer(app),
	io = require('socket.io').listen(http),
	lstPedidos = [];

app.set('view engine', 'ejs');

app.get('/', function(req, res){

	//res.sendFile( __dirname + "/index.html");
	//res.send(lstAgendas);
	res.render("index", {app_name : lstPedidos});

});

app.get('/select', function(req, res){

	//res.sendFile( __dirname + "/index.html");
	//res.send(lstAgendas);
	var id = req.query.id;



	if (id != null || id > lstPedidos.length){
		res.send(lstPedidos[id]);
	}else{
		res.send(lstPedidos);
	}

});

app.get('/disconnect', function(req, res){

	res.send(disconnect:1);

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
