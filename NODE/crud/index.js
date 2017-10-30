
var app = require('express')(),
	http = require('http').createServer(app),
	lstPessoas = [];

app.get('/', function(req, res){

	res.sendFile( __dirname + "/index.html");

});

app.get('/list', function(req, res){

	res.send(lstPessoas);

});

app.get('/inserir', function(req, res){

	var _nome = req.query.nome,
		_sobrenome = req.query.sobrenome,
		p = { nome : _nome , sobrenome : _sobrenome };

	lstPessoas.push(p);

	res.send("Inserido com sucesso");

});

app.get('/delete', function(req, res){

	var _nome = req.query.nome;

	for( i = 0 ; i < lstPessoas.length ; i++){

		if (_nome == lstPessoas[i].nome){

			lstPessoas.splice(i, 1);

		}

	}

	res.send("Deletado com sucesso");

});

http.listen(8888, function(){

	console.log("Servidor rodando na porta 8888");

});
