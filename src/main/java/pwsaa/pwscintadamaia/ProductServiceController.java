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
    //tempat untuk meletakkan produk.
    @RequestMapping(value = "/create")
    public ResponseEntity<Object> create(
            @RequestParam(value = "name", required = false, defaultValue = "honey") String name
    ) {
        //untuk menambahkan produk ke dalam daftar secara  manual.
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        honey.setPrice(20000.00);
        honey.setDisc(15);
        honey.setTotal(honey.getPrice(), honey.getDisc());
        productRepo.put(honey.getId(), honey);

        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        almond.setPrice(4000.00);
        almond.setDisc(15);
        almond.setTotal(almond.getPrice(), almond.getDisc());
        productRepo.put(almond.getId(), almond);
       return new ResponseEntity<>("produk dibuat", HttpStatus.OK); 
    }
     //menampilkan produk.
    @RequestMapping(value = "/products")
    public ResponseEntity<Object> getProducts(
            @RequestParam(value = "name", required = false, defaultValue = "honey") String name
    ) {
        
       return new ResponseEntity<>(productRepo.values(), HttpStatus.OK); 
    }
    //menambahkan produk melalui postman.
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //jika ada id yang dimasukkan sama maka akan menampilkan pemeberitahuan bahwa id yang dimasukkan sudah ada.
        if (productRepo.containsKey(product.getId())){
            return new ResponseEntity<>("Product already exists", HttpStatus.CONFLICT);
        }
        else{
            product.setTotal(product.getPrice(), product.getDisc());
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
    }

    //mengedit produk yang sudah ada.
   @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      //jika id yang dimasukkan tidak ada dalam daftar produk.
       if(!productRepo.containsKey(id)){
          return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      }
      else {
        productRepo.remove(id);
        product.setId(id);
        product.setTotal(product.getPrice(), product.getDisc());
        productRepo.put(id, product);
        return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
      }
   }
   //menghapus produk.
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

        
        

