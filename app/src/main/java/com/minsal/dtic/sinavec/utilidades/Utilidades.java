package com.minsal.dtic.sinavec.utilidades;

import android.database.Cursor;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.minsal.dtic.sinavec.EntityDAO.CtlCanton;
import com.minsal.dtic.sinavec.EntityDAO.CtlCantonDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserio;
import com.minsal.dtic.sinavec.EntityDAO.CtlCaserioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamento;
import com.minsal.dtic.sinavec.EntityDAO.CtlDepartamentoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimiento;
import com.minsal.dtic.sinavec.EntityDAO.CtlEstablecimientoDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipio;
import com.minsal.dtic.sinavec.EntityDAO.CtlMunicipioDao;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriadero;
import com.minsal.dtic.sinavec.EntityDAO.CtlPlCriaderoDao;
import com.minsal.dtic.sinavec.EntityDAO.DaoSession;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUser;
import com.minsal.dtic.sinavec.EntityDAO.FosUserUserDao;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnopheles;
import com.minsal.dtic.sinavec.EntityDAO.PlCapturaAnophelesDao;
import com.minsal.dtic.sinavec.EntityDAO.PlColvol;
import com.minsal.dtic.sinavec.EntityDAO.PlColvolDao;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvaria;
import com.minsal.dtic.sinavec.EntityDAO.PlPesquisaLarvariaDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividad;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoActividadDao;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCaptura;
import com.minsal.dtic.sinavec.EntityDAO.PlTipoCapturaDao;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrador on 07-18-18.
 */

public class Utilidades {

    public static int rotacion = 0;
    public static boolean valida = true;

    public static final int LIST = 1;
    public static final int GRID = 2;


    public static int visualizacion = LIST;

    public static int fragment = 0;


    DaoSession daoSession;
    CtlMunicipioDao daoMunicipio;
    CtlCantonDao daoCanton;
    CtlCaserioDao daoCaserio;
    PlCapturaAnophelesDao capDao;
    PlPesquisaLarvariaDao pesDao;
    CtlPlCriaderoDao criaderoDao;
    PlColvolDao colvolDao;
    CtlEstablecimientoDao estDao;

    ArrayList<String> listaMunicipio;
    ArrayList<String> listaCanton;
    List<CtlCanton> cantones;
    ArrayList<String> listaCaserios;
    ArrayList<String> listaCapturas;

    List<CtlCaserio> caserios;
    List<PlCapturaAnopheles> capturas;
    List<PlPesquisaLarvaria> pesquisasBySem;

    List<CtlPlCriadero> criaderosMap;
    List<CtlEstablecimiento> establecimientoMap;
    List<PlColvol> colvolMap;
    List<CtlPlCriadero> criaderos;
    List<PlColvol> colvols;
    ArrayList<String> pesquisaPrueba;
    private List<PlPesquisaLarvaria> pesquisas;

