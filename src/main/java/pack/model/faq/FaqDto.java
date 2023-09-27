package pack.model.faq;

import lombok.Data;

@Data
public class FaqDto {
	private String faq_no, faq_category, faq_question, faq_answer;
	private int num;
}
