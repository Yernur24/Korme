package kz.bit.kormefinall.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long cartId;
    private List<CartItemResponseDTO> items;
    private Double totalPrice;
}
