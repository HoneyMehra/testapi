package test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class demo {

	@Test
	public void login() throws Exception
	{
		String resturl="https://api-preprod.ailiens.com/login/users/end-user/token";
		URIBuilder builder= new URIBuilder(resturl);
		HttpPost httppost = new HttpPost(builder.build());
		httppost.setHeader("Content-Type", "application/json");
		httppost.setHeader("module", "odin");	
		//httppost.setParams("","");
		JSONObject obj=new JSONObject();
		obj.put("grant_type", "password");
		obj.put("password", "12345678");
		obj.put("username", "himanshumehra364@gmail.com");
		StringEntity entity=new StringEntity(obj.toString());
		httppost.setEntity(entity);		
		HttpClient client= HttpClients.createDefault();
		HttpResponse response= client.execute(httppost);
		int statuscode=response.getStatusLine().getStatusCode();		
		HttpEntity httpentity=response.getEntity();
		String stringresponse= EntityUtils.toString(httpentity, "UTF-8");
		JSONObject object= new JSONObject(stringresponse);
		String accesstoken=object.getJSONObject("data").getString("access_token");
		System.out.println(accesstoken);

		////////////////////////////////////////////////////////////////////////////////////////////////// get style id
		String resturl2="https://api-preprod.ailiens.com/d/apiV2/listing/products";
		URIBuilder build= new URIBuilder(resturl2);
		HttpPost post= new HttpPost(build.build());
		post.setHeader("Content-Type", "application/json");
		post.setHeader("module", "odin");
		JSONObject o= new JSONObject();
		o.put("deeplinkurl", "/products?p=1");
		StringEntity e= new StringEntity(o.toString());
		post.setEntity(e);
		HttpResponse r= client.execute(post);
		HttpEntity en= r.getEntity();
		String re= EntityUtils.toString(en);
		System.out.println("response string is "+re);
		JSONObject ob=new JSONObject(re);
		JSONObject as= ob.getJSONObject("data");
		JSONArray aa= as.getJSONObject("styles").getJSONArray("styleList");
		System.out.println(aa.length());
		String style=aa.getJSONObject(2).getString("id");
		System.out.println(style);
		
		///////////////////////////////////////////////////////////////////////////////////////////////////get sku id
		String url3="https://api-preprod.ailiens.com/d/api/product/details";
		URIBuilder b = new URIBuilder(url3);
		HttpPost p = new HttpPost(b.build());
		p.setHeader("Content-Type", "application/json");
		p.setHeader("module", "odin");
		JSONObject j = new JSONObject();
		j.put("styleId", style);
		p.setEntity(new StringEntity(j.toString()));
		HttpResponse r1= client.execute(p);
		HttpEntity he= r1.getEntity();
		String respons= EntityUtils.toString(he);
		System.out.println("response isss"+respons);
		JSONObject j1= new JSONObject(respons);
		JSONArray aaa= j1.getJSONObject("data").getJSONObject("mainStyle").getJSONArray("skus");
		System.out.println("lenght iss"+aaa.length());
		String skuId= aaa.getJSONObject(0).getString("skuId");
		System.out.println("sku is"+skuId);
		
		/////////////////////////////////////////////////////////////////////update bag
		String url4= "https://api-preprod.ailiens.com/d/api/myBag/v3";
		URIBuilder b1= new URIBuilder(url4);
		HttpPut p1= new HttpPut(b1.build());
		p1.setHeader("Content-Type", "application/json");
		p1.setHeader("module", "odin");
		p1.setHeader("authorization", "Bearer " +accesstoken);
		JSONArray products= new JSONArray();		
		JSONObject j2= new JSONObject();
		j2.put("skuId", skuId);
		j2.put("qty", 1);
		products.put(j2);
		JSONObject az= new JSONObject();
		az.put("products", products);
		StringEntity e1= new StringEntity(az.toString());
		p1.setEntity(e1);
		HttpResponse r3= client.execute(p1);
		System.out.println("status code"+r3.getStatusLine().getStatusCode());
		HttpEntity h1= r3.getEntity();
		String resp= EntityUtils.toString(h1);
		System.out.println("sstring response is "+resp);
		
		
		////////////////////////////////////////checkout
		String url5= "https://api-preprod.ailiens.com/d/apiV2/checkout";
		URIBuilder u1= new URIBuilder(url5);
		HttpPost p3= new HttpPost(u1.build());
		p3.setHeader("Content-Type", "application/json");
		p3.setHeader("module", "odin");
		p3.setHeader("authorization", "Bearer " +accesstoken);
		JSONObject request= new JSONObject();
		JSONObject graphProps= new JSONObject();
		graphProps.put("isBagDetails", true);
		graphProps.put("isPromotionDetails", true);
		graphProps.put("isPricingOverview", true);
		graphProps.put("isApplicablePromotions", true);
		request.put("graphProps", graphProps);
		request.put("type", "read");
		request.put("page", "checkout");
		request.put("pincode", "560001");
		JSONArray guestCart = new JSONArray();
		request.put("guestCart", guestCart);
		JSONArray removeCart = new JSONArray();
		request.put("removeCart", removeCart);
		JSONArray selectedShippingMethods= new JSONArray();
		request.put("selectedShippingMethods", selectedShippingMethods);
		JSONArray updateQtyCart = new JSONArray();
		request.put("updateQtyCart", updateQtyCart);
		request.put("addressId", "edd9b0fd-80b8-45ff-8c38-8f179b1810ff");
		StringEntity ssd= new StringEntity(request.toString());
		p3.setEntity(ssd);
		HttpResponse hr=client.execute(p3);
		HttpEntity hee= hr.getEntity();
		String resp1=EntityUtils.toString(hee);
		System.out.println("string response iss"+resp1);
		JSONObject jo= new JSONObject(resp1);
		int amount=	jo.getJSONObject("data").getJSONObject("overview").getInt("total");
		System.out.println("cart amount is"+amount);
		
		
		//////////////////////////////////////////////////////////////////////////payment
		String urlfinal="https://api-preprod.ailiens.com/d/apiV2/payment/initiatePayment/v2";
		URIBuilder build1 = new URIBuilder(urlfinal);
		HttpPost ps= new HttpPost(build1.build());
		ps.setHeader("Content-Type", "application/json");
		ps.setHeader("module", "odin");
		ps.setHeader("authorization", "Bearer " +accesstoken);
		JSONObject req= new JSONObject();
		req.put("addressId", "4166241b-8f73-4ec0-ad0d-5061d08c46bc");
		JSONObject pricingDetails = new JSONObject();
		//pricingDetails.put("mrp", arg1);
	}
	
	
}
