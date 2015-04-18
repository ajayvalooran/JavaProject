/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flag;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author c0647711
 */
@Path("/high")
public class highscore {
    @GET
    @Produces("text/html")
    public String getScore() {        
        return getHigh();
    }
    
    public String getHigh(){
        String query1 = "select highScore from highScore";
        int count = 0;
            try (Connection conn1 = Credentials.getConnection()) {
            PreparedStatement pstmt1 = conn1.prepareStatement(query1);
            ResultSet rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                count = rs1.getInt("highScore");
            }
                    
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return Integer.toString(count);    
    }
    
    @POST
    @Consumes("application/json")
    public boolean doPut(String data){
         System.out.println("inside do post:");
         JsonObject json = Json.createReader(new StringReader(data)).readObject();
         int score = json.getInt("score");
         System.out.println("inside do post: " + score);
        String query = "update highScore set highScore = ?";
        try (Connection conn = Credentials.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, score);
            boolean updated = pstmt.execute();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());

        } 
        
        return true;
    }
}
