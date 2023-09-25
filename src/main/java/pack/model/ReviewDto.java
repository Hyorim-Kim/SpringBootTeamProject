package pack.model;

import lombok.Data;

@Data
public class ReviewDto {
   private int rating, cont_no;
   private String content, user_id;
}