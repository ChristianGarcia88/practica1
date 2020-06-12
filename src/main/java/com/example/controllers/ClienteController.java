package com.example.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Cliente;
import com.example.repository.ICliente;

@Controller
public class ClienteController {

	
	@Autowired
	private   ICliente   clienteRepo;
	private  Integer valorEditar;

	
	@GetMapping( {"/index", "/"})
	public String  inicio( Model m) {
		Cliente cliente= new Cliente();
		m.addAttribute("cliente2", cliente);
		
		  
		return "index";
	}
	

	@PostMapping( {"/index"})
	public String  validaciones ( @Valid  @ModelAttribute("cliente2") Cliente cliente , BindingResult result ,Model m ) {
		if(result.hasErrors()) {
			
			return "index";
			
		}
		 
		 Date date = new Date();
		
		
		cliente.setFecha(date);
		
		
		m.addAttribute("objeto", cliente);
		clienteRepo.save(cliente);
		cliente=null;
		return "exitoCreacion";
		
		 
	} 
		

	 
	
	
	
	@GetMapping("/listar")
	public  String  listar( Model  m ) {
		
		List<Cliente>  clientes=  (List<Cliente>) clienteRepo.findAll();
		m.addAttribute("lista", clientes);
	

		return "listar";
	}
	
	 
	@PostMapping("/editacion")
	public String  editacion( Cliente  clientesi,  Model m) { 
	//	clientesi.setId(valorEditar);
		System.out.println("datos como quiero que queden :" + clientesi.getNombre()+ " " + clientesi.getApellido() +" " +  clientesi.getId());
		m.addAttribute("algo", clientesi);
	
	
	/*Cliente  d= clienteRepo.findById(clientesi.getId()).get();
	System.out.println( "consulta a la bd :"+  d.getNombre()+ " " + d.getApellido() +" " +  d.getId());
	 d.setNombre(clientesi.getNombre());
	 d.setApellido(clientesi.getApellido());*/
	clienteRepo.save(clientesi);
	//System.out.println( "consulta modificada:"+  d.getNombre()+ " " + d.getApellido() +" " +  d.getId());
		return "mensaje";
	} 
	
	 
	
@GetMapping("/editar")
	
	public String  editar(Cliente c,  Model m) {
		

		m.addAttribute("clienteEditar", c);
		System.out.println(c.getNombre()+ " " +  c.getId()  + " " + c.getProcesado());
		
	//valorEditar= c.getId();
		return "editarExitosos";
	}



@GetMapping ("/procesar")
public  String  procesar( Model  m  ) {
	
	List<Cliente>  clientes=  ((List<Cliente>) clienteRepo.findAll()).
			                                                          stream(). 
			                                                          filter(c -> c.getProcesado().equals("Sin Procesar")).collect(Collectors.toList());
	
	
	m.addAttribute("lista2", clientes);
	return "procesar";
}
	



@PostMapping ("/procesa")
public  String  procesar( @RequestParam("checa") ArrayList<String> checa, Model  m  ) {
	
	
	for(String i: checa) {
		
	Cliente cl= clienteRepo.findById(Integer.parseInt(i)).get();
	cl.setProcesado("Procesado");
	clienteRepo.save(cl);
		

	}
	
	return "procesa";
}


@GetMapping("/eliminar/{id}")

public  String   eliminar(@PathVariable("id") Integer id,  RedirectAttributes m) {
	
	
	m.addFlashAttribute("bandera", "elemento borrado");
	clienteRepo.deleteById(id);
	
	
	return "redirect:/listar";
}
 


@GetMapping("/consultar/{id}")
@ResponseBody
public Optional<Cliente> getcliente(@PathVariable("id") Integer id) {
	
   
	  
	
	return  clienteRepo.findById(id);
	
}

@RequestMapping(value = "/actualizar", method = {RequestMethod.PUT, RequestMethod.GET} )
public String actualizar(Cliente  c, Model m ) {
	c.setFecha(new Date());
	clienteRepo.save(c);
	 
	
	return"redirect:/listar";
	
	
}


}
