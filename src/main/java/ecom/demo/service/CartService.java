
package ecom.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.dto.ProductCartDto;
import ecom.demo.models.Cart;
import ecom.demo.models.Product;
import ecom.demo.models.UserCart;
import ecom.demo.repository.CartRepo;
import ecom.demo.repository.ProductRepo;

@Service
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CartService(CartRepo cartRepo,  ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this. productRepo =  productRepo;

    }

    public int addCart(Cart cart) {
        return cartRepo.addCart(cart);
    }


    // public Cart getCartById(UUID cartID) {
    //     return cartRepo.getCartById(cartID);
    // }

    public int deleteCart(UUID cartID) {
        return cartRepo.deleteCart(cartID);
    }

    public UserCart getUserCartById(UUID userID){
        return cartRepo.getUserCartById(userID);
    }

    public int addUserCart(UserCart userCart){
        return cartRepo.addUserCart( userCart);
    }

    public List<ProductCartDto> getCartProductsByUserId(UUID userID) {
        UserCart userCart = cartRepo.getUserCartById(userID);
    
        if (userCart == null) {
            return Collections.emptyList();
        }
    
        UUID cartID = userCart.getCartID(); // Get the cartID
        List<Cart> cartEntries = cartRepo.getCartEntriesById(cartID);
    
        List<ProductCartDto> cartProducts = new ArrayList<>();
        for (Cart entry : cartEntries) {
            Optional<Product> optionalProduct = productRepo.getProductByID(entry.getProductID());
            
            
            System.out.println("Product ID: " + entry.getProductID());
            
            // If the product is present, add it to the list
            optionalProduct.ifPresent(product -> {
                cartProducts.add(new ProductCartDto(product, entry.getQuantity(), entry.isChoose()));
                // Print product details
                System.out.println("Product Details:");
                System.out.println("Name: " + product.getProductName());
                System.out.println("Images: " + product.getImages());
                System.out.println("Price: " + product.getPrice());

                System.out.println("quantity: " + entry.getQuantity());
                System.out.println("selected: " + entry.isChoose());
                // Add any other product details you want to display
            });
        }
    
        return cartProducts;
    }

    public Float calculateTotalAmount(List<ProductCartDto> cartProducts) {
        return cartProducts.stream()
                .filter(ProductCartDto::isSelected) // Only get selected items
                .map(item -> item.getProduct().getPrice() * item.getQuantity())
                .reduce(0.0f, Float::sum);
    }


    public int removeFromCart(UUID userID, UUID productID){
        UserCart userCart = cartRepo.getUserCartById(userID);
        
        if (userCart == null) {
            System.out.println("no cart made!111");
            return 0; // If user has no cart, return false
        }

        UUID cartID = userCart.getCartID(); // Get the cartID
        System.out.println("trying to remove the product from cart");
        return cartRepo.removeFromCart(cartID, productID);
    }

    public int removeOrderedProductsFromCart(UUID userID) {
        UserCart userCart = cartRepo.getUserCartById(userID);
        UUID cartID = userCart.getCartID();
        return cartRepo.removeOrderedProductsFromCart(cartID);
    }


    public void updateProductSelection(UUID userId, UUID productID, boolean selected) {
        UserCart userCart = cartRepo.getUserCartById(userId);
        
        if (userCart == null) {
            System.out.println("No cart found for user!");
            return; // Optionally, handle the case where no cart exists for the user
        }
        
        UUID cartID = userCart.getCartID();
        System.out.println("Cart id : " +cartID);
        Cart cart = cartRepo.getCartById(cartID, productID); // Ensure this method retrieves the cart
        if (cart == null) {
            System.out.println("No cart found for cartID!");
            return; // Handle the case where the cart is not found
        }
        
        cart.setChoose(selected);
        cartRepo.updateCart(cart);
        System.out.println("Cart selected : " + cart.isChoose() + " product: " + cart.getProductID());
    }
    
    public void updateProductQuantity(UUID userId, UUID productID, int quantity) {
        UserCart userCart = cartRepo.getUserCartById(userId);
        
        if (userCart == null) {
            System.out.println("No cart found for user!");
            return; // Optionally, handle the case where no cart exists for the user
        }
        
        UUID cartID = userCart.getCartID();
        System.out.println("Cart id : " +cartID);
        Cart cart = cartRepo.getCartById(cartID, productID); // Ensure this method retrieves the cart
        if (cart == null) {
            System.out.println("No cart found for cartID!");
            return; // Handle the case where the cart is not found
        }
        cart.setQuantity(quantity);
        cartRepo.updateCart(cart);
    }
    
    

}
