package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchCriteria {
    private String search = ""; // Mặc định là chuỗi rỗng nếu không có giá trị
    private Integer page = 0; // Mặc định là trang đầu tiên (0)
    private Integer size = 10; // Mặc định là 10 phần tử mỗi trang
    private String sortBy = "id"; // Mặc định là sắp xếp theo id
    private String sortDir = "asc"; // Mặc định là sắp xếp theo chiều tăng dần (asc)
}
