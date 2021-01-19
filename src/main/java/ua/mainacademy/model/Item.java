package ua.mainacademy.model;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String code;
    private String name;
    private int price;
    private int initPrice;
    private String url;
    private String imageUrl;
    private String group;
    private String seller;

}
