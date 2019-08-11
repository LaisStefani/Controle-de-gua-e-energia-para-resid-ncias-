<?php
	// Este script manda os dados dos sensores
	// para a tela "Histórico"do aplicativo.
	$servidor='localhost';
	$banco='measurer';
	$usuario='root';
	$senha='';
 	// Conectando e consutando o banco de dados.
 	$conn = new mysqli($servidor, $usuario, $senha, $banco);
	// Checando a conexão.
 	if ($conn->connect_error) {
		die("Conexão falhou!: " . $conn->connect_error);
	}
	// Selecionando os valores da base de dados.
 	$medidas = array(); 
 	$sql = "SELECT horario, vazao, energia FROM medidas;";
	$stmt = $conn->prepare($sql);
	$stmt->execute();
	$stmt->bind_result($horario, $vazao, $energia);
	while($stmt->fetch()){
		$temp = [
		'horario'=>$horario,
		'vazao'=>$vazao,
		'energia'=>$energia
		];
 		array_push($medidas, $temp);
	}
	// Armazenando os valores em JSON (JavaScript Object Notation - Notação de Objetos JavaScript).
 	echo json_encode($medidas);
	//Encerrando a conexão.
	$conn->close();
?>
