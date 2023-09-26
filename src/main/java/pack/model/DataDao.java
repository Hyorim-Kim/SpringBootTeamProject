package pack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pack.controller.FormBean;
import pack.model.container.ContainerDto;
import pack.model.owner.OwnerDto;
import pack.model.user.UserDto;

@Repository
public class DataDao {
   private Logger logger = LoggerFactory.getLogger(this.getClass());
   
   
   @Autowired
   private DataMapperInterface dataMapper;

   public DataDao() {
      
   }
   
   // User 정보 출력하기-------------------------------------------------------------
   public List<UserDto> getUserAll(){
      List<UserDto> list = dataMapper.selectAll();  // sql문이 실행
      logger.info("datas1 : " + list.size() + "개");
      return list;
   }
   // User에서 원하는 정보 검색하기 
   public List<UserDto> getUserSearch(FormBean bean){   // 검색용
      List<UserDto> list = (List<UserDto>)dataMapper.selectSearch(bean); // sql문이 실행
      logger.info("search datas : " + list.size() + "개");
      return list;
   }
   /*
   public List<UserDto> search(FormBean bean){
      List<UserDto> slist = dataMapper.selectSearch(bean);
      return slist;
   }
   */
   // Owner 정보 출력 및 검색하기-------------------------------------------------------------
   public List<OwnerDto> getOwnerAll(){
      List<OwnerDto> list2 = dataMapper.selectAll2();  // sql문이 실행
      logger.info("datas2 : " + list2.size() + "개");
      return list2;
   }
   
   public List<OwnerDto> getOwnerSearch(FormBean bean){   // 검색용
      List<OwnerDto> list = (List<OwnerDto>)dataMapper.selectSearch2(bean); // sql문이 실행
      logger.info("search datas : " + list.size() + "개");
      return list;
   }
   /*
   public List<OwnerDto> search2(FormBean bean){
      List<OwnerDto> slist = dataMapper.selectSearch2(bean);
      return slist;
   }
   */
   // Container 정보 출력 및 검색하기-------------------------------------------------------------
   public List<ContainerDto> getConAll(){
      List<ContainerDto> list3 = dataMapper.selectAll3();  // sql문이 실행
      logger.info("datas3 : " + list3.size() + "개");
      return list3;
   }
   
   // Cotainer 세부정보 보기 및 수정 삭제하기
   public ContainerDto condetail(String cont_no) {  // 상세보기용
      
       ContainerDto containerDto = dataMapper.selectOne(cont_no);
       return containerDto;
   }

   
   @Transactional  // detail.html에서 삭제버튼 누르면 삭제하도록 하기
      public boolean condelete(String cont_no) {
         boolean b = false;
         int re = dataMapper.delete(cont_no);
         if (re > 0)
            b = true;
         
         return b;
      }
   
   // 사용한 창고 정보 출력 및 검색하기-------------------------------------------------------------
      public List<ContainerDto> getUserCon(){
         List<ContainerDto> list4 = dataMapper.selectAll4();  // sql문이 실행
         logger.info("datas4 : " + list4.size() + "개");
         return list4;
      }
      
     
      /*
      public List<ContainerDto> getreviews(){
         List<ContainerDto> list6 = dataMapper.selectAll6();  // sql문이 실행
         logger.info("datas6 : " + list6.size() + "개");
         return list6;
      }
      */
      
      /*
      // ajax
       public void saveReviewAjax(ReviewDto reviewDto) {
           dataMapper.insertReview(reviewDto);
       }
       
       public List<ReviewDto> getAllReviews() {
           List<ReviewDto> reviews = dataMapper.selectAllReviews();
           logger.info("reviews : " + reviews.size() + "개");
           return reviews;
       }
       */
}