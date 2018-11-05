package co.com.microservices.oferta.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import co.com.microservices.oferta.model.Oferta;

@EnableScan
public interface OfertaRepository extends CrudRepository<Oferta, String>{
	public List<Oferta> findByIdOferta(String idAdmin);
	public Oferta findByIdNegocio(String idNegocio);
}
