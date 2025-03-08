package org.example.dreamshop.request;

import lombok.Data;
import org.example.dreamshop.Model.Category;
import java.math.BigDecimal;

@Data
public class ProductUpdateReqeust {
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
}
