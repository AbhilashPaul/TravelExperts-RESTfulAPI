package RestWebService;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;

import models.Customer;

//URL: http://localhost:8181/TravelExperts/rs/login/authenticateuser

@Path("/login")
public class LoginService {

	@POST
	@Path("/authenticateuser")
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String authenticateUser(String authenticationString) {
		
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		//convert user info from client to customer client
		Customer user = gson.fromJson(authenticationString, Customer.class);
		
		//get customer details from database
		TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.custUsername=:username", Customer.class);
		query.setParameter("username", user.getCustUsername());
		Customer cust = query.getSingleResult();
		
		//verify user credentials
		if(BCrypt.checkpw(user.getCustPassword().trim(),cust.getCustPassword().trim())) {
			//return the customer details as json string
			return gson.toJson(cust);
		} else {
			return "Invalid Login! Please try again.";
		}
	}
}
