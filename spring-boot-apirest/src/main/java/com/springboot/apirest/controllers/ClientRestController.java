package com.springboot.apirest.controllers;

import com.springboot.apirest.entity.Client;
import com.springboot.apirest.entity.ErrorResponse;
import com.springboot.apirest.entity.Region;
import com.springboot.apirest.exceptions.ClientNotFoundException;
import com.springboot.apirest.services.IClientService;
import com.springboot.apirest.services.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"}) // origins  = indicamos dominio,
@RestController
@RequestMapping("api/clients")
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping("")
    public ResponseEntity<List<Client>> getAll(){

        try{
            List<Client> clients = clientService.findAll();

            if(clients != null && clients.size() > 0)
                return ResponseEntity.ok(clients);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Client>> getAll(@PathVariable Integer page){

        try{
            Page<Client> clients = clientService.findAll(PageRequest.of(page, 5));

            if(clients != null && clients.getTotalElements() > 0)
                return ResponseEntity.ok(clients);
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable final Integer id){

        Map<String, Object> response = new HashMap<>();

        try {
            Client client = clientService.findById(id);

            return ResponseEntity.ok(client);

        }catch (ClientNotFoundException e){
            return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.builder().message(e.getMessage()).code(e.getRawStatusCode()).build());
        }catch (DataAccessException e){
            response.put("message", "Error consulting clients!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("")
    public ResponseEntity<?> add(@Valid @RequestBody final Client client, BindingResult result){      // @Valid antes entrar al metodo create se tenga que validar a traves de un interceptor de spring @Valid

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            response.put("errors", this.bindingResultValidate(result));
            System.out.println(response);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try{
            Client newClient = clientService.save(client);

            response.put("message", "Client created successfully!");
            response.put("client", newClient);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }catch (DataAccessException e){
            response.put("message", "Error inserting a client!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody final Client client, BindingResult result , @PathVariable final Integer id){

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            response.put("errors", this.bindingResultValidate(result));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Client c = clientService.update(client, id);

            response.put("message", "Client updated successfully!");
            response.put("client", c);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }catch (ClientNotFoundException e){
            return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.builder().message(e.getMessage()).code(e.getRawStatusCode()).build());
        }catch(DataAccessException e){
            response.put("message", "Error updating a client!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable final Integer id){

        Map<String, Object> response = new HashMap<>();

        try{
            Client client = clientService.findById(id);
            String previousFileName = client.getPhoto();

            uploadFileService.delete(previousFileName);
            clientService.deleteById(id);

            response.put("message", "Client removed successfully!");
            response.put("client", client);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (ClientNotFoundException e){
            return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.builder().message(e.getMessage()).code(e.getRawStatusCode()).build());
        }catch (DataAccessException e){
            response.put("message", "Error removing a client!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id){

        Map<String, Object> response = new HashMap<>();
        Client client = clientService.findById(id);

        if(!file.isEmpty()) {

            String fileName = null;

            try {
                fileName = uploadFileService.copy(file);
            } catch (IOException e) {
                response.put("message", "Error uploading client image");
                response.put("error", e.getCause().getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            String previousFileName = client.getPhoto();
            uploadFileService.delete(previousFileName);

            client.setPhoto(fileName);
            clientService.save(client);

            response.put("client", client);
            response.put("message", "You have successfully uploaded image: " + fileName);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/uploads/img/{fileName:.+}")               // expresion regular que indica que este parametro va a recibir un punto y la extension
    public ResponseEntity<Resource> seePhoto(@PathVariable String fileName){

        Resource resource = null;

        try {
            resource = uploadFileService.load(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();                        // pasamos las cabeceras de la respuesta para que esta imagen lo forcemos para que se pueda descargar
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");     // attachment: va a forzar que se descarge la imagen, de esta forma lo podemos incluir en el elemento html de imagen img en el atributo src y asi lo va a mostrar correctamente en el navegador

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(resource);
    }

    //@Secured("ROLE_ADMIN")
    @GetMapping("/regions")
    public List<Region> listRegions(){

        return clientService.findAllRegions();
    }

    private List<String> bindingResultValidate(BindingResult result){

        return result.getFieldErrors().stream()
                .map(err -> "Field '" + err.getField() + "' " + err.getDefaultMessage())
                .collect(Collectors.toList());
    }

}
