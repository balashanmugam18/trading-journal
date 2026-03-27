package com.trading.journal.utility;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FieldMapping {

    public record StrikePriceRange(int min, int max) {
    }

    private final Map<String, List<String>> mapping = new HashMap<>();
    private final Map<String, StrikePriceRange> strikePriceMapping = new HashMap<>(); //using record to fix strikeRange

    public FieldMapping() {
        mapping.put("orderType", Arrays.asList("MKT", "AMO"));
        mapping.put("exchange", Arrays.asList("NSE", "BSE"));
        mapping.put("symbol", Arrays.asList("NIFTY", "SENSEX"));
        mapping.put("instrument", Arrays.asList("Options", "Commodity"));
        mapping.put("productType", Arrays.asList("Intra trade", "Normal"));
//        mapping.put("optionType", Arrays.asList("CE", "PE"));// added in optionValidator
        // NEW: Symbol → Strike ranges
        strikePriceMapping.put("NIFTY", new StrikePriceRange(21000, 50000));
        strikePriceMapping.put("SENSEX", new StrikePriceRange(71000, 90000));
    }

    public List<String> getAllowedList(String fieldName) {
        return mapping.getOrDefault(fieldName, Collections.emptyList());
    }


    public StrikePriceRange getAllowedStrikePrice(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) return null;
        return strikePriceMapping.get(symbol.toUpperCase());
    }

    public boolean validExchange(String symbol, String exchange) {
        if (symbol == null || exchange == null) {
            return false;
        }

        return
                switch (symbol.toUpperCase(Locale.ROOT)) {
                    case "NIFTY", "BANKNIFTY" -> "NSE".equalsIgnoreCase(exchange);
                    case "SENSEX" -> "BSE".equalsIgnoreCase(exchange);
                    default -> false;  // Unknown symbol
                };
    }

}
