<?php	
	// Este script recebe os dados do Arduino na forma de um GET com
	// os valores de vão e corrente passados ​​como parâmetros na URL e os
	// armazena no banco de dados.
	$servidor='localhost'; 
	$banco='measurer';
	$usuario='root';
	$senha='';
	// Conectando e consutando o banco de dados.
	$link = mysqli_connect($servidor,$usuario,$senha, $banco);
	date_default_timezone_set('America/Sao_Paulo');
	$horario = date('d-m-Y H:i:s', time());
	// Base de dados
	if (isset($_GET['vazao']) or isset($_GET['energia'])) {
		// Preparando os dados que vamos enviar.
		$vazao = $_GET['vazao'];
		$energia = $_GET['energia'];
		// Gravando os dados no banco.
		$query = "INSERT INTO medidas(horario, vazao, energia) VALUES ('$horario','$vazao','$energia')";
	
		mysqli_query($link,$query);
		mysqli_close($link);
		// Mensagem que aparecerá no navegador.
		echo 'DADOS INSERIDOS NO BANCO!';
	}else{	
		// Caso houver erros a mensagem abaixo aparecerá no navegador.
		echo 'ERRO AO GRAVAR NO BANCO DE DADOS!';
	}	
?>
