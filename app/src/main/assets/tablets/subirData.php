<?php
$dbconn = pg_connect("host=localhost dbname=sinave2 user=igmer password=12345")
or die('No se ha podido conectar: ' . pg_last_error());

$json = file_get_contents('php://input');
	$obj = json_decode($json);
	$obj22 = json_decode($json,true);
	$capturas = $obj->pl_capturas_insert;
	//$updateCriaderos = $obj->criaderos_update;
	//$pesquisas = $obj->pl_pesquisas_insert;
	//primero vamos a guardar las capturas de anopheles
	$insertedCapturasArray = array();
	$insertedPesquisasArray = array();	
	$insertar= array();
	$veces =0;
	$cadena = "";
	try{
		$file=fopen("/home/igmer/json/datos_cadena.txt","a") or die("Problemas");
		foreach ($capturas as $key3 =>$value3) {
			$veces= $veces+1;
			$cadena = $cadena.$value3;
			
			//$prueba = pg_query($dbconn,"insert into prueba (id,json)values(".$veces.",".utf8_encode($value3).");");
			 
		/*
		for ($i=0; $i<count($insertar) ; $i++) { 
			$resul = pg_query($dbconn,$insertar[$i]);
		}*/

		

		/*for ($i=0; $i <count($capturas) ; $i++) { 
			$objeto = $capturas[i];
		}*/
			//$insertedCapturasArray[]=$key3;	
		}
		//fputs($file,$cadena);
			//  fputs($file,"\n");
		$result2= pg_query($dbconn,$cadena) or die("falla update");
		
  //vamos añadiendo el contenido
		 
		 
		  fclose($file);


/*

		$conteo = pg_query($dbconn, "insert into prueba(id)values(".$veces.") ");
		$criaderos_update = array();	
		foreach($updateCriaderos as $key2 => $value2){
			$result2= pg_query($dbconn,$value2) or die("falla update");
			//$criaderos_update[]=$key2;
		}
		foreach ($pesquisas as $key1 =>$value1) {
			$result1 = pg_query($dbconn,$value1) or die("falla pesquisa");
			//$insertedPesquisasArray[]=$key1;	
		}*/

		pg_close($dbconn);

	}catch(Exception $e){

	}
	
		
		
	$insertedRow = array();
	$insertedRow['capturas']= $capturas;
	$insertedRow['pesquisas']= $insertedPesquisasArray;
	$insertedRow['criaderos_update']= $criaderos_update;

	echo json_encode($insertedRow);




