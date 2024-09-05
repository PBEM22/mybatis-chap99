package com.management.product.controller;


import com.common.SearchCondition;
import com.management.product.model.dao.ProductDAO;
import com.management.product.model.dto.ProductDTO;
import com.management.product.model.service.ProductService;
import com.management.product.view.ProductPrint;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

import static com.common.Template.getSqlSession;

public class ProductController {

    // * 주석을 지우고 Controller 역할에 해당하는 내용을 작성하세요.

    // 1. 자주 사용할 Service와 Print 객체를 선언하고, Controller 객체 생성 시 생성되도록 작성하세요.
    private final ProductService productService;
    private final ProductPrint productPrint;

    public ProductController() {
        this.productService = new ProductService();
        this.productPrint = new ProductPrint();
    }

    public void selectAllProductList() {

        // 2. 전체 제품 목록을 조회하는 메소드
        //    (조건 1) Service 객체를 호출하여 List<ProductDTO> 타입으로 전체 제품 목록을 조회하세요.
        List<ProductDTO> productList = productService.selectAllProductList();

        //    (조건 2) 제품 목록이 비어있지 않은 경우, Print 객체를 통해 제품 목록을 출력하세요.
        if (!productList.isEmpty()){
            productPrint.printAllProductList(productList);
        } else {
            //    (조건 3) 제품 목록이 없는 경우, Print 객체를 통해 조회 결과가 없다는 오류 메세지를 출력하세요.
            productPrint.printErrorMessage("404");
        }

    }

    public void selectProductByCondition(SearchCondition searchCondition) {

        // 3. 조건에 따른 제품 목록을 조회하는 메소드
        //    (조건 1) Service 객체를 호출하여 List<ProductDTO> 타입으로 조건에 따른 제품 목록을 조회하세요.
        List<ProductDTO> productList = productService.selectProductByCondition(searchCondition);

        //    (조건 2) 제품 목록이 비어있지 않은 경우, SearchCondition과 List<ProductDTO> 객체를 반환하여
        //    　　　　　Print 객체를 통해 조회 조건과 제품 목록을 출력하세요.
        if (!productList.isEmpty()){
            productPrint.printProductList(productList, searchCondition);
        } else {
        //    (조건 3) 제품 목록이 없는 경우, Print 객체를 통해 조회 결과가 없다는 오류 메세지를 출력하세요.
            productPrint.printErrorMessage("404");
        }

    }

    public void registNewProduct(ProductDTO product) {

        // 4. 제품 정보를 등록하는 메소드
        //    (조건 1) 화면에서 releaseDate를 0000-00-00 형태로 받아옵니다.
        //            해당 필드에 매핑되는 DB 컬럼 releaseDate가 8byte이므로 '-' 문자를 제거하여 product객체에 setting 하세요.
        //            또한 제품 최초 등록 시 생산여부는 무조건 '생산중(Y)'이고, 판매량은 0이므로 해당 값을 product객체에 setting 하세요.
        String releaseDate = product.getReleaseDate().replace("-", "");
        product.setReleaseDate(releaseDate);
        product.setProductionStatus("Y");
        product.setSalesQuantity("0");

        System.out.println(product);
        //　  (조건 2) Service 객체를 호출하여 등록을 수행하고, 결과를 boolean 값으로 return 받으세요.
        boolean result = productService.registNewProduct(product);

        //    (조건 3) insert가 정상적으로 수행된 경우, Print 객체를 통해 등록 성공했다는 성공 메세지를 출력하세요.
        if (result) productPrint.printSuccessMessage("성공");
        
        //    (조건 4) insert가 정상적으로 수행되지 않은 경우, Print 객체를 통해 등록 실패했다는 오류 메세지를 출력하세요.
        else productPrint.printErrorMessage("실패");
    }

    public void modifyProductInfo(ProductDTO product) {

        // 5. 제품 정보를 수정하는 메소드
        //    (조건 1) 화면에서 releaseDate를 0000-00-00 형태로 받아옵니다.
        //            해당 필드에 매핑되는 DB 컬럼 releaseDate가 8byte이므로 '-' 문자를 제거하여 product객체에 setting 하세요.
        String releaseDate = product.getReleaseDate().replace("-", "");
        product.setReleaseDate(releaseDate);

        //　  (조건 2) Service 객체를 호출하여 수정을 수행하고, 결과를 boolean 값으로 return 받으세요.
        boolean result = productService.modifyProductInfo(product);

        //    (조건 3) update가 정상적으로 수행된 경우, Print 객체를 통해 수정 성공했다는 성공 메세지를 출력하세요.
        if (result) productPrint.printSuccessMessage("정보 수정 성공");
        
        //    (조건 4) update가 정상적으로 수행되지 않은 경우, Print 객체를 통해 수정 실패했다는 오류 메세지를 출력하세요.
        else productPrint.printErrorMessage("정보 수정 실패");
    }

    public void deleteProduct(Map<String, String> parameter) {

        // 6. 제품 정보를 삭제하는 메소드
        //    (조건 1) Service 객체를 호출하여 수정을 수행하고, 결과를 boolean 값으로 return 받으세요.
        boolean result = productService.deleteProduct(parameter);
        
        //    (조건 2) delete가 정상적으로 수행된 경우, Print 객체를 통해 삭제 성공했다는 성공 메세지를 출력하세요.
        if (result) productPrint.printSuccessMessage("삭제 성공");
        
        //    (조건 3) delete가 정상적으로 수행되지 않은 경우, Print 객체를 통해 삭제 실패했다는 오류 메세지를 출력하세요.
        else productPrint.printErrorMessage("삭제 실패");
    }
}