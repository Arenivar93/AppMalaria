package com.minsal.dtic.greendaogenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class EntityGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.minsal.dtic.sinavec.EntityDAO"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

       // addTables(schema);

        //Entidades para catalogos de Malaria

        //2Tabla ctl_procedencia
        Entity ctlProcedencia=schema.addEntity("CtlProcedencia");
        ctlProcedencia.addLongProperty("id").primaryKey();
        ctlProcedencia.addStringProperty("nombre");

        //3Tabla clave
        Entity clave=schema.addEntity("Clave");
        clave.addLongProperty("id").primaryKey();
        clave.addIntProperty("idDepartamento");
        clave.addIntProperty("idMunicipio");
        clave.addIntProperty("correlativo");
        clave.addStringProperty("clave");
        Property idProcedencia=clave.addLongProperty("idProcedencia").notNull().getProperty();
        clave.addToOne(ctlProcedencia,idProcedencia);

        //4Tabla CtlPais
        Entity ctlPais=schema.addEntity("CtlPais");
        ctlPais.addLongProperty("id").primaryKey();
        ctlPais.addStringProperty("nombre");
        ctlPais.addBooleanProperty("activo");

        //5Tabla CtlDepartamento
        Entity ctlDepartamento=schema.addEntity("CtlDepartamento");
        ctlDepartamento.addLongProperty("id").primaryKey();
        ctlDepartamento.addStringProperty("nombre");
        Property idPais=ctlDepartamento.addLongProperty("idPais").notNull().getProperty();
        ctlDepartamento.addToOne(ctlPais,idPais);

        //6Tabla CtlMunicipio
        Entity ctlMunicipio=schema.addEntity("CtlMunicipio");
        ctlMunicipio.addLongProperty("id").primaryKey();
        ctlMunicipio.addStringProperty("nombre");
        ctlMunicipio.addIntProperty("idDeptoApoyo");
        Property idDepartamento=ctlMunicipio.addLongProperty("idDepartamento").notNull().getProperty();
        ctlMunicipio.addToOne(ctlDepartamento,idDepartamento);

        //7Tabla CtlCanton
        Entity ctlCanton=schema.addEntity("CtlCanton");
        ctlCanton.addLongProperty("id").primaryKey();
        ctlCanton.addStringProperty("nombre");
        Property idMunicipio=ctlCanton.addLongProperty("idMunicipio").notNull().getProperty();
        ctlCanton.addToOne(ctlMunicipio,idMunicipio);

        //8Tabla CtlCaserio
        Entity ctlCaserio=schema.addEntity("CtlCaserio");
        ctlCaserio.addLongProperty("id").primaryKey();
        ctlCaserio.addStringProperty("nombre");
        ctlCaserio.addLongProperty("idDeptoApoyo");
        ctlCaserio.addIntProperty("bandera");
        Property idCanton=ctlCaserio.addLongProperty("idCanton").notNull().getProperty();
        ctlCaserio.addToOne(ctlCanton,idCanton);

        //9Tabla PlTipoCaptura
        Entity plTipoCaptura=schema.addEntity("PlTipoCaptura");
        plTipoCaptura.addLongProperty("id").primaryKey();
        plTipoCaptura.addStringProperty("nombre");
        plTipoCaptura.addStringProperty("descripcion");

        //10Tabla PlTipoActividad
        Entity plTipoActividad=schema.addEntity("PlTipoActividad");
        plTipoActividad.addLongProperty("id").primaryKey();
        plTipoActividad.addStringProperty("nombre");

        //11Tabla CtlInsititucion
        Entity ctlInstitucion=schema.addEntity("CtlInstitucion");
        ctlInstitucion.addLongProperty("id").primaryKey();
        ctlInstitucion.addStringProperty("nombre");

        //12Tabla CtlTipoEstablecimiento
        Entity ctlTipoEstablecimiento=schema.addEntity("CtlTipoEstablecimiento");
        ctlTipoEstablecimiento.addLongProperty("id").primaryKey();
        ctlTipoEstablecimiento.addStringProperty("nombre");
        Property idInstitucion=ctlTipoEstablecimiento.addLongProperty("idInstitucion").notNull().getProperty();
        ctlTipoEstablecimiento.addToOne(ctlInstitucion,idInstitucion);

        //13Tabla CtlEstablecimiento
        Entity ctlEstablecimiento=schema.addEntity("CtlEstablecimiento");
        ctlEstablecimiento.addLongProperty("id").primaryKey();
        ctlEstablecimiento.addStringProperty("nombre");
        ctlEstablecimiento.addStringProperty("latitud");
        ctlEstablecimiento.addStringProperty("longitud");
        Property idMunicipioEst=ctlEstablecimiento.addLongProperty("idMunicipio").notNull().getProperty();
        Property idTipoEstablecimiento=ctlEstablecimiento.addLongProperty("idTipoEstablecimiento").notNull().getProperty();
        ctlEstablecimiento.addLongProperty("idEstablecimientoPadre").notNull().getProperty();
        ctlEstablecimiento.addToOne(ctlMunicipio,idMunicipioEst);
        ctlEstablecimiento.addToOne(ctlTipoEstablecimiento,idTipoEstablecimiento);

        //14 Tabla FosUserUSer
        Entity fosUserUser=schema.addEntity("FosUserUser");
        fosUserUser.addLongProperty("id").primaryKey();
        fosUserUser.addStringProperty("username");
        fosUserUser.addStringProperty("password");
        fosUserUser.addIntProperty("tipoEmpleado");
        fosUserUser.addStringProperty("firstname");
        fosUserUser.addStringProperty("lastname");
        fosUserUser.addStringProperty("salt");
        Property idSibasiEmpleado=fosUserUser.addLongProperty("idSibasi").notNull().getProperty();
        fosUserUser.addToOne(ctlEstablecimiento,idSibasiEmpleado);


        //15 Tabla PlColvol
        Entity plColvol=schema.addEntity("PlColvol");
        plColvol.addLongProperty("id").primaryKey();
        plColvol.addStringProperty("latitud");
        plColvol.addStringProperty("longitud");
        plColvol.addStringProperty("nombre");
        plColvol.addIntProperty("estado");
        plColvol.addStringProperty("clave");
        Property idCaserioPlColvol=plColvol.addLongProperty("idCaserio").notNull().getProperty();
        Property idSibasiPlColvol=plColvol.addLongProperty("idSibasi").notNull().getProperty();
        plColvol.addToOne(ctlCaserio,idCaserioPlColvol);
        plColvol.addToOne(ctlEstablecimiento,idSibasiPlColvol);

        //16 Tabla ColvolClave
        Entity colvolClave=schema.addEntity("ColvolCalve");
        colvolClave.addLongProperty("id").primaryKey();
        Property idClaveColvolClave=colvolClave.addLongProperty("idClave").notNull().getProperty();
        Property idColvolColvolClave=colvolClave.addLongProperty("idColvol").notNull().getProperty();
        colvolClave.addToOne(clave,idClaveColvolClave);
        colvolClave.addToOne(plColvol,idColvolColvolClave);

        //17 Tabla EstablecimientoClave
        Entity establecimientoClave=schema.addEntity("EstablecimientoClave");
        establecimientoClave.addLongProperty("id").primaryKey();
        Property idClaveEstablecimientoClave=establecimientoClave.addLongProperty("idClave").notNull().getProperty();
        Property idEstablecimientoEstablecimientoClave=establecimientoClave.addLongProperty("idEstablecimiento").notNull().getProperty();
        establecimientoClave.addToOne(clave,idClaveEstablecimientoClave);
        establecimientoClave.addToOne(ctlEstablecimiento,idEstablecimientoEstablecimientoClave);

        //18 Tabla PromotorClave
        Entity promotorClave=schema.addEntity("PromotorClave");
        promotorClave.addLongProperty("id").primaryKey();
        Property idClavePromotorClave=promotorClave.addLongProperty("idClave").notNull().getProperty();
        Property idFosUserUserPromotorClave=promotorClave.addLongProperty("idEmpleado").notNull().getProperty();
        promotorClave.addToOne(clave,idClavePromotorClave);
        promotorClave.addToOne(fosUserUser,idFosUserUserPromotorClave);

        //19 Tabla CtlSemanaEpi
        Entity ctlSemanaEpi=schema.addEntity("CtlSemanaEpi");
        ctlSemanaEpi.addLongProperty("id").primaryKey();
        ctlSemanaEpi.addIntProperty("anio");
        ctlSemanaEpi.addIntProperty("semana");
        ctlSemanaEpi.addDateProperty("fechaInicio");
        ctlSemanaEpi.addDateProperty("fechaFin");

        //20 Tabla CtlTablet
        Entity ctlTablet=schema.addEntity("CtlTablet");
        ctlTablet.addLongProperty("id").primaryKey();
        ctlTablet.addStringProperty("codigo");
        ctlTablet.addStringProperty("serie");
        Property idSibasiCtlTablet=ctlTablet.addLongProperty("idSibasi").notNull().getProperty();
        ctlTablet.addToOne(ctlEstablecimiento,idSibasiCtlTablet);

        //1Tabla bitacora
        Entity bitacora = schema.addEntity("Bitacora");
        bitacora.addLongProperty("id").primaryKey().autoincrement();
        bitacora.addStringProperty("operacion");
        bitacora.addStringProperty("sentenciaSql");
        bitacora.addStringProperty("nombreTabla");
        bitacora.addStringProperty("banderaEstado");
        Property idTabletBitacora=bitacora.addLongProperty("idTablet").notNull().getProperty();
        bitacora.addToOne(ctlTablet,idTabletBitacora);

        //21 Tabla CtlPlCriadero
        Entity ctlPlCriadero=schema.addEntity("CtlPlCriadero");
        ctlPlCriadero.addLongProperty("id").primaryKey();
        ctlPlCriadero.addIntProperty("idTipoCriadero");
        ctlPlCriadero.addIntProperty("idEstadoCriadero");
        ctlPlCriadero.addStringProperty("nombre").notNull();
        ctlPlCriadero.addStringProperty("descripcion").notNull();
        ctlPlCriadero.addStringProperty("latitud");
        ctlPlCriadero.addStringProperty("longitud");
        ctlPlCriadero.addIntProperty("longitudCriadero").notNull();
        ctlPlCriadero.addIntProperty("anchoCriadero").notNull();
        ctlPlCriadero.addDateProperty("fechaHoraReg");
        ctlPlCriadero.addDateProperty("fechaHoraMod");
        ctlPlCriadero.addLongProperty("idUsarioReg").notNull();
        Property idSibasiCtlPlCriadero=ctlPlCriadero.addLongProperty("idSibasi").notNull().getProperty();
        Property idCaserioCtlPlCriadero=ctlPlCriadero.addLongProperty("idCaserio").notNull().getProperty();
        Property idUsuarioMod=ctlPlCriadero.addLongProperty("idUsuarioMod").notNull().getProperty();
        ctlPlCriadero.addToOne(ctlEstablecimiento,idSibasiCtlPlCriadero);
        ctlPlCriadero.addToOne(ctlCaserio,idCaserioCtlPlCriadero);
        ctlPlCriadero.addToOne(fosUserUser,idUsuarioMod);

        //22 Tabla CtlPlCriaderoTmp
        Entity ctlPlCriaderoTmp=schema.addEntity("CtlPlCriaderoTmp");
        ctlPlCriaderoTmp.addLongProperty("id").primaryKey();
        ctlPlCriaderoTmp.addIntProperty("idTipoCriadero");
        ctlPlCriaderoTmp.addIntProperty("idEstadoCriadero");
        ctlPlCriaderoTmp.addStringProperty("nombre");
        ctlPlCriaderoTmp.addStringProperty("descripcion");
        ctlPlCriaderoTmp.addStringProperty("latitud");
        ctlPlCriaderoTmp.addStringProperty("longitud");
        ctlPlCriaderoTmp.addIntProperty("longitudCriadero");
        ctlPlCriaderoTmp.addIntProperty("anchoCriadero");
        ctlPlCriaderoTmp.addDateProperty("fechaHoraReg");
        ctlPlCriaderoTmp.addDateProperty("fechaHoraMod");
        ctlPlCriaderoTmp.addLongProperty("idUsarioReg");
        Property idBitacoraCaserioTmp=ctlPlCriaderoTmp.addLongProperty("idBitacora").notNull().getProperty();
        Property idSibasiCtlPlCriaderoTmp=ctlPlCriaderoTmp.addLongProperty("idSibasi").notNull().getProperty();
        Property idCaserioCtlPlCriaderoTmp=ctlPlCriaderoTmp.addLongProperty("idCaserio").notNull().getProperty();
        Property idUsuarioModTmp=ctlPlCriaderoTmp.addLongProperty("idUsuarioMod").notNull().getProperty();
        ctlPlCriaderoTmp.addToOne(ctlEstablecimiento,idSibasiCtlPlCriaderoTmp);
        ctlPlCriaderoTmp.addToOne(ctlCaserio,idCaserioCtlPlCriaderoTmp);
        ctlPlCriaderoTmp.addToOne(fosUserUser,idUsuarioModTmp);
        ctlPlCriaderoTmp.addToOne(bitacora,idBitacoraCaserioTmp);

        //23 Tabla PlCapturaAnopheles
        Entity plCapturaAnopheles=schema.addEntity("PlCapturaAnopheles");
        plCapturaAnopheles.addLongProperty("id").primaryKey().autoincrement();
        plCapturaAnopheles.addIntProperty("idEstado").notNull();
        plCapturaAnopheles.addIntProperty("totalMosquitos").notNull();
        plCapturaAnopheles.addIntProperty("totalAnopheles").notNull();
        plCapturaAnopheles.addIntProperty("casaPositiva").notNull();
        plCapturaAnopheles.addIntProperty("casaInspeccionada");
        plCapturaAnopheles.addIntProperty("componenteInspeccionado");
        plCapturaAnopheles.addIntProperty("tiempoColecta").notNull();
        plCapturaAnopheles.addDateProperty("fechaHoraMod");
        plCapturaAnopheles.addDateProperty("fecha");
        plCapturaAnopheles.addDateProperty("fechaHoraReg");
        plCapturaAnopheles.addStringProperty("propietario");
        plCapturaAnopheles.addIntProperty("idSemanaEpidemiologica").notNull();
        plCapturaAnopheles.addLongProperty("idUsuarioMod").notNull();
        Property idSibasiCaptura=plCapturaAnopheles.addLongProperty("idSibasi").notNull().getProperty();
        Property idTabletCaptura=plCapturaAnopheles.addLongProperty("idTablet").notNull().getProperty();
        Property idCaserioCaptura=plCapturaAnopheles.addLongProperty("idCaserio").notNull().getProperty();
        Property idUserCaptura=plCapturaAnopheles.addLongProperty("idUsuarioReg").notNull().getProperty();
        Property idTipoCapturaAnopheles=plCapturaAnopheles.addLongProperty("idTipoCaptura").notNull().getProperty();
        Property idTipoActividadCaptura=plCapturaAnopheles.addLongProperty("idTipoActividad").notNull().getProperty();
        plCapturaAnopheles.addToOne(ctlEstablecimiento,idSibasiCaptura);
        plCapturaAnopheles.addToOne(ctlCaserio,idCaserioCaptura);
        plCapturaAnopheles.addToOne(ctlTablet,idTabletCaptura);
        plCapturaAnopheles.addToOne(fosUserUser,idUserCaptura);
        plCapturaAnopheles.addToOne(plTipoCaptura,idTipoCapturaAnopheles);
        plCapturaAnopheles.addToOne(plTipoActividad,idTipoActividadCaptura);

        //24 Tabla PlSeguimientoBotiquin
        Entity plSeguimientoBotiquin=schema.addEntity("PlSeguimientoBotiquin");
        plSeguimientoBotiquin.addLongProperty("id").primaryKey().autoincrement();
        plSeguimientoBotiquin.addIntProperty("idEstadoFormulario").notNull();
        plSeguimientoBotiquin.addIntProperty("supervisado").notNull();
        plSeguimientoBotiquin.addIntProperty("visitado").notNull();
        plSeguimientoBotiquin.addIntProperty("enRiesgo").notNull();
        plSeguimientoBotiquin.addIntProperty("numeroPersonaDivulgo").notNull();
        plSeguimientoBotiquin.addDateProperty("fechaHoraReg");
        plSeguimientoBotiquin.addDateProperty("fechaHoraMod");
        plSeguimientoBotiquin.addDateProperty("fecha").notNull();
        plSeguimientoBotiquin.addIntProperty("idSemanaEpidemiologica").notNull();
        plSeguimientoBotiquin.addDateProperty("fechaRegistro").notNull();
        plSeguimientoBotiquin.addIntProperty("numeroMuestra").notNull();
        plSeguimientoBotiquin.addIntProperty("idUsuarioMod").notNull();
        Property idClaveSeguimiento=plSeguimientoBotiquin.addLongProperty("idClave").notNull().getProperty();
        Property idTabletSeguimiento=plSeguimientoBotiquin.addLongProperty("idTablet").notNull().getProperty();
        Property idUsuarioSeguimiento=plSeguimientoBotiquin.addLongProperty("idUsuarioReg").notNull().getProperty();
        Property idSibasiSeguimeinto=plSeguimientoBotiquin.addLongProperty("idSibasi").notNull().getProperty();
        plSeguimientoBotiquin.addToOne(clave,idClaveSeguimiento);
        plSeguimientoBotiquin.addToOne(ctlTablet,idTabletSeguimiento);
        plSeguimientoBotiquin.addToOne(fosUserUser,idUsuarioSeguimiento);
        plSeguimientoBotiquin.addToOne(ctlEstablecimiento,idSibasiSeguimeinto);

        //25 Tabla PlPesquisaLarvaria
        Entity plPesquisaLarvaria=schema.addEntity("PlPesquisaLarvaria");
        plPesquisaLarvaria.addLongProperty("id").primaryKey().autoincrement();
        plPesquisaLarvaria.addIntProperty("idSemanaEpidemiologica").notNull();
        plPesquisaLarvaria.addIntProperty("idUsuarioMod").notNull();
        plPesquisaLarvaria.addIntProperty("idEstado").notNull();
        plPesquisaLarvaria.addDateProperty("fechaHoraReg").notNull();
        plPesquisaLarvaria.addDateProperty("fechaHoraMod").notNull();
        plPesquisaLarvaria.addFloatProperty("indiceLarvario").notNull();
        plPesquisaLarvaria.addIntProperty("anophelesDos").notNull();
        plPesquisaLarvaria.addIntProperty("anophelesUno").notNull();
        plPesquisaLarvaria.addIntProperty("culicinosUno").notNull();
        plPesquisaLarvaria.addIntProperty("culicinosDos").notNull();
        plPesquisaLarvaria.addIntProperty("pupa").notNull();
        plPesquisaLarvaria.addIntProperty("numeroCucharonada").notNull();
        plPesquisaLarvaria.addFloatProperty("ancho").notNull();
        plPesquisaLarvaria.addFloatProperty("largo").notNull();
        plPesquisaLarvaria.addDateProperty("fecha").notNull();
        Property idTabletPesquisa=plPesquisaLarvaria.addLongProperty("idTablet").notNull().getProperty();
        Property idSibasiPesquisa=plPesquisaLarvaria.addLongProperty("idSibasi").notNull().getProperty();
        Property idCriaderoPesquisa=plPesquisaLarvaria.addLongProperty("idCriadero").notNull().getProperty();
        Property idCaserioPesquisa=plPesquisaLarvaria.addLongProperty("idCaserio").notNull().getProperty();
        Property idUserPesquisa=plPesquisaLarvaria.addLongProperty("idUsuarioReg").notNull().getProperty();
        plPesquisaLarvaria.addToOne(ctlTablet,idTabletPesquisa);
        plPesquisaLarvaria.addToOne(ctlEstablecimiento,idSibasiPesquisa);
        plPesquisaLarvaria.addToOne(ctlPlCriadero,idCriaderoPesquisa);
        plPesquisaLarvaria.addToOne(ctlCaserio,idCaserioPesquisa);
        plPesquisaLarvaria.addToOne(fosUserUser,idUserPesquisa);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addUserEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addUserEntities(final Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement();
        user.addIntProperty("user_id").notNull();
        user.addStringProperty("last_name");
        user.addStringProperty("first_name");
        user.addStringProperty("email");
        return user;
    }

}

