package test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class login {

	@Test
public void logintest() throws Exception
{
	JSONObject obj=new JSONObject();
	obj.put("grant_type", "password");
	obj.put("password", "12345678");
	obj.put("username", "himanshumehra364@gmail.com");
	
String res=given().header("Content-Type","application/json").body(obj.toString()).post("https://api-preprod.ailiens.com/login/users/end-user/token").asString();
//Response res=given().header("Content-Type","application/json").body(obj.toString()).post("https://api-preprod.ailiens.com/login/users/end-user/token");
//System.out.println(res.getStatusCode());
//ResponseBody body = res.getBody();
//System.out.println(body.asString());
//System.out.println(body.getClass().getName());
System.out.println("response string is-"+res);
}
}
