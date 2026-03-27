package com.trading.journal.controller;

import com.trading.journal.model.TradeRequest;
import com.trading.journal.model.TradeResponseList;
import com.trading.journal.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "trade-journal-collection-api", description = "Trade Journal Collection API")
@Validated
@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Operation(summary = "Endpoint to fetch all trades", description = "This endpoint will be used to fetch all trade.", operationId = "trade-journal")
    @GetMapping
    public ResponseEntity<TradeResponseList> getAllTrades() {//add filters to fetch based on date/market
        TradeResponseList response = tradeService.getAllTrades();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint to add new trade record", description = "This endpoint will be used to add new trade.", operationId = "trade-journal")
    @PostMapping
    public ResponseEntity<String> addTrade(@Valid @RequestBody TradeRequest tradeRequest) {
        String response = tradeService.addTrade(tradeRequest);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint to update editable fields in record", description = "This endpoint will be used to modify specific trade record.", operationId = "trade-journal")
    @PutMapping
    public String updateTrade(@Validated @RequestBody TradeRequest tradeRequest) {
        return tradeService.updateTrade(tradeRequest);
    }

    @Operation(summary = "Endpoint to flush all trade records at once", description = "This endpoint will be used to delete all trade.", operationId = "trade-journal")
    @DeleteMapping
    public String ClearAllTrade() {
        return tradeService.ClearAllTrade();
    }

    @Operation(summary = "Endpoint to delete specific trade record", description = "This endpoint will be used to delete specific trade record.", operationId = "trade-journal")
    @DeleteMapping("/remove")
    public String removeTrade(@RequestBody TradeRequest request) {
        return tradeService.removeTrade(request);
    }

}