    public Utilidades(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public List<CtlMunicipio> loadspinnerMunicipio(int idDepto) {
        CtlMunicipio municipio = null;
        daoMunicipio = daoSession.getCtlMunicipioDao();
        List<CtlMunicipio> municipios = new ArrayList<CtlMunicipio>();
        municipios = daoMunicipio.queryBuilder().where(CtlMunicipioDao.Properties.IdDepartamento.eq(idDepto))
                .orderAsc(CtlMunicipioDao.Properties.Nombre).list();
        return municipios;
    }

    public ArrayList<String> obtenerListaMunicipio(List<CtlMunicipio> municipios) {
        listaMunicipio = new ArrayList<String>();
        listaMunicipio.add("Seleccione");
        for (int i = 0; i < municipios.size(); i++) {
            listaMunicipio.add(municipios.get(i).getNombre());
        }
        return listaMunicipio;
    }
    public ArrayList<String> getMunicipioTodos(List<CtlMunicipio> municipios) {
        listaMunicipio = new ArrayList<String>();
        listaMunicipio.add("Todos");
        for (int i = 0; i < municipios.size(); i++) {
            listaMunicipio.add(municipios.get(i).getNombre());
        }
        return listaMunicipio;
    }


    public List<CtlCanton> loadSpinerCanton(Long idM) {
        daoCanton = daoSession.getCtlCantonDao();
        cantones = new ArrayList<CtlCanton>();
        cantones = daoCanton.queryBuilder().where(CtlCantonDao.Properties.IdMunicipio.eq(idM))
                .orderAsc(CtlCantonDao.Properties.Nombre).list();
        return cantones;
    }

    public ArrayList<String> obetenerListaCantones(List<CtlCanton> cantones) {
        listaCanton = new ArrayList<String>();
        listaCanton.add("Seleccione");
        for (int i = 0; i < cantones.size(); i++) {
            listaCanton.add(cantones.get(i).getNombre());
        }
        return listaCanton;
    }


    public List<CtlCaserio> loadSpinerCaserio(Long idCtn) {
        daoCaserio = daoSession.getCtlCaserioDao();
        caserios = new ArrayList<CtlCaserio>();
        caserios = daoCaserio.queryBuilder().where(CtlCaserioDao.Properties.IdCanton.eq(idCtn))
                .orderAsc(CtlCaserioDao.Properties.Nombre).list();
        return caserios;
    }

    public ArrayList<String> obetenerListaCaserios(List<CtlCaserio> caserios) {
        listaCaserios = new ArrayList<String>();
        listaCaserios.add("Seleccione");
        for (int i = 0; i < caserios.size(); i++) {
            listaCaserios.add(caserios.get(i).getNombre());
        }
        return listaCaserios;
    }

    public List<CtlPlCriadero> obtenerCriaderosByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas) {
        criaderos = new ArrayList<CtlPlCriadero>();
        if (idMuni != 0) {
            if (idCtn != 0 && idCas == 0) {
                //Ejecutara busqueda de municipio y canton
                 /* QueryBuilder<User> queryBuilder = userDao.queryBuilder();
                queryBuilder.join(Address.class, AddressDao.Properties.userId)
                        .where(AddressDao.Properties.Street.eq("Sesame Street"));
                List<User> users = queryBuilder.list();*/

                CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                criaderos = queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            } else if (idCtn != 0 && idCas != 0) {
                //Ejecutara busqueda de muni ctn y cass
                criaderos = daoSession.getCtlPlCriaderoDao()
                        .queryBuilder()
                        .where(CtlPlCriaderoDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();

            } else {
                //ejecutara busqueda solo de municipio
                CtlPlCriaderoDao criaderoDao = daoSession.getCtlPlCriaderoDao();

                QueryBuilder<CtlPlCriadero> queryBuilder = criaderoDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(CtlPlCriaderoDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                Join ctlMunicipio = queryBuilder.join(ctlCanton, CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class, CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                criaderos = queryBuilder.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
            }
        } else {

        }

        return criaderos;

    }

    public List<PlColvol> obtenerColvolByIds(DaoSession daoSession, int idMuni, int idCtn, int idCas) {
        colvols = new ArrayList<PlColvol>();
        if (idMuni != 0) {
            if (idCtn != 0 && idCas == 0) {
                PlColvolDao colvolDao = daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(PlColvolDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                ctlCanton.where(CtlCantonDao.Properties.Id.eq(idCtn));
                colvols = queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();

            } else if (idCtn != 0 && idCas != 0) {
                //Ejecutara busqueda de muni ctn y cass
                colvols = daoSession.getPlColvolDao()
                        .queryBuilder()
                        .where(PlColvolDao.Properties.IdCaserio.eq(idCas))
                        .orderAsc(PlColvolDao.Properties.Nombre).list();

            } else {
                //ejecutara busqueda solo de municipio
                PlColvolDao colvolDao = daoSession.getPlColvolDao();

                QueryBuilder<PlColvol> queryBuilder = colvolDao.queryBuilder();
                Join ctlCaserio = queryBuilder.join(PlColvolDao.Properties.IdCaserio, CtlCaserio.class);
                Join ctlCanton = queryBuilder.join(ctlCaserio, CtlCaserioDao.Properties.IdCanton,
                        CtlCanton.class, CtlCantonDao.Properties.Id);
                Join ctlMunicipio = queryBuilder.join(ctlCanton, CtlCantonDao.Properties.IdMunicipio,
                        CtlMunicipio.class, CtlMunicipioDao.Properties.Id);
                ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMuni));
                colvols = queryBuilder.orderAsc(PlColvolDao.Properties.Nombre).list();
            }
        } else {

        }

        return colvols;

    }

    public List<PlTipoActividad> loadspinnerActividad() {
        //PlTipoActividad actividad=null;
        PlTipoActividadDao actividadDao = daoSession.getPlTipoActividadDao();
        List<PlTipoActividad> actividad = new ArrayList<PlTipoActividad>();
        actividad = actividadDao.loadAll();
        return actividad;
    }

    public ArrayList<String> obtenerListaActividad(List<PlTipoActividad> actividades) {
        ArrayList<String> listaActividad = new ArrayList<String>();
        listaActividad.add("Seleccione");
        for (int i = 0; i < actividades.size(); i++) {
            listaActividad.add(actividades.get(i).getNombre());
        }
        return listaActividad;
    }


    public List<PlTipoCaptura> loadspinnerCaptura() {
        PlTipoCapturaDao capturaDao = daoSession.getPlTipoCapturaDao();
        List<PlTipoCaptura> captura = new ArrayList<PlTipoCaptura>();
        captura = capturaDao.loadAll();
        return captura;
    }

    public ArrayList<String> obtenerListaCaptura(List<PlTipoCaptura> capturas) {
        ArrayList<String> listaCaptura = new ArrayList<String>();
        listaCaptura.add("Seleccione");
        for (int i = 0; i < capturas.size(); i++) {
            listaCaptura.add(capturas.get(i).getNombre());
        }
        return listaCaptura;
    }

    public int deptoUser(String username) {
        Log.d("Error1",username);
        CtlDepartamentoDao departamentoDao = daoSession.getCtlDepartamentoDao();
        String sqlQUERY = "SELECT d.id FROM ctl_departamento d " +
                "INNER JOIN ctl_municipio m on (m.id_departamento = d.id)\n" +
                "INNER JOIN ctl_establecimiento es on(es.id_municipio= m.id)\n" +
                "INNER JOIN fos_user_user f on (f.id_sibasi= es.id)\n" +
                "WHERE f.username ='" + username + "'";
        Cursor cursor = daoSession.getDatabase().rawQuery(sqlQUERY, null);

        int idDepartamento = 0;
        if (cursor.moveToFirst()) {
            idDepartamento = cursor.getInt(0);
        }
        return idDepartamento;
    }

    public List<Double> getCoordenadasDepartamento(int idDepto) {
        List<Double> coordenadas = new ArrayList<Double>();
        switch (idDepto) {
            case 1:
                coordenadas.add(13.924417447800225);
                coordenadas.add(-89.84502099044607);
                break;
            case 2:
                coordenadas.add(13.98944214420671);
                coordenadas.add(-89.55775603094685);
                break;
            case 3:
                coordenadas.add(13.713998160450721);
                coordenadas.add(-89.7241812947351);
                break;
            case 4:
                coordenadas.add(14.040246499891111);
                coordenadas.add(-88.93654739847989);
                break;
            case 5:
                coordenadas.add(13.48790905547123);
                coordenadas.add(-89.3138970134354);
                break;
            case 6:
                coordenadas.add(13.700522292352314);
                coordenadas.add(-89.2243065830553);
                break;
            case 7:
                coordenadas.add(13.721760783928321);
                coordenadas.add(-88.93454747211422);
                break;
            case 8:
                coordenadas.add(13.509297506318713);
                coordenadas.add(-88.87253379682079);
                break;
            case 9:
                coordenadas.add(13.840857923573395);
                coordenadas.add(-88.85099106975167);
                break;
            case 10:
                coordenadas.add(13.638495811409328);
                coordenadas.add(-88.7812149622755);
                break;
            case 11:
                coordenadas.add(13.337202572142669);
                coordenadas.add(-88.43989024244115);
                break;
            case 12:
                coordenadas.add(13.481664900678894);
                coordenadas.add(-88.17468692767659);
                break;
            case 13:
                coordenadas.add(13.690076813573697);
                coordenadas.add(-88.10076110598004);
                break;
            case 14:
                coordenadas.add(13.166424607066649);
                coordenadas.add(-87.94670660373527);
                break;
        }

        return coordenadas;
    }

    public List<PlCapturaAnopheles> loadListcapturas() {
        capDao = daoSession.getPlCapturaAnophelesDao();
        capturas = new ArrayList<PlCapturaAnopheles>();
        capturas = capDao.loadAll();
        return capturas;
    }

    public ArrayList<String> getListaCapturas(List<PlCapturaAnopheles> capturas) {
        listaCapturas = new ArrayList<String>();
        for (int i = 0; i < capturas.size(); i++) {
            listaCapturas.add(capturas.get(i).getCtlCaserio().getCtlCanton().getCtlMunicipio().getNombre()
                    + "-" + capturas.get(i).getCtlCaserio().getCtlCanton().getNombre()
                    + "-" + capturas.get(i).getCtlCaserio().getNombre()
                    + "-" + capturas.get(i).getPropietario()
                    + "-" + capturas.get(i).getTotalAnopheles()
                    + "-" + capturas.get(i).getIdSemanaEpidemiologica());
        }
        return listaCapturas;
    }

    /**
    *metodos para lista de pesquisa larvaria
    */

    public ArrayList<String> loadListPesquisaBySem() {
        pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesquisasBySem = new ArrayList<PlPesquisaLarvaria>();
        pesquisaPrueba = new ArrayList<String>();
        float indice;
        Cursor c = daoSession.getDatabase().rawQuery("SELECT strftime('%Y', fecha_hora_reg) AS anio, id_semana_epidemiologica," +
                " SUM(anopheles_uno),SUM(anopheles_dos),SUM(pupa),SUM(numero_cucharonada)FROM PL_PESQUISA_LARVARIA GROUP BY id_semana_epidemiologica, anio", null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(3)>0){
                    indice = (float) c.getInt(2)/ c.getInt(3);
                }else{indice=0;}
                pesquisaPrueba.add(c.getString(0) + "-" + c.getInt(1) + "-" + c.getInt(2)+"-"+c.getInt(3)+"-"+c.getInt(4)+"-"+c.getInt(5)+"-"+indice);
            } while (c.moveToNext());
        }
        c.close();
        return pesquisaPrueba;
    }
    public List<PlPesquisaLarvaria> loadPesquisasDetalleSemana(int semana) {
        pesDao = daoSession.getPlPesquisaLarvariaDao();
        pesquisas = new ArrayList<PlPesquisaLarvaria>();
        QueryBuilder<PlPesquisaLarvaria> qb = pesDao.queryBuilder().where(PlPesquisaLarvariaDao.Properties.IdSemanaEpidemiologica.eq(semana));
        pesquisas = qb.list();
        return pesquisas;
    }


