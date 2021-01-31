package ua.mainacademy.model;

import lombok.*;

@Setter
@Getter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Integer id;
    private String code;
    private String name;
    private int price;
    private Integer initPrice;
    private String url;
    private String imageUrl;
    private String group;
    private String seller;

}
