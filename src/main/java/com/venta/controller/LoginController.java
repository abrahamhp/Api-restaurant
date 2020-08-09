package com.venta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import javax.jms.Message;
import com.venta.domain.Login;
import com.venta.domain.Venta;
import com.venta.jms.Consumer;
import com.venta.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@SessionAttributes("name")
@RequestMapping("/")
@Api(value = "ventas", description = "Operations pertaining to sales in Online API Restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);  
	
	    @Autowired
	    LoginService service;
	    
	    @Autowired private JmsTemplate jmsTemplate;
	    
	    
	    @ApiOperation(value = "Login to a Restaurant API Sales")
		@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved "),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"), 
		@ApiResponse(code=  500, message="Internal Server Error"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	    @RequestMapping(value="/login", method = RequestMethod.GET,  produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	    public ModelAndView showLoginPage(Model model,HttpServletRequest request,  
	    		HttpServletResponse response) {

	    	model.addAttribute("login", new Login());
	    	return new ModelAndView("login");    
	    	
	    }
	    
	    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully log in"),
	   	@ApiResponse(code = 401, message = "You are not authorized to view the resource"), 
	   	@ApiResponse(code=  500, message="Internal Server Error"),
	   	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	    @PostMapping(value= "/login", produces = MediaType.TEXT_HTML_VALUE)
	    public ModelAndView showMenuPage(@RequestParam String name, @RequestParam String password,
	    		Model model, HttpServletRequest request, HttpServletResponse response) {
    	 try {
    	    	 boolean isValidUser = service.validateUser(name, password);
	   		 	 if (!isValidUser) {
	   		 	   model.addAttribute("login", new Login());
	   			   model.addAttribute("errorMessage", "Credenciales Invalidas");
	   			   log.warn("Error de ingreso- credenciales incorrectas");
	   			  return new ModelAndView("login"); 
	   			} 
	   		   model.addAttribute("name", name);
	   		   model.addAttribute("password", password);
	   		   log.info("Bienvenido a la API del Restaurant Santiago", HttpStatus.OK);
    	     } catch (Exception e) {
	 	            e.printStackTrace();
	 	            log.error("Error inesperado en el login: "+ e.getMessage()); 
    	    	 
    	     }
	   		return new ModelAndView("menu");   	    
		}
	    
	    @ApiOperation(value = "Show a API Dashboard")
	    @RequestMapping(value="/volverMenu", method = RequestMethod.GET,  produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	    public ModelAndView showMenuPage(Model model,HttpServletRequest request,  
	    		HttpServletResponse response) {
	    	model.addAttribute("login", new Login());
	    	return new ModelAndView("menu");
	    }
	    
	    
	    @ApiOperation(value = "Enter a sale")
	    @RequestMapping(value="/crearVenta", method = RequestMethod.GET,  produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	    public ModelAndView ingresarVenta(Model model,HttpServletRequest request,  
	    		HttpServletResponse response) {
	    	model.addAttribute("crearVenta", new Venta());
	    	log.info("Bienvenido a la pagina de Ingresar datos de la venta", HttpStatus.OK);
	    	return new ModelAndView("crearVenta");    
	    	
	    }
	    
	    
	    @ApiOperation(value = "Add a sale")   
	    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added sale"),
	    @ApiResponse(code = 401, message = "You are not authorized to view the resource"), 
	   	@ApiResponse(code=  500, message="Internal Server Error"),
	   	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
		@PostMapping(value = "/sendVenta", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	   	public ModelAndView sendVenta(@RequestParam int idVenta, 
	   								  @RequestParam long precio, 
	        		                  @RequestParam String pago, 
	        		                  @RequestParam int cantidad, ModelMap m, Model model, HttpServletRequest request, 
	        		                  HttpServletResponse response) {
	    	Date fecha = new Date();
	    	//obtener la session
	    	HttpSession mysession= request.getSession(true);
	    	List<Venta> ventas=(List<Venta>) mysession.getAttribute("ventasSession");
	    	  
	    	//asignarlo a lista de ventas
	    	
	        	 try {       	        		 	        	  
					  Venta venta = new Venta();
					  venta.setIdVenta(idVenta);
					  venta.setFecha(fecha);
					  venta.setPrecio(precio);
					  venta.setPago(pago);
					  venta.setCantidad(cantidad);
					  model.addAttribute("idVenta", idVenta); 
					  model.addAttribute("fecha", fecha); 
					  model.addAttribute("precio", precio);
					  model.addAttribute("pago", pago); 
					  model.addAttribute("cantidad", cantidad);
					  log.info("Registro de venta: idVenta: {}, fecha: {}, precio: {}, pago:{}, cantidad:{} ", idVenta,fecha,precio,pago,cantidad);        	    	        	    	        	    	        	    				  
					 
					  
	        	    
	        	    log.info("Enviando mensaje a la cola de ventas ...");
	        	    jmsTemplate.convertAndSend("ventaQueue", venta);
	        	    
	        	    
	        	    if (null == ventas || ventas.isEmpty()) {
	        	    	List<Venta>	ventasNueva = new ArrayList<Venta>();
	        	    	ventasNueva.add(venta);
	        	    	mysession.setAttribute("ventasSession",ventasNueva);
	        	    	log.info("No hay ventas registradas aun en lista de sesión");
 	        		 } else {
 	        			ventas.add(venta);
 	        			mysession.setAttribute("ventasSession",ventas);
 	        			log.info("Hay ventas registradas en lista de sesión");
	        			 
	        		 }
	        	    	        	  	     
	        	    //m.put("successMessage", "creacion de venta exitosa con codigo: "+ idVenta);
	        	    model.addAttribute("successMessage", "creacion de venta exitosa con codigo: "+ idVenta);
	        	    
	        	    log.info("creacion de venta exitosa con codigo: "+ idVenta, HttpStatus.OK);
	        	    } catch (Exception e) {
	 	            e.printStackTrace();
	 	            log.error("Error en el proceso: "+ e.getMessage());
	 	            m.put("errorMessage", "Error al ingresar la venta");
	 	            model.addAttribute("errorMessage", m);
	
	 	        }
	        	 
	    
	        	 return new ModelAndView("mensaje"); 
	        }   
	 
	    
	    @ApiOperation(value = "View a list of sales added", response = Iterable.class)
		@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"), 
		@ApiResponse(code=  500, message="Internal Server Error"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	    @RequestMapping(value = "/listaVenta",  method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Venta> getVentas(Model model, HttpServletRequest request, HttpServletResponse response){ 
	   
	    HttpSession mysession= (HttpSession) request.getSession();
	    List<Venta> ventasdia= (List<Venta>) mysession.getAttribute("ventasSession");
	   
	    try {	    
	    	 log.info("cantidad de ventas en lista:"+ventasdia.size(), HttpStatus.OK);
	    }
	    catch (Exception e) {
	          e.printStackTrace();
	          log.error("Error al listar ventas: "+ e.getMessage());	        
	    }
	    
	    return ventasdia;
}
	   

}
