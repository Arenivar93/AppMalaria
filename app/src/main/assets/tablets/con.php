<?php
// Conectando y seleccionado la base de datos  
$dbconn = pg_connect("host=localhost dbname=sinave2 user=igmer password=12345")
    or die('No se ha podido conectar: ' . pg_last_error());


   /* if(!$dbconn) {
echo "Error: No se ha podido conectar a la base de datos\n";
} else {
echo "Conexión exitosa\n";
}*/
//var_dump('inicia');exit();


//var_dump($resutado);exit();

