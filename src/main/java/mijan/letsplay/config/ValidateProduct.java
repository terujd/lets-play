package mijan.letsplay.config;


import mijan.letsplay.exceptions.ProductCollectionException;
import mijan.letsplay.models.Product;

public class ValidateProduct {
    public static void validateProduct(Product product) throws ProductCollectionException {
        if (product.getName() != null) {// if product.getName() is not null, trim, else throw exception
            product.setName(product.getName().trim());// Trim the name field
            System.out.println("product name: " + product.getName());// Print the name field
        } else {
            // Throw an exception if the name is null or empty.
            throw new ProductCollectionException("Product name" + ProductCollectionException.NullException());
        }
        // if product.getDescription() is not null, trim, else throw exception
        if (product.getDescription() != null) {
            product.setDescription(product.getDescription().trim());// Trim the description field
            System.out.println("product description: " + product.getDescription());// Print the description field
        } else {
            // Throw an exception if the description is null or empty.
            throw new ProductCollectionException("Product description" + ProductCollectionException.NullException());
        }
        // if product.getPrice() is not null, trim, else throw exception
        if (product.getUserId() != null) {
            product.setUserId(product.getUserId().trim()); // Trim the ID field as well
            System.out.println("product userId: " + product.getUserId());// Print the ID field
        } else {
            // Throw an exception if the user ID is null or empty.
            throw new ProductCollectionException("Product userId" + ProductCollectionException.NullException());
        }
    }
}
