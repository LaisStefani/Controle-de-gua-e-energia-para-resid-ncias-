<?php
	// Este script manda os dados dos sensores
	// para a tela "Medições" do aplicativo.
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
	$sql = "SELECT * FROM medidas ORDER BY id DESC LIMIT 1";
	$result = $conn->query($sql);
	if ($result->num_rows > 0) {
	    while($row[] = $result->fetch_assoc()) {
		// Armazenando os valores em JSON (JavaScript Object Notation - Notação de Objetos JavaScript).
		$json = json_encode($row);
	    }
	} else {
	    echo "0 Resultados";
	}
	//Exibindo os valores de JSON.
	echo $json;
	//Encerrando a conexão.
	$conn->close();
?>
