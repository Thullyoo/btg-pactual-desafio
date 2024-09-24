package com.thullyoo.btg_pactual.controller;

import com.thullyoo.btg_pactual.controller.dto.ApiResponse;
import com.thullyoo.btg_pactual.controller.dto.OrderResponse;
import com.thullyoo.btg_pactual.controller.dto.PaginationResponse;
import com.thullyoo.btg_pactual.services.QueueService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    private final QueueService queueService;

    public OrderController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/client/{clientId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByClient(@PathVariable("clientId") Long clientId,
                                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
                                                                        {
        var pageResponse = queueService.findByClientId(clientId, PageRequest.of(page, pageSize));
        var totalOnOrders = queueService.findTotalForClientId(clientId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
