package com.trading.journal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trading.journal.validation.annotation.ValidField;
import com.trading.journal.validation.annotation.ValidOption;
import com.trading.journal.validation.annotation.ValidTradeParams;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ValidTradeParams//verifies trade params
@ValidOption//verifies StrikePriceRange based on Symbol
public class TradeRequest {

    @Schema(description = "orderType", example = "MKT/AMO")
    @ValidField(field = "orderType")
    private String orderType;

    @Schema(description = "exchange", example = "NSE")
    @ValidField(field = "exchange")
    private String exchange;

    @Schema(description = "symbol", example = "NIFTY/SENSEX")
    @ValidField(field = "symbol")
    private String symbol;

    @Schema(description = "instrument", example = "Options/Commodity")
    @ValidField(field = "instrument")
    private String instrument;

    @Schema(description = "productType", example = "Intra trade/Normal")
    @ValidField(field = "productType")
    private String productType;

    @Schema(description = "strikePrice", example = "25000")
    private Integer strikePrice;

    @Schema(description = "optionType", example = "CE/PE")
    private String optionType;

    @Schema(description = "quantity", example = "20")
    @Min(1)
    private Integer quantity;

    @Schema(description = "Trade entry value", example = "36.50")
    @Min(1)
    private Double entryValue;

    @Schema(description = "Target exit value", example = "49.50")
    @Min(1)
    private Double exitValue;

    @Schema(description = "Actual Target value", example = "36.50")
    @Min(1)
    private Double targetValue;

    @Schema(description = "Stop Loss value", example = "25.00")
    @Min(1)
    private Double stopLossValue;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "trade date in DD/MM/YYYY format", example = "03/03/2026")
    private LocalDate tradeDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "expiry date in DD/MM/YYYY format", example = "12/12/2025")
    private LocalDate expiryDate;

    public String getTradeKey() {
        return strikePrice + "_" + optionType + "_" + String.valueOf(entryValue) + "_" + String.valueOf(exitValue);
    }

}
