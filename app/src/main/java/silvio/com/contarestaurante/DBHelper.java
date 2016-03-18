package silvio.com.contarestaurante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "RACHA_CONTA_DB";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlCreateTabelaCardapio = "CREATE TABLE Cardapio("
                + "id_item INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name_item TEXT,"
                + "price_item VARCHAR"
                + ")";
        String sqlCreateTabelaIntegrantes = "CREATE TABLE Integrantes("
                + "id_integrante INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name_integrante TEXT"
                + ")";
        String sqlCreateTabelaPedidos = "CREATE TABLE Pedidos("
                + "id_pedido INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_pedido_item INTEGER,"
                + "pedido_number INTEGER"
                + ")";
        String sqlCreateTabelaPI = "CREATE TABLE PedidosIntegrantes("
                + "id_PI INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_PI_pedido INTEGER,"
                + "id_PI_integrante INTEGER"
                + ")";

        db.execSQL(sqlCreateTabelaCardapio);
        db.execSQL(sqlCreateTabelaIntegrantes);
        db.execSQL(sqlCreateTabelaPedidos);
        db.execSQL(sqlCreateTabelaPI);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE Cardapio");
        db.execSQL("DROP TABLE Integrantes");
        db.execSQL("DROP TABLE Pedidos");
        db.execSQL("DROP TABLE PedidosIntegrantes");

        onCreate(db);
    }

    //============================== Métodos da Cardapio ===========================================
    public void insertItem(Item item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("name_item", item.getNome());
        cv.put("price_item", item.getPreco());

        db.insert("Cardapio", null, cv);

        db.close();
    }

    public List<Item> selectTodosOsItens(){
        List<Item> listItens = new ArrayList<Item>();
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelectTodosItens = "SELECT * FROM Cardapio";

        Cursor c = db.rawQuery(sqlSelectTodosItens, null);

        if(c.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(c.getInt(0));
                item.setNome(c.getString(1));
                item.setPreco(c.getDouble(2));

                listItens.add(item);
            } while (c.moveToNext());
        }

        return listItens;
    }

    public void DropCardapioTable(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE Cardapio");  //Deletes the data and the table structure
        db.execSQL("CREATE TABLE Cardapio(" //Creates another empty table
                + "id_item INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name_item TEXT,"
                + "price_item VARCHAR"
                + ")");
    }

    //============================== Métodos da Integrantes=========================================

    public void insertIntegrante(Integrante integrante){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv2 = new ContentValues();

        cv2.put("name_integrante", integrante.getName());

        db.insert("Integrantes", null, cv2);

        db.close();
    }

    public List<Integrante> selectTodosOsIntegrantes(){
        List<Integrante> listIntegrantes = new ArrayList<Integrante>();
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelectTodosIntegrantes = "SELECT * FROM Integrantes";

        Cursor c = db.rawQuery(sqlSelectTodosIntegrantes, null);

        if(c.moveToFirst()) {
            do {
                Integrante integrante = new Integrante();
                integrante.setId(c.getInt(0));
                integrante.setName(c.getString(1));

                listIntegrantes.add(integrante);
            } while (c.moveToNext());
        }


        return listIntegrantes;
    }

    public void DropIntegrantesTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE Integrantes");
        db.execSQL("CREATE TABLE Integrantes("
                + "id_integrante INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name_integrante TEXT"
                + ")");
    }

    //====================== Metodos da Pedidos ====================================================
    public void insertPedido(Item item, int numero_integrantes){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id_pedido_item", item.getId());
        cv.put("pedido_number", numero_integrantes);


        db.insert("Pedidos", null, cv);

        db.close();
    }

    public int getIdLastPedido(){   //método usado para obter o id do último pedido feito
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Pedidos", null);
        c.moveToLast();
        int idPedido = c.getInt(0);

        return idPedido;
    }

    public void DropPedidosTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE Pedidos");   //Deletes the entire table
        db.execSQL("CREATE TABLE Pedidos("  //Creates a new table
                + "id_pedido INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_pedido_item INTEGER,"
                + "pedido_number INTEGER"
                + ")");
    }

    //====================== Metodos da PedidosIntegrantes========================================

    public void insertPI(int id_Pedido, Integrante integrante){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("id_PI_pedido", id_Pedido);
        cv.put("id_PI_integrante", integrante.getId());

        db.insert("PedidosIntegrantes", null, cv);

        db.close();
    }

    public void DropPITable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE PedidosIntegrantes");
        db.execSQL("CREATE TABLE PedidosIntegrantes("
                + "id_PI INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_PI_pedido INTEGER,"
                + "id_PI_integrante INTEGER"
                + ")");
    }
}
