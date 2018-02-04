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
    private static String username="msimon";
    private static String collection="business";
    private static String password="QnUUqbvOrN/ZXQc1x";

    public static void main(String [] args){

      MongoCredential credenciales=MongoCredential.createCredential(username,collection,password.toCharArray());

      MongoClient client=new MongoClient(new ServerAddress("10.0.101.195",27017), Arrays.asList(credenciales));

      MongoDatabase database=client.getDatabase("business");

      MongoCollection<Document> coll=database.getCollection("bankRegistry");

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

        try{
            while(cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }
        }finally {
            cursor.close();
        }


      client.close();
    }
}
