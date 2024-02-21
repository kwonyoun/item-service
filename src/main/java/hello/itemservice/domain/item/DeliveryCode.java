package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FAST: 빠른배송
 * NORMAL: 일반배송
 * SLOW: 느린배송
 */

@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code; //FAST - 시스템 전달값
    private String displayName; //빠른배송 - 고객에게 보여주는 값
    
}
