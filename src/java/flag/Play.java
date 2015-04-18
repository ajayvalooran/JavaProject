package flag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
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
        return randomGenerator.nextInt(7);
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
        String query = "select * from flag where id =" + get;
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Name:" +rs.getString("name"));
                JsonObjectBuilder json = Json.createObjectBuilder()
                        .add("answer", rs.getString("name"))
                        .add("opt1", rs.getString("opt1"))
                        .add("opt2", rs.getString("opt2"))
                        .add("opt3", rs.getString("opt3"))
                        .add("opt4", rs.getString("opt4"));

                play = json.build();
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());

        }
        return play;
    }

}
