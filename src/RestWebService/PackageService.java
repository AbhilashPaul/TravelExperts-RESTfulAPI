package RestWebService;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import models.TravelPackage;


//URL: http://localhost:8181/TravelExperts/rs/packages/getallpackages

@Path("/packages")
public class PackageService {

	@GET
	@Path("/getallpackages")
    @Produces(MediaType.APPLICATION_JSON)
	public String getAllPackages() {

		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		Date today = new Date();
		EntityManager em = Persistence.createEntityManagerFactory("TravelExperts").createEntityManager();
		Query query = em.createNamedQuery("TravelPackage.findAll");
		List<TravelPackage> resultList = query.getResultList();
		
		for(TravelPackage pkg : resultList) {
			if(pkg.getPkgStartDate().after(today)) {
				packages.add(pkg);
			}
		}
		
		Gson gson = new Gson();
		return gson.toJson(packages);
	}
}