    public List<CtlPlCriadero> loadCriaderosMap( int idMunicipio) {

        criaderoDao = daoSession.getCtlPlCriaderoDao();

        criaderosMap = new ArrayList<CtlPlCriadero>();
        if (idMunicipio>0){
            QueryBuilder<CtlPlCriadero> qb = criaderoDao.queryBuilder().where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1));
            Join ctlCaserio = qb.join(CtlPlCriaderoDao.Properties.IdCaserio,CtlCaserio.class);
            Join ctlCanton = qb.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                    CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = qb.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                    CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMunicipio));
            criaderosMap = qb.orderAsc(CtlPlCriaderoDao.Properties.Nombre).list();
        }else {
            criaderosMap = criaderoDao.queryBuilder()
                    .where(CtlPlCriaderoDao.Properties.Latitud.isNotNull()).where(CtlPlCriaderoDao.Properties.Estado_sync.notEq(1)).list();
        }

        return criaderosMap;

    }
    public List<CtlEstablecimiento> loadEstablecimientoMap(int idMunicipio, int idDepto) {
        estDao = daoSession.getCtlEstablecimientoDao();
        establecimientoMap = new ArrayList<CtlEstablecimiento>();
        if (idMunicipio>0){
            QueryBuilder<CtlEstablecimiento> qb = estDao.queryBuilder().where(CtlEstablecimientoDao.Properties.Latitud.isNotNull()).where(CtlEstablecimientoDao.Properties.IdMunicipio.eq(idMunicipio));

            establecimientoMap = qb.orderAsc(CtlEstablecimientoDao.Properties.Nombre).list();
        }else {
            QueryBuilder<CtlEstablecimiento> qb = estDao.queryBuilder().where(CtlEstablecimientoDao.Properties.Latitud.isNotNull());
            Join ctlMunicipio = qb.join(CtlEstablecimientoDao.Properties.IdMunicipio,CtlMunicipio.class);
            Join ctlDepartamento = qb.join(ctlMunicipio,CtlMunicipioDao.Properties.IdDepartamento,
                    CtlDepartamento.class,CtlDepartamentoDao.Properties.Id);
            ctlDepartamento.where(CtlDepartamentoDao.Properties.Id.eq(idDepto));
            establecimientoMap = qb.list();
        }
        return establecimientoMap;
    }



    public List<PlColvol> loadColvolMap( int idMunicipio) {

        colvolDao = daoSession.getPlColvolDao();
        colvolMap = new ArrayList<PlColvol>();
        if (idMunicipio>0){
            QueryBuilder<PlColvol> qb = colvolDao.queryBuilder().where(PlColvolDao.Properties.Latitud.isNotNull());
            Join ctlCaserio = qb.join(PlColvolDao.Properties.IdCaserio,CtlCaserio.class);
            Join ctlCanton = qb.join(ctlCaserio,CtlCaserioDao.Properties.IdCanton,
                    CtlCanton.class,CtlCantonDao.Properties.Id);
            Join ctlMunicipio = qb.join(ctlCanton,CtlCantonDao.Properties.IdMunicipio,
                    CtlMunicipio.class,CtlMunicipioDao.Properties.Id);
            ctlMunicipio.where(CtlMunicipioDao.Properties.Id.eq(idMunicipio));
            colvolMap = qb.orderAsc(PlColvolDao.Properties.Nombre).list();
        }else {
            colvolMap = colvolDao.queryBuilder()
                    .where(PlColvolDao.Properties.Latitud.isNotNull()).list();
        }
        return colvolMap;

    }

        //obteniendo el usuario en sesion
        public long getIdUser(String username) {
            long id = 0;
            if (!username.equals("")) {
                List<FosUserUser> ids = null;
                FosUserUserDao userDao = daoSession.getFosUserUserDao();
                QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
                qb.where(FosUserUserDao.Properties.Username.eq(username));
                ids = qb.list();
                for (FosUserUser f : ids) {
                    id = f.getId();
                }
            }
            return id;
        }


    public long getIdSibasiUser(String username) {
        long idSibasi = 0;
        if (!username.equals("")) {
            List<FosUserUser> ids = null;
            FosUserUserDao userDao = daoSession.getFosUserUserDao();
            QueryBuilder<FosUserUser> qb = userDao.queryBuilder();
            qb.where(FosUserUserDao.Properties.Username.eq(username));
            ids = qb.list();
            for (FosUserUser f : ids) {
                idSibasi = f.getIdSibasi();
            }
        }
        return idSibasi;

    }
}
