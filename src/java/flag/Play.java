package flag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c0647711
 */
@Path("/play")
public class Play {

    @GET
    @Produces("application/json")
    public String getFlag() {
        
        return falgGen();
    }

    public int randomGen() {
        System.out.println("Random Gen");
        Random randomGenerator = new Random();
        return (randomGenerator.nextInt(19) + 1);
    }

    public String falgGen() {
        System.out.println("flagGen");
        int newNum = randomGen();
        String result = getName(newNum).toString();
        System.out.println(result);
        return result;
    }

    private JsonObject getName(Integer get) {
         System.out.println("getName");
        JsonObject play = null;
        int count =0;
        int check  = 0;
        do{
        String query = "select count(*) AS count from flag where picked = 'N' and id =" + get;
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                              
                       count = rs.getInt("count");   
                        System.out.println("count "+count);
                        check = 1;
            }
            System.out.println("out count "+count);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());

        } 
        if(count == 0){
            get = randomGen();
        }
        }while(count == 0);
        
            System.out.println("Random number: "+get);
            String query1 = "select * from flag where picked = 'N' and id =" + get;
            String query2 = "update flag set picked = 'N' where id =" + get;
            try (Connection conn1 = Credentials.getConnection()) {
            PreparedStatement pstmt1 = conn1.prepareStatement(query1);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                System.out.println("Name:" +rs1.getString("name"));
                JsonObjectBuilder json = Json.createObjectBuilder()
                        .add("answer", rs1.getString("name"))
                        .add("opt1", rs1.getString("opt1"))
                        .add("opt2", rs1.getString("opt2"))
                        .add("opt3", rs1.getString("opt3"))
                        .add("opt4", rs1.getString("opt4"));

                play = json.build();
            }
            
            PreparedStatement pstmt2 = conn1.prepareStatement(query2);
            boolean updated = pstmt2.execute();
                    
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
                
              
        
        return play;
    }
    
    @PUT
    @Consumes("text/html")
    public void doPut(){
        System.out.println("inside do post");
        String query = "update flag set picked = 'Y'";
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            boolean updated = pstmt.execute();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());

        } 
    }

}
