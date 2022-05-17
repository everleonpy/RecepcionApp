package py.com.softpoint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import py.com.softpoint.apiclient.InvMaterialApi;
import py.com.softpoint.apiclient.InvReceivingApi;
import py.com.softpoint.apiclient.InvUnitOfMeasureApi;
import py.com.softpoint.apiclient.PoPurchOrdersProdsVwApi;
import py.com.softpoint.apiclient.PoPurchaseOrdersWSApi;
import py.com.softpoint.pojos.InvMaterial;
import py.com.softpoint.pojos.InvPoReceivingItem;
import py.com.softpoint.pojos.InvReceiving;
import py.com.softpoint.pojos.InvUnitOfMeasure;
import py.com.softpoint.pojos.InvWarehouse;
import py.com.softpoint.pojos.PayVendor;
import py.com.softpoint.pojos.PoPurchOrdersProdsVw;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import py.com.softpoint.pojos.User;
import py.com.softpoint.utils.Cliente;
import retrofit2.Call;
import retrofit2.Response;

public class DetailReception extends AppCompatActivity  implements View.OnKeyListener {

    private TextView nroOC, proveedorName,depositoName, tvDetailDescription;
    private TextView tvLastCode, tvLastUm, tvLastQty;
    private EditText etDetailCodigo, etDetailQty;
    private Spinner spListaUm;
    private Button btnRecibir;
    private String urlBase;

    // Varialbles de datos
    private PayVendor proveedorSelected;
    private PoPurchaseOrdersVw ocSelected;
    private InvWarehouse depositoSelected;
    private User userLoged;
    private boolean isItemsOk;
    private InvMaterial itemProducto;
    private InvUnitOfMeasure unidadMedidaSelected;
    private PoPurchOrdersProdsVw itemOC;
    private List<InvUnitOfMeasure> listUm;
    private InvReceiving recepcionSelected;

    //Atributo auxiliar para validar si existe el producto imputado en la Orden de compras
    private boolean isFoundProdOnOc = false;
    private boolean isFoundReceving = false;

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

        initComponent();
        validOcVsRecepcion();
        listUm = cargarUnidadMedida();
        prepararSpinerUm();
    }



    //<editor-fold desc="prepareSpinerUm">
    /**
    * Metodo que prepara el spiner de unidades de medida
    */
    private void prepararSpinerUm() {
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

        // Buttons
        btnRecibir = findViewById(R.id.btnRecibirItem);
        btnRecibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( etDetailQty.getText().toString().length() > 0  ) {

                    //<editor-fold desc="RECEIVING_ID Verficamos">
                    if ( !isFoundReceving )
                    {
                        // Sino existe la recepcion lo creamos
                        if( recepcionSelected.getIdentifier() == 0 || recepcionSelected.getIdentifier() == null ) {
                            ocSelected.setReceivingId(createReciving(recepcionSelected));
                            if (ocSelected.getReceivingId() != null){
                                updateOcReceivingId(ocSelected.getIdentifier(), ocSelected.getReceivingId());
                                isFoundReceving = true;
                            }
                        }else{ // Si existe lo recuperamos la recepcion
                            recepcionSelected = getRecepcion(ocSelected.getReceivingId());
                        }

                    } else {
                            // TODO Verificamos si puede recepcionar productos que no esten en la OC

                    }
                    //</editor-fold>

                    mostrarUltimoCargado();
                    deactivateComponents();

                }else { // Validamos que tenga cargado la cantidad
                    etDetailQty.setError("INGRESE CANTIDAD");
                    return;
                }
            }

            /*
                metodo para guardar los item de recepcion
             */
            private boolean guardarItemRecepcion() {

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

    private void mostrarUltimoCargado() {

        tvLastCode.setText(itemProducto.getBarCode());
        tvLastUm.setText(itemOC.getUomName()); //TODO este hay que tomar del item selecconado al cargar......
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
        if (ocSelected.getReceivingId() == 0 || ocSelected.getReceivingId() != null){

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

            isFoundReceving = false; // Avisamos que no exite ninguna recepcion asociada

        }else {
            recepcionSelected = getRecepcion(ocSelected.getReceivingId());
            if (recepcionSelected != null) {
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
            itemProducto = getProucto(etDetailCodigo.getText().toString().trim());
            if( itemProducto ==  null )
            {
                etDetailCodigo.setError("EL PRODUCTO NO ESTA CREADO");
                return false;
            }else{

                    //TODO ACA HAY QUE VALIDAR SI ES QUE PUEDE O NO RECIBIR ITEMS QUE NO ESTAN EN LA ORDEN.
                    if( ocSelected.getRcvUnordItems() == "N")
                    {
                        if(verficarItemEnOC(ocSelected.getIdentifier(), itemProducto.getIdentifier())){
                            spListaUm.setSelection(getOumPosition( itemOC.getItemUomId()));
                            return true;
                        }else{
                            etDetailCodigo.setError("ESTE PRODUCTO NO EXITE EN EL PEDIDO...");
                            return false;
                        }

                    }else {
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

    //<editor-fold desc="getRecepcion">
    /**
     * Recupera la recepcion asignada e la OC
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
                        }else{
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
     *
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