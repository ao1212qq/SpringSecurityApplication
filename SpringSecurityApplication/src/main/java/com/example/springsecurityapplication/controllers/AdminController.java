package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.*;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.services.OrderService;
import com.example.springsecurityapplication.services.PersonDataService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    private final OrderRepository orderRepository;

    private final PersonRepository personRepository;

    private final OrderService orderService;

    private final PersonService personService;

    private final PersonDataService personDataService;



    public AdminController(ProductService productService, CategoryRepository categoryRepository, OrderRepository orderRepository, CategoryRepository orderRepository1, OrderRepository orderRepository2, PersonRepository personRepository, OrderService orderService, PersonService personService, PersonDataService personDataService) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.personRepository = personRepository;
        this.orderService = orderService;
        this.personService = personService;
        this.personDataService = personDataService;
    }

    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }

        if(file_one != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);

        }

        if(file_two != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_three != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_four != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }

        if(file_five != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five .getOriginalFilename();
            file_five .transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }


    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";


    }

    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    //"/person account/product/info/{id}"
    @GetMapping("/admin/order/info/{id}")
    public String infoOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.getOrderId(id));
        return "/admin/infoOrder";
        //"/user/infoProduct"
    }
    @GetMapping("/detailInfoOrders")
    public String getAllOrder(Model model){
            model.addAttribute("orders", orderService.getAllOrder());
            return "/detailInfoOrders";
        }

    @GetMapping("/detailInfoPerson")
    public String getAllPerson(Model model){
        model.addAttribute("person", personDataService.getAllPerson());
        return "/detailInfoPerson";
    }

    @GetMapping("/detailInfoOrders/edit/{id}")
    public String editOrder(Model model, @PathVariable("id") int id){
        model.addAttribute("orders", orderService.getOrderId(id));
        return "/editInfoOrders";


    }

    @PostMapping("/detailInfoOrders/edit/{id}")
    public String editOrder(@ModelAttribute("orders") @Valid Order order, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            return "/editInfoOrders";
        }
        orderService.updateOrder(id, order);
        return "redirect:/admin";
    }

    @GetMapping("detailInfoPerson/edit/{id}")
    public String editPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personService.getPersonId(id));
        return "/editInfoPerson";


    }

    @PostMapping("/detailInfoPerson/edit/{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
           return "/editInfoPerson";
        }
        personService.updatePerson(id, person.getLogin(), person.getPassword(),person);
        return "redirect:/admin";
    }



    @PostMapping("detailInfoOrders/search")
    public String orderSearch(@RequestParam("search") String search, String contract, Model model){
        model.addAttribute("orders", orderService.getAllOrder());
        model.addAttribute("search_order", orderRepository.findByNumberContainingIgnoreCase(search));
        model.addAttribute("value_search", search);
        return "detailInfoOrders";
    }


}

