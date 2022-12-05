/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwsaa.pwscintadamaia;




import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import model.Product;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author acer
 */
@RestController
public class ProductServiceController {
        private static Map<String, Product> productRepo = new HashMap<>();
        static {
        
    }

    @RequestMapping(value = "/create")
    public ResponseEntity<Object> create(
            @RequestParam(value = "name", required = false, defaultValue = "honey") String name
    ) {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        productRepo.put(honey.getId(), honey);

        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        productRepo.put(almond.getId(), almond);
       return new ResponseEntity<>("produk dibuat", HttpStatus.OK); 
    }

    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam(value = "name", required = false, defaultValue = "honey") String name
    ) {
        
       return new ResponseEntity<>(productRepo.values(), HttpStatus.OK); 
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        if (productRepo.containsKey(product.getId())){
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        }
        else{
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
    }


    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      if(!productRepo.containsKey(id)){
          return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      }
      else {
        productRepo.remove(id);
        product.setId(id);
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
      }
   }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        if(!productRepo.containsKey(id)){
          return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      }
      else {
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
        }

    }
}

        
        

