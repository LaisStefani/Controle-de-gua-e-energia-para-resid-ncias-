<?php
	// Este script manda os dados em R$ armazenados
	// do banco de dados para o aplicativo.
	$servidor='localhost';
	$banco='measurer';
	$usuario='root';
	$senha='';
	$mil_L='1000';
	$custo_A='2.415';
	$custo_E='0.00042';
	$min='60';
	// Conectando e consutando o banco de dados.
	$conn = new mysqli($servidor, $usuario, $senha, $banco);
	// Checando a conexão.
	if ($conn->connect_error) {
		die("Conexão falhou!: " . $conn->connect_error);
	}
	// Selecionando e somando os valores do banco de dados.
	$sql= mysqli_query($conn, "SELECT SUM(vazao) FROM medidas");
	$result = mysqli_num_rows($sql);
	// Realizando conta do consumo de água em R$.
	while($result = mysqli_fetch_array($sql)){
		$conta_A=$result['SUM(vazao)'] / $mil_L * $custo_A;
	}
	// Selecionando e somando os valores do banco de dados.
	$sql2= mysqli_query($conn, "SELECT SUM(energia) FROM medidas");
	$result2 = mysqli_num_rows($sql2);
	// Realizando conta do consumo de energia em R$.
	while($result2 = mysqli_fetch_array($sql2)){
		$conta_E=$result2['SUM(energia)'] * $custo_E / $min;
	}
	// Criação de um vetor.
	$medidas = array(); 
	$temp = [
		'agua'=>number_format($conta_A, 5, ',', ' '),
		'energia'=>number_format($conta_E,5, ',', ' ')];
 	array_push($medidas, $temp);
	// Armazenando os valores do vetor em JSON 
	//(JavaScript Object Notation - Notação de Objetos JavaScript).
 	echo json_encode($medidas);
	$conn->close();
?>
