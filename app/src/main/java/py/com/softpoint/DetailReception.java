package py.com.softpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import py.com.softpoint.apiclient.InvMaterialApi;
import py.com.softpoint.apiclient.InvPoReceivingItemApi;
import py.com.softpoint.apiclient.InvReceivingApi;
import py.com.softpoint.apiclient.InvUnitOfMeasureApi;
import py.com.softpoint.apiclient.PoPurchOrdersProdsVwApi;
import py.com.softpoint.apiclient.PoPurchaseOrdersWSApi;
import py.com.softpoint.pojos.InvMaterial;
import py.com.softpoint.pojos.InvPoReceivingItem;
import py.com.softpoint.pojos.InvPoReceivingItemQTY;
import py.com.softpoint.pojos.InvReceiving;
import py.com.softpoint.pojos.InvUnitOfMeasure;
import py.com.softpoint.pojos.InvWarehouse;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.PoPurchOrdersProdsVw;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import py.com.softpoint.utils.DataEnvManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReception extends AppCompatActivity  implements View.OnKeyListener {

    private TextView nroOC, proveedorName,depositoName, tvDetailDescription;
    private TextView tvLastCode, tvLastUm, tvLastQty;
    private EditText etDetailCodigo, etDetailQty;
    private Spinner spListaUm;
    private Button btnRecibir, btnRcpConfirmar, btnConsItemsRecepcionados;
    private String urlBase;

    // Varialbles de datos
    private PayVendor proveedorSelected;
    private PoPurchaseOrdersVw ocSelected;
    private InvWarehouse depositoSelected;
    private User userLoged;
    private boolean isItemsOk;
    private boolean isConfirm;
    private InvMaterial itemProducto;
    private InvUnitOfMeasure unidadMedidaSelected;
    private PoPurchOrdersProdsVw itemOC;
    private List<InvUnitOfMeasure> listUm;
    private InvReceiving recepcionSelected;
    private Long deviceID;

    //Atributo auxiliar para validar si existe el producto imputado en la Orden de compras
    private boolean isFoundProdOnOc = false;
    private boolean isFoundReceving = false;
    private Double itemProdQty = 0.0; // Total de item ya colectado



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reception);

        // Datos de Entorno
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        userLoged = (User) extras.get("USER_LOGED");
        proveedorSelected = (PayVendor) extras.get("PROVEEDOR");
        ocSelected = (PoPurchaseOrdersVw) extras.get("OC_SELECTED");
        depositoSelected = (InvWarehouse) extras.get("DEPOSITO");
        urlBase = (String) extras.get("URL_BASE");
        isConfirm = false;

        initComponent();
        validOcVsRecepcion();
        listUm = cargarUnidadMedida();
        prepararSpinerUm();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(getApplicationContext(), "OnResumen",Toast.LENGTH_LONG).show();
    }


    //<editor-fold desc="prepareSpinerUm">
    /**
    * Metodo que prepara el spiner de unidades de medida
    */
    private void prepararSpinerUm()
    {
        ArrayAdapter<InvUnitOfMeasure> adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,listUm);

        spListaUm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    unidadMedidaSelected = (InvUnitOfMeasure) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spListaUm.setAdapter(adapter);
    }
    //</editor-fold>

    /**
    * Inicializamos y conectamos los componentes de la GUI
    */
    private void initComponent()
    {
        //<editor-fold desc="Component">
        nroOC = findViewById(R.id.dataNroOC);
        nroOC.setText(ocSelected.getPoNumber());
        proveedorName = findViewById(R.id.dataProveedorName);
        proveedorName.setText(proveedorSelected.getName());
        depositoName = findViewById(R.id.dataDeposito);
        depositoName.setText(depositoSelected.getName());
        //</editor-fold>

        // TextView
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        spListaUm = findViewById(R.id.spListaUm);
        tvLastCode = findViewById(R.id.tvLastCode);
        tvLastUm = findViewById(R.id.tvLastUm);
        tvLastQty = findViewById(R.id.tvLastQty);


        // EditText
        etDetailCodigo = findViewById(R.id.etDetailCodigo);
        etDetailCodigo.setOnKeyListener(this);
        etDetailQty = findViewById(R.id.etDetailQty);

        // Recuperamos el ID del PDA que esta en el properties
        deviceID = Long.parseLong(DataEnvManager.getEnv(getApplicationContext()).get("ID_MOVIL").toString());
                //(Long) DataEnvManager.getEnv(getApplicationContext()).get("ID_MOVIL");

        // Buton Consultar items colectados
        btnConsItemsRecepcionados = findViewById(R.id.btnConsItemsRecepcionados);
        btnConsItemsRecepcionados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ocSelected.getReceivingId() != null && ocSelected.getReceivingId() > 0 )
                {
                    Log.i("VER_TEMS", "Mostar Lista de Items Colectados");
                    Intent intent = new Intent(getApplicationContext(), ItemsRecepcionados.class);
                    intent.putExtra("ORDER_ID", ocSelected.getIdentifier());
                    intent.putExtra("RECEIV_ID", ocSelected.getReceivingId());
                    intent.putExtra("BASE_URL",urlBase);
                    startActivity(intent);
                }else{
                    Toast msg = Toast.makeText(getApplicationContext(),"RECEPCION NO TIENE NINGUN ITEM",Toast.LENGTH_LONG);
                    msg.setGravity(Gravity.CENTER,0,0);
                    // msg.setMargin(20,20);
                    msg.show();
                }
            }
        });

        // Buton para confirmar una recepcion
        btnRcpConfirmar = findViewById(R.id.btnRcpConfirmar);
        btnRcpConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Log.i("CONFIRMAR", "Confirmar Recepcion");
                AlertDialog.Builder xdialog = new AlertDialog.Builder(DetailReception.this);
                xdialog.setTitle("CONFIRMAR");
                xdialog.setMessage("Desea confirmar la recepcion  "+ocSelected.getPoNumber() +" ?");
                xdialog.setCancelable(false);

                // Boton Cancelar
                xdialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Boton Aceptar
                xdialog.setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( confirmReceving(ocSelected.getIdentifier(), ocSelected.getReceivingId()) )
                        {
                            DetailReception.super.onBackPressed();
                        }
                    }
                });

                xdialog.show();
            }
        });

        //Boton para recibir un item
        btnRecibir = findViewById(R.id.btnRecibirItem);
        btnRecibir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                /*
                *  Buscamos en el BackEnd si ya hay registros de conteo y si hay lo recuperamos para sumar a la cantidad colectada
                * */
                itemProdQty = getTotalQtyFromApi(unidadMedidaSelected.getIdentifier(),
                                                 itemProducto.getIdentifier(),
                                                 ocSelected.getIdentifier());

                Log.i("CANTIDAD","Cant. OC         : "+itemOC.getQuantity());
                Log.i("CANTIDAD","Cant. Confirmada : "+itemProdQty);

                 /* TODO  : Si se cargo un monto mayor a cero en el campo cantidad y
                 *  se verifica que la cantidad colectada tampoco supere a la cantidad pedida en la OC
                 */
                if( etDetailQty.getText().toString().length() > 0
                    && ( Double.valueOf(etDetailQty.getText().toString()) + itemProdQty ) <= itemOC.getQuantity())
                    {

                    //<editor-fold desc="RECEIVING_ID Verficamos">
                    if ( !isFoundReceving ) // si no  existe la recepcion asociada a la OC seleccionada
                    {
                        Log.i("RECEPCION","NO EXISTE LA RECEPCION");
                        // Sino existe la recepcion lo creamos
                        // con los datos preparados al validar las OC y Recepcion
                        if( recepcionSelected.getIdentifier() == null ) {

                                ocSelected.setReceivingId(createReciving(recepcionSelected));
                                recepcionSelected = getRecepcion(ocSelected.getReceivingId());

                            if (ocSelected.getReceivingId() != null || ocSelected.getReceivingId() > 0 ){
                                updateOcReceivingId(ocSelected.getIdentifier(), ocSelected.getReceivingId());
                                guardarItemRecepcion();
                                isFoundReceving = true;
                            }

                        }else{ // Si existe lo recuperamos la recepcion

                            Log.i("RECE","Receving ID "+ocSelected.getReceivingId() );
                            Log.i("RECE","Recepcion Selected ID "+recepcionSelected.getIdentifier() );

                            if( ocSelected.getReceivingId().longValue() != recepcionSelected.getIdentifier()){
                                recepcionSelected = getRecepcion(ocSelected.getReceivingId());
                            }
                        }
                        // TODO Verificamos si puede recepcionar productos que no esten en la OC
                        if( ocSelected.getRcvUnordItems() != null )
                        {
                            if( !ocSelected.getRcvUnordItems().toString().equalsIgnoreCase("N") )
                            {
                                guardarItemRecepcion();
                            }
                        }

                    } else {  // si ya existe la recepcion
                            Log.i("RECE", "OcSelected ID: "+ocSelected.getReceivingId()+"  S/N : "+ocSelected.getRcvUnordItems());
                            // TODO Verificamos si puede recepcionar productos que no esten en la OC
                            if( ocSelected.getRcvUnordItems() != null )
                            {
                                if( !ocSelected.getRcvUnordItems().toString().equalsIgnoreCase("N") )
                                {
                                    guardarItemRecepcion();
                                }
                            }else{ guardarItemRecepcion(); }
                    }
                    //</editor-fold>
                    mostrarUltimoCargado();
                    deactivateComponents();
                    etDetailCodigo.requestFocus();

                }else { // Validamos que tenga cargado la cantidad
                    etDetailQty.setError("CANTIDAD INCORRECTA...");
                    return;
                }
            }


            /*
            *   metodo para guardar los item de recepcion
            */
            private boolean guardarItemRecepcion() {

                // TODO validamos que cantidad no supere a la de la orden..... ( Pedido Maria )
                Log.i("CANTIDAD","Cant.OC : "+itemOC.getQuantity());
                Log.i("CANTIDAD","Cant.PDA : "+itemOC.getQuantity());

                /*if ( itemOC.getQuantity() >= Double.valueOf(etDetailQty.getText().toString()) )
                {
                    etDetailQty.setError("Cantidad supera el pedido");
                    return false;
                }*/

                // TODO mandamos a la Base de datos el item
                InvPoReceivingItem recItem = new InvPoReceivingItem();
                recItem.setReceivingId(ocSelected.getReceivingId().longValue());
                recItem.setPurchOrderId(ocSelected.getIdentifier());
                recItem.setOrgId(ocSelected.getOrgId());
                recItem.setUnitId(ocSelected.getUnitId());
                recItem.setItemType("PRODUCTO");
                recItem.setItemNumber(0l);
                recItem.setQuantity(Double.valueOf(etDetailQty.getText().toString()));
                recItem.setDescription(itemProducto.getDescription());
                recItem.setCreatedBy(userLoged.getName());
                recItem.setCreateOn(new Date());
                recItem.setInvMaterialId(itemProducto.getIdentifier());
                recItem.setInvMtlBarCode(itemProducto.getBarCode());
                recItem.setItemUomId(unidadMedidaSelected.getIdentifier());
                recItem.setDeviceId(deviceID); // Identificador del Dipositivo....

                // Enviamos a la base de datos ....
                InvPoReceivingItemApi api = Cliente.getClient(urlBase).create(InvPoReceivingItemApi.class);
                Call<Integer> call  = api.createItem(recItem);

                /*
                 *  Guardamos el item de recepcion
                 */
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if( response.isSuccessful() && response.code() == 201 ) // 201 created status
                        {
                            Log.i("POST","Item Creado : "+response.body());
                        }else{
                            Log.i("POST","ERROR AL CREAR ITEM RECEPCION");
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error : "+t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                return  true;
            }

            /**
            * Metodo encargado de actulizar la columna RECEVING_ID en la tabla PO_PURCHACE_ORDERS
            * @param identifierOc
            * @param receivingId
            */
            private void updateOcReceivingId(Long identifierOc, Integer receivingId) {
                PoPurchaseOrdersWSApi api = Cliente.getClient(urlBase).create(PoPurchaseOrdersWSApi.class);
                Call<Void>  call  = api.updateReceivingId(identifierOc, receivingId.longValue());

                try {
                    Thread hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Response<Void> httpResp = call.execute();
                                if( httpResp.code() != 202) {  // ACEPTED
                                    ocSelected.setReceivingId(null);
                                }else{

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    hilo.start();
                    hilo.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        deactivateComponents();
    }

    /**
    * <p> Metodo encargado de recuperar la cantidad ya recepcionada desde la base de datos via API<p/>
    * @param itemUomId
    * @param invMatrialId
    * @param purchOrderId
    * @return
    */
    private Double getTotalQtyFromApi(Long itemUomId, Long invMatrialId, Long purchOrderId)
    {
        InvPoReceivingItemApi api =   Cliente.getClient(urlBase).create(InvPoReceivingItemApi.class);
        Call<InvPoReceivingItemQTY> call = api.getQtyItemProduct(itemUomId, invMatrialId, purchOrderId);

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<InvPoReceivingItemQTY> httpResp = call.execute();
                        if( httpResp.code() == 200 )
                        {
                             InvPoReceivingItemQTY qty = httpResp.body();
                             if( qty != null )
                             {
                                 itemProdQty = qty.getQuantity();
                             }else{
                                 itemProdQty = 0.0;
                             }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemProdQty;
    }


    /**
    * <p> Metodo encargado de marcar como finalizado una recepcion </p>
    * @param identifierOc
    * @param receivingId
    * @return
    */
    private boolean confirmReceving(Long identifierOc, Integer receivingId)
    {

        PoPurchaseOrdersWSApi api = Cliente.getClient(urlBase).create(PoPurchaseOrdersWSApi.class);
        Call<Void>  call  = api.confirmReceivingId(identifierOc, receivingId.longValue());
        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<Void> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200) {
                                isConfirm = true;
                        }else{
                            isConfirm = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isConfirm;
    }

    private void mostrarUltimoCargado()
    {
        tvLastCode.setText(itemProducto.getBarCode());
        //tvLastUm.setText(itemOC.getUomName()); //TODO este hay que tomar del item selecconado al cargar......
        tvLastUm.setText(unidadMedidaSelected.getName());
        tvLastQty.setText(etDetailQty.getText());
    }

    /**
     *  VALIDACION ORDEN DE COMPRAS CON RECEPCION
     */
    private void validOcVsRecepcion() {

        /*  Aca verificamos que no existe un recepcion ya que el valor de las columna
         *  ReceivingId en la tabla de Orden de compras corresponde al Id de la recepcion
         *  sino creamos uno temporal que se confirmara al guardar el primer item de producto.-
         */
        Log.i("RECEPCION", "OC SELECTED : "+ocSelected.getReceivingId() );

        if (ocSelected.getReceivingId() == 0 || ocSelected.getReceivingId() == null){

            //TODO Preparamos la nueva RECEPCION a Confirmar al guardar el primer item Recepcionado.
            recepcionSelected = new InvReceiving();
            recepcionSelected.setOrgId(ocSelected.getOrgId());
            recepcionSelected.setUnitId(ocSelected.getUnitId());
            recepcionSelected.setStatus("INGRESADO");
            recepcionSelected.setSiteId(ocSelected.getSiteId());
            recepcionSelected.setReceivingClass("STANDARD");
            recepcionSelected.setCreatedBy(userLoged.getName());
            recepcionSelected.setVendorId(ocSelected.getVendorId());
            recepcionSelected.setVendorSiteId(ocSelected.getVendorSiteId().longValue());
            recepcionSelected.setWarehouseId(depositoSelected.getIdentifier().longValue());
            recepcionSelected.setReceiverId(userLoged.getIdentifier());
            recepcionSelected.setPurchaseOrderId(ocSelected.getIdentifier());
            recepcionSelected.setProgram("Avanza_Mobile_Receivings");
            recepcionSelected.setReceiptPath("RECEPCIONES_OC");

            Log.i("RECEPCION", "RECEPCION PREPARADA PARA CREARSE ...." );
            isFoundReceving = false; // Avisamos que no exite ninguna recepcion asociada

        }else {

            recepcionSelected = getRecepcion(ocSelected.getReceivingId());
            Log.i("RECEPCION", "ID DE RECEPCION : "+recepcionSelected.getIdentifier());
            if ( recepcionSelected != null ) {
                isFoundReceving = true;
            }else{
                isFoundReceving = false;
            }
        }
    }

    /**
    *
    * @param receiving
    * @return
    */
    private Integer createReciving(InvReceiving receiving) {
        InvReceivingApi api = Cliente.getClient(urlBase).create(InvReceivingApi.class);
        Call<Long> call = api.createReceiving(receiving);

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<Long> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            Log.i("CREATE_RECEIVING","ID_RECEIVING : "+httpResp.body());
                            ocSelected.setReceivingId(httpResp.body().intValue());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ocSelected.getReceivingId();
    }

    /**
    * Metodo encargado de validar que exista el item en la orden de compras
    *
    * @param identiferOC
    * @param itemProducto
    * @return
    */
    private boolean verficarItemEnOC(Long identiferOC, Long itemProducto) {
        PoPurchOrdersProdsVwApi api = Cliente.getClient(urlBase).create(PoPurchOrdersProdsVwApi.class);
        Call<PoPurchOrdersProdsVw> call = api.getItemOc( identiferOC, itemProducto);

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<PoPurchOrdersProdsVw> httpResp = call.execute();

                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            itemOC = httpResp.body();
                            isFoundProdOnOc = true;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isFoundProdOnOc;
    }

    //<editor-fold desc="Check Items">
    /**
    * Chequea si los datos son correctos y validos
    * @return falso o verdadero
    */
    private boolean CheckItems() {
        if( etDetailCodigo.length() > 0 )
        {
            // Buscamos el producto si esta creado
            itemProducto = getProucto(etDetailCodigo.getText().toString().trim());
            if( itemProducto ==  null )
            {
                etDetailCodigo.setError("EL PRODUCTO NO ESTA CREADO");
                return false;
            }else{

                    // TODO ACA HAY QUE VALIDAR SI ES QUE PUEDE O NO RECIBIR ITEMS QUE NO ESTAN EN LA ORDEN RcvUnordItems() tiene que tener valor  "S"
                    // PARA HABILITAR LA RECEPCION DE PRODUCTOS QUE NO ESTEN EN LA ORDEN DE COMPRAS
                    if( ocSelected.getRcvUnordItems() == "N" || ocSelected.getRcvUnordItems() == null )
                    {
                        if( verficarItemEnOC(ocSelected.getIdentifier(), itemProducto.getIdentifier()) )
                        {
                            spListaUm.setSelection(getOumPosition( itemOC.getItemUomId()));
                            return true;
                        }else{
                            etDetailCodigo.setError("ESTE PRODUCTO NO EXITE EN EL PEDIDO...");
                            return false;
                        }

                    }else {
                        if( !verficarItemEnOC(ocSelected.getIdentifier(), itemProducto.getIdentifier()) ){
                            spListaUm.setSelection(getOumPosition(itemProducto.getBaseUomId()));
                            Toast.makeText(getApplicationContext(),"VERIFIQUE BIEN ESTE PRODUCTO",Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }

            }

        }else{
            etDetailCodigo.setError("INGRESE UN CODIGO DE PRODUCTO");
            return false;
        }

    }
    //</editor-fold>

    //<editor-fold desc="getOumPosition">
    /**
     * Seleccona la posicion del la unidad de medida en la lista
     * @param baseUomId
     * @return
     */
    private int getOumPosition(Long baseUomId) {
        for (int i=0 ; i < listUm.size(); i++){
             if( listUm.get(i).toString().startsWith(""+baseUomId) ){
                 return i;
             }
        }
        return 0;
    }
    //</editor-fold>

    //<editor-fold desc="CargarUnidadMedida">
    /**
    * Metodo encargado de cargar la lista de valores par el spiiner de Unidad de Medidas
    * @return
    */
    private List<InvUnitOfMeasure> cargarUnidadMedida()
    {
        InvUnitOfMeasureApi umApi = Cliente.getClient(urlBase).create(InvUnitOfMeasureApi.class);
        Call<List<InvUnitOfMeasure>> call = umApi.getAll();

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<List<InvUnitOfMeasure>> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            listUm = httpResp.body();
                        }else{
                            if( httpResp.code() == 204 ){
                                Log.i("PROD : ","NO HAY LISTA DE UNIDADES DE MEDIDA");
                                listUm = null;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listUm;
    }
    //</editor-fold>

    //<editor-fold desc="getRecepcion">
    /**
     * <p> Recupera la recepcion asignada e la OC </p>
     * @param receivingId
     * @return
     */
    private InvReceiving getRecepcion(Integer receivingId) {
        InvReceivingApi api = Cliente.getClient(urlBase).create(InvReceivingApi.class);
        Call<InvReceiving> call = api.getReceiving(Long.valueOf(receivingId));

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<InvReceiving> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            recepcionSelected = httpResp.body();
                            Log.i("RECEPCOIN", " RECEPCION : "+httpResp.body());
                        }else{
                            Log.i("RECEPCOIN"," NO EXISTE LA RECEPCION : "+receivingId);
                            recepcionSelected = null;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recepcionSelected;
    }
    //</editor-fold>

    //<editor-fold desc="findItemOC">
    /**
    * Validar Producto si existe en la Orden de compras
    * @param identifierOc
    * @param identifierProduct
    * @return
    */
    private PoPurchOrdersProdsVw findItemonOC(Long identifierOc, Long identifierProduct) {

        PoPurchOrdersProdsVwApi api  = Cliente.getClient(urlBase).create(PoPurchOrdersProdsVwApi.class);
        Call<PoPurchOrdersProdsVw>  call = api.getItemOc(identifierOc, identifierProduct);
        itemOC = null;

        try {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<PoPurchOrdersProdsVw> httpResp = call.execute();
                        if (httpResp.isSuccessful() && httpResp.code() == 200) {
                            itemOC = httpResp.body();
                        }
                    }catch (IOException io){
                        io.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemOC;
    }
    //</editor-fold>

    //<editor-fold desc="getProducto">
    /**
    * Buscar articulo si existe en el systema
    * @param txtCode
    * @return
    */
    private InvMaterial getProucto(String txtCode) {
        InvMaterialApi prodApi = Cliente.getClient(urlBase).create(InvMaterialApi.class);
        Call<InvMaterial> call = prodApi.getProducto(txtCode);

        try{
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<InvMaterial> httpResp = call.execute();
                        if( httpResp.isSuccessful() && httpResp.code() == 200 )
                        {
                            itemProducto = httpResp.body();
                        }
                        else if( httpResp.code() == 204 ){
                            Log.i("PROD : ","no existe item");
                            itemProducto = null;
                        }
                    } catch (IOException io){
                        io.printStackTrace();
                    }
                }
            });
            hilo.start();
            hilo.join();

        }catch (Exception e ){
            e.printStackTrace();
        }
        return itemProducto;
    }
    //</editor-fold>

    /**
     * Evento que se dispara cuando se pulsa Enter en el campo codigo de producto.-
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if( event.getKeyCode() == KeyEvent.KEYCODE_ENTER )
        {
            isItemsOk = CheckItems();
            if( isItemsOk )
            {
                tvDetailDescription.setText(itemProducto.getDescription());
                etDetailQty.setEnabled(true);
                etDetailQty.requestFocus();
                activateComponents();
            }

        }
        return false;
    }

    private void activateComponents() {
        spListaUm.setEnabled(true);
        btnRecibir.setEnabled(true);
        btnRecibir.setVisibility(View.VISIBLE);
        etDetailQty.setEnabled(true);
    }

    private void deactivateComponents() {
        spListaUm.setEnabled(false);
        btnRecibir.setEnabled(false);
        btnRecibir.setVisibility(View.GONE);
        etDetailQty.setEnabled(false);

        etDetailCodigo.getText().clear();
        tvDetailDescription.setText("");
        etDetailQty.getText().clear();

    }


    /**
     * Pulsar BackProcess
     */
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(" Cerrar ventana Registro ?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DetailReception.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }) ;

        AlertDialog msg = alertBuilder.create();
        msg.show();

    }
}