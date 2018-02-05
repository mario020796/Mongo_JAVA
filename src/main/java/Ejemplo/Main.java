package Ejemplo;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import java.util.Arrays;
import org.bson.Document;
import java.util.Scanner;

public class Main {


    private static Scanner sc=new Scanner(System.in);
    private static String username;
    private static String collection;
    private static String password;
    private static String host;
    private static String table;

    public static void main(String [] args){
        System.out.println("Introduzca su usuario");
        username=sc.nextLine();
        System.out.println("Introduzca la colección que desee visualizar");
        collection=sc.nextLine();
        System.out.println("Introduzca su contraseña");
        password=sc.nextLine();

        MongoCredential credenciales=MongoCredential.createCredential(username,collection,password.toCharArray());

        System.out.println("Introduzca el host");
        host=sc.nextLine();

        MongoClient client=new MongoClient(new ServerAddress(host,27017), Arrays.asList(credenciales));

        MongoDatabase database=client.getDatabase(collection);

        System.out.println("Introduzca la tabla a consultar");
        table=sc.nextLine();

        MongoCollection<Document> coll=database.getCollection(table);

        System.out.println("Introduzca su banco");

        String banco=sc.nextLine();

        System.out.println("Introduzca el error que desea buscar");

        String error=sc.nextLine();

        System.out.println("Introduzca la fecha a partir de la que desea buscar (incluida) Formato: YYYY-MM-DD");

        String fecha=sc.nextLine();

        //Recuperar listado completo de nombres en formato JSON
        MongoCursor<Document> cursor=coll.find(and(
                 eq("systemBankId",banco)
                 ,eq("lastRequestStatusDetail.providerErrorCode",error.toUpperCase())
                 ,gte("dataUpdatedUntil",fecha))).iterator();

        System.out.println("userId"+"\t"+"dataUpdatedUntil");

        try{
            while(cursor.hasNext()){
                System.out.println(cursor.next().get("userId")+"\t"+cursor.next().get("dataUpdatedUntil"));
                }
            }finally {
                cursor.close();
            }


        client.close();
    }
}
