<?php
include 'con.php';
$imei = $_GET['imei'];
//$imei= 867105021262810;


    $total =0;
    $query = "SELECT id, nombre,case activo when false then 0 else 1 end as activo from ctl_pais
    ORDER BY id";
    $pais = pg_query($query) or die('Error: ' . pg_last_error());
    $paisesArray = array();
    while ($row = pg_fetch_assoc($pais)) {
       $paisesArray[] = $row;
    }
    $total=count($paisesArray);
    //json_encode($paisesArray);

    $query2 = "SELECT id, nombre FROM ctl_procedencia
    ORDER BY id";
    $procedencia = pg_query($query2) or die('Error: ' . pg_last_error());
    $procedenciaArray = array();
    while ($row = pg_fetch_assoc($procedencia)) {
       $procedenciasArray[] = $row;
    }
    $total=$total+count($procedenciasArray);
    //json_encode($procedenciasArray);

    $query3 = "SELECT t1.id,t1.id_procedencia, t1.id_departamento,t1.id_municipio,t1.correlativo,t1.clave FROM clave t1
    	inner join ctl_establecimiento t2 on(t2.id_municipio = t1.id_municipio)
    	inner join ctl_tablet t3 on (t3.id_sibasi=t2.id)
    	where t3.imei='$imei'
    	ORDER BY id";
    $clave = pg_query($query3) or die('Error: ' . pg_last_error());
    //var_dump($clave);exit();
    $claveArray = array();

    while ($row = pg_fetch_assoc($clave)) {
       $claveArray[] = $row;
    }
    $total=$total+count($claveArray);
    //json_encode($claveArray);

    $query4 = "SELECT id, nombre,id_pais from ctl_departamento
    ORDER BY id";
    $departamento = pg_query($query4) or die('Error: ' . pg_last_error());
    $departamentoArray = array();
    while ($row = pg_fetch_assoc($departamento)) {
       $departamentoArray[] = $row;
    }
    $total=$total+count($departamentoArray);
    //json_encode($departamentoArray);

    $query5 = "SELECT id, nombre,id_departamento,id_depto_apoyo from ctl_municipio
    ORDER BY id";
    $municipio = pg_query($query5) or die('Error: ' . pg_last_error());
    $municipioArray = array();
    while ($row = pg_fetch_assoc($municipio)) {
       $municipioArray[] = $row;
    }
    $total=$total+count($municipioArray);
    $query6 = "SELECT id, nombre,id_municipio from ctl_canton
    ORDER BY id";
    $canton = pg_query($query6) or die('Error: ' . pg_last_error());
    $cantonArray = array();
    while ($row = pg_fetch_assoc($canton)) {
       $cantonArray[] = $row;
    }
    $total=$total+count($cantonArray);
		    $query7 = "SELECT cas.id, cas.nombre,cas.id_canton,cas.bandera,cas.id_depto_apoyo
		 from ctl_caserio cas
		 inner join ctl_canton can  on(can.id=cas.id_canton)
		 inner join ctl_municipio mun on(mun.id =can.id_municipio)
		 inner join ctl_departamento dep on (dep.id = mun.id_departamento) 
		 where dep.id=(SELECT d.id FROM ctl_departamento d
	                 INNER JOIN ctl_municipio m on (m.id_departamento = d.id) 
	                 INNER JOIN ctl_establecimiento es on(es.id_municipio= m.id)
	                 INNER JOIN ctl_tablet ta on (es.id=ta.id_sibasi) where imei='$imei')
   ORDER BY cas.id";
    $caserio = pg_query($query7) or die('Error: ' . pg_last_error());
    $caserioArray = array();
    while ($row = pg_fetch_assoc($caserio)) {
       $caserioArray[] = $row;
    }
    $total=$total+count($caserioArray);
    $query10 = "SELECT id, nombre from ctl_institucion order by id";
    $institucion = pg_query($query10) or die('Error: ' . pg_last_error());
    $institucionArray = array();
    while ($row = pg_fetch_assoc($institucion)) {
       $institucionArray[] = $row;
    }
    $total=$total+count($institucionArray);
    $query11 = "SELECT id, nombre,id_institucion from ctl_tipo_establecimiento
     order by id";
    $tipoEstablecimiento = pg_query($query11) or die('Error: ' . pg_last_error());
    $tipoEstablecimientoArray = array();
    while ($row = pg_fetch_assoc($tipoEstablecimiento)) {
       $tipoEstablecimientoArray[] = $row;
    }
    $total=$total+count($tipoEstablecimientoArray);

    $query8 = "SELECT id, codigo,id_sibasi,imei from ctl_tablet
    where imei ='$imei'
    ORDER BY id";
    $tablet = pg_query($query8) or die('Error: ' . pg_last_error());
    $tabletArray = array();
    while ($row = pg_fetch_assoc($tablet)) {
       $tabletArray[] = $row;
    }
    $total=$total+count($tabletArray);

    $query9 = ("SELECT t1.id as id, t1.nombre,t1.latitud, t1.longitud, t1.id_municipio, t1.id_tipo_establecimiento, t1.id_establecimiento_padre
                from ctl_establecimiento t1
                inner join ctl_tablet t2 on(t1.id_establecimiento_padre=t2.id_sibasi)
                where t2.imei='$imei' union SELECT t3.id as id, t3.nombre,t3.latitud, t3.longitud, t3.id_municipio, t3.id_tipo_establecimiento, t3.id_establecimiento_padre
                from ctl_establecimiento t3
                inner join ctl_tablet t2 on(t3.id=t2.id_sibasi)
                where t2.imei='$imei' union  SELECT t3.id as id, t3.nombre,t3.latitud, t3.longitud, t3.id_municipio, t3.id_tipo_establecimiento, t3.id_establecimiento_padre
                from ctl_establecimiento t3 where id in (1,2,3,4,5)
                order by id");
    $establecimiento = pg_query($query9) or die('Error: ' . pg_last_error());
    $establecimientoArray = array();
    while ($row = pg_fetch_assoc($establecimiento)) {
       $establecimientoArray[] = $row;
    }
    $total=$total+count($establecimientoArray);


    $sqlUsuarios = "SELECT f.id,f.firstname,f.username,f.lastname,f.password,f.salt,f.id_tipo_empleado,f.id_sibasi from fos_user_user f
    inner join ctl_tablet t on (t.id_sibasi= f.id_sibasi)
    where t.imei='$imei'";
    $usuarios = pg_query($sqlUsuarios) or die('Error: ' . pg_last_error());
    $usuarioArray = array();
    while ($row = pg_fetch_assoc($usuarios)) {
       $usuarioArray[] = $row;
    }
    $total=$total+count($usuarioArray);


    $sqlCriaderos = "SELECT c.id, c.id_caserio, c.id_tipo_criadero,c.id_estado_criadero, c.id_usuario_reg, c.id_estado_criadero,
           c.id_usuario_mod, c.nombre, c.descripcion, c.latitud, c.longitud, c.longitud_criadero,
           c.ancho_criadero, c.fecha_hora_reg, c.fecha_hora_mod,  c.id_sibasi
      FROM ctl_pl_criadero c
      INNER JOIN ctl_tablet t on (t.id_sibasi=c.id_sibasi)
      WHERE t.imei='$imei'";
    $criaderos = pg_query($sqlCriaderos) or die('Error: ' . pg_last_error());
    $criaderoArray = array();
    while ($row = pg_fetch_assoc($criaderos)) {
       $criaderoArray[] = $row;
    }
    $total=$total+count($criaderoArray);


    $sqlTipoActivad = "SELECT id, nombre FROM ctl_pl_tipo_actividad";
    $actividad = pg_query($sqlTipoActivad) or die('Error: ' . pg_last_error());
    $actividadArray = array();
    while ($row = pg_fetch_assoc($actividad)) {
       $actividadArray[] = $row;
    }
    $total=$total+count($actividadArray);


    $sqlTipoCaptura = "SELECT id, nombre FROM pl_tipo_captura";
    $tipoCaptura = pg_query($sqlTipoCaptura) or die('Error: ' . pg_last_error());
    $tipoCapturarray = array();
    while ($row = pg_fetch_assoc($tipoCaptura)) {
       $tipoCapturarray[] = $row;
    }
    $total=$total+count($tipoCapturarray);

   // 
    $sqlSemana = "SELECT id, anio,semana,feci,fecf FROM ctl_semana_epi";
    $semana = pg_query($sqlSemana) or die('Error: ' . pg_last_error());
    $semanaArray = array();
    while ($row = pg_fetch_assoc($semana)) {
       $semanaArray[] = $row;
    }
    $total=$total+count($semanaArray);


    //colvol no lleva longitud ni latitud poirque esta null

    $sqlColvol = "  SELECT col.id, col.id_caserio, col.id_sibasi, col.nombre,
    col.latitud,col.longitud, CASE col.estado WHEN false then 0 else 1 end as estado,col.clave
				FROM pl_colvol col
				INNER JOIN ctl_tablet tb on(tb.id_sibasi = col.id_sibasi)
				where tb.imei= '$imei'";
    $colvol = pg_query($sqlColvol) or die('Error: ' . pg_last_error());
    $colvolArray = array();
    while ($row = pg_fetch_assoc($colvol)) {
       $colvolArray[] = $row;
    }
    $total=$total+count($colvolArray);
   // var_dump($colvolArray);exit();

   
    
    $catalagosArray              = array();
    $catalagosArray[paises]      =$paisesArray;
    $catalagosArray[procedencia] =$procedenciasArray;
    $catalagosArray[clave]       =$claveArray;
    $catalagosArray[departamento]=$departamentoArray;
    $catalagosArray[municipio]   =$municipioArray;
    $catalagosArray[canton]      =$cantonArray;
    $catalagosArray[caserio]     =$caserioArray;
    $catalagosArray[tablet]      =$tabletArray;
    $catalagosArray[institucion]  =$institucionArray;
    $catalagosArray[tipoEstablecimiento]  =$tipoEstablecimientoArray;
    $catalagosArray[establecimiento]= $establecimientoArray;
    $catalagosArray[usuario]      = $usuarioArray;
    $catalagosArray[criadero]     = $criaderoArray;
    $catalagosArray[actividad]    = $actividadArray;
    $catalagosArray[tipoCaptura]  = $tipoCapturarray;
    $catalagosArray[semana]       = $semanaArray;
    $catalagosArray[colvol]       = $colvolArray;
   // $catalagosArray[total]       = $total;

      echo json_encode($catalagosArray);
