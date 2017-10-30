
var app = require('express')(),
	http = require('http').createServer(app),
	io = require('socket.io').listen(http),
	lstAgendas = [];

app.set('view engine', 'ejs');

app.get('/', function(req, res){

	//res.sendFile( __dirname + "/index.html");
	//res.send(lstAgendas);
	res.render("index", { app_name : lstAgendas});

});

app.get('/inserir', function(req, res){

	var _data = req.query.data,
		_titulo = req.query.titulo,
		_descricao = req.query.descricao,
		p = { data : _data , titulo : _titulo , descricao: _descricao };

	lstAgendas.push(p);

	res.send({ mensagem : "Inserido com sucesso"});
	io.sockets.emit("novo_usuario", p);

});

app.get('/delete', function(req, res){

	var _nome = req.query.nome;

	for( i = 0 ; i < lstAgendas.length ; i++){

		if (_nome == lstAgendas[i].nome){

			lstAgendas.splice(i, 1);

		}

	}

	res.send("Deletado com sucesso");

});



http.listen(8888, function(){

	console.log("Servidor rodando na porta 8888");

});
