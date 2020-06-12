  
 $('document').ready(function(event){
	 
	 
	 
	 
	 setTimeout(function(){
		 
     $('.aviso').fadeOut(4000);
		 
		 
	 }, 1000);
	
	
	
	 
	 
	 
	 
	 
	 $('.table .miboton').on("click", function (event){
		 
		  event.preventDefault();
var  url= $(this).attr("href");
		 
		 alert(url);
		 $.ajax({
			 async:true,
			 type:"GET",
			 url:url,
			 success:function(objeto){
				 
				 $('#formulario #id').val(objeto.id);
				 $('#formulario #nombre').val(objeto.nombre);
				 $('#formulario #apellido').val(objeto.apellido);
				 
				 
			 },
			 error: function(error){
				 
				 
			 }
			 
			 
			 
			 
		 });
		 
		 
		 $('#exampleModal').modal();
		 
		 
		 
		 
		 
	 });
			
			
			
			

		 
		 
		 

	 
	 
 });
 
/* funciona con el 1  de listar
 
 function  funcion(variable){
	 
	 var  url= "http://localhost:8081/consultar/"+variable;
	
	 $.get(url, function(objeto){
			
			$('#formulario #id').val(objeto.id);
			$('#formulario #nombre').val(objeto.nombre);
			$('#formulario #apellido').val(objeto.apellido);
		
		});
	 
	 $('#exampleModal').modal();
 }*/