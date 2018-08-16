package com.minsal.dtic.greendaogenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class EntityGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.minsal.dtic.sinavec.EntityDAO"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        //Entidades para catalogos de Malaria

        //Tabla bitacora
        Entity bitacora = schema.addEntity("Bitacora");
        bitacora.addLongProperty("id").primaryKey().autoincrement();
        bitacora.addStringProperty("operacion");
        bitacora.addStringProperty("sentenciaSql");
        bitacora.addStringProperty("nombreTabla");
        bitacora.addLongProperty("idTablet");//Verificar si sera foranea
        bitacora.addStringProperty("banderaEstado");

        //Tabla ctl_procedencia
        Entity ctlProcedencia=schema.addEntity("CtlProcedencia");
        ctlProcedencia.addLongProperty("id").primaryKey();
        ctlProcedencia.addStringProperty("nombre");

        //Tabla clave
        Entity clave=schema.addEntity("Clave");
        clave.addLongProperty("id").primaryKey();
        clave.addLongProperty("idDepartamento");
        clave.addLongProperty("idMunicipio");
        clave.addLongProperty("correlativo");
        clave.addStringProperty("clave");
        Property idProcedencia=clave.addLongProperty("idProcedencia").notNull().getProperty();
        clave.addToOne(ctlProcedencia,idProcedencia);

        //Tabla CtlPais
        Entity ctlPais=schema.addEntity("CtlPais");
        ctlPais.addLongProperty("id").primaryKey();
        ctlPais.addStringProperty("nombre");
        ctlPais.addBooleanProperty("activo");

        //Tabla CtlDepartamento
        Entity ctlDepartamento=schema.addEntity("CtlDepartamento");
        ctlDepartamento.addLongProperty("id").primaryKey();
        ctlDepartamento.addStringProperty("nombre");
        Property idPais=ctlDepartamento.addLongProperty("idPais").notNull().getProperty();
        ctlDepartamento.addToOne(ctlPais,idPais);

        //Tabla CtlMunicipio
        Entity ctlMunicipio=schema.addEntity("CtlMunicipio");
        ctlMunicipio.addLongProperty("id").primaryKey();
        ctlMunicipio.addStringProperty("nombre");
        ctlMunicipio.addLongProperty("idDeptoApoyo");
        Property idDepartamento=ctlMunicipio.addLongProperty("idDepartamento").notNull().getProperty();
        ctlMunicipio.addToOne(ctlDepartamento,idDepartamento);

        //Tabla CtlCanton
        Entity ctlCanton=schema.addEntity("CtlCanton");
        ctlCanton.addLongProperty("id").primaryKey();
        ctlCanton.addStringProperty("nombre");
        Property idMunicipio=ctlCanton.addLongProperty("idMunicipio").notNull().getProperty();
        ctlCanton.addToOne(ctlMunicipio,idMunicipio);

        //Tabla CtlCaserio
        Entity ctlCaserio=schema.addEntity("CtlCaserio");
        ctlCaserio.addLongProperty("id").primaryKey();
        ctlCaserio.addStringProperty("nombre");
        ctlCaserio.addLongProperty("idDeptoApoyo");
        Property idCanton=ctlCaserio.addLongProperty("idCanton").notNull().getProperty();
        ctlCaserio.addToOne(ctlCanton,idCanton);

        //Tabla PlTipoCaptura
        Entity plTipoCaptura=schema.addEntity("PlTipoCaptura");
        plTipoCaptura.addLongProperty("id").primaryKey();
        plTipoCaptura.addStringProperty("nombre");
        plTipoCaptura.addStringProperty("descripcion");

        //Tabla PlTipoActividad
        Entity plTipoActividad=schema.addEntity("PlTipoActividad");
        plTipoActividad.addLongProperty("id").primaryKey();
        plTipoActividad.addStringProperty("nombre");

        //Tabla CtlInsititucion
        Entity ctlInstitucion=schema.addEntity("CtlInstitucion");
        ctlInstitucion.addLongProperty("id").primaryKey();
        ctlInstitucion.addStringProperty("nombre");

        //Tabla CtlTipoEstablecimiento
        Entity ctlTipoEstablecimiento=schema.addEntity("CtlTipoEstablecimiento");
        ctlTipoEstablecimiento.addLongProperty("id").primaryKey();
        ctlTipoEstablecimiento.addStringProperty("nombre");
        Property idInstitucion=ctlTipoEstablecimiento.addLongProperty("idInstitucion").notNull().getProperty();
        ctlTipoEstablecimiento.addToOne(ctlInstitucion,idInstitucion);

        //Tabla CtlEstablecimiento
        Entity ctlEstablecimiento=schema.addEntity("CtlEstablecimiento");
        ctlEstablecimiento.addLongProperty("id").primaryKey();
        ctlEstablecimiento.addStringProperty("nombre");
        ctlEstablecimiento.addFloatProperty("latitud");
        ctlEstablecimiento.addFloatProperty("longitud");
        Property idMunicipioEst=ctlEstablecimiento.addLongProperty("idMunicipio").notNull().getProperty();
        ctlEstablecimiento.addToOne(ctlMunicipio,idMunicipioEst);
        Property idTipoEstablecimiento=ctlEstablecimiento.addLongProperty("idTipoEstablecimiento").notNull().getProperty();
        ctlEstablecimiento.addToOne(ctlTipoEstablecimiento,idTipoEstablecimiento);
        Property idEstablecimientoPadre=ctlEstablecimiento.addLongProperty("idEstablecimientoPadre").notNull().getProperty();
        ctlEstablecimiento.addToOne(ctlEstablecimiento,idEstablecimientoPadre);

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

