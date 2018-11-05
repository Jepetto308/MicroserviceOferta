package co.com.microservices.oferta.api;

import co.com.microservices.oferta.model.ErrorDetail;
import co.com.microservices.oferta.model.GeneralRequest;
import co.com.microservices.oferta.model.JsonApiBodyResponseSuccess;
import co.com.microservices.oferta.model.Oferta;
import co.com.microservices.oferta.repository.OfertaRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-09-14T10:21:40.463-05:00")

@Controller
public class OfertaApiController implements OfertaApi {

    private static final Logger log = LoggerFactory.getLogger(OfertaApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    
    final private static Map mapOfertas = new HashMap();
	static {
		mapOfertas.put("1", "Descuentos");
		mapOfertas.put("2", "Promociones");
	}
    
    @Autowired
	OfertaRepository ofertaRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public OfertaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> addOferta(@ApiParam(value = "oferta object that needs to be added to the store" ,required=true )  @Valid @RequestBody Oferta oferta) {
        
    	String accept = request.getHeader("Accept");
    	ErrorDetail oError = new ErrorDetail();
    	
        if (accept != null && accept.contains("application/json")) {
        	if (oferta.getIdOferta() != null && ofertaRepository.exists(oferta.getIdOferta())) {
				oError.setCode("204");
				oError.detail("Ya existe una oferta con ID: " + oferta.getIdOferta());
				oError.setStatus("No content");
				oError.setTitle("Error");

				return returnError(oError,HttpStatus.METHOD_FAILURE);
			}else {
				if (!mapOfertas.containsKey(oferta.getTipoOferta())) {
					oError.setCode("204");
					oError.detail("No existe oferta asociada al codigo: [" + oferta.getTipoOferta() + "].");
					oError.setStatus("No content");
					oError.setTitle("Error");

					return returnError(oError,HttpStatus.METHOD_FAILURE);

				}
				else if(ofertaRepository.exists(oferta.getIdOferta())) {
					oError.setCode("409 ");
					oError.detail("Ya existe una oferta con ID: [" + oferta.getIdOferta() + "].");
					oError.setStatus("Conflict");
					oError.setTitle("Error");

					return returnError(oError,HttpStatus.CONFLICT);
				}
				else {
					return new ResponseEntity<Oferta> (ofertaRepository.save(oferta),HttpStatus.OK);
				}
			}
        }
        
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
        
    }

    public ResponseEntity<?> deleteOferta(@ApiParam(value = "oferta object that needs to be added to the store" ,required=true )  @Valid @RequestParam String idOferta) {
        String accept = request.getHeader("Accept");
        
        if (accept != null && accept.contains("application/json")) {
        	if(idOferta != null && ofertaRepository.exists(idOferta)) {
        		ofertaRepository.delete(idOferta);
        		
        		GeneralRequest oResponse = new GeneralRequest();
        		oResponse.setParametro(idOferta);
        		oResponse.setResponse("Oferta eliminada exitosamente");
        		
        		return new ResponseEntity<GeneralRequest>(oResponse,HttpStatus.OK);
        		
        	}else if(idOferta == null) {
        		ErrorDetail oError = new ErrorDetail();
    			oError.setCode("404");
    			oError.detail("El parametro idOferta es obligatorio");
    			oError.setStatus("No content");
    			oError.setTitle("Error");

    			return returnError(oError,HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> getAll() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            JsonApiBodyResponseSuccess oResponse = new JsonApiBodyResponseSuccess();
			List<Oferta> lista = (List<Oferta>) ofertaRepository.findAll();
			
			return new ResponseEntity<JsonApiBodyResponseSuccess>(oResponse, HttpStatus.NOT_IMPLEMENTED);
        }else {
        	ErrorDetail oError = new ErrorDetail();
			oError.setCode("404");
			oError.detail("No hay información disponible");
			oError.setStatus("No content");
			oError.setTitle("Error");

			return returnError(oError,HttpStatus.NOT_FOUND);
        }

        
    }
    
    private ResponseEntity<?> returnError(ErrorDetail oError, HttpStatus status){
		List listaErrores = new ArrayList();
		listaErrores.add(oError);
		return new ResponseEntity<List>(listaErrores, status);
	}

    public ResponseEntity<?> updateOferta(@ApiParam(value = "oferta object that needs to be added to the store" ,required=true )  @Valid @RequestBody Oferta oferta) {
    	String accept = request.getHeader("Accept");
    	ErrorDetail oError = new ErrorDetail();
    	
        if (accept != null && accept.contains("application/json")) {
        	if (oferta.getIdOferta() != null && !ofertaRepository.exists(oferta.getIdOferta())) {
				oError.setCode("204");
				oError.detail("No existe una oferta con ID: " + oferta.getIdOferta());
				oError.setStatus("No content");
				oError.setTitle("Error");

				return returnError(oError,HttpStatus.METHOD_FAILURE);
			}else {
				if (!mapOfertas.containsKey(oferta.getTipoOferta())) {
					oError.setCode("204");
					oError.detail("No existe oferta asociada al codigo: [" + oferta.getTipoOferta() + "].");
					oError.setStatus("No content");
					oError.setTitle("Error");

					return returnError(oError,HttpStatus.METHOD_FAILURE);

				}
				else {
					return new ResponseEntity<Oferta> (ofertaRepository.save(oferta),HttpStatus.OK);
				}
			}
        }
        
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
