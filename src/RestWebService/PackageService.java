package RestWebService;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;


//URL: http://localhost:8181/TravelExperts/rs/packages/getallpackages

@Path("/packages")
public class PackageService {

	@GET
	@Path("/getallpackages")
    @Produces(MediaType.APPLICATION_JSON)
	public String getAllPackages() {

		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		Query query = em.createNamedQuery("Package.findAll");
		List<Package> list = query.getResultList();
		
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
