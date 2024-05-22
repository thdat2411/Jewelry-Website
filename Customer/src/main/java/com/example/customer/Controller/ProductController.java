package com.example.customer.Controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.service.CategoryService;
import com.example.library.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product")
    public String menu(Model model) {
        model.addAttribute("page", "Product");
        model.addAttribute("title", "Shop");
        List<Category> categories = categoryService.findAllByActivated();
        List<ProductDto> products = productService.products();
        List<Long> productIds = productService.getProductIdList(products);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("productIds", productIds);
        return "product";
    }

    @GetMapping("/product-detail/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.getById(id);
        List<ProductDto> productDtoList = productService.findAllByCategory(product.getCategory().getCategoryName());
        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Product Detail");
        model.addAttribute("page", "Product Detail");
        model.addAttribute("productDetail", product);
        return "product-detail";
    }

    @GetMapping("/search-products")
    public String searchProduct(@RequestParam(value = "keyword") String keyword,
            Model model) {
        if (!Objects.equals(keyword, "")) {
            List<ProductDto> products = productService.findProductByActivated(keyword);
            List<Long> productIds = productService.getProductIdList(products);
            model.addAttribute("title", "Result Search Products");
            model.addAttribute("products", products);
            model.addAttribute("productIds", productIds);
            return "product";
        } else {
            return "redirect:/product";
        }
    }

    @GetMapping("/search-product/{init}/{end}")
    public String filterPriceProduct(@PathVariable("init") Double init,
            @PathVariable("end") Double end,
            Model model) {
        List<ProductDto> products = productService.getProductByPrice(init, end);
        List<Long> productIds = productService.getProductIdList(products);
        model.addAttribute("title", "Search Product By Price");
        model.addAttribute("products", products);
        model.addAttribute("productIds", productIds);
        return "product";
    }

    @GetMapping("/order-product/{productList}/{flag}")
    public String filterOrderProduct(@PathVariable("productList") List<Long> productIds,
            @PathVariable("flag") String flag,
            Model model) {
        List<ProductDto> products = productService.getOrderPriceProduct(productIds, flag);
        List<Long> productIdList = productService.getProductIdList(products);
        model.addAttribute("title", "Search Product by Order");
        model.addAttribute("products", products);
        model.addAttribute("productIds", productIdList);
        return "product";
    }
    // @GetMapping("/filter-product/{categoryName}")
    // public String details(@PathVariable("categoryName") String categoryName,
    // Model model) {
    // List<ProductDto> productDtoList =
    // productService.findAllByCategory(categoryName);
    // model.addAttribute("productsCategory", productDtoList);
    // model.addAttribute("title", "Product Detail");
    // model.addAttribute("page", "Product Detail");
    // return "product";
    // }
}
