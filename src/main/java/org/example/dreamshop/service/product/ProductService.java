package org.example.dreamshop.service.product;

import lombok.RequiredArgsConstructor;
import org.example.dreamshop.Model.Category;
import org.example.dreamshop.Model.Product;
import org.example.dreamshop.exception.productNotFoundException;
import org.example.dreamshop.repository.CategoryRepository;
import org.example.dreamshop.repository.ProductRepository;
import org.example.dreamshop.request.AddProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		// check if the category is found in the db
		// if yes, set it as the new product category
		// if not, create a new category and set it as the new product category
		// The set as the new product category

		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(()->{
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});

		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private Product createProduct(AddProductRequest request, Category category){
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category);
	}
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(()->new productNotFoundException("Product Not Found |"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete,
				()->{throw new productNotFoundException("Product Not Found |");});
	}

	@Override
	public void updateProduct(Product product, Long productId) {

	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String category, String name) {
		return productRepository.findByBrandAndName(category, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}
}
