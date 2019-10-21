package RestWebService;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import models.Booking;
import models.Bookingdetail;
import models.TravelPackage;
import util.BookingNumberGenerator;

@Path("/booking")
public class BookingService {
	
	//URL: http://localhost:8181/TravelExperts/rs/booking/makeabooking
	@POST
	@Path("/makeabooking")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String makeABooking(String jsonString) {
		Gson gson = new Gson();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		String[] destinations = {"Bahamas","Tahiti","Asia","Europe"};
		
		Booking bookingReceived = gson.fromJson(jsonString, Booking.class);
		if(bookingReceived != null) {
			bookingReceived.setBookingNo(BookingNumberGenerator.generateBookingNumber(6));
			//update persistence context
			em.getTransaction().begin();
			em.persist(bookingReceived);
			em.getTransaction().commit();
			
			Bookingdetail bookingDetail = new Bookingdetail();
			TravelPackage travelPackage = em.find(TravelPackage.class, bookingReceived.getPackageId());
			if(travelPackage != null) {
			bookingDetail.setBasePrice(travelPackage.getPkgBasePrice());
			bookingDetail.setAgencyCommission(travelPackage.getPkgAgencyCommission());
			bookingDetail.setTripStart(travelPackage.getPkgStartDate());
			bookingDetail.setTripEnd(travelPackage.getPkgEndDate());
			bookingDetail.setDescription(travelPackage.getPkgDesc());
			bookingDetail.setDestination(destinations[travelPackage.getPackageId()-1]);
			}
			
			Booking bookedTrip  = getLastInsertedBooking(bookingReceived.getCustomerId());
			bookingDetail.setBooking(bookedTrip);
			bookingDetail.setBookingDetailId(bookedTrip.getBookingId());
			em.getTransaction().begin();
			em.persist(bookingDetail);
			em.getTransaction().commit();
			//close entity manager
			em.close();
			
			return "Congratulations! Your trip is booked";
		}else {
			return "Error while booking. Please try again!";
		}
	}
	
	public Booking getLastInsertedBooking(int customerId) {
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		
		Query query = em.createQuery("SELECT b FROM Booking b WHERE b.customerId=:id ORDER BY b.bookingId DESC", Booking.class);
		query.setParameter("id", customerId);
		query.setMaxResults(1);
		Booking booking =(Booking) query.getSingleResult();
		em.close();
		return booking;
			
	}
}
