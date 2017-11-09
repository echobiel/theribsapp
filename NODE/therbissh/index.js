
var app = require('express')(),
	http = require('http').createServer(app),
	io = require('socket.io').listen(http),
	lstSalas = [],
	lstProdutos = [],
	mysql = require('mysql'),
	con = mysql.createConnection({
	  //host: "10.107.144.13",
	  host: "localhost",
	  //host: "10.107.134.26",
	  //host: "10.107.134.15",
	  user: "root",
	  password: "bcd127",
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

	  var command = "select id_produto, nome as 'nome_produto', descricao as 'desc_produto', imagem as 'foto_produto', concat('R$ ', format(preco,2,'de_DE')) as 'preco_produto' from tbl_produto where statusAprovacao = 1;";

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
            
            resultadoFinal = {id_cliente : id, permissao : permissao, foto : foto, nome : nome, mensagem : 'Login efetuado com sucesso.'};
            
            contador = contador + 1;
            
        }else{
            var contador2 = 0;
            var command2 = "select * from tbl_funcionario where email = '" + _email + "' or login = '" + _email + "' and senha = '" + _senha + "'";
        
            con.query(command2, function(err, result, fields) {
                if (err) throw err + command2;

                // Verificação das linhas no select
                if (contador2 < result.length) {

                    var foto = result[contador2].foto;
                    var id = result[contador2].id_cliente;
                    var nome = result[contador2].nome_completo;

                    var permissao = 2;

                    resultadoFinal = {id_cliente : id, permissao : permissao, foto : foto, nome : nome, mensagem : 'Login efetuado com sucesso.'};

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

app.get('/criacaoSala', function(req, res){

	var _id_sala = lstSalas.length,
        _id_funcionario = req.query.id_funcionario,
        s = {id_sala : _id_sala};
    
    lstProdutos[][0] = {'0','Maçã','Maçã','Maçã','Maçã','Maçã','Maçã'}
    
    lstProdutos[1] = [{},{}]
    
    lstPedido[_id_sala] = lstProdutos[]
    
    io.sockets.emit("novo_usuario", s);
});



http.listen(8100, function(){

	console.log("Servidor rodando na porta 8100");

});
