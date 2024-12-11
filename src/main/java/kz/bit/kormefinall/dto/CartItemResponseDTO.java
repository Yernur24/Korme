package kz.bit.kormefinall.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private Double productPrice;
    private int quantity;
    private String productImage;
}
