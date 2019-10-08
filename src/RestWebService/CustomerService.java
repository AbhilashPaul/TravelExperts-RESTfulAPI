package RestWebService;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;

import models.Booking;
import models.Bookingdetail;
import models.Customer;




@Path("/customer")
public class CustomerService {

	//URL: http://localhost:8181/TravelExperts/rs/customer/getcustomer/104
	@GET
	@Path("/getcustomer/{customerid}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getCustomerById(@PathParam("customerid") int customerId) {
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		Customer cust = em.find(Customer.class, customerId);
		if(cust != null) {
			return gson.toJson(cust);
		} else {
			return "Oops... No record found!";
		}
	}
	
	//URL: http://localhost:8181/TravelExperts/rs/customer/updatecustomer
	@PUT
	@Path("/updatecustomer")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes (MediaType.APPLICATION_JSON)
	public String putAgent(String jsonString) {
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		Customer cust = gson.fromJson(jsonString, Customer.class);
		
		em.getTransaction().begin();
		Customer existingCustomer= em.find(Customer.class , cust.getCustomerId());
		
		if(existingCustomer != null) {
			
			existingCustomer.setCustFirstName(cust.getCustFirstName());
			existingCustomer.setCustLastName(cust.getCustLastName());
			existingCustomer.setCustAddress(cust.getCustAddress());
			existingCustomer.setCustPostal(cust.getCustPostal());
			existingCustomer.setCustCity(cust.getCustCity());
			existingCustomer.setCustProv(cust.getCustProv());
			existingCustomer.setCustCountry(cust.getCustCountry());
			existingCustomer.setCustBusPhone(cust.getCustBusPhone());
			existingCustomer.setCustHomePhone(cust.getCustHomePhone());
			existingCustomer.setCustEmail(cust.getCustEmail());
			existingCustomer.setCustUsername(cust.getCustUsername());
			existingCustomer.setCustPassword(BCrypt.hashpw(cust.getCustPassword(), BCrypt.gensalt()));
			
			//update persistence context
			em.persist(existingCustomer);
			em.getTransaction().commit();	//Committing transaction will update database
			
			//close entity manager
			em.close();
			return "Successfully updated";
		}else {
			return "Error while trying to update. Please try again!";
		}
	}
	
	//URL: http://localhost:8181/TravelExperts/rs/customer/getbookings/104
	@GET
	@Path("/getbookings/{customerid}")
    @Produces(MediaType.APPLICATION_JSON)
	public String getBookings(@PathParam("customerid") int customerId) {
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		
		TypedQuery<Bookingdetail> query =
		em.createQuery("SELECT b FROM Bookingdetail b WHERE b.booking.customerId=:id GROUP BY b.booking.bookingId",
		Bookingdetail.class); query.setParameter("id", customerId);
		
		List<Bookingdetail> bookings = query.getResultList();
				
		if(bookings != null) {
			return gson.toJson(bookings);
		} else {
			return "No bookings found!";
		}
	}
	
	//URL: http://localhost:8181/TravelExperts/rs/customer/updatecustomer
	@POST
	@Path("/createcustomer")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes (MediaType.APPLICATION_JSON)
	public String addAgent(String jsonString) {
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		Customer cust = gson.fromJson(jsonString, Customer.class);
		
		if(cust != null) {
			em.getTransaction().begin();
			//update persistence context
			em.merge(cust);
			em.getTransaction().commit();
			//close entity manager
			em.close();
			return "Successfully created a new customer account";
		}else {
			return "Error while creating new account. Please try again!";
		}
	}
}
