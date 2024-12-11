package kz.bit.kormefinall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartListsResponseDTO {
    private Long cartId;
    private String userName;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double totalSum;
    private String status;
    private List<CartItemResponseDTO> items;
}
